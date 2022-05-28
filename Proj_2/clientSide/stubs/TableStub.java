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

public class TableStub
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

   public TableStub (String serverHostName, int serverPortNumb)
   {
      this.serverHostName = serverHostName;
      this.serverPortNumb = serverPortNumb;
   }

  /**
   *  Operation enter.
   *
   *  A student enters the restaurant
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
      outMessage = new Message (MessageType.enterTableREQ, ((Student) Thread.currentThread ()).getStudentId(),
                                ((Student) Thread.currentThread ()).getStudentState());
      com.writeObject (outMessage);
      inMessage = (Message) com.readObject ();
      if (inMessage.getMsgType () != MessageType.enterTableDONE)
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
         { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
           GenericIO.writelnString (inMessage.toString ());
           System.exit (1);
         }
      ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
      com.close ();
   }

   /**
   *  Operation readTheMenu.
   *
   *  The student reads the menu
   */

  public void readTheMenu ()
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
     outMessage = new Message (MessageType.readTheMenuREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.readTheMenuDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

   /**
   *  Operation joinTheTalk.
   *
   *  The first student joins the talk after finishing ordering
   */

  public void joinTheTalk ()
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
     outMessage = new Message (MessageType.joinTheTalkREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.joinTheTalkDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

  /**
   *  Operation startEating.
   *
   *  A student starts to eat
   */

  public void startEating ()
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
     outMessage = new Message (MessageType.startEatingREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.startEatingDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

   /**
   *  Operation endEating.
   *
   *  A student finishes his portion
   */

  public void endEating ()
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
     outMessage = new Message (MessageType.endEatingREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.endEatingDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

   /**
   *  Operation informCompanion.
   *
   *  Student informs the first student to arrive what his order will be
   */

  public void informCompanion ()
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
     outMessage = new Message (MessageType.informCompanionREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.informCompanionDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

  /**
   *  Operation hasEverybodyFinished.
   *
   *  Student waits for others to finish
   */

  public void hasEverybodyFinished ()
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message
             System.out.println("here");
     com = new ClientCom (serverHostName, serverPortNumb);
     while (!com.open ())                                           // waits for a connection to be established
     { try
       { Thread.currentThread ().sleep ((long) (10));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.hasEverybodyFinishedREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.hasEverybodyFinishedDONE)
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
   *  Operation exit.
   *
   *  Student exits the restaurant
   */

  public void exit ()
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
     outMessage = new Message (MessageType.exitREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.exitDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

  /**
   *  Operation prepareOrder.
   *
   *  The first student to arrive at the restaurant waits for all the others to finish their orders
   */

  public void prepareOrder ()
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
     outMessage = new Message (MessageType.prepareOrderREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.prepareOrderDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

  /**
   *  Operation callTheWaiter.
   *
   *  Student calls the waiter aftr all other student have completed their order
   */

  public void callTheWaiter ()
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
     outMessage = new Message (MessageType.callTheWaiterREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.callTheWaiterDONE)
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
   *  Operation signalTheWaiter.
   *
   *  The student signals the waiter for another portion or the next course
   */

  public void signalTheWaiter ()
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
     outMessage = new Message (MessageType.signalTheWaiterREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.signalTheWaiterDONE)
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
   *  Operation shouldHaveArrivedEarlier.
   *
   *  The last student to arrive at the restaurant call the waiter to pay
   */

  public void shouldHaveArrivedEarlier ()
  {
     ClientCom com;                                                 // communication channel
     Message outMessage,                                            // outgoing message
             inMessage;                                             // incoming message
             System.out.println("here");
     com = new ClientCom (serverHostName, serverPortNumb);
     
     while (!com.open ())                                           // waits for a connection to be established
     { try
       { Thread.currentThread ().sleep ((long) (10));
       }
       catch (InterruptedException e) {}
     }
     outMessage = new Message (MessageType.shouldHaveArrivedEarlierREQ, ((Student) Thread.currentThread ()).getStudentId(),
                               ((Student) Thread.currentThread ()).getStudentState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.shouldHaveArrivedEarlierDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getStuId() != ((Student) Thread.currentThread ()).getStudentId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
      ((Student) Thread.currentThread ()).setStudentState(inMessage.getStuState());
     com.close ();
  }

  /**
   *  Operation saluteTheClient.
   *
   *  Waiter salutes the student that entered the restaurant
   */

  public void saluteTheClient ()
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
     outMessage = new Message (MessageType.saluteTheClientREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.saluteTheClientDONE)
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
   *  Operation getThePaf
   *
   *  Waiter gets the pad
   */

  public void getThePad ()
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
     outMessage = new Message (MessageType.getThePadREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.getThePadDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

  /**
   *  Operation deliverPortion.
   *
   *  Waiter delivers each portion
   */

  public void deliverPortion ()
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
     outMessage = new Message (MessageType.deliverPortionREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.deliverPortionDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
  }

  /**
   *  Operation presentTheBill.
   *
   *  Waiter preents the bill to the last student to arrive
   */

  public void presentTheBill ()
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
     outMessage = new Message (MessageType.presentTheBillREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.presentTheBillDONE)
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
   *  Operation checkingFlags.
   *
   *  Waiter checkingFlags
   */

  public boolean checkingFlags()
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
     outMessage = new Message (MessageType.checkingFlagsREQ, ((Waiter) Thread.currentThread ()).getWaiterId(),
                               ((Waiter) Thread.currentThread ()).getWaiterState());
     com.writeObject (outMessage);
     inMessage = (Message) com.readObject ();
     if (inMessage.getMsgType () != MessageType.checkingFlagsDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     if (inMessage.getWaiId() != ((Waiter) Thread.currentThread ()).getWaiterId())
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid barber id!");
          GenericIO.writelnString (inMessage.toString ());
          System.exit (1);
        }
     com.close ();
     return inMessage.getflagCheck();
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
     return inMessage.getlook();
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
