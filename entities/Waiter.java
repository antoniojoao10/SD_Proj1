package entities;

import main.SimulPar;
import sharedRegions.*;

/**
 *   Waiter thread.
 *
 *   It simulates the Waiter life cycle.
 *   Static solution.
 */

public class Waiter extends Thread
{
  /**
   *  Waiter identification.
   */

   private int WaiterId;

  /**
   *  Waiter state.
   */

  private int WaiterState;
  private boolean alertTheWaiter;
  private int movement;
  private boolean callTheWaiter;
  private boolean signalTheWaiter;
  private boolean shouldHaveArrivedEarlier;


  /**
   *  Reference to the Waiter table.
   */

   private final Table wTable;

   /**
   *  Reference to the Waiter bar.
   */

  private final Bar wBar;

  private final Kitchen wKitchen;

  /**
   *   Instantiation of a Waiter thread.
   *
   *     @param name thread name
   *     @param WaiterId Waiter id
   *     @param wTable reference to the Waiter table
   *     @param wBar reference to the Waiter bar
   *     @param wKitchen reference to the Waiter kitchen
   */

   public Waiter (String name, int WaiterId, Table wTable, Bar wBar, Kitchen wKitchen)
   {
      super (name);
      this.WaiterId = WaiterId;
      this.alertTheWaiter = false;
      this.callTheWaiter = false;
      this.movement = 0;
      this.shouldHaveArrivedEarlier = false;
      this.signalTheWaiter = false;
      WaiterState = WaiterStates.APPRASINGSITUATION;
      this.wTable = wTable;
      this.wBar = wBar;
      this.wKitchen = wKitchen;
   }

  /**
   *   Set Waiter id.
   *
   *     @param id Waiter id
   */

   public void setWaiterId (int id)
   {
      WaiterId = id;
   }

  /**
   *   Get Waiter id.
   *
   *     @return Waiter id
   */

   public int getWaiterId ()
   {
      return WaiterId;
   }

  /**
   *   Set Waiter state.
   *
   *     @param state new Waiter state
   */

   public void setWaiterState (int state)
   {
      WaiterState = state;
   }

  /**
   *   Get Waiter state.
   *
   *     @return Waiter state
   */

   public int getWaiterState ()
   {
      return WaiterState;
   }

  /**
   *   Life cycle of the Waiter.
   */

  public void setalertTheWaiter ()
  {
     this.alertTheWaiter = !this.alertTheWaiter;
  }

  public boolean getalertTheWaiter ()
  {
     return this.alertTheWaiter;
  }

  public void setcallTheWaiter ()
  {
     this.callTheWaiter = !this.callTheWaiter;
  }

  public boolean getcallTheWaiter ()
  {
     return this.callTheWaiter;
  }

  public void setmovement (int n)
  {
     this.movement += n;
  }

  public void removemovement ()
  {
     this.movement --;
  }

  public int getmovement ()
  {
     return this.movement;
  }

  public void setshouldHaveArrivedEarlier ()
  {
     this.shouldHaveArrivedEarlier = !this.shouldHaveArrivedEarlier;
  }

  public boolean getshouldHaveArrivedEarlier ()
  {
     return this.shouldHaveArrivedEarlier;
  }

  public void setsignalTheWaiter ()
  {
     this.signalTheWaiter = !this.signalTheWaiter;
  }

  public boolean getsignalTheWaiter ()
  {
     return this.signalTheWaiter;
  }


   @Override
   public void run ()
   { 
      while( wTable.checkingFlags()){
         switch(wBar.lookAround()){
            case 1:
               wTable.saluteTheClient(); 
               break;
            case 2:
               break;
            case 3:
               wTable.getThePad();
               wKitchen.handTheNoteToTheChef();
               break;
            case 4:
               for(int i = 0 ; i < SimulPar.P ; i++){
                  wKitchen.collectPortion();
                  wTable.deliverPortion();
               }
               break;
            case 5:
               wBar.prepareTheBill();
               wTable.presentTheBill();
               break;

         }
         wBar.returnToTheBar();
      }
   }

   public void deliverPortion()
   {
      try
      { sleep ((long) (1 + 10 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }

}
