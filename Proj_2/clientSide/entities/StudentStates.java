package clientSide.entities;

/**
 *    Definition of the internal states of the barber during his life cycle.
 */

public final class StudentStates
{
  /**
   *   The barber is resting while waiting for a customer.
   */

   public static final int GOINGTOTHERESTAURANT = 0;

  /**
   *   The barber is cutting some customer hair.
   */

   public static final int TAKINGASEATATTHETABLE = 1;

   public static final int SELECTINGTHECOURSES = 2;

   public static final int ENJOYINGTHEMEAL = 3;

   public static final int CHATTINGWITHCOMPANIONS = 4;

   public static final int ORGANIZINGTHEORDER = 5;

   public static final int GOINGHOME = 6;

   public static final int PAYINGTHEBILL = 7;

  /**
   *   It can not be instantiated.
   */

   private StudentStates
 ()
   { }
}
