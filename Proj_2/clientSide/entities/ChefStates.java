package clientSide.entities;

/**
 *    Definition of the internal states of the barber during his life cycle.
 */

public final class ChefStates
{
  /**
   *   The barber is resting while waiting for a customer.
   */

   public static final int WAITINGFORANORDER = 0;

  /**
   *   The barber is cutting some customer hair.
   */

   public static final int PREPARINGACOURSE = 1;

   public static final int CLOSINGSERVICE = 2;

   public static final int DELIVERINGTHEPORTIONS = 3;

   public static final int DISHINGTHEPORTIONS = 4;


  /**
   *   It can not be instantiated.
   */

   private ChefStates
 ()
   { }
}
