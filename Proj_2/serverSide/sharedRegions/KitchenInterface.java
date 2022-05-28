package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the Kitchenber Shop.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Kitchenber Shop and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class KitchenInterface
{
  /**
   *  Reference to the Kitchenber shop.
   */

   private final Kitchen Kitchen;

  /**
   *  Instantiation of an interface to the Kitchenber shop.
   *
   *    @param Kitchen reference to the Kitchenber shop
   */

   public KitchenInterface (Kitchen Kitchen)
   {
      this.Kitchen = Kitchen;
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
        case MessageType.SLEEP:    if ((inMessage.getKitchenbId () < 0) || (inMessage.getKitchenbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Kitchenber id!", inMessage);
                                   break;
        case MessageType.CALLCUST: if ((inMessage.getKitchenbId () < 0) || (inMessage.getKitchenbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Kitchenber id!", inMessage);
                                      else if ((inMessage.getKitchenbState () < KitchenberStates.SLEEPING) || (inMessage.getKitchenbState () > KitchenberStates.INACTIVITY))
                                              throw new MessageException ("Invalid Kitchenber state!", inMessage);
                                   break;
        case MessageType.RECPAY:   if ((inMessage.getKitchenbId () < 0) || (inMessage.getKitchenbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Kitchenber id!", inMessage);
                                      else if ((inMessage.getKitchenbState () < KitchenberStates.SLEEPING) || (inMessage.getKitchenbState () > KitchenberStates.INACTIVITY))
                                              throw new MessageException ("Invalid Kitchenber state!", inMessage);
                                              else if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= SimulPar.N))
                                                      throw new MessageException ("Invalid Waiter id!", inMessage);
                                   break;
        case MessageType.ENDOP:    if ((inMessage.getKitchenbId () < 0) || (inMessage.getKitchenbId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Kitchenber id!", inMessage);
                                   break;
        case MessageType.SHUT:     // check nothing
                                   break;
        default:                   throw new MessageException ("Invalid message type!", inMessage);
      }*/

     /* processing */
     System.out.println("type" + inMessage.getMsgType ());
      switch (inMessage.getMsgType ())

      { case MessageType.handTheNoteToTheChefREQ:  ((KitchenClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                   ((KitchenClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                   Kitchen.handTheNoteToTheChef();
                                   outMessage = new Message (MessageType.handTheNoteToTheChefDONE,
                                                             ((KitchenClientProxy) Thread.currentThread ()).getWaiterId (),
                                                             ((KitchenClientProxy) Thread.currentThread ()).getWaiterState ());
                                    break;
         case MessageType.collectPortionREQ:  ((KitchenClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((KitchenClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    Kitchen.collectPortion();
                                    outMessage = new Message (MessageType.collectPortionDONE,
                                                              ((KitchenClientProxy) Thread.currentThread ()).getWaiterId (),
                                                              ((KitchenClientProxy) Thread.currentThread ()).getWaiterState ());
                                     break;
         case MessageType.watchTheNewsREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                    ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                    Kitchen.watchTheNews();
                                    outMessage = new Message (MessageType.watchTheNewsDONE,
                                                              ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                              ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                     break;
         case MessageType.startPreparationREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                     ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                     Kitchen.startPreparation();
                                     outMessage = new Message (MessageType.startPreparationDONE,
                                                               ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                               ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                      break;
         case MessageType.continuePreparationREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                     ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                     Kitchen.continuePreparation();
                                     outMessage = new Message (MessageType.continuePreparationDONE,
                                                               ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                               ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                      break;
        case MessageType.proceedToPresentationREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                      ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                      Kitchen.proceedToPresentation();
                                      outMessage = new Message (MessageType.proceedToPresentationDONE,
                                                                ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                       break;
        case MessageType.haveNextPortionReadyREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                      ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                      Kitchen.haveNextPortionReady();
                                      outMessage = new Message (MessageType.haveNextPortionReadyDONE,
                                                                ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                                ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                       break;
         case MessageType.cleanUpREQ:  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                       ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                       Kitchen.cleanUp();
                                       outMessage = new Message (MessageType.cleanUpDONE,
                                                                 ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                                 ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                        break;
         case MessageType.alertTheWaiterREQ :  ((KitchenClientProxy) Thread.currentThread ()).setChefId(inMessage.getChefId());
                                        ((KitchenClientProxy) Thread.currentThread ()).setChefState(inMessage.getChefId ());
                                        Kitchen.alertTheWaiter();
                                        outMessage = new Message (MessageType.alertTheWaiterDONE,
                                                                  ((KitchenClientProxy) Thread.currentThread ()).getChefId (),
                                                                  ((KitchenClientProxy) Thread.currentThread ()).getChefState ());
                                         break;
        case MessageType.ENDOPW:    Kitchen.endOperationW(inMessage.getWaiId());
                                   outMessage = new Message (MessageType.EOPDONEW, inMessage.getWaiId ());
                                   break;
         case MessageType.ENDOPC:    Kitchen.endOperationC(inMessage.getChefId());
                                   outMessage = new Message (MessageType.EOPDONEC, inMessage.getChefId ());
                                   break;
        case MessageType.SHUT:     Kitchen.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
}
