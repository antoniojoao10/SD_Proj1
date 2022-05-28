package serverSide.sharedRegions;

import serverSide.main.*;
import clientSide.entities.*;
import genclass.GenericIO;
import genclass.TextFile;
import java.util.Objects;

/**
 *  General Repository.
 *
 *    It is responsible to keep the visible internal state of the problem and to provide means for it
 *    to be printed in the logging file.
 *    It is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are no internal synchronization points.
 */

public class GeneralRepos
{
  /**
   *  Name of the logging file.
   */

   private String logFileName;

  /**
   *  Number of iterations of the waiter life cycle.
   */

  /**
   *  State of the students.
   */

   private final int [] studentState;

  /**
   *  State of the waiters.
   */
 
   private  int  waiterState;

   /**
   *  State of the chef.
   */
   private  int  chefState;

   /**
   *  Number of the current course
   */
   private int NCourse;

   /**
   *  Number of portions eaten
   */
   private int NPortion;

   /**
   *  Seat to sit the next student to enter the restaurant
   */
   private int currentSit;

   /**
   *  Array with the seats of the students
   */
   private int [] studentSit;

   /**
   *   Number of entity groups requesting the shutdown.
   */

  private int nEntities;

   /**
   *  Number of iterations of the customer life cycle.
   */

  private int nIter;

  /**
   *   Instantiation of a general repository object.
   *
   *     @param logFileName name of the logging file
   */

   public GeneralRepos()
   {
      if ((logFileName == null) || Objects.equals (logFileName, ""))
         this.logFileName = "logger";
      studentState = new int [SimulPar.N];
      nIter = 0;
      for (int i = 0; i < SimulPar.N; i++)
        studentState[i] = StudentStates.GOINGTOTHERESTAURANT;
      waiterState = WaiterStates.APPRASINGSITUATION;
      chefState = ChefStates.WAITINGFORANORDER;
      this.NCourse = 0;
      this.NPortion = 0;
      this.currentSit = 0;
      studentSit = new int [SimulPar.N];
      for (int i = 0; i < SimulPar.N; i++)
        studentSit[i] = -1;
      nEntities = 0;
   }

   /**
   *   Operation initialization of simulation.
   *
   *   New operation.
   *
   *     @param logFileName name of the logging file
   *     @param nIter number of iterations of the customer life cycle
   */

  public synchronized void initSimul (String logFileName, int nIter)
  {
     if (!Objects.equals (logFileName, ""))
        this.logFileName = logFileName;
     this.nIter = nIter;
     reportInitialStatus ();
  }

 /**
  *   Operation server shutdown.
  *
  *   New operation.
  */

  public synchronized void shutdown ()
  {
      nEntities += 1;
      System.out.println(nEntities);
      if (nEntities >= SimulPar.EG)
         ServerTheRestaurantGeneralRepos.waitConnection = false;
  }

  /**
   *   Set student state.
   *
   *     @param id student id
   *     @param state student state
   */

   public synchronized void setstudentState (int id, int state)
   {
      studentState[id] = state;
      reportStatus ();
   }

  /**
   *   Set waiter state.
   *
   *     @param id waiter id
   *     @param state waiter state
   */

   public synchronized void setwaiterState (int id, int state)
   {
      waiterState = state;
      reportStatus ();
   }

   /**
   *   Set chef state.
   *
   *     @param id chef id
   *     @param state chef state
   */
   public synchronized void setchefState (int id, int state)
   {
      chefState = state;
      reportStatus ();
   }

   /**
   *   Update the numbers of portions eaten.
   *
   *     @param p portions
   */

   public synchronized void setNPortion (int p)
   {
      NPortion = p;
      reportStatus ();
   }

   /**
   *   Update the number of courses served
   *
   *     @param p courses
   */
   public synchronized void setNCourse (int p)
   {
      NCourse = p;
      reportStatus ();
   }

   /**
   *   Sit the student
   *
   *     @param StudentId student id
   */

   public synchronized void writeSit (int[] entry)
   {
      this.studentSit = entry;
   }

   /**
   *  Remove Student from his seat when exiting the restaurant
   *
   *     @param StudentId student id
   */
   public synchronized void removeSit (int[] entry)
   {
      this.studentSit = entry;
   }

