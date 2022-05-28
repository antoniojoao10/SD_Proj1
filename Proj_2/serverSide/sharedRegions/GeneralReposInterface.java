package serverSide.sharedRegions;

import serverSide.main.*;
import serverSide.entities.*;
import clientSide.entities.*;
import commInfra.*;

/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralReposInterface
{
  /**
   *  Reference to the general repository.
   */

   private final GeneralRepos repos;

  /**
   *  Instantiation of an interface to the general repository.
   *
   *    @param repos reference to the general repository
   */

   public GeneralReposInterface (GeneralRepos repos)
   {
      this.repos = repos;
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
      Message outMessage = null;                                     // mensagem de resposta


     /* processing */

      switch (inMessage.getMsgType ())

      { case MessageType.SETNFIC:  repos.initSimul (inMessage.getLogFName (), inMessage.getNIter ());
                                   outMessage = new Message (MessageType.NFICDONE);
                                   break;
        case MessageType.setStudentREQ:    repos.setstudentState (inMessage.getStuId(), inMessage.getStuState());
                                   outMessage = new Message (MessageType.SACK);
                                   break;
        case MessageType.setChefREQ:    repos.setchefState (inMessage.getChefId(), inMessage.getChefState());
                                   outMessage = new Message (MessageType.SACK);
                                   break;
        case MessageType.setWaiterREQ:    repos.setwaiterState (inMessage.getWaiId(), inMessage.getWaiState());
                                   outMessage = new Message (MessageType.SACK);
                                   break;
        case MessageType.setNPortionREQ:    repos.setNPortion(inMessage.getNPortion());
                                   outMessage = new Message (MessageType.setNPortionDONE);
                                   break;
        case MessageType.setNCourseREQ:     repos.setNCourse(inMessage.getNCourse());
                                   outMessage = new Message (MessageType.setNCourseDONE);
                                   break;
        case MessageType.writeSitREQ:    repos.writeSit(inMessage.getSits());
                                   outMessage = new Message (MessageType.writeSitDONE);
                                   break;
        case MessageType.removeSitREQ:    repos.removeSit(inMessage.getSits());
                                   outMessage = new Message (MessageType.removeSitDONE);
                                   break;
        case MessageType.SHUT:     repos.shutdown ();
                                   outMessage = new Message (MessageType.SHUTDONE);
                                   break;
      }

     return (outMessage);
   }
}
