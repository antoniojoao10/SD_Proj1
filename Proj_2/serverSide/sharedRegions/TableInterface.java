package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the Tableber Shop.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    Tableber Shop and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class TableInterface
{
  /**
   *  Reference to the Tableber shop.
   */

   private final Table Table;

  /**
   *  Instantiation of an interface to the Tableber shop.
   *
   *    @param Table reference to the Tableber shop
   */

   public TableInterface (Table Table)
   {
      this.Table = Table;
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
        case MessageType.SLEEP:    if ((inMessage.getTablebId () < 0) || (inMessage.getTablebId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Tableber id!", inMessage);
                                   break;
        case MessageType.CALLCUST: if ((inMessage.getTablebId () < 0) || (inMessage.getTablebId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Tableber id!", inMessage);
                                      else if ((inMessage.getTablebState () < TableberStates.SLEEPING) || (inMessage.getTablebState () > TableberStates.INACTIVITY))
                                              throw new MessageException ("Invalid Tableber state!", inMessage);
                                   break;
        case MessageType.RECPAY:   if ((inMessage.getTablebId () < 0) || (inMessage.getTablebId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Tableber id!", inMessage);
                                      else if ((inMessage.getTablebState () < TableberStates.SLEEPING) || (inMessage.getTablebState () > TableberStates.INACTIVITY))
                                              throw new MessageException ("Invalid Tableber state!", inMessage);
                                              else if ((inMessage.getCustId () < 0) || (inMessage.getCustId () >= SimulPar.N))
                                                      throw new MessageException ("Invalid Waiter id!", inMessage);
                                   break;
        case MessageType.ENDOP:    if ((inMessage.getTablebId () < 0) || (inMessage.getTablebId () >= SimulPar.M))
                                      throw new MessageException ("Invalid Tableber id!", inMessage);
                                   break;
        case MessageType.SHUT:     // check nothing
                                   break;
        default:                   throw new MessageException ("Invalid message type!", inMessage);
      }*/

     /* processing */

      System.out.println("type" + inMessage.getMsgType ());
      switch (inMessage.getMsgType ())

      { case MessageType.enterTableREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                   ((TableClientProxy) Thread.currentThread ()).setStudentState(inMessage.getStuId ());
                                   
                                   outMessage = new Message (MessageType.enterTableDONE,
                                                             ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                                                             ((TableClientProxy) Thread.currentThread ()).getStudentState (), Table.enter());
                                    break;
         case MessageType.lookArREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    
                                    outMessage = new Message (MessageType.lookArDONE,
                                                              ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                                              ((TableClientProxy) Thread.currentThread ()).getWaiterState (), Table.lookAround());
                                     break;
         case MessageType.saluteTheClientREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    Table.saluteTheClient();
                                       outMessage = new Message (MessageType.saluteTheClientDONE,
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                    
                                   break;
           case MessageType.getThePadREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    Table.getThePad();
                                       outMessage = new Message (MessageType.getThePadDONE,
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                    
                                   break;
         case MessageType.presentTheBillREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                   ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                   Table.presentTheBill();
                                      outMessage = new Message (MessageType.presentTheBillDONE,
                                      ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                      ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                   
                                  break;
           case MessageType.deliverPortionREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                    Table.deliverPortion();
                                       outMessage = new Message (MessageType.deliverPortionDONE,
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterState ());
                                    
                                   break;
         case MessageType.checkingFlagsREQ:  ((TableClientProxy) Thread.currentThread ()).setWaiterId(inMessage.getWaiId());
                                    ((TableClientProxy) Thread.currentThread ()).setWaiterState(inMessage.getWaiId ());
                                       outMessage = new Message (MessageType.checkingFlagsDONE,
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterId (),
                                       ((TableClientProxy) Thread.currentThread ()).getWaiterState (), Table.checkingFlags());
                                   break;
         case MessageType.readTheMenuREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                   ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                   Table.readTheMenu();
                                   outMessage = new Message (MessageType.readTheMenuDONE,
                                  ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                                  ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                     
                                  break;
         case MessageType.joinTheTalkREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                  Table.joinTheTalk();
                                  outMessage = new Message (MessageType.joinTheTalkDONE,
                                 ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                                 ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                    
                                 break;
         case MessageType.startEatingREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                 ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                 Table.startEating();
                                 outMessage = new Message (MessageType.startEatingDONE,
                                ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                                ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                   
                                break;
         case MessageType.endEatingREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                Table.endEating();
                                outMessage = new Message (MessageType.endEatingDONE,
                               ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                               ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                  
                               break;
         case MessageType.informCompanionREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                Table.informCompanion();
                                outMessage = new Message (MessageType.informCompanionDONE,
                               ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                               ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                  
                               break;
         case MessageType.hasEverybodyFinishedREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                                ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                                Table.hasEverybodyFinished();
                                outMessage = new Message (MessageType.hasEverybodyFinishedDONE,
                               ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                               ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                  
                               break;
         case MessageType.exitREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                               ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                               Table.exit();
                               outMessage = new Message (MessageType.exitDONE,
                              ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                              ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                 
                              break;
         case MessageType.prepareOrderREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                              ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                              Table.prepareOrder();
                              outMessage = new Message (MessageType.prepareOrderDONE,
                             ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                             ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                                
                             break;
         case MessageType.callTheWaiterREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                             ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                             Table.callTheWaiter();
                             outMessage = new Message (MessageType.callTheWaiterDONE,
                            ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                            ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                               
                            break;
         case MessageType.signalTheWaiterREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                             ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                             Table.signalTheWaiter();
                             outMessage = new Message (MessageType.signalTheWaiterDONE,
                            ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                            ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                               
                            break;
         case MessageType.shouldHaveArrivedEarlierREQ:  ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId());
                             ((TableClientProxy) Thread.currentThread ()).setStudentId(inMessage.getStuId ());
                             Table.shouldHaveArrivedEarlier();
                             outMessage = new Message (MessageType.shouldHaveArrivedEarlierDONE,
                            ((TableClientProxy) Thread.currentThread ()).getStudentId (),
                            ((TableClientProxy) Thread.currentThread ()).getStudentState ());
                               
                            break;
        case MessageType.ENDOPW:    Table.endOperationW(inMessage.getWaiId());
                                   outMessage = new Message (MessageType.EOPDONEW, inMessage.getWaiId ());
                                   break;
         case MessageType.ENDOPS:    Table.endOperationS(inMessage.getStuId());
                                   outMessage = new Message (MessageType.EOPDONES, inMessage.getStuId ());
                                   break;
        case MessageType.SHUT:     Table.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
}
