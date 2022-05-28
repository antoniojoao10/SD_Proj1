package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.*;
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

   private final TableClientProxy [] stu;

   /**
   * Reference to Waiter thread
   */

   private TableClientProxy wai;


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

   private final GeneralReposStub repos;

   /**
   *  Reference to waiter threads.
   */

  private final TableClientProxy [] waiter;

  /**
   *  Reference to waiter threads.
   */

  private final TableClientProxy [] student;

   /**
   *   Number of entity groups requesting the shutdown.
   */

  private int nEntities;

  /**
   *  Table instantiation.
   *
   *    @param repos reference to the general repository
   */

   public Table (GeneralReposStub repos)
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
      stu = new TableClientProxy[SimulPar.N];
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

      waiter = new TableClientProxy[SimulPar.W];
      for (int i = 0; i < SimulPar.W; i++)
         waiter[i] = null;
      student = new TableClientProxy[SimulPar.N];
      for (int i = 0; i < SimulPar.N; i++)
            student[i] = null;
      nEntities = 0;
      this.repos = repos;
   }

   /**
   * The student enters the restaurant.
   * Transitions the student state from "going to the restaurant" to "taking a sit at the table" 
   */

   public synchronized int enter ()
   {
        int StudentId;

        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
        stu[StudentId] = (TableClientProxy) Thread.currentThread ();
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
        if( StudentId == firstStudent) return 1;
        if( StudentId == lastStudent) return -1;
        else return 0;

   }

   /**
   * Flags function just to alter the variables in the waiter thread.
   * @return Will return True while there are students in the restaurant. And, when False will kill the waiter thread
   */

   public synchronized boolean checkingFlags ()
   {
        if(this.exit) return false;  // return false if all students left the restaurant
        if(portions == SimulPar.P ) this.signalTheWaiter = false;  // if all the students have finished all the portions, they cannot signal the waiter anymore
        System.out.println(portions);
        notifyAll ();     
        
        
        return true;  
   }

   /**
   * The waiter is waken up by one of the following operations: alertTheWaiter of the chef, enter and exit of all the students, callTheWaiter of
         the first student to sit at the table, signalTheWaiter of the last student to
         finish a course and shouldHaveArrivedEarlier of the last student to sit
         at the table; transition occurs when the last student has left the
         restaurant
         @return 1 --> There were movement in the restaurant
          2 --> %%%%%%%%%
          3 --> The first student is ready to describe the order
          4 --> The chef has finished preparing the course
          5 --> The last student signal the he is ready to pay the bill
   */
  public synchronized int lookAround ()   
  {
     int res = 0;
     while (this.movement==0 && !this.callTheWaiter && !this.shouldHaveArrivedEarlier && !this.signalTheWaiter){
        try {
           wait();
           return res;
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
     }while (this.portions % 7 != 0){
        try {
           wait();
           return res;
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
     }
     if(portions == SimulPar.P ) this.signalTheWaiter = false;
     if( this.movement > 0 ){
        this.movement--; 
        res = 1;
     }
     else if( ((TableClientProxy) Thread.currentThread ()).getalertTheWaiter() ){
        ((TableClientProxy) Thread.currentThread ()).setWaiterState(WaiterStates.PROCESSINGTHEBILL);
        repos.setwaiterState( ((TableClientProxy) Thread.currentThread ()).getWaiterId(), WaiterStates.PROCESSINGTHEBILL);
        ((TableClientProxy) Thread.currentThread ()).setalertTheWaiter(); 
        res = 2;
     }
     else if( this.callTheWaiter ){
        ((TableClientProxy) Thread.currentThread ()).setWaiterState(WaiterStates.TAKINGTHEORDER);
        repos.setwaiterState( ((TableClientProxy) Thread.currentThread ()).getWaiterId(), WaiterStates.TAKINGTHEORDER);
        this.callTheWaiter = false;
        res = 3;
     }
     else if( this.signalTheWaiter ){
        this.signalTheWaiter = false;
        res = 4;
     }
     else if( this.shouldHaveArrivedEarlier ){
        ((TableClientProxy) Thread.currentThread ()).setWaiterState(WaiterStates.PROCESSINGTHEBILL);
        repos.setwaiterState( ((TableClientProxy) Thread.currentThread ()).getWaiterId(), WaiterStates.PROCESSINGTHEBILL);
        this.shouldHaveArrivedEarlier = false;
        res = 5;
     }
     
     System.out.println("waiter " + res);
     notifyAll ();                
     return res;
  }

   /**
   * The student reads the menu.
   * Transitions the student state from "taking a sit at the table" to "selecting the courses" 
   */

   public synchronized void readTheMenu ()
   {
        int StudentId;
        //set state
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
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
      StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
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
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.ENJOYINGTHEMEAL);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());

        //student be eating during a random time
        stu[StudentId].eating();

        notifyAll ();                                        
   }

   /**
   * The student finished the portion.
   * Transitions the student state from "enjoying meal" to "chatting with companions" 
   */
   public synchronized void endEating ()
   {
        int StudentId;
        //set state
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
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

   /**
   * The student informs the first student that it has decided his course.
   * Transitions the student state from "selecting the courses" to "chatting with companions" 
   */

   public synchronized void informCompanion ()
   {
        int StudentId;
        //set state
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.CHATTINGWITHCOMPANIONS);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());
        
        //Increment the number of students that alrready decided their courses
        this.decidedStudents++;
        notifyAll();

        //wait until the first student joins the "chatting with companions" state
        while ( this.stu[firstStudent].getStudentState() != StudentStates.CHATTINGWITHCOMPANIONS ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll ();                                        
   }

   /**
   * Students wait until everyone has finished their meals
   */

   public synchronized void hasEverybodyFinished ()
   {
       //wait until the last student has honnour the waiter
        while ( !this.hasBeenHonour ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll ();                                        
   }

   /**
   * The student finished the portion.
   * Transitions the student state from "chatting with companions" o "going home" 
   */

   public synchronized void exit ()
   {
        int StudentId;
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
            
        repos.removeSit(StudentId); // remove student from his sit at the table

        this.waitingTable--; // Decrement number of students in the restaurant
        //wait for all student to leave the table
        if(this.waitingTable == 0){
            this.exit = true;
        }

        notifyAll();
        while (!this.exit && this.stu[lastStudent].getStudentState() != StudentStates.GOINGHOME){
            try {
               wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }

        //set state
        stu[StudentId].setStudentState (StudentStates.GOINGHOME);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());
        notifyAll ();                                        
   }

    /**
     * The first student prepares the order.
     * Transitions the student state from "selecting the courses" to "organazing the order" 
     */
    public synchronized void prepareOrder ()
    {
        int StudentId;
        //set state
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
        stu[StudentId].setStudentState (StudentStates.ORGANIZINGTHEORDER);
        repos.setstudentState(StudentId, stu[StudentId].getStudentState());

        this.decidedStudents ++; //the first student has already decided his own course

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

    /**
    * The first student calls the waiter to inform of the students order.
    */

    public synchronized void callTheWaiter ()
    {

        this.callTheWaiter = true; //Flag that the waiter has been called

        notifyAll ();                                        

        // Sleep while the waiter hasnt given the pad yet
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

    /**
     * The student signals the waiter for the next protion
     */
    public synchronized void signalTheWaiter ()
    {
        this.signalTheWaiter = true; // Flag to signla the waiter that wants another portion
        int StudentId;
        
        StudentId = ((TableClientProxy) Thread.currentThread ()).getStudentId ();
    
        // Sleep while the waiter doenst deliver his portion
        while ( !stu[StudentId].getHasPortion()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        notifyAll ();                                        
    }

    /**
     * The last student pays the bill.
     * Transitions the student state from "chatting with companions" to "paying the bill" 
     */
    public synchronized void shouldHaveArrivedEarlier ()
    {   
        while (this.portions % 7 != 0){
            try {
               wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
        //set state
        stu[lastStudent].setStudentState (StudentStates.PAYINGTHEBILL);
        repos.setstudentState(lastStudent, stu[lastStudent].getStudentState());

        this.shouldHaveArrivedEarlier = true; // flag that the last student is ready to pay the bill

        notifyAll ();                                        


        // Sleep while the waiter hasnt given the bill to the last student
        while (!(this.presentBill) ){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stu[lastStudent].honourTheBill(); // honour the waiter
        this.hasBeenHonour = true; // Flag that the waiter has been honoured
        notifyAll ();                                        
    }

    /**
   * The waiter will salute the students that entered the restaurant.
   * Transitions the waiter state from "apprassing the situation" to "presenting the menu" 
   */

    public synchronized void saluteTheClient ()
    {
        int StudentId = 0;
        //set state
        wai = ((TableClientProxy) Thread.currentThread ());
        repos.setwaiterState( ((TableClientProxy) Thread.currentThread ()).getWaiterId(), WaiterStates.PRESENTINGTHEMENU);

        try {
            //reads the student by the order of arrival
            StudentId = order.read();
        } catch (MemException e) { 
            e.printStackTrace();
        }

        repos.writeSit(StudentId); //sits the student in the respective seat at the table
        
        stu[StudentId].setStudentMenu(); // Gives the student his menu

        notifyAll();

        // Sleeps while waiting for the student to read the menu
        while( !stu[StudentId].getRead()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll ();                                        
    }

    /**
   * Change the getThePad flag.
   */
    public synchronized void getThePad ()
    {
        this.getThePad = true;
        notifyAll ();                                        
    }

    
    /**
   * The waiter delivers the portion.
   */
    public synchronized void deliverPortion ()
    {
        int StudentId = 0;

        wai = ((TableClientProxy) Thread.currentThread ());
        
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

        stu[StudentId].setHasPortion(); //Gives the student his portion

        notifyAll ();                                        
    }

    /**
   * The waiter presents the bill to the last student.
   * Transitions the waiter state from "processing the bill" to "receiving the bill" 
   */
    public synchronized void presentTheBill ()
    {
        this.presentBill = true; //Flags that the waiter has given the last student the bill
        notifyAll();

        //Sleeps while waiting for the last student to honour him
        while( !this.hasBeenHonour){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //set State
        ((TableClientProxy) Thread.currentThread ()).setWaiterState(WaiterStates.RECEIVINGPAYMENT);
        repos.setwaiterState(wai.getWaiterId(), WaiterStates.RECEIVINGPAYMENT);
        stu[lastStudent].setStudentState (StudentStates.GOINGHOME);
        this.stu[lastStudent].setStudentState(StudentStates.GOINGHOME);
        notifyAll ();                                        
    }

    public synchronized void endOperationW(int id)
  {
     while (nEntities == 0)
     { /* the barber waits for the termination of the customers */
        try
        { wait ();
        }
        catch (InterruptedException e) {}
     }
     if (waiter[id] != null)
     waiter[id].interrupt ();
  }

  /**
  *  Operation end of work waiter
  *
  *   New operation.
  *
  *      @param id  id
  */

 public synchronized void endOperationS(int id)
 {
    while (nEntities == 0)
    { /* the barber waits for the termination of the customers */
       try
       { wait ();
       }
       catch (InterruptedException e) {}
    }
    if (student[id] != null)
    student[id].interrupt ();
 }


  /**
   *   Operation server shutdown.
  *
  *   New operation.
  */

  public synchronized void shutdown ()
  {
        nEntities += 1;
        if (nEntities >= SimulPar.ET)
           ServerTheRestaurantTable.waitConnection = false;
        notifyAll ();                                        // the barber may now terminate
  }
}
