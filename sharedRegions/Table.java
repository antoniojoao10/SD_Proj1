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
   *  Number students
   */

   private int waitingTable;

   /**
   * Student already choose Course
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

  private boolean hasBeenHonour;

  private int movement;

  private boolean signalTheWaiter;

  private boolean callTheWaiter;

  private boolean shouldHaveArrivedEarlier;

  private int firstStudent;
  private int lastStudent;
  private boolean exit;

  /**
   *  Reference to Student threads.
   */

   private final Student [] stu;

   private Waiter wai;


  /**
   *   Waiting seats occupation.
   */

   private MemFIFO<Integer> sitStudent;

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

   public synchronized void enter ()
   {
        int StudentId;

        StudentId = ((Student) Thread.currentThread ()).getStudentId ();
        stu[StudentId] = (Student) Thread.currentThread ();
        try {
            sitStudent.write(StudentId);
            order.write(StudentId);
        } catch (MemException e) { 
            e.printStackTrace();
        }   
        if( firstStudent == -1) { 
            firstStudent = StudentId; 
            stu[StudentId].setFirst();
        }
        if(sitStudent.full()){
            lastStudent = StudentId;
            stu[lastStudent].setlast();
        }
        stu[StudentId].setStudentState (StudentStates.TAKINGASEATATTHETABLE);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState()); 
        movement ++;
        waitingTable++; 
        stu[StudentId].enterBar();
        notifyAll ();   
            // Sleep while waiting for the menu 
        while ( !(stu[StudentId].getStudentMenu()) ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
        notifyAll ();                                        // the Student settles the account
   }

   public synchronized boolean checkingFlags ()
   {
        if(this.exit) return false;
       if(portions == SimulPar.P ) this.signalTheWaiter = false;
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
        
        
      return true;                                   // the Student settles the account
   }

   public synchronized void readTheMenu ()
   {
      int StudentId;
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.SELECTINGTHECOURSES);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      stu[StudentId].setRead();

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void joinTheTalk ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void startEating ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.ENJOYINGTHEMEAL);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());


      stu[StudentId].eating();

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void endEating ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      stu[StudentId].incrementStudentCourse();
      stu[StudentId].setHasPortion();

      this.portions++;
      repos.setNPortion(this.portions);

      notifyAll ();                                        // the Student settles the account
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
      notifyAll ();                                        // the Student settles the account
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
        notifyAll ();                                        // the Student settles the account
   }


   public synchronized void exit ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.GOINGHOME);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      this.waitingTable--;
      if(this.waitingTable == 0){
        this.exit = true;
      }
      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void prepareOrder ()
   {
      int StudentId;
      
      StudentId = ((Student) Thread.currentThread ()).getStudentId ();
      stu[StudentId].setStudentState (StudentStates.ORGANIZINGTHEORDER);
      repos.setstudentState(StudentId, stu[StudentId].getStudentState());

      this.decidedStudents ++;

      notifyAll ();                                        // the Student settles the account

      // Sleep while waiting for all students to decide
      while ( this.decidedStudents != SimulPar.N ){
         try {
             //System.out.println(this.decidedStudents);
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void callTheWaiter ()
   {

      this.callTheWaiter = true; 

      notifyAll ();                                        // the Student settles the account

      // Sleep while waiting for all students to decide
      while ( !(this.getThePad) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
      this.getThePad = false;
      notifyAll ();                                        // the Student settles the account
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

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void shouldHaveArrivedEarlier ()
   {
      stu[lastStudent].setStudentState (StudentStates.PAYINGTHEBILL);
      repos.setstudentState(lastStudent, stu[lastStudent].getStudentState());

      this.shouldHaveArrivedEarlier = true;

      notifyAll ();                                        // the Student settles the account


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
      notifyAll ();                                        // the Student settles the account
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
        notifyAll ();                                        // the Student settles the account
   }

   public synchronized void getThePad ()
   {
      this.getThePad = true;
      
     notifyAll ();                                        // the Student settles the account
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

     notifyAll ();                                        // the Student settles the account
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
        notifyAll ();                                        // the Student settles the account
    }
}
