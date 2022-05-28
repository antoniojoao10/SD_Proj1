package clientSide.entities;

/**
 *    Barber cloning.
 *
 *      It specifies his own attributes.
 *      Implementation of a client-server model of type 2 (server replication).
 *      Communication is based on a communication channel under the TCP protocol.
 */

public interface ChefCloning
{
 /**
   *   Set Chef id.
   *
   *     @param id Chef id
   */

  public void setChefId (int id);

 /**
  *   Get Chef id.
  *
  *     @return Chef id
  */

  public int getChefId ();

 /**
  *   Set Chef state.
  *
  *     @param state new Chef state
  */

  public void setChefState (int state);

 /**
  *   Get Chef state.
  *
  *     @return Chef state
  */

  public int getChefState ();
}
