package sharedRegions;

import main.*;
import entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Kitchen.
 *
 *    It is responsible to keep a continuously updated account of the Students inside the Kitchen
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 *    There are two internal synchronization points: a single blocking point for the Kitchenbers, where they wait for a Student;
 *    and an array of blocking points, one per each Student, where he both waits his turn to cut the hair and sits on the
 *    cutting chair while having his hair cut.
 */

public class Kitchen
{
  /**
   *  Number of hair cuts which have been requested and not serviced yet.
   */

   private boolean noteGiven;
   private boolean preparationStarted;
   private boolean ready;
   private int course;
   private final GeneralRepos repos;

  /**
   *  Kitchen instantiation.
   *
   *    @param repos reference to the general repository
   */

   public Kitchen (GeneralRepos repos)
   {
      this.noteGiven = false;
      this.preparationStarted = false;
      this.ready = false;
      this.course = 0;
      this.repos = repos;
   }

   public synchronized void handTheNoteToTheChef ()   
   {
      int WaiterID;
      
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.PLACINGTHEORDER);

      this.noteGiven = true;

      notifyAll ();                                        // the Student settles the account


      while (!(this.preparationStarted) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      this.preparationStarted = false;

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void collectPortion ()   
   {
      int WaiterID;
      
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.WAITINGFORPORTION);

      while (!(this.ready) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }
      
      this.ready = false;
      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void watchTheNews ()   
   {
      while (!(this.noteGiven) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      this.noteGiven = false;

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void startPreparation ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.PREPARINGACOURSE);

      this.preparationStarted = true;

      course ++;
      repos.setNCourse(course);

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void continuePreparation ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.PREPARINGACOURSE);

      course ++;
      repos.setNCourse(course);

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void proceedToPresentation ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DISHINGTHEPORTIONS);

      this.ready = true;

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void haveNextPortionReady ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DISHINGTHEPORTIONS);

      this.ready = true;

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void cleanUp ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.CLOSINGSERVICE);

      notifyAll ();                                        // the Student settles the account
   }

   public synchronized void alertTheWaiter ()   
   {
      int ChefID;
      
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DELIVERINGTHEPORTIONS);

      System.out.println("Chef ready");

      while ((this.ready) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      notifyAll ();                                        // the Student settles the account
   }


   
}


