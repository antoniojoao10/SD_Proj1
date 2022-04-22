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

   private boolean StudentMenu; // read the menu

   private int StudentCourse; //course the student is in

   private boolean hasPortion;

   private boolean iamthefirst;
   private boolean iamthelast;
   private boolean read;

  /**
   *  Reference to the Student table.
   */

   private final Table sTable;

   private final Bar sBar;

   /**
   *  Reference to the Student bar.
   */

  /**
   *   Instantiation of a Student thread.
   *
   *     @param name thread name
   *     @param StudentId Student id
   *     @param sTable reference to the Student table
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

   public boolean getStudentMenu()
   {
      return StudentMenu;
   }

   public int getStudentCourse()
   {
      return StudentCourse;
   }

   public void incrementStudentCourse()
   {
      StudentCourse++;
   }

   public void setFirst()
   {
      this.iamthefirst = true;
   }

   public void setlast()
   {
      this.iamthelast = true;
   }

   public void enterBar(){
      sBar.enter();
   }

   public boolean getHasPortion(){ return this.hasPortion; }
   public void setHasPortion(){ this.hasPortion = !this.hasPortion; }

   public boolean getRead(){ return read;}
   public void setRead(){ this.read = !this.read; }




  /**
   *   Life cycle of the Student.
   */

   @Override
   public void run ()
   {
      /*
      int studentID;                                      // customer id
      boolean endOp;                                       // flag signaling end of operations

      while (true)
      { endOp = sBar.walkabit();                        // the Student sleeps while waiting for other friends
        if (endOp) break;                                  // check for end of operations
        studentID = sTable.callACustomer ();               // the Student has waken up and calls next customer
        cutHair ();                                        // the Student cuts the customer hair
        sTable.receivePayment (studentID);                 // the Student finishes his service and receives payment for it
      }
      */

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

  /**
   *  Cutting the customer hair.
   *
   *  Internal operation.
   */

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
