package clientSide.entities;

/**
 *    Definition of the internal states of the barber during his life cycle.
 */

public final class WaiterStates
{
  /**
   *   The barber is resting while waiting for a customer.
   */

   public static final int PRESENTINGTHEMENU = 0;

  /**
   *   The barber is cutting some customer hair.
   */

   public static final int TAKINGTHEORDER = 1;

   public static final int APPRASINGSITUATION = 2;

   public static final int PLACINGTHEORDER = 3;

   public static final int RECEIVINGPAYMENT = 4;

   public static final int PROCESSINGTHEBILL = 5;

   public static final int WAITINGFORPORTION = 6;


  /**
   *   It can not be instantiated.
   */

   private WaiterStates
 ()
   { }
}
