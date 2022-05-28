package sharedRegions;


import entities.*;

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
   *  Flag - order given fromt the waiter to the chef
   */
   private boolean noteGiven;

   /**
   *  Flag - The food preparation has started
   */
   private boolean preparationStarted;

   /**
   *  Flag - protion is ready
   */
   private boolean ready;

   /**
   *  Number of courses served
   */
   private int course;

   /**
   *   Reference to the general repository.
   */

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

   /**
   * The waiter gives the note with the order to the chef.
   * Transitions the waiter state from "taking the order" to "placing the order" 
   */

   public synchronized void handTheNoteToTheChef ()   
   {
      int WaiterID;
      // Set state
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.PLACINGTHEORDER);

      this.noteGiven = true; // the note has be given to the chef

      notifyAll ();                                        

      //Sleep while waiting for the course to start being prepared
      while (!(this.preparationStarted) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      this.preparationStarted = false; //Turn off the flag

      notifyAll ();                                        
   }

   /**
   * The waiter collects the portion.
   * Transitions the waiter state from "apprassing the situation" to "collect the portion" 
   */

   public synchronized void collectPortion ()   
   {
      int WaiterID;
      //Set the state
      WaiterID = ((Waiter) Thread.currentThread ()).getWaiterId ();
      repos.setwaiterState(WaiterID, WaiterStates.WAITINGFORPORTION);

      //Sleep while waiting for the portion to be ready
      while (!(this.ready) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }
      
      this.ready = false; // Turn off the flag
      notifyAll ();                                        
   }

   /**
   * The Chef watchs the news while waiting for the order.
   */
   public synchronized void watchTheNews ()   
   {
      //Sleeps while the note has not be given
      while (!(this.noteGiven) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      this.noteGiven = false; // turn off the flag

      notifyAll ();                                        
   }

   /**
   * The Chef starts preparing the course
   * Transitions the chef state from "waiting for an ordr" to "preparing a course" 
   */

   public synchronized void startPreparation ()   
   {
      int ChefID;
      //Set state
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.PREPARINGACOURSE);

      this.preparationStarted = true; //The chef starts preparing the food

      course ++; // First course 
      repos.setNCourse(course);//Update the number of courses

      notifyAll ();                                        
   }

   /**
   * The chef continues preparing the courses
   * Transitions the chef state from "delivering the portions" to "preparing a course" 
   */

   public synchronized void continuePreparation ()   
   {
      int ChefID;
      //Set state
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.PREPARINGACOURSE);

      course ++; //Start another course
      repos.setNCourse(course); //Update the number of courses

      notifyAll ();                                        
   }

   /**
   * The chef will dish the protions
   * Transitions the chef state from "preparing a course" to "dishing the portions" 
   */

   public synchronized void proceedToPresentation ()   
   {
      int ChefID;
      //Set state
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DISHINGTHEPORTIONS);

      this.ready = true; // the portion is ready

      notifyAll ();                                        
   }

   /**
   * The chef continue the portion dishing
   * Transitions the chef state from "delivering the portions" to "dishing the portions" 
   */
   public synchronized void haveNextPortionReady ()   
   {
      int ChefID;
      //Set State
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DISHINGTHEPORTIONS);

      this.ready = true; // The portion is ready

      notifyAll ();                                        
   }

   /**
   * The chef will close service
   * Transitions the chef state from "delivering the portions" to "closing service" 
   */

   public synchronized void cleanUp ()   
   {
      int ChefID;
      //Set state
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.CLOSINGSERVICE);

      notifyAll ();                                        
   }

   /**
   * The chef alerts the waiter that the portion is ready
   * Transitions the chef state from "dishing the portions" to "delivering the portions"
   */
   public synchronized void alertTheWaiter ()   
   {
      int ChefID;
      //Set state
      ChefID = ((Chef) Thread.currentThread ()).getChefId();
      repos.setchefState(ChefID, ChefStates.DELIVERINGTHEPORTIONS);
      

      //Sleep while waiting for the waiter to take the order
      while ((this.ready) ){
         try {
             wait();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
      }

      notifyAll ();                                        
   }


   
}


