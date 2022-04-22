package sharedRegions;

import main.*;
import entities.*;
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
      }
      else if( ((Waiter) Thread.currentThread ()).getalertTheWaiter() ){
         ((Waiter) Thread.currentThread ()).setWaiterState(WaiterStates.PROCESSINGTHEBILL);
         repos.setwaiterState( ((Waiter) Thread.currentThread ()).getWaiterId(), WaiterStates.PROCESSINGTHEBILL);
        ((Waiter) Thread.currentThread ()).setalertTheWaiter(); 
        res = 2;
      }
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
      
      System.out.println("waiter " + res);
      notifyAll ();                                        // the Student settles the account
      return res;
   }

   public synchronized void returnToTheBar ()   
   {
      int WaiterID;
      
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.APPRASINGSITUATION);

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void prepareTheBill ()   
   {
      int WaiterID;
      
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.PROCESSINGTHEBILL);

      notifyAll ();                                        // the Student settles the account
   }


   public synchronized void enter ()   
   {
      notifyAll ();                                        // the Student settles the account
   }

}


