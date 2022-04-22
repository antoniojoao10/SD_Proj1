package entities;

import sharedRegions.*;

/**
 *   Student thread.
 *
 *   It simulates the Student life cycle.
 *   Static solution.
 */

public class Student extends Thread
{
  /**
   *  Student identification.
   */

   private int StudentId;

  /**
   *  Student state.
   */

   private int StudentState;

   /**
   * Flag - Student has the menu
   */

   private boolean StudentMenu;

   /**
   *  Number of course that the student is in
   */
   private int StudentCourse; 

   /**
   *  Flag - Student has portion
   */

   private boolean hasPortion;

   /**
   *  Flag - Is the first student to arrive
   */
   private boolean iamthefirst;

   /**
   *  Flag - Is the last student to arrive
   */
   private boolean iamthelast;

   /**
   *  Flag - Student already read the menu
   */
   private boolean read;

  /**
   *  Reference to the Student table.
   */

   private final Table sTable;

   /**
      *  Reference to the Student bar.
      */
   private final Bar sBar;


  /**
   *   Instantiation of a Student thread.
   *
   *     @param name thread name
   *     @param StudentId Student id
   *     @param sTable reference to the Student table
   *     @param sTable reference to the Student bar
   * 
   */

   public Student (String name, int StudentId, Table sTable, Bar sBar)
   {
      super (name);
      this.StudentId = StudentId;
      StudentState = StudentStates.GOINGTOTHERESTAURANT;
      this.sTable = sTable;
      this.sBar = sBar;
      this.StudentCourse = 0;
      this.StudentMenu = false;
      this.read = false;
      this.hasPortion = false;
      this.iamthefirst = false;
      this.iamthelast = false;
   }

  /**
   *   Set Student id.
   *
   *     @param id Student id
   */

   public void setStudentId (int id)
   {
      StudentId = id;
   }

   /**
   *   Change the flag StudentMenu
   */
   public void setStudentMenu ()
   {
      StudentMenu= !StudentMenu;
   }

  /**
   *   Get Student id.
   *
   *     @return Student id
   */

   public int getStudentId ()
   {
      return StudentId;
   }

  /**
   *   Set Student state.
   *
   *     @param state new Student state
   */

   public void setStudentState (int state)
   {
      StudentState = state;
   }

  /**
   *   Get Student state.
   *
   *     @return Student state
   */

   public int getStudentState ()
   {
      return StudentState;
   }

   /**
   *   Get flag Student menu.
   *
   *     @return Student state
   */

   public boolean getStudentMenu()
   {
      return StudentMenu;
   }

   /**
   *   Get Student course.
   *
   *     @return Student state
   */

   public int getStudentCourse()
   {
      return StudentCourse;
   }

    /**
      *   Increment Student course
      */
   public void incrementStudentCourse()
   {
      StudentCourse++;
   }

   /**
      *  Set this student as the first student
      */

   public void setFirst()
   {
      this.iamthefirst = true;
   }

   /**
      *   Set this student as the last student
      */

   public void setlast()
   {
      this.iamthelast = true;
   }

   /**
      *   Student enters the bar - Wake threads at the bar
      */

   public void enterBar(){
      sBar.enter();
   }

   /**
      *  Get flag hasPortion
      *  @return hasPortion
      */
   public boolean getHasPortion(){ return this.hasPortion; }
   /**
      *  Change flag hasPortion
      */
   public void setHasPortion(){ this.hasPortion = !this.hasPortion; }

   /**
      *  Get flag read
      *  @return read
      */
   public boolean getRead(){ return read;}

   /**
      *  Change flag read
      */
   public void setRead(){ this.read = !this.read; }

  /**
   *   Life cycle of the Student.
   */

   @Override
   public void run ()
   {
      walkabit();
      sTable.enter();
      sTable.readTheMenu();
      if(this.iamthefirst){
         sTable.prepareOrder();
         sTable.callTheWaiter();
         sTable.joinTheTalk();
      }
      else{
         sTable.informCompanion();
      }
      for(int i = 0 ; i < 3 ; i++){
         sTable.signalTheWaiter();
         sTable.startEating();
         sTable.endEating();
      }
      if(this.iamthelast){
         sTable.shouldHaveArrivedEarlier();
      }
      sTable.hasEverybodyFinished();
      sTable.exit();

   }

  public void eating()
  {
     try
     { sleep ((long) (1 + 50 * Math.random ()));
     }
     catch (InterruptedException e) {}
  }

   private void walkabit()
   {
      try
      { sleep ((long) (1 + 20 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }

   public void honourTheBill()
   {
      try
      { sleep ((long) (1 + 10 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }
}
