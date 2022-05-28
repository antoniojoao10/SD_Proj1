package clientSide.stubs;

import serverSide.main.*;
import clientSide.entities.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *  Stub to the barber shop.
 *
 *    It instantiates a remote reference to the barber shop.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class BarStub
{
  /**
   *  Name of the platform where is located the barber shop server.
   */

   private String serverHostName;

  /**
   *  Port number for listening to service requests.
   */

   private int serverPortNumb;

  /**
   *   Instantiation of a stub to the barber shop.
   *
   *     @param serverHostName name of the platform where is located the barber shop server
   *     @param serverPortNumb port number for listening to service requests
   */

   public BarStub (String serverHostName, int serverPortNumb)
   {
      this.serverHostName = serverHostName;
      this.serverPortNumb = serverPortNumb;
   }

  /**
   *  Operation lookArround.
   *
   * Waiter check all flags
   */

   public int lookAround ()
   {
      ClientCom com;                                                 // communication channel
      Message outMessage,                                            // outgoing message
              inMessage;                                             // incoming message

      com = new ClientCom (serverHostName, serverPortNumb);
      while (!com.open ())                                           // waits for a connection to be established
      { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
      }
      outMessage = new Message (MessageType.lookArREQ, ((Waiter) Thread.currentThread ()).getWaiterId());
      com.writeObject(outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType () != MessageType.lookArDONE)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId ())
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
      System.out.println("Move " + inMessage.getlook());
      return inMessage.getlook();
   }

  /**
   *  Operation returnToTheBar.
   *
   *  Waiter retuns to the bar
   */

   public void returnToTheBar ()
   {
      ClientCom com;                                                 // communication channel
      Message outMessage,                                            // outgoing message
              inMessage;                                             // incoming message

      com = new ClientCom (serverHostName, serverPortNumb);
      while (!com.open ())                                           // waits for a connection to be established
      { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException e) {}
      }
      outMessage = new Message (MessageType.returnBarREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                                ((Waiter) Thread.currentThread ()).getWaiterState());
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType () != MessageType.returnBarDONE)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId())
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      ((Waiter) Thread.currentThread ()).setWaiterState(inMessage.getWaiState());
      com.close ();
   }

   /**
   *  Operation prepareTheBill.
   *
   *  Waiter prepares the bill for the laast student
   */

  public void prepareTheBill ()
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message

     com = new ClientCom (serverHostName, serverPortNumb);
     while (!com.open ())                                           // waits for a connection to be established
     { try
       { Thread.currentThread ().sleep ((long) (10));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.prepareTheBillREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.prepareTheBillDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Waiter) Thread.currentThread ()).setWaiterState(inMessage.getWaiState());
     com.close ();
  }

    /**
   *  Operation enter.
   *
   *  Student wakes bar
   */

  public void enter ()
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message

     com = new ClientCom (serverHostName, serverPortNumb);
     while (!com.open ())                                           // waits for a connection to be established
     { try
       { Thread.currentThread ().sleep ((long) (10));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.enterBarREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.enterBarDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

  /**
   *  Operation end of work.
   *
   *   New operation.
   *
   *      @param waiterId chef id
   */
  public void endOperationW (int waiterId)
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message

     com = new ClientCom (serverHostName, serverPortNumb);
     while (!com.open ())
     { try
       { Thread.sleep ((long) (1000));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.ENDOPW, waiterId);
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType() != MessageType.EOPDONEW)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != waiterId)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

   /**
   *  Operation end of work.
   *
   *   New operation.
   *
   *      @param studentId chef id
   */
  public void endOperationS (int studentId)
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message

     com = new ClientCom (serverHostName, serverPortNumb);
     while (!com.open ())
     { try
       { Thread.sleep ((long) (1000));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.ENDOPS, studentId);
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType() != MessageType.EOPDONES)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != studentId)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

   /**
   *   Operation server shutdown.
   *
   *   New operation.
   */

   public void shutdown ()
   {
      ClientCom com;                                                 // communication channel
      Message outMessage,                                            // outgoing message
              inMessage;                                             // incoming message

      com = new ClientCom (serverHostName, serverPortNumb);
      while (!com.open ())
      { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
      }
      outMessage = new Message (MessageType.SHUT);
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType() != MessageType.SHUTDONE)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      com.close ();
   }
}
