package sharedRegions;

import main.*;
import entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Table.
 *
 *    It is responsible to keep a continuously updated account of the Students inside the Table
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are two internal synchronization points: a single blocking point for the barbers, where they wait for a Student;
 *    and an array of blocking points, one per each Student, where he both waits his turn to cut the hair and sits on the
 *    cutting chair while having his hair cut.
 */

public class Table
{
  /**
   *  Number students in the restaurante
   */

   private int waitingTable;

   /**
   * Student already decided Course
   */

  private int decidedStudents;

  /**
   * Waiter will give the bill
   */

  private boolean presentBill;

  /**
   * Waiter will take the order
   */

  private boolean getThePad;

  /**
   * Portions
   */

  private int portions;

  /**
   * Flag - Last student to arrived has already honour the waiter
   */

  private boolean hasBeenHonour;

  /**
   * Number of students that entered the restaurant and havent been saluted
   */

  private int movement;

  /**
   * Flag - Student signals the waiter for more food/next portion
   */

  private boolean signalTheWaiter;

  /**
   * Falg - First Student calls the waiter to describe the order
   */

  private boolean callTheWaiter;

  /**
   * Flag - Signal the waiter that the last Student will pay the bill
   */

  private boolean shouldHaveArrivedEarlier;

  /**
   * First student id - The one that will describe the order to the waiter
   */

  private int firstStudent;

  /**
   * Last student it - The one the will pay the bill and honour the waiter
   */
  private int lastStudent;

  /**
   * Flag -  Signal the waiter thar every student leaved the restaurant
   */
  private boolean exit;

  /**
   *  Reference to Student threads.
   */

   private final Student [] stu;

   /**
   * Reference to Waiter thread
   */

   private Waiter wai;


  /**
   *   Order of students arrival.
   */

   private MemFIFO<Integer> sitStudent;

   /**
   * Temporary order of students arrival. Temp fifo just for the salute function 
   */

   private MemFIFO<Integer> order;

  /**
   *   Reference to the general repository.
   */

   private final GeneralRepos repos;

  /**
   *  Table instantiation.
   *
   *    @param repos reference to the general repository
   */

   public Table (GeneralRepos repos)
   {
      this.waitingTable = 0;
      this.decidedStudents = 0;
      this.portions = 0;
      this.presentBill = false;
      this.getThePad = false;
      this.hasBeenHonour = false;
      this.movement = 0;
      this.callTheWaiter = false; 
      this.signalTheWaiter = false; 
      this.shouldHaveArrivedEarlier = false;
      this.firstStudent = -1;
      this.lastStudent = -1;
      this.wai = null;
      this.exit = false;
      stu = new Student [SimulPar.N];
      for (int i = 0; i < SimulPar.N; i++)
        stu[i] = null;
      try
      { sitStudent = new MemFIFO<> (new Integer [SimulPar.N]);
      }
      catch (MemException e)
      { GenericIO.writelnString ("Instantiation of waiting FIFO failed: " + e.getMessage ());
        sitStudent = null;
        System.exit (1);
      }
      try
      { order = new MemFIFO<> (new Integer [SimulPar.N]);
      }
      catch (MemException e)
      { GenericIO.writelnString ("Instantiation of waiting FIFO failed: " + e.getMessage ());
        order = null;
        System.exit (1);
      }
      this.repos = repos;
   }

   /**
   * The student enters the restaurant.
   * Transitions the student state from "going to the restaurant" to "taking a sit at the table" 
   */

   public synchronized void enter ()
   {
        int StudentId;

        StudentId = ((Student) Thread.currentThread ()).getStudentId ();
        stu[StudentId] = (Student) Thread.currentThread ();
        //write in fifo
        try {
            sitStudent.write(StudentId);
            order.write(StudentId);
        } catch (MemException e) { 
            e.printStackTrace();
        }   
        // If it is the first student to arrive
        if( firstStudent == -1) { 
            firstStudent = StudentId; 
            stu[StudentId].setFirst();
        }
        // If it is the last student to arrive
        if(sitStudent.full()){
            lastStudent = StudentId;
            stu[lastStudent].setlast();
        }
        //set state
        stu[StudentId].setStudentState (StudentStates.TAKINGASEATATTHETABLE);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState()); 

        movement ++;
        waitingTable++; 
        stu[StudentId].enterBar();
        notifyAll ();   
        // Student will sleep while waiting for the menu 
        while ( !(stu[StudentId].getStudentMenu()) ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
        notifyAll ();    
   }

   /**
   * Flags function just to alter the variables in the waiter thread.
   * @return Will return True while there are students in the restaurant. And, when False will kill the waiter thread
   */

   public synchronized boolean checkingFlags ()
   {
        if(this.exit) return false;  // return false if all students left the restaurant
        if(portions == SimulPar.P ) this.signalTheWaiter = false;  // if all the students have finished all the portions, they cannot signal the waiter anymore

        if( this.movement > 0){
            ((Waiter) Thread.currentThread ()).setmovement(movement);
            this.movement = 0;
        }
        else if( this.shouldHaveArrivedEarlier){
            ((Waiter) Thread.currentThread ()).setshouldHaveArrivedEarlier();
            this.shouldHaveArrivedEarlier = false;
        }
        else if( this.callTheWaiter){
            ((Waiter) Thread.currentThread ()).setcallTheWaiter();
            this.callTheWaiter = false;
        }
        else if( this.signalTheWaiter){
            ((Waiter) Thread.currentThread ()).setsignalTheWaiter();
            this.signalTheWaiter = false;
        }
        notifyAll ();     
        
        
        return true;  
   }

