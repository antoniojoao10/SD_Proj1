package clientSide.main;

import clientSide.entities.*;
import clientSide.stubs.*;
import serverSide.main.*;
import commInfra.*;
import genclass.GenericIO;

/**
 *    Client side of the Sleeping Students (Students).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class ClientTheRestaurantStudent
{
  /**
   *  Main method.
   *
   *    @param args runtime arguments
   *        args[0] - name of the platform where is located the Student Bar server
   *        args[1] - port nunber for listening to service requests
   *        args[2] - name of the platform where is located the Student Table server
   *        args[3] - port nunber for listening to service requests
   *        args[4] - name of the platform where is located the general repository server
   *        args[5] - port nunber for listening to service requests
   */

   public static void main (String [] args)
   {
      String BarServerHostName;                               // name of the platform where is located the Student bar server
      int BarServerPortNumb = -1;                             // port number for listening to service requests
      String TableServerHostName;                               // name of the platform where is located the Student table server
      int TableServerPortNumb = -1;                             // port number for listening to service requests
      String genReposServerHostName;                              // name of the platform where is located the general repository server
      int genReposServerPortNumb = -1;                               // port number for listening to service requests
      Student [] Student = new Student [SimulPar.N];                    // array of Student threads
      BarStub barStub;                                        // remote reference to the Student bar
      TableStub tableStub;                                       // remote reference to the Student Table
      GeneralReposStub genReposStub;                                 // remote reference to the general repository


     /* getting problem runtime parameters */

      if (args.length != 6)
         { GenericIO.writelnString ("Wrong number of parameters!");
           System.exit (1);
         }
      BarServerHostName = args[0];
      try
      { BarServerPortNumb = Integer.parseInt (args[1]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[1] is not a number!");
        System.exit (1);
      }
      if ((BarServerPortNumb < 4000) || (BarServerPortNumb >= 65536))
         { GenericIO.writelnString ("args[1] is not a valid port number!");
           System.exit (1);
         }
      TableServerHostName = args[2];
      try
      { TableServerPortNumb = Integer.parseInt (args[3]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[3] is not a number!");
        System.exit (1);
      }
      if ((TableServerPortNumb < 4000) || (TableServerPortNumb >= 65536))
         { GenericIO.writelnString ("args[3] is not a valid port number!");
           System.exit (1);
         }
      genReposServerHostName = args[4];
      try
      { genReposServerPortNumb = Integer.parseInt (args[5]);
      }
      catch (NumberFormatException e)
      { GenericIO.writelnString ("args[5] is not a number!");
        System.exit (1);
      }
      if ((genReposServerPortNumb < 4000) || (genReposServerPortNumb >= 65536))
         { GenericIO.writelnString ("args[5] is not a valid port number!");
           System.exit (1);
         }

     /* problem initialization */

      barStub = new BarStub (BarServerHostName, BarServerPortNumb);
      tableStub = new TableStub (TableServerHostName, TableServerPortNumb);
      genReposStub = new GeneralReposStub (genReposServerHostName, genReposServerPortNumb);
      for (int i = 0; i < SimulPar.N; i++){
        Student[i] = new Student ("Stu_" + (i+1), i, tableStub, barStub);
        if(i==0) Student[i].setFirst();
        if(i==SimulPar.N-1) Student[SimulPar.N-1].setlast();
      }

     /* start of the simulation */

      for (int i = 0; i < SimulPar.N; i++)
        Student[i].start ();
     /* waiting for the end of the simulation */

      GenericIO.writelnString ();
      for (int i = 0; i < SimulPar.N; i++)
      {
        try
        { Student[i].join ();
        }
        catch (InterruptedException e) {}
        GenericIO.writelnString ("The Student " + (i+1) + " has terminated.");
      }
      GenericIO.writelnString ();
      barStub.shutdown ();
      tableStub.shutdown ();
      genReposStub.shutdown ();
   }
}
