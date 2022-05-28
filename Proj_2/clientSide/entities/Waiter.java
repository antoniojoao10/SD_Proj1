package clientSide.entities;

import clientSide.stubs.BarStub;
import clientSide.stubs.KitchenStub;
import clientSide.stubs.TableStub;
import serverSide.main.SimulPar;
import serverSide.sharedRegions.*;

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

   /**
   *  Flag - The waiter has been alerted
   */
   private boolean alertTheWaiter;

   /**
   *  Number of movement that the waiter needs to salute
   */
   private int movement;

   /**
   *  Flag - The waiter has been called to take the order
   */
   private boolean callTheWaiter;

   /**
   *  The waiter has been called to give the next portion
   */
   private boolean signalTheWaiter;

   /**
   *  The waiter has been called to give the bill to the last student
   */
   private boolean shouldHaveArrivedEarlier;


  /**
   *  Reference to the Waiter table.
   */

   private final TableStub wTable;

   /**
   *  Reference to the Waiter bar.
   */

   private final BarStub wBar;

   /**
   *  Reference to the Waiter kitchen.
   */

   private final KitchenStub wKitchen;

  /**
   *   Instantiation of a Waiter thread.
   *
   *     @param name thread name
   *     @param WaiterId Waiter id
   *     @param wTable reference to the Waiter table
   *     @param wBar reference to the Waiter bar
   *     @param wKitchen reference to the Waiter kitchen
   */

   public Waiter (String name, int WaiterId, TableStub wTable, BarStub wBar, KitchenStub wKitchen)
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
   *   Change the flag alert the waiter
   */

   public void setalertTheWaiter ()
   {
      this.alertTheWaiter = !this.alertTheWaiter;
   }

   /**
   *   Get the flag alert the waiter
   */

   public boolean getalertTheWaiter ()
   {
      return this.alertTheWaiter;
   }

   /**
   *   Change the flag call the waiter
   */

   public void setcallTheWaiter ()
   {
      this.callTheWaiter = !this.callTheWaiter;
   }

   /**
   *   Get the flag call the waiter
   */

   public boolean getcallTheWaiter ()
   {
      return this.callTheWaiter;
   }

   /**
   *   Icrement the movement
   *  @param n number of movements
   */
   public void setmovement (int n)
   {
      this.movement += n;
   }

   /**
   *   Decrement the movement
   *  @param n number of movements
   */

   public void removemovement ()
   {
      this.movement --;
   }

   /**
   *   Get the movements
   */

   public int getmovement ()
   {
      return this.movement;
   }

   /**
   *   Change the flag shouldHaveArrivedEarlier
   */

   public void setshouldHaveArrivedEarlier ()
   {
      this.shouldHaveArrivedEarlier = !this.shouldHaveArrivedEarlier;
   }

   /**
   *   Get the flag shouldHaveArrivedEarlier
   */

   public boolean getshouldHaveArrivedEarlier ()
   {
      return this.shouldHaveArrivedEarlier;
   }

   /**
   *   Change the flag signal the waiter
   */

   public void setsignalTheWaiter ()
   {
      this.signalTheWaiter = !this.signalTheWaiter;
   }

   /**
   *   Get the flag signal the waiter
   */

   public boolean getsignalTheWaiter ()
   {
      return this.signalTheWaiter;
   }

   /**
   *   Life cycle of the Waiter.
   */

   @Override
   public void run ()
   { 
      while( wTable.checkingFlags()){
         switch(wTable.lookAround()){
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
               for(int i = 0 ; i < SimulPar.N ; i++){
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