   /**
   * The student reads the menu.
   * Transitions the student state from "taking a sit at the table" to "selecting the courses" 
   */

   public synchronized void readTheMenu ()
   {
        int StudentId;
        //set state
        StudentId = ((Student) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.SELECTINGTHECOURSES);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());

        //change the flag of the current student thread to inform that he/she already read the menu
        stu[StudentId].setRead();

        notifyAll ();                                       
   }

   /**
   * The first student joins the companions talk.
   * Transitions the student state from "organizing the order" to "chatting with companions" 
   */

   public synchronized void joinTheTalk ()
   {
      int StudentId;
      //set state
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      notifyAll ();                                        
   }

   /**
   * The student starts eating.
   * Transitions the student state from "chatting with companions" to "enjoying meal" 
   */

   public synchronized void startEating ()
   {
        int StudentId;
        //set state
        StudentId = ((Student) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.ENJOYINGTHEMEAL);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());

        //student be eating during a random time
        stu[StudentId].eating();

        notifyAll ();                                        
   }

   /**
   * The student finished the portion.
   * Transitions the student state from "enjoying meal" o "chatting with companions" 
   */
   public synchronized void endEating ()
   {
        int StudentId;
        //set state
        StudentId = ((Student) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());

        //increment the student course
        stu[StudentId].incrementStudentCourse();
        //Change the flag hasPortion in student, to inform that this student no longer has a portion
        stu[StudentId].setHasPortion();

        this.portions++; //  number of portions already served and eaten
        repos.setNPortion(this.portions);

        notifyAll ();                                        
   }

   public synchronized void informCompanion ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      this.decidedStudents++;
      notifyAll();

      while ( this.stu[firstStudent].getStudentState() != StudentStates.CHATTINGWITHCOMPANIONS ){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         }
      notifyAll ();                                        
   }

   public synchronized void hasEverybodyFinished ()
   {
        while ( !this.hasBeenHonour ){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        notifyAll ();                                        
   }


   public synchronized void exit ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.GOINGHOME);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      repos.removeSit(StudentId);

      this.waitingTable--;
      if(this.waitingTable == 0){
        this.exit = true;
      }
      notifyAll ();                                        
   }

   public synchronized void prepareOrder ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.ORGANIZINGTHEORDER);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      this.decidedStudents ++;

      notifyAll ();                                        

      // Sleep while waiting for all students to decide
      while ( this.decidedStudents != SimulPar.N ){
         try {
             //System.out.println(this.decidedStudents);
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

      notifyAll ();                                        
   }

   public synchronized void callTheWaiter ()
   {

      this.callTheWaiter = true; 

      notifyAll ();                                        

      // Sleep while waiting for all students to decide
      while ( !(this.getThePad) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
      this.getThePad = false;
      notifyAll ();                                        
   }

   public synchronized void signalTheWaiter ()
   {
      this.signalTheWaiter = true; 
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
   
      // Sleep while waiting for all students to decide
      while ( !stu[StudentId].getHasPortion()){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

      notifyAll ();                                        
   }

   public synchronized void shouldHaveArrivedEarlier ()
   {
      stu[lastStudent].setStudentState (StudentStates.PAYINGTHEBILL);
      repos.setstudentState(lastStudent, stu[lastStudent].getStudentState());

      this.shouldHaveArrivedEarlier = true;

      notifyAll ();                                        


      // Sleep while waiting for all students to decide
      while (!(this.presentBill) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

      stu[lastStudent].honourTheBill();
      this.hasBeenHonour = true;
      notifyAll ();                                        
   }

   public synchronized void saluteTheClient ()
   {

        int StudentId = 0;

        wai = ((Waiter) Thread.currentThread ());
        repos.setwaiterState( ((Waiter) Thread.currentThread ()).getWaiterId(), WaiterStates.PRESENTINGTHEMENU);

        try {
            StudentId = order.read();
        } catch (MemException e) { 
            e.printStackTrace();
        }

        repos.writeSit(StudentId);
        
        stu[StudentId].setStudentMenu();
        notifyAll();

        while( !stu[StudentId].getRead()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll ();                                        
   }

   public synchronized void getThePad ()
   {
      this.getThePad = true;
      
     notifyAll ();                                        
   }

   public synchronized void deliverPortion ()
   {
      
    int StudentId = 0;

    wai = ((Waiter) Thread.currentThread ());
    
    try {
        StudentId = sitStudent.read();
    } catch (MemException e) { 
        e.printStackTrace();
    }

    try {
        sitStudent.write(StudentId);
    } catch (MemException e) { 
        e.printStackTrace();
    }

    stu[StudentId].setHasPortion();

     notifyAll ();                                        
   }

   public synchronized void presentTheBill ()
   {
        this.presentBill = true;
        notifyAll();
        while( !this.hasBeenHonour){
            try {
                wait();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
         }
        ((Waiter) Thread.currentThread ()).setWaiterState(WaiterStates.RECEIVINGPAYMENT);
        repos.setwaiterState(wai.getWaiterId(), WaiterStates.RECEIVINGPAYMENT);
        notifyAll ();                                        
    }
}
