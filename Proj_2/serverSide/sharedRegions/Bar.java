package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import clientSide.stubs.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Bar.
 *
 *    It is responsible to keep a continuously updated account of the Students inside the Bar
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are two internal synchronization points: a single blocking point for the waiters, where they wait for a Student;
 *    and an array of blocking points, one per each Student, where he both waits his turn to cut the hair and sits on the
 *    cutting chair while having his hair cut.
 */

public class Bar
{
  /**
   *   Reference to the general repository.
   */

   private final GeneralReposStub repos;

   /**
   *  Reference to waiter threads.
   */

  private final BarClientProxy [] waiter;

  /**
   *  Reference to student threads.
   */

   private final BarClientProxy student;

   /**
   *   Number of entity groups requesting the shutdown.
   */

  private int nEntities;

  /**
   *  Bar instantiation.
   *
   *    @param repos reference to the general repository
   */

   public Bar (GeneralReposStub repos)
   {
      this.repos = repos;
      waiter = new BarClientProxy[SimulPar.W];
      for (int i = 0; i < SimulPar.W; i++)
         waiter[i] = null;
      student = null;
      nEntities = 0;
   }


   /**
   * The waiter returns tho the bar.
   * Transitions the waiter state from any state to "apprasing the situation" 
   */

   public synchronized void returnToTheBar ()   
   {
      int WaiterID;
      //set State
      WaiterID = ((BarClientProxy) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.APPRASINGSITUATION);

      notifyAll ();                                        
   }

   /**
   * The waiter prepares the bill.
   * Transitions the waiter state from "apprassing the situation" to "processing the bill" 
   */
   public synchronized void prepareTheBill ()   
   {
      int WaiterID;
      //Set state
      WaiterID = ((BarClientProxy) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.PROCESSINGTHEBILL);

      notifyAll ();                                        
   }

   /**
   * The student wakes all threads at the Bar to signal the he had entered the restaurant
   */
   public synchronized void enter ()   
   {
      notifyAll ();                                        
   }

   /**
   *  Operation end of work waiter
   *
   *   New operation.
   *
   *      @param id  id
   */

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
   *  Operation end of work student
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
     if (student != null)
     student.interrupt ();
  }


   /**
    *   Operation server shutdown.
   *
   *   New operation.
   */

   public synchronized void shutdown ()
   {
         nEntities += 1;
         if (nEntities >= SimulPar.EB)
            ServerTheRestaurantBar.waitConnection = false;
         notifyAll ();                                        // the barber may now terminate
   }
}


