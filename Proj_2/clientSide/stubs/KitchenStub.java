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

public class KitchenStub
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

   public KitchenStub (String serverHostName, int serverPortNumb)
   {
      this.serverHostName = serverHostName;
      this.serverPortNumb = serverPortNumb;
   }


  /**
   *  Operation handTheNoteToTheChef.
   *
   * Waiter hands the note to the chef
   */

   public void handTheNoteToTheChef ()
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
      outMessage = new Message (MessageType.handTheNoteToTheChefREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                                ((Waiter) Thread.currentThread ()).getWaiterState());
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType () != MessageType.handTheNoteToTheChefDONE)
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
   *  Operation collectPortion.
   *
   * Waiter colects the portion from the kitchen
   */

  public void collectPortion ()
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
     outMessage = new Message (MessageType.collectPortionREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.collectPortionDONE)
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
   *  Operation watchTheNews.
   *
   * Chef waits for orders
   */

  public void watchTheNews ()
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
     outMessage = new Message (MessageType.watchTheNewsREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.watchTheNewsDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

   /**
   *  Operation startPreparation.
   *
   * Chef starts preparing the portions
   */

  public void startPreparation ()
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
     outMessage = new Message (MessageType.startPreparationREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.startPreparationDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }

  /**
   *  Operation continuePreparation.
   *
   * Chef continues  preparing the portions
   */

  public void continuePreparation ()
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
     outMessage = new Message (MessageType.continuePreparationREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.continuePreparationDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }
  /**
   *  Operation proceedToPresentation.
   *
   * Chef prepares the portions to be presentabel
   */

  public void proceedToPresentation ()
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
     outMessage = new Message (MessageType.proceedToPresentationREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.proceedToPresentationDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }

  /**
   *  Operation haveNextPortionReady.
   *
   * Chef starts preparing the next portion
   */

  public void haveNextPortionReady ()
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
     outMessage = new Message (MessageType.haveNextPortionReadyREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.haveNextPortionReadyDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }

   /**
   *  Operation cleanUp.
   *
   * Chef ends the job
   */

  public void cleanUp ()
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
     outMessage = new Message (MessageType.cleanUpREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.cleanUpDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }

   /**
   *  Operation alertTheWaiter.
   *
   * Chef 
   */

  public void alertTheWaiter ()
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
     outMessage = new Message (MessageType.alertTheWaiterREQ, ((Chef) Thread.currentThread ()).getChefId(),
                               ((Chef) Thread.currentThread ()).getChefState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.alertTheWaiterDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getChefId() != ((Chef) Thread.currentThread ()).getChefId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Chef) Thread.currentThread ()).setChefState(inMessage.getChefId());
     com.close ();
  }

  /**
   *  Operation end of work.
   *
   *   New operation.
   *
   *      @param chefIf chef id
   */
  public void endOperationC (int chefIf)
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
      outMessage = new Message (MessageType.ENDOPC, chefIf);
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType() != MessageType.EOPDONEC)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      if (inMessage.getChefId() != chefIf)
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
