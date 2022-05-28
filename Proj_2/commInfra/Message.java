package commInfra;

import java.io.*;
import serverSide.main.*;
import genclass.GenericIO;

/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable
{
  /**
   *  Serialization key.
   */

   private static final long serialVersionUID = 2021L;

  /**
   *  Message type.
   */

   private int msgType = -1;

  /**
   *  Waiter identification.
   */

   private int WaiId = -1;

  /**
   *  Waiter state.
   */

   private int WaiState = -1;

  /**
   *  Student identification.
   */

   private int StuId = -1;

  /**
   *  Student state.
   */

   private int StuState = -1;

   /**
   *  Student identification.
   */

  private int ChefId = -1;

  /**
   *  Student state.
   */

   private int ChefState = -1;

  /**
   *  End of operations (Waiter).
   */

   private boolean endOp = false;

  /**
   *  Name of the logging file.
   */

   private String fName = null;

  /**
   *  Number of iterations of the Student life cycle.
   */

   private int nIter = -1;

   /**
   *  result of flag cheking
   */

  private boolean flagCheck = false;

  /**
   *  result of look arounfd
   */

  private int lookAr = -1;

  /**
   *  Message instantiation (form 1).
   *
   *     @param type message type
   */

  /**
   *  Number of iterations of the waiter life cycle.
   */

  /**
   *  State of the students.
   */

  private int [] studentState;

  /**
   *  State of the waiters.
   */
 
   private  int  waiterState;

   /**
   *  State of the chef.
   */
   private  int  chefState;

   /**
   *  Number of the current course
   */
   private int NCourse;

   /**
   *  Number of portions eaten
   */
   private int NPortion;

   /**
   *  Seat to sit the next student to enter the restaurant
   */
   private int currentSit;

   /**
   *  Array with the seats of the students
   */
   private int [] studentSit;

   public Message (int type)
   {
      msgType = type;
      this.NCourse = 0;
      this.NPortion = 0;
      this.currentSit = 0;
      this.studentSit = new int [SimulPar.N];
      for (int i = 0; i < SimulPar.N; i++)
        studentSit[i] = -1;
   }

  /**
   *  Message instantiation (form 2).
   *
   *     @param type message type
   *     @param id Waiter / Student / Chef identification
   *     @param state Waiter / Student / Chef state
   */

   public Message (int type, int id, int state)
   {
      msgType = type;
      if( studentSit == null){
         this.studentSit = new int [SimulPar.N];
         for (int i = 0; i < SimulPar.N; i++)
            studentSit[i] = -1;
      }
      if ((msgType == MessageType.checkingFlagsREQ) || (msgType == MessageType.checkingFlagsDONE) || (msgType == MessageType.lookArREQ) || (msgType == MessageType.ENDOPW) || (msgType == MessageType.returnBarREQ) || (msgType == MessageType.saluteTheClientREQ) ||
      (msgType == MessageType.getThePadREQ) || (msgType == MessageType.handTheNoteToTheChefREQ) || (msgType == MessageType.collectPortionREQ) ||
      (msgType == MessageType.deliverPortionREQ) || (msgType == MessageType.prepareTheBillREQ) || (msgType == MessageType.presentTheBillREQ) || (msgType == MessageType.lookArDONE) || (msgType == MessageType.returnBarDONE) || (msgType == MessageType.saluteTheClientDONE) ||
      (msgType == MessageType.getThePadDONE) || (msgType == MessageType.handTheNoteToTheChefDONE) || (msgType == MessageType.collectPortionDONE) ||
      (msgType == MessageType.deliverPortionDONE) || (msgType == MessageType.prepareTheBillDONE) || (msgType == MessageType.presentTheBillDONE)|| (msgType == MessageType.setWaiterREQ))
         { WaiId= id;
           WaiState = state;
         }
         else if ((msgType == MessageType.watchTheNewsREQ) || (msgType == MessageType.ENDOPC) ||  (msgType == MessageType.startPreparationREQ) || (msgType == MessageType.proceedToPresentationREQ) ||
         (msgType == MessageType.alertTheWaiterREQ) || (msgType == MessageType.haveNextPortionReadyREQ)|| (msgType == MessageType.continuePreparationREQ) ||
         (msgType == MessageType.cleanUpREQ) || (msgType == MessageType.watchTheNewsDONE) || (msgType == MessageType.startPreparationDONE) || (msgType == MessageType.proceedToPresentationDONE) ||
         (msgType == MessageType.alertTheWaiterDONE) || (msgType == MessageType.haveNextPortionReadyDONE)|| (msgType == MessageType.continuePreparationDONE) ||
         (msgType == MessageType.cleanUpDONE)|| (msgType == MessageType.setChefREQ) || (msgType == MessageType.SACK))
                 { ChefId= id;
                   ChefState = state;
                 }
                 else if ((msgType == MessageType.readTheMenuREQ) ||(msgType == MessageType.enterBarREQ) ||(msgType == MessageType.enterBarDONE) ||(msgType == MessageType.enterTableREQ) ||(msgType == MessageType.enterTableDONE) || (msgType == MessageType.ENDOPS) ||  (msgType == MessageType.prepareOrderREQ) ||
                 (msgType == MessageType.callTheWaiterREQ) || (msgType == MessageType.joinTheTalkREQ) || (msgType == MessageType.signalTheWaiterREQ) ||
                 (msgType == MessageType.startEatingREQ) || (msgType == MessageType.endEatingREQ) || (msgType == MessageType.hasEverybodyFinishedREQ) ||
                 (msgType == MessageType.shouldHaveArrivedEarlierREQ) || (msgType == MessageType.exitREQ) || (msgType == MessageType.readTheMenuDONE) || (msgType == MessageType.prepareOrderDONE) ||
                 (msgType == MessageType.callTheWaiterDONE) || (msgType == MessageType.joinTheTalkDONE) || (msgType == MessageType.signalTheWaiterDONE) ||
                 (msgType == MessageType.startEatingDONE) || (msgType == MessageType.endEatingDONE) || (msgType == MessageType.hasEverybodyFinishedDONE) ||
                 (msgType == MessageType.shouldHaveArrivedEarlierDONE) || (msgType == MessageType.exitDONE) || (msgType == MessageType.setStudentREQ)|| (msgType == MessageType.informCompanionREQ)|| (msgType == MessageType.informCompanionDONE))
                        { StuId= id;
                           StuState = state;
                        }
                        else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation! 1");
                                 System.exit (1);
                              }
   }

  /**
   *  Message instantiation (form 3).
   *
   *     @param type message type
   *     @param id Waiter identification
   */

   public Message (int type, int id)
   {
      msgType = type;
      if( studentSit == null){
         this.studentSit = new int [SimulPar.N];
         for (int i = 0; i < SimulPar.N; i++)
            studentSit[i] = -1;
      }
      if ((msgType == MessageType.checkingFlagsREQ) || (msgType == MessageType.checkingFlagsDONE) || (msgType == MessageType.lookArREQ) || (msgType == MessageType.ENDOPW) || (msgType == MessageType.returnBarREQ) || (msgType == MessageType.saluteTheClientREQ) ||
      (msgType == MessageType.getThePadREQ) || (msgType == MessageType.handTheNoteToTheChefREQ) || (msgType == MessageType.collectPortionREQ) ||
      (msgType == MessageType.deliverPortionREQ) || (msgType == MessageType.prepareTheBillREQ) || (msgType == MessageType.presentTheBillREQ) || (msgType == MessageType.lookArDONE) || (msgType == MessageType.returnBarDONE) || (msgType == MessageType.saluteTheClientDONE) ||
      (msgType == MessageType.getThePadDONE) || (msgType == MessageType.handTheNoteToTheChefDONE) || (msgType == MessageType.collectPortionDONE) ||
      (msgType == MessageType.deliverPortionDONE) || (msgType == MessageType.prepareTheBillDONE) || (msgType == MessageType.presentTheBillDONE))
         { WaiId= id;
         }
         else if ((msgType == MessageType.watchTheNewsREQ) || (msgType == MessageType.ENDOPC) ||  (msgType == MessageType.startPreparationREQ) || (msgType == MessageType.proceedToPresentationREQ) ||
                  (msgType == MessageType.alertTheWaiterREQ) || (msgType == MessageType.haveNextPortionReadyREQ)|| (msgType == MessageType.continuePreparationREQ) ||
                  (msgType == MessageType.cleanUpREQ) || (msgType == MessageType.watchTheNewsDONE) || (msgType == MessageType.startPreparationDONE) || (msgType == MessageType.proceedToPresentationDONE) ||
                  (msgType == MessageType.alertTheWaiterDONE) || (msgType == MessageType.haveNextPortionReadyDONE)|| (msgType == MessageType.continuePreparationDONE) ||
                  (msgType == MessageType.cleanUpDONE))
                 { ChefId= id;
                 }
                 else if ((msgType == MessageType.readTheMenuREQ) ||(msgType == MessageType.enterBarREQ) ||(msgType == MessageType.enterBarDONE) ||(msgType == MessageType.enterTableREQ) ||(msgType == MessageType.enterTableDONE) || (msgType == MessageType.ENDOPS) ||  (msgType == MessageType.prepareOrderREQ) ||
                           (msgType == MessageType.callTheWaiterREQ) || (msgType == MessageType.joinTheTalkREQ) || (msgType == MessageType.signalTheWaiterREQ) ||
                           (msgType == MessageType.startEatingREQ) || (msgType == MessageType.endEatingREQ) || (msgType == MessageType.hasEverybodyFinishedREQ) ||
                           (msgType == MessageType.shouldHaveArrivedEarlierREQ) || (msgType == MessageType.exitREQ) || (msgType == MessageType.readTheMenuDONE) || (msgType == MessageType.prepareOrderDONE) ||
                           (msgType == MessageType.callTheWaiterDONE) || (msgType == MessageType.joinTheTalkDONE) || (msgType == MessageType.signalTheWaiterDONE) ||
                           (msgType == MessageType.startEatingDONE) || (msgType == MessageType.endEatingDONE) || (msgType == MessageType.hasEverybodyFinishedDONE) ||
                           (msgType == MessageType.shouldHaveArrivedEarlierDONE) || (msgType == MessageType.exitDONE) || (msgType == MessageType.informCompanionREQ)|| (msgType == MessageType.informCompanionDONE))
                        { StuId= id;
                        }else if( msgType == MessageType.setNPortionREQ) NPortion = id;
                        else if( msgType == MessageType.setNCourseREQ ) NCourse = id;
                        else if( msgType == MessageType.writeSitREQ ) {
                           this.studentSit[currentSit] = id;
                           this.currentSit++;
                        }
                        else if( msgType == MessageType.removeSitREQ ){
                           for( int i = 0; i < SimulPar.N ; i++){
                              if( studentSit[i] == id ){
                                 studentSit[i] = -1;
                                 break;
                              } 
                           }
                        }

                        else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation! 2");
                                 System.exit (1);
                              }
   }

  /**
   *  Message instantiation (form 4).
   *
   *     @param type message type
   *     @param id Waiter identification
   *     @param endOP end of operations flag
   */

   public Message (int type, int id, boolean endOp)
   {
      msgType = type;
      msgType = type;
      if ((msgType == MessageType.checkingFlagsREQ) || (msgType == MessageType.checkingFlagsDONE) || (msgType == MessageType.lookArREQ) || (msgType == MessageType.ENDOPW) || (msgType == MessageType.returnBarREQ) || (msgType == MessageType.saluteTheClientREQ) ||
      (msgType == MessageType.getThePadREQ) || (msgType == MessageType.handTheNoteToTheChefREQ) || (msgType == MessageType.collectPortionREQ) ||
      (msgType == MessageType.deliverPortionREQ) || (msgType == MessageType.prepareTheBillREQ) || (msgType == MessageType.presentTheBillREQ) || (msgType == MessageType.lookArDONE) || (msgType == MessageType.returnBarDONE) || (msgType == MessageType.saluteTheClientDONE) ||
      (msgType == MessageType.getThePadDONE) || (msgType == MessageType.handTheNoteToTheChefDONE) || (msgType == MessageType.collectPortionDONE) ||
      (msgType == MessageType.deliverPortionDONE) || (msgType == MessageType.prepareTheBillDONE) || (msgType == MessageType.presentTheBillDONE))
         { WaiId= id;
         }
         else if ((msgType == MessageType.watchTheNewsREQ) || (msgType == MessageType.ENDOPC) ||  (msgType == MessageType.startPreparationREQ) || (msgType == MessageType.proceedToPresentationREQ) ||
         (msgType == MessageType.alertTheWaiterREQ) || (msgType == MessageType.haveNextPortionReadyREQ)|| (msgType == MessageType.continuePreparationREQ) ||
         (msgType == MessageType.cleanUpREQ) || (msgType == MessageType.watchTheNewsDONE) || (msgType == MessageType.startPreparationDONE) || (msgType == MessageType.proceedToPresentationDONE) ||
         (msgType == MessageType.alertTheWaiterDONE) || (msgType == MessageType.haveNextPortionReadyDONE)|| (msgType == MessageType.continuePreparationDONE) ||
         (msgType == MessageType.cleanUpDONE))
                 { ChefId= id;
                 }
                 else if ((msgType == MessageType.readTheMenuREQ) ||(msgType == MessageType.enterBarREQ) ||(msgType == MessageType.enterBarDONE) ||(msgType == MessageType.enterTableREQ) ||(msgType == MessageType.enterTableDONE) || (msgType == MessageType.ENDOPS) ||  (msgType == MessageType.prepareOrderREQ) ||
                 (msgType == MessageType.callTheWaiterREQ) || (msgType == MessageType.joinTheTalkREQ) || (msgType == MessageType.signalTheWaiterREQ) ||
                 (msgType == MessageType.startEatingREQ) || (msgType == MessageType.endEatingREQ) || (msgType == MessageType.hasEverybodyFinishedREQ) ||
                 (msgType == MessageType.shouldHaveArrivedEarlierREQ) || (msgType == MessageType.exitREQ) || (msgType == MessageType.readTheMenuDONE) || (msgType == MessageType.prepareOrderDONE) ||
                 (msgType == MessageType.callTheWaiterDONE) || (msgType == MessageType.joinTheTalkDONE) || (msgType == MessageType.signalTheWaiterDONE) ||
                 (msgType == MessageType.startEatingDONE) || (msgType == MessageType.endEatingDONE) || (msgType == MessageType.hasEverybodyFinishedDONE) ||
                 (msgType == MessageType.shouldHaveArrivedEarlierDONE) || (msgType == MessageType.exitDONE)|| (msgType == MessageType.informCompanionREQ)|| (msgType == MessageType.informCompanionDONE))
                        { StuId= id;
                        }
                        else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation!3");
                                 System.exit (1);
                              }
      this.endOp = endOp;
   }

  /**
   *  Message instantiation (form 5).
   *
   *     @param type message type
   *     @param WaiId Waiter identification
   *     @param WaiState Waiter state
   *     @param StuId Student identification
   */

   public Message (int type, int WaiId, int WaiState, boolean flagCheck)
   {
      msgType = type;
      this.WaiId= WaiId;
      this.WaiState = WaiState;
      this.flagCheck= flagCheck;
   }

   /**
   *  Message instantiation (form 5).
   *
   *     @param type message type
   *     @param WaiId Waiter identification
   *     @param WaiState Waiter state
   *     @param StuId Student identification
   */

  public Message (int type, int WaiId, int WaiState, int lookAr)
  {
     msgType = type;
     this.WaiId= WaiId;
     this.WaiState = WaiState;
     this.lookAr= lookAr;
  }

  /**
   *  Message instantiation (form 7).
   *
   *     @param type message type
   *     @param name name of the logging file
   *     @param nIter number of iterations of the Student life cycle
   */

   public Message (int type, String name, int nIter)
   {
      msgType = type;
      fName= name;
      this.nIter = nIter;
   }

  /**
   *  Getting message type.
   *
   *     @return message type
   */

   public int getMsgType ()
   {
      return (msgType);
   }

  /**
   *  Getting Waiter identification.
   *
   *     @return Waiter identification
   */

   public int getWaiId ()
   {
      return (WaiId);
   }

  /**
   *  Getting Waiter state.
   *
   *     @return Waiter state
   */

   public int getWaiState ()
   {
      return (WaiState);
   }

   /**
   *  Getting Chef identification.
   *
   *     @return Chef identification
   */

  public int getChefId ()
  {
     return (ChefId);
  }

 /**
  *  Getting Chef state.
  *
  *     @return Chef state
  */

  public int getChefState ()
  {
     return (ChefState);
  }

  /**
   *  Getting Student identification.
   *
   *     @return Student identification
   */

   public int getStuId ()
   {
      return (StuId);
   }

  /**
   *  Getting Student state.
   *
   *     @return Student state
   */

   public int getStuState ()
   {
      return (StuState);
   }

  /**
   *  Getting end of operations flag (Waiter).
   *
   *     @return end of operations flag
   */

   public boolean getEndOp ()
   {
      return (endOp);
   }

  /**
   *  Getting name of logging file.
   *
   *     @return name of the logging file
   */

   public String getLogFName ()
   {
      return (fName);
   }

  /**
   *  Getting the number of iterations of the Student life cycle.
   *
   *     @return number of iterations of the Student life cycle
   */

   public int getNIter ()
   {
      return (nIter);
   }

   /**
   *  Getting the number result of flag cheing
   *
   *     @return result of flag cheking
   */

  public boolean getflagCheck ()
  {
     return (flagCheck);
  }

  /**
   *  Getting the number result of look Arrounf
   *
   *     @return result of lookArround
   */

  public int getlook ()
  {
     return (lookAr);
  }

  public int getNPortion(){ return NPortion; }
  public int getNCourse(){ return NCourse ; }
  public int[] getSits(){ return studentSit ; }


  /**
   *  Printing the values of the internal fields.
   *
   *  It is used for debugging purposes.
   *
   *     @return string containing, in separate lines, the pair field name - field value
   */

   @Override
   public String toString ()
   {
      return ("Message type = " + msgType +
              "\nWaiter Id = " + WaiId +
              "\nWaiter State = " + WaiState +
              "\nStudent Id = " + StuId +
              "\nStudent State = " + StuState +
              "\nChef Id = " + ChefId +
              "\nChef State = " + ChefState +
              "\nEnd of Operations (Waiter) = " + endOp +
              "\nName of logging file = " + fName +
              "\nNumber of iterations = " + nIter);
   }
}
