package main;

import entities.*;
import sharedRegions.*;


/**
 *   Simulation of the Problem The Restaurant.
 */

public class TheRestaurant
{
  /**
   *    Main method.
   *
   *    @param args runtime arguments
   */

   public static void main (String [] args)
   {
    Chef chef;												//Reference to the Chef Thread
    Waiter waiter;											//Reference to the Waiter Thread
    Student[] student = new Student[SimulPar.N];    		//array of references to the Students Threads
    Kitchen kitchen;							//Reference to the kitchen
    Bar bar;												//Reference to the Bar
    Table table;						//Reference to the table
    GeneralRepos repos;                             			//Reference to the General Repository

    System.out.println("The Restaurant");
    /* problem initialization */
    repos = new GeneralRepos("logger");
    kitchen = new Kitchen(repos);
    bar = new Bar(repos);
    table = new Table(repos);

    chef = new Chef("Chef_1", 0, kitchen);
    waiter = new Waiter("Waiter_1", 0, table, bar, kitchen);
    for (int i = 0; i < SimulPar.N; i++){
      student[i] = new Student("Student_"+(i+1), i, table,bar);
    }
    
    /* start of the simulation */
    chef.start();
    waiter.start();
    for (int i = 0; i < SimulPar.N; i++)
      student[i].start ();

    /* waiting for the end of the simulation */
    for (int i = 0; i < SimulPar.N; i++)
    { try
    { student[i].join ();
    }
    catch (InterruptedException e) {}
    System.out.println("The Student "+(i+1)+" just terminated");
    }
    
    try {
      chef.join();
    } catch (InterruptedException e) {}
      System.out.println("The Chef has terminated");
      
      try {
        waiter.join();	
      } catch (InterruptedException e) {}
        System.out.println("The Waiter has terminated");
      

      System.out.println("End of the Simulation");
    }
}
