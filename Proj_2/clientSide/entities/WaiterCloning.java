package clientSide.entities;

/**
 *    Barber cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */

public interface WaiterCloning
{
  /**
   *   Set Waiter id.
   *
   *     @param id Waiter id
   */

  public void setWaiterId (int id);

 /**
  *   Get Waiter id.
  *
  *     @return Waiter id
  */

  public int getWaiterId ();

 /**
  *   Set Waiter state.
  *
  *     @param state new Waiter state
  */

  public void setWaiterState (int state);

 /**
  *   Get Waiter state.
  *
  *     @return Waiter state
  */

  public int getWaiterState ();

  /**
  *   Change the flag alert the waiter
  */

  public void setalertTheWaiter ();
  /**
  *   Get the flag alert the waiter
  */

  public boolean getalertTheWaiter ();

  /**
  *   Change the flag call the waiter
  */

  public void setcallTheWaiter ();

  /**
  *   Get the flag call the waiter
  */

  public boolean getcallTheWaiter ();

  /**
  *   Icrement the movement
  *  @param n number of movements
  */
  public void setmovement (int n);

  /**
  *   Decrement the movement
  *  @param n number of movements
  */

  public void removemovement ();

  /**
  *   Get the movements
  */

  public int getmovement ();
  /**
  *   Change the flag shouldHaveArrivedEarlier
  */

  public void setshouldHaveArrivedEarlier ();

  /**
  *   Get the flag shouldHaveArrivedEarlier
  */

  public boolean getshouldHaveArrivedEarlier ();

  /**
  *   Change the flag signal the waiter
  */

  public void setsignalTheWaiter ();

  /**
  *   Get the flag signal the waiter
  */

  public boolean getsignalTheWaiter ();
}
