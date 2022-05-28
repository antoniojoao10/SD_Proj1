package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the Barber Shop.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Barber Shop and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class BarInterface
{
  /**
   *  Reference to the barber shop.
   */

   private final Bar bar;

  /**
   *  Instantiation of an interface to the barber shop.
   *
   *    @param bar reference to the barber shop
   */

   public BarInterface (Bar bar)
   {
      this.bar = bar;
   }

  /**
   *  Processing of the incoming messages.
   *
   *  Validation, execution of the corresponding method and generation of the outgoing message.
   *
   *    @param inMessage service request
   *    @return service reply
   *    @throws MessageException if the incoming message is not valid
   */

   public Message processAndReply (Message inMessage) throws MessageException
   {
      Message outMessage = null;                                     // outgoing message

     /* validation of the incoming message */

      /*switch (inMessage.getMsgType ())
      { case MessageType.REQCUTH:  if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= SimulPar.N))
                                      throw new MessageException ("Invalid Waiter id!", inMessage);
                                      else if ((inMessage.getCustState () < WaiterStates.DAYBYDAYLIFE) || (inMessage.getCustState () > WaiterStates.CUTTHEHAIR))
                                              throw new MessageException ("Invalid Waiter state!", inMessage);
                                   break;
        case MessageType.SLEEP:    if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid barber id!", inMessage);
                                   break;
        case MessageType.CALLCUST: if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid barber id!", inMessage);
                                      else if ((inMessage.getBarbState () < BarberStates.SLEEPING) || (inMessage.getBarbState () > BarberStates.INACTIVITY))
                                              throw new MessageException ("Invalid barber state!", inMessage);
                                   break;
        case MessageType.RECPAY:   if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid barber id!", inMessage);
                                      else if ((inMessage.getBarbState () < BarberStates.SLEEPING) || (inMessage.getBarbState () > BarberStates.INACTIVITY))
                                              throw new MessageException ("Invalid barber state!", inMessage);
                                              else if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= SimulPar.N))
                                                      throw new MessageException ("Invalid Waiter id!", inMessage);
                                   break;
        case MessageType.ENDOP:    if ((inMessage.getBarbId () < 0) || (inMessage.getBarbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid barber id!", inMessage);
                                   break;
        case MessageType.SHUT:     // check nothing
                                   break;
        default:                   throw new MessageException ("Invalid message type!", inMessage);
      }*/

     /* processing */
     System.out.println("type" + inMessage.getMsgType ());
      switch (inMessage.getMsgType ())

      { 
         case MessageType.returnBarREQ:  ((BarClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    bar.returnToTheBar();
                                    outMessage = new Message (MessageType.returnBarDONE,
                                   ((BarClientProxy) Thread.currentThread ()).getWaiterId (),
                                   ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                      
                                   break;
         case MessageType.prepareTheBillREQ:  ((BarClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((BarClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    bar.prepareTheBill();
                                    outMessage = new Message (MessageType.prepareTheBillDONE,
                                   ((BarClientProxy) Thread.currentThread ()).getWaiterId (),
                                   ((BarClientProxy) Thread.currentThread ()).getWaiterState ());
                                      
                                   break;
         case MessageType.enterBarREQ:  ((BarClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                   ((BarClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                   bar.prepareTheBill();
                                   outMessage = new Message (MessageType.enterBarDONE,
                                  ((BarClientProxy) Thread.currentThread ()).getStudentId (),
                                  ((BarClientProxy) Thread.currentThread ()).getStudentState ());
                                     
                                  break;
        case MessageType.ENDOPW:    bar.endOperationW(inMessage.getWaiId());
                                   outMessage = new Message (MessageType.EOPDONEW, inMessage.getWaiId ());
                                   break;
         case MessageType.ENDOPS:    bar.endOperationS(inMessage.getStuId());
                                   outMessage = new Message (MessageType.EOPDONES, inMessage.getStuId ());
                                   break;
        case MessageType.SHUT:     bar.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
}
