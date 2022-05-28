package commInfra;

/**
 *   Type of the exchanged messages.
 *the
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class MessageType
{
  /**
   *  Initialization of the logging file name and the number of iterations (service request).
   */

   public static final int SETNFIC = 1;

  /**
   *  Logging file was initialized (reply).
   */

   public static final int NFICDONE = 2;

   /**
   *  Request lookArround (service request).
   */

  public static final int lookArREQ = 3;

  /**
   *  lookArround (reply).
   */

   public static final int lookArDONE = 4;

   /**
   *  Request returnToTheBar (service request).
   */

  public static final int returnBarREQ = 5;

  /**
   *  returnToTheBar (reply).
   */

   public static final int returnBarDONE = 6;

   /**
   *  Request prepareTheBill (service request).
   */

  public static final int prepareTheBillREQ = 7;

  /**
   *  prepareTheBill (reply).
   */

   public static final int prepareTheBillDONE = 8;


    /**
   *  Request enterBar (service request).
   */

  public static final int enterBarREQ = 9;

  /**
   *  enterBar (reply).
   */

   public static final int enterBarDONE = 10;

   /**
   *  Request enterTable (service request).
   */

  public static final int enterTableREQ = 11;

  /**
   *  enterTable (reply).
   */

   public static final int enterTableDONE = 12;

   /**
   *  Request checkingFlags (service request).
   */

  public static final int checkingFlagsREQ = 13;

  /**
   *  checkingFlags (reply).
   */

   public static final int checkingFlagsDONE = 14;

  /**
   *  Request readTheMenu (service request).
   */

  public static final int readTheMenuREQ = 15;

  /**
   *  readTheMenu (reply).
   */

   public static final int readTheMenuDONE = 16;

  /**
   *  Request joinTheTalk (service request).
   */

  public static final int joinTheTalkREQ = 17;

  /**
   *  joinTheTalk (reply).
   */

   public static final int joinTheTalkDONE = 18;

   /**
   *  Request startEating (service request).
   */

  public static final int startEatingREQ = 19;

  /**
   *  startEating (reply).
   */

   public static final int startEatingDONE = 20;


   /**
   *  Request endEating (service request).
   */

  public static final int endEatingREQ = 21;

  /**
   *  endEating (reply).
   */

   public static final int endEatingDONE = 22;

   /**
   *  Request informCompanion (service request).
   */

  public static final int informCompanionREQ = 23;

  /**
   *  informCompanion (reply).
   */

   public static final int informCompanionDONE = 24;

   /**
   *  Request hasEverybodyFinished (service request).
   */

  public static final int hasEverybodyFinishedREQ = 25;

  /**
   *  hasEverybodyFinished (reply).
   */

   public static final int hasEverybodyFinishedDONE = 26;

   /**
   *  Request prepareOrder (service request).
   */

  public static final int prepareOrderREQ = 27;

  /**
   *  prepareOrder (reply).
   */

   public static final int prepareOrderDONE = 28;

   /**
   *  Request callTheWaiter (service request).
   */

  public static final int callTheWaiterREQ = 29;

  /**
   *  callTheWaiter (reply).
   */

   public static final int callTheWaiterDONE = 30;


   /**
   *  Request signalTheWaiter (service request).
   */

  public static final int signalTheWaiterREQ = 31;

  /**
   *  signalTheWaiter (reply).
   */

   public static final int signalTheWaiterDONE = 32;

   /**
   *  Request exit (service request).
   */

  public static final int exitREQ = 33;

  /**
   *  exit (reply).
   */

   public static final int exitDONE = 34;

   /**
   *  Request shouldHaveArrivedEarlier (service request).
   */

  public static final int shouldHaveArrivedEarlierREQ = 35;

  /**
   *  shouldHaveArrivedEarlier (reply).
   */

   public static final int shouldHaveArrivedEarlierDONE = 36;

   /**
   *  Request saluteTheClient (service request).
   */

  public static final int saluteTheClientREQ = 37;

  /**
   *  saluteTheClient (reply).
   */

   public static final int saluteTheClientDONE = 38;

   /**
   *  Request getThePad (service request).
   */

  public static final int getThePadREQ = 39;

  /**
   *  getThePad (reply).
   */

   public static final int getThePadDONE = 40;

   /**
   *  Request deliverPortion (service request).
   */

  public static final int deliverPortionREQ = 41;

  /**
   *  deliverPortion (reply).
   */

   public static final int deliverPortionDONE = 42;

   /**
   *  Request presentTheBill (service request).
   */

  public static final int presentTheBillREQ = 43;

  /**
   *  presentTheBill (reply).
   */

   public static final int presentTheBillDONE = 44;

   /**
   *  Request handTheNoteToTheChef (service request).
   */

  public static final int handTheNoteToTheChefREQ = 45;

  /**
   *  handTheNoteToTheChef (reply).
   */

   public static final int handTheNoteToTheChefDONE = 46;

   /**
   *  Request collectPortion (service request).
   */

  public static final int collectPortionREQ = 47;

  /**
   *  collectPortion (reply).
   */

   public static final int collectPortionDONE = 48;


   /**
   *  Request watchTheNews (service request).
   */

  public static final int watchTheNewsREQ = 49;

  /**
   *  watchTheNews (reply).
   */

   public static final int watchTheNewsDONE = 50;

   /**
   *  Request startPreparation (service request).
   */

  public static final int startPreparationREQ = 51;

  /**
   *  startPreparation (reply).
   */

   public static final int startPreparationDONE = 52;

   /**
   *  Request continuePreparation (service request).
   */

  public static final int continuePreparationREQ = 53;

  /**
   *  continuePreparation (reply).
   */

   public static final int continuePreparationDONE = 54;

    /**
   *  Request proceedToPresentation (service request).
   */

  public static final int proceedToPresentationREQ = 55;

  /**
   *  proceedToPresentation (reply).
   */

   public static final int proceedToPresentationDONE = 56;


   /**
   *  Request haveNextPortionReady (service request).
   */

  public static final int haveNextPortionReadyREQ = 57;

  /**
   *  haveNextPortionReady (reply).
   */

   public static final int haveNextPortionReadyDONE = 58;

   /**
   *  Request cleanUp (service request).
   */

  public static final int cleanUpREQ = 59;

  /**
   *  cleanUp (reply).
   */

   public static final int cleanUpDONE = 60;

   /**
   *  Request alertTheWaiter (service request).
   */

  public static final int alertTheWaiterREQ = 61;

  /**
   *  alertTheWaiter (reply).
   */

   public static final int alertTheWaiterDONE = 62;

  /**
   *  Server shutdown (service request).
   */

   public static final int SHUT = 63;

  /**
   *  Server was shutdown (reply).
   */

   public static final int SHUTDONE = 64;

  /**
   *  Set Waiter state (service request).
   */

   public static final int setWaiterREQ = 65;

  /**
   *  Set Chef state (service request).
   */

   public static final int setChefREQ = 66;

  /**
   *  Set Student states (service request).
   */

   public static final int setStudentREQ = 67;

  /**
   *  Setting acknowledged (reply).
   */

   public static final int SACK = 68;

   /**
   *  Request set n portion (service request).
   */

  public static final int setNPortionREQ = 69;

  /**
   *  Portion has been set (reply).
   */

   public static final int setNPortionDONE = 70;

   /**
   *  Request set n course (service request).
   */

  public static final int setNCourseREQ = 71;

  /**
   *   setNCourse has been set (reply).
   */

   public static final int  setNCourseDONE = 72;
   
   /**
   *  Request swriteSit (service request).
   */

  public static final int writeSitREQ = 73;

  /**
   *   writeSit has been set (reply).
   */

   public static final int writeSitDONE = 74;

   /**
   *  Request removeSit (service request).
   */

  public static final int removeSitREQ = 75;

  /**
   *   removeSit has been set (reply).
   */

   public static final int  removeSitDONE = 76;

   /**
   *  Request end Chef (service request).
   */

  public static final int ENDOPC = 77;

  /**
   *   end Chef has been set (reply).
   */

   public static final int  EOPDONEC = 78;

   /**
   *  Request end waiter (service request).
   */

  public static final int ENDOPW = 79;

  /**
   *   end waiter has been set (reply).
   */

   public static final int  EOPDONEW = 80;

   /**
   *  Request end studnet (service request).
   */

  public static final int ENDOPS = 81;

  /**
   *   end studnet has been set (reply).
   */

   public static final int  EOPDONES = 82;

   /**
   *  Request end studnet (service request).
   */

  public static final int checkFirstREQ = 83;

  /**
   *   end studnet has been set (reply).
   */

   public static final int  checkFirstDONE = 84;

   /**
   *  Request end studnet (service request).
   */

  public static final int checkLastREQ = 85;

  /**
   *   end studnet has been set (reply).
   */

   public static final int  checkLastDONE = 86;
}