  /**
   *  Write the header to the logging file.
   *
   *  The students are sleeping, the waiter and the ched are carrying out normal duties.
   *  Internal operation.
   */

   private void reportInitialStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      if (!log.openForWriting (".", logFileName))
         { GenericIO.writelnString ("The operation of creating the file " + logFileName + " failed!");
           System.exit (1);
         }
      log.writelnString ("                Problem of the Restaurant");
      //log.writelnString ("\nNumber of iterations = " + nIter + "\n");
      log.writelnString ("Chef\tWaiter\tStu0\tStu1\tStu2\tStu3\tStu4\tStu5\tStu6\tNCourse\tNPortion\t\t\t\t\t\t\tTable");
      log.writelnString ("State\tState\tState\tState\tState\tState\tState\tState\tState\t\t\t\t\t\tSeat0\tSeat1\tSeat2\tSeat3\tSeat4\tSeat5\tSeat6");
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
      reportStatus ();
   }

  /**
   *  Write a state line at the end of the logging file.
   *
   *  The current state of the students and the waiters is organized in a line to be printed.
   *  Internal operation.
   */

   private void reportStatus ()
   {
      TextFile log = new TextFile ();                      // instantiation of a text file handler

      String lineStatus = "";                              // state line to be printed

      if (!log.openForAppending (".", logFileName))
         { GenericIO.writelnString ("The operation of opening for appending the file " + logFileName + " failed!");
           System.exit (1);
         }

      switch (chefState)
        { case ChefStates.WAITINGFORANORDER:  lineStatus += "WAFOR\t";
                                             break;
          case ChefStates.PREPARINGACOURSE: lineStatus += "PRPCS\t";
                                             break;
          case ChefStates.DISHINGTHEPORTIONS:      lineStatus += "DSHPT\t";
                                             break;
          case ChefStates.DELIVERINGTHEPORTIONS:    lineStatus += "DLVPT\t";
                                             break;
         case ChefStates.CLOSINGSERVICE: lineStatus += "CLSSV\t";
                                             break;
        }

        switch (waiterState)
        { case WaiterStates.APPRASINGSITUATION:  lineStatus += "APPST\t";
                                             break;
          case WaiterStates.PRESENTINGTHEMENU: lineStatus += "PRSMN\t";
                                             break;
          case WaiterStates.TAKINGTHEORDER:      lineStatus += "TKODR\t";
                                             break;
          case WaiterStates.PLACINGTHEORDER:    lineStatus += "PCODR\t";
                                             break;
         case WaiterStates.WAITINGFORPORTION: lineStatus += "WTFPT\t";
                                             break;
          case WaiterStates.PROCESSINGTHEBILL:      lineStatus += "PRCBL\t";
                                             break;
          case WaiterStates.RECEIVINGPAYMENT:    lineStatus += "RECPM\t";
                                             break;
        }
      for (int i = 0; i < SimulPar.N; i++)
        switch (studentState[i])
        { case StudentStates.GOINGTOTHERESTAURANT:   lineStatus += "GGTRT\t";
                                        break;
          case StudentStates.TAKINGASEATATTHETABLE : lineStatus += "TKSTT\t";
                                        break;
         case StudentStates.SELECTINGTHECOURSES:   lineStatus += "SELCS\t";
                                        break;
          case StudentStates.ORGANIZINGTHEORDER : lineStatus += "OGODR\t";
                                        break;
         case StudentStates.CHATTINGWITHCOMPANIONS:   lineStatus += "CHTWC\t";
                                        break;
          case StudentStates.ENJOYINGTHEMEAL : lineStatus += "EJYML\t";
                                        break;
         case StudentStates.PAYINGTHEBILL:   lineStatus += "PYTBL\t";
                                        break;
          case StudentStates.GOINGHOME : lineStatus += "GGHOM\t";
                                        break;
        }
      
        
        lineStatus += "\t" + this.NCourse + "\t";
        lineStatus += "\t" + this.NPortion + "\t";

        for(int i=0; i < SimulPar.N ; i++){
         lineStatus += "\t" + this.studentSit[i] + "\t";
        }
        
      log.writelnString (lineStatus);
      if (!log.close ())
         { GenericIO.writelnString ("The operation of closing the file " + logFileName + " failed!");
           System.exit (1);
         }
   }
}
