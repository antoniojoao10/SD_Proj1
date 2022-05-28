package clientSide.main;

import clientSide.entities.*;
import clientSide.stubs.*;
import serverSide.main.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Client side of the Sleeping Chefs (Chefs).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class ClientTheRestaurantChef
{
  /**
   *  Main method.
   *
   *    @param args runtime arguments
   *        args[0] - name of the platform where is located the Kitchen server
   *        args[1] - port nunber for listening to service requests
   *        args[2] - name of the platform where is located the general repository server
   *        args[3] - port nunber for listening to service requests
   */

   public static void main (String [] args)
   {
      String KitchenServerHostName;                               // name of the platform where is located the Chef shop server
      int KitchenServerPortNumb = -1;                             // port number for listening to service requests
      String genReposServerHostName;                              // name of the platform where is located the general repository server
      int genReposServerPortNumb = -1;                               // port number for listening to service requests
      Chef [] Chef = new Chef [SimulPar.C];                    // array of Chef threads
      KitchenStub kitchenStub;                                      // remote reference to the Chef shop
      GeneralReposStub genReposStub;                                 // remote reference to the general repository


     /* getting problem runtime parameters */

      if (args.length != 4)
         { GenericIO.writelnString ("Wrong number of parameters!");
           System.exit (1);
         }
      KitchenServerHostName = args[0];
      try
      { KitchenServerPortNumb = Integer.parseInt (args[1]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[1] is not a number!");
        System.exit (1);
      }
      if ((KitchenServerPortNumb < 4000) || (KitchenServerPortNumb >= 65536))
         { GenericIO.writelnString ("args[1] is not a valid port number!");
           System.exit (1);
         }
      genReposServerHostName = args[2];
      try
      { genReposServerPortNumb = Integer.parseInt (args[3]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[3] is not a number!");
        System.exit (1);
      }
      if ((genReposServerPortNumb < 4000) || (genReposServerPortNumb >= 65536))
         { GenericIO.writelnString ("args[3] is not a valid port number!");
           System.exit (1);
         }

     /* problem initialization */

      kitchenStub = new KitchenStub (KitchenServerHostName, KitchenServerPortNumb);
      genReposStub = new GeneralReposStub (genReposServerHostName, genReposServerPortNumb);
      for (int i = 0; i < SimulPar.C; i++)
        Chef[i] = new Chef ("barb_" + (i+1), i, kitchenStub);

     /* start of the simulation */

      for (int i = 0; i < SimulPar.C; i++)
        Chef[i].start ();

     /* waiting for the end of the simulation */

      GenericIO.writelnString ();
      for (int i = 0; i < SimulPar.C; i++)
      { try
        { Chef[i].join ();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("The Chef " + (i+1) + " has terminated.");
      }
      GenericIO.writelnString ();
      kitchenStub.shutdown ();
      genReposStub.shutdown ();
   }
}
