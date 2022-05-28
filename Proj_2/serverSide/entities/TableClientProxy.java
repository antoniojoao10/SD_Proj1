package serverSide.entities;

import serverSide.sharedRegions.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Service provider agent for access to the Table.
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class TableClientProxy extends Thread implements WaiterCloning, StudentCloning
{
  /**
   *  Number of instantiayed threads.
   */

   private static int nProxy = 0;

  /**
   *  Communication channel.
   */

   private ServerCom sconi;

  /**
   *  Interface to the Table.
   */

   private TableInterface bShopInter;

  /**
   *  Waiter identification.
   */

   private int WaiterId;

  /**
   *  Waiter state.
   */

   private int WaiterState;

  /**
   *  Student identification.
   */

   private int StudentId;

  /**
   *  Student state.
   */

   private int StudentState;

   /**
   *  Chef identification.
   */

   private int ChefId;

  /**
   *  Chef state.
   */

   private int ChefState;

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
   * Flag - Student has the menu
   */

  private boolean StudentMenu;

  /**
  *  Number of course that the student is in
  */
  private int StudentCourse; 

  /**
  *  Flag - Student has portion
  */

  private boolean hasPortion;

  /**
  *  Flag - Is the first student to arrive
  */
  private boolean iamthefirst;

  /**
  *  Flag - Is the last student to arrive
  */
  private boolean iamthelast;

  /**
  *  Flag - Student already read the menu
  */
  private boolean read;


  /**
   *  Instantiation of a client proxy.
   *
   *     @param sconi communication channel
   *     @param bShopInter interface to the Table
   */

   public TableClientProxy (ServerCom sconi, TableInterface bShopInter)
   {
      super ("TableProxy_" + TableClientProxy.getProxyId ());
      this.sconi = sconi;
      this.bShopInter = bShopInter;
   }

  /**
   *  Generation of the instantiation identifier.
   *
   *     @return instantiation identifier
   */

   private static int getProxyId ()
   {
      Class<?> cl = null;                                            // representation of the TableClientProxy object in JVM
      int proxyId;                                                   // instantiation identifier

      try
      { cl = Class.forName ("serverSide.entities.TableClientProxy");
      }
      catch (ClassNotFoundException e)
      { GenericIO.writelnString ("Data type TableClientProxy was not found!");
        e.printStackTrace ();
        System.exit (1);
      }
      synchronized (cl)
      { proxyId = nProxy;
        nProxy += 1;
      }
      return proxyId;
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
   *   Set Student id.
   *
   *     @param id Student id
   */

   public void setStudentId (int id)
   {
      StudentId = id;
   }

  /**
   *   Get Student id.
   *
   *     @return Student id
   */

   public int getStudentId ()
   {
      return StudentId;
   }

  /**
   *   Set Student state.
   *
   *     @param state new Student state
   */

   public void setStudentState (int state)
   {
      StudentState = state;
   }

  /**
   *   Get Student state.
   *
   *     @return Student state
   */

   public int getStudentState ()
   {
      return StudentState;
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
   *   Get flag Student menu.
   *
   *     @return Student state
   */

  public boolean getStudentMenu()
  {
     return StudentMenu;
  }

  /**
  *   Get Student course.
  *
  *     @return Student state
  */

  public int getStudentCourse()
  {
     return StudentCourse;
  }

   /**
     *   Increment Student course
     */
  public void incrementStudentCourse()
  {
     StudentCourse++;
  }

  /**
     *  Set this student as the first student
     */

  public void setFirst()
  {
     this.iamthefirst = true;
  }

  /**
     *   Set this student as the last student
     */

  public void setlast()
  {
     this.iamthelast = true;
  }

  /**
     *   Student enters the bar - Wake threads at the bar
     */

  public void enterBar(){
     //sBar.enter();
  }

  /**
     *  Get flag hasPortion
     *  @return hasPortion
     */
  public boolean getHasPortion(){ return this.hasPortion; }
  /**
     *  Change flag hasPortion
     */
  public void setHasPortion(){ this.hasPortion = !this.hasPortion; }

  /**
     *  Get flag read
     *  @return read
     */
  public boolean getRead(){ return read;}

  /**
     *  Change flag read
     */
  public void setRead(){ this.read = !this.read; }

  /**
   *   Change the flag StudentMenu
   */
  public void setStudentMenu ()
  {
     StudentMenu= !StudentMenu;
  }

  public void eating()
  {
     try
     { sleep ((long) (1 + 50 * Math.random ()));
     }
     catch (InterruptedException e) {}
  }

   private void walkabit()
   {
      try
      { sleep ((long) (1 + 20 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }

   public void honourTheBill()
   {
      try
      { sleep ((long) (1 + 10 * Math.random ()));
      }
      catch (InterruptedException e) {}
   }


  /**
   *  Life cycle of the service provider agent.
   */

   @Override
   public void run ()
   {
      Message inMessage = null,                                      // service request
              outMessage = null;                                     // service reply

     /* service providing */

      inMessage = (Message) sconi.readObject ();                     // get service request
      try
      { outMessage = bShopInter.processAndReply (inMessage);         // process it
      }
      catch (MessageException e)
      { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
        GenericIO.writelnString (e.getMessageVal ().toString ());
        System.exit (1);
      }
      sconi.writeObject (outMessage);                                // send service reply
      sconi.close ();                                                // close the communication channel
   }
}
