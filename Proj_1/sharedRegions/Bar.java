package sharedRegions;

import entities.*;

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

   private final GeneralRepos repos;


  /**
   *  Bar instantiation.
   *
   *    @param repos reference to the general repository
   */

   public Bar (GeneralRepos repos)
   {
      this.repos = repos;
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
      while ( ((Waiter) Thread.currentThread ()).getmovement()==0 && !((Waiter) Thread.currentThread ()).getalertTheWaiter() && !((Waiter) Thread.currentThread ()).getcallTheWaiter() && !((Waiter) Thread.currentThread ()).getshouldHaveArrivedEarlier() && !((Waiter) Thread.currentThread ()).getsignalTheWaiter()){
         try {             
            wait();
            return res;
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if( ((Waiter) Thread.currentThread ()).getmovement() > 0 ){
         ((Waiter) Thread.currentThread ()).removemovement(); 
         res = 1;
      }/*
      else if( ((Waiter) Thread.currentThread ()).getalertTheWaiter() ){
         ((Waiter) Thread.currentThread ()).setWaiterState(WaiterStates.PROCESSINGTHEBILL);
         repos.setwaiterState( ((Waiter) Thread.currentThread ()).getWaiterId(), WaiterStates.PROCESSINGTHEBILL);
         ((Waiter) Thread.currentThread ()).setalertTheWaiter(); 
         res = 2;
      }*/
      else if( ((Waiter) Thread.currentThread ()).getcallTheWaiter() ){
         ((Waiter) Thread.currentThread ()).setWaiterState(WaiterStates.TAKINGTHEORDER);
         repos.setwaiterState( ((Waiter) Thread.currentThread ()).getWaiterId(), WaiterStates.TAKINGTHEORDER);
         ((Waiter) Thread.currentThread ()).setcallTheWaiter(); 
         res = 3;
      }
      else if( ((Waiter) Thread.currentThread ()).getsignalTheWaiter() ){
         ((Waiter) Thread.currentThread ()).setsignalTheWaiter(); 
         res = 4;
      }
      else if( ((Waiter) Thread.currentThread ()).getshouldHaveArrivedEarlier() ){
         ((Waiter) Thread.currentThread ()).setWaiterState(WaiterStates.PROCESSINGTHEBILL);
         repos.setwaiterState( ((Waiter) Thread.currentThread ()).getWaiterId(), WaiterStates.PROCESSINGTHEBILL);
         ((Waiter) Thread.currentThread ()).setshouldHaveArrivedEarlier(); 
         res = 5;
      }
      
      //System.out.println("waiter " + res);
      notifyAll ();                
      return res;
   }

   /**
   * The waiter returns tho the bar.
   * Transitions the waiter state from any state to "apprasing the situation" 
   */

   public synchronized void returnToTheBar ()   
   {
      int WaiterID;
      //set State
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
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
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.PROCESSINGTHEBILL);

      notifyAll ();                                        
   }

   /**
   * The student wakes all threads at the Bar to signal the he had entered the restaurant
   */
   public synchronized void wakeUp ()   
   {
      notifyAll ();                                        
   }

}


