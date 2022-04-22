package entities;

import main.SimulPar;
import sharedRegions.*;

/**
 *   Chef thread.
 *
 *   It simulates the Chef life cycle.
 *   Static solution.
 */

public class Chef extends Thread
{
  /**
   *  Chef identification.
   */

   private int ChefId;

  /**
   *  Chef state.
   */

   private int ChefState;

  /**
   *  Reference to the Chef table.
   */

   /**
   *  Reference to the Chef bar.
   */

  private final Kitchen cKitchen;

  /**
   *   Instantiation of a Chef thread.
   *
   *     @param name thread name
   *     @param ChefId Chef id
   *     @param cKitchen reference to the Chef table
   */

   public Chef (String name, int ChefId, Kitchen cKitchen)
   {
      super (name);
      this.ChefId = ChefId;
      ChefState = ChefStates.WAITINGFORANORDER;
      this.cKitchen = cKitchen;
   }

  /**
   *   Set Chef id.
   *
   *     @param id Chef id
   */

   public void setChefId (int id)
   {
      ChefId = id;
   }

  /**
   *   Get Chef id.
   *
   *     @return Chef id
   */

   public int getChefId ()
   {
      return ChefId;
   }

  /**
   *   Set Chef state.
   *
   *     @param state new Chef state
   */

   public void setChefState (int state)
   {
      ChefState = state;
   }

  /**
   *   Get Chef state.
   *
   *     @return Chef state
   */

   public int getChefState ()
   {
      return ChefState;
   }

  /**
   *   Life cycle of the Chef.
   */

   @Override
   public void run ()
   {
      cKitchen.watchTheNews();
      cKitchen.startPreparation();
      for(int ai = 0 ; ai < SimulPar.C ; ai++){
         cKitchen.proceedToPresentation();
         for(int i = 0 ; i < SimulPar.N ; i++){
            cKitchen.alertTheWaiter();
            cKitchen.haveNextPortionReady();
         }
         if(ai != SimulPar.C-1) cKitchen.continuePreparation();
      }
      cKitchen.cleanUp();
   }

  /**
   *  Cutting the customer hair.
   *
   *  Internal operation.
   */

   

}
