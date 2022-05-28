echo "Compiling source code."
javac */*.java */*/*.java
echo "Distributing intermediate code to the different execution environments."
echo "  General Repository of Information"
rm -rf dirGeneralRepos
mkdir -p dirGeneralRepos dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/entities dirGeneralRepos/serverSide/sharedRegions \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerTheRestaurantGeneralRepos.class dirGeneralRepos/serverSide/main
cp serverSide/entities/GeneralReposClientProxy.class dirGeneralRepos/serverSide/entities
cp serverSide/sharedRegions/GeneralReposInterface.class serverSide/sharedRegions/GeneralRepos.class dirGeneralRepos/serverSide/sharedRegions
cp clientSide/entities/StudentStates.class clientSide/entities/ChefStates.class clientSide/entities/WaiterStates.class dirGeneralRepos/clientSide/entities
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ServerCom.class dirGeneralRepos/commInfra
echo " Bar"
rm -rf dirBar
mkdir -p dirBar dirBar/serverSide dirBar/serverSide/main dirBar/serverSide/entities dirBar/serverSide/sharedRegions \
         dirBar/clientSide dirBar/clientSide/entities dirBar/clientSide/stubs dirBar/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerTheRestaurantBar.class serverSide/main/ServerTheRestaurantTable.class serverSide/main/ServerTheRestaurantKitchen.class dirBar/serverSide/main
cp serverSide/entities/BarClientProxy.class serverSide/entities/TableClientProxy.class serverSide/entities/KitchenClientProxy.class dirBar/serverSide/entities
cp serverSide/sharedRegions/GeneralReposInterface.class serverSide/sharedRegions/BarInterface.class serverSide/sharedRegions/Bar.class \ 
   serverSide/sharedRegions/TableInterface.class serverSide/sharedRegions/Table.class serverSide/sharedRegions/KitchenInterface.class serverSide/sharedRegions/Kitchen.class dirBar/serverSide/sharedRegions
cp clientSide/entities/StudentStates.class clientSide/entities/WaiterStates.class clientSide/entities/ChefStates.class clientSide/entities/StudentCloning.class clientSide/entities/WaiterCloning.class \
   clientSide/entities/ChefCloning.class dirBar/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirBar/clientSide/stubs
cp commInfra/*.class dirBar/commInfra
echo " Table"
rm -rf dirTable
mkdir -p dirTable dirTable/serverSide dirTable/serverSide/main dirTable/serverSide/entities dirTable/serverSide/sharedRegions \
         dirTable/clientSide dirTable/clientSide/entities dirTable/clientSide/stubs dirTable/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerTheRestaurantBar.class serverSide/main/ServerTheRestaurantTable.class serverSide/main/ServerTheRestaurantKitchen.class dirTable/serverSide/main
cp serverSide/entities/BarClientProxy.class serverSide/entities/TableClientProxy.class serverSide/entities/KitchenClientProxy.class dirTable/serverSide/entities
cp serverSide/sharedRegions/GeneralReposInterface.class serverSide/sharedRegions/BarInterface.class serverSide/sharedRegions/Bar.class \ 
   serverSide/sharedRegions/TableInterface.class serverSide/sharedRegions/Table.class serverSide/sharedRegions/KitchenInterface.class serverSide/sharedRegions/Kitchen.class dirTable/serverSide/sharedRegions
cp clientSide/entities/StudentStates.class clientSide/entities/WaiterStates.class clientSide/entities/ChefStates.class clientSide/entities/StudentCloning.class clientSide/entities/WaiterCloning.class \
   clientSide/entities/ChefCloning.class dirTable/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirTable/clientSide/stubs
cp commInfra/*.class dirTable/commInfra
echo " Kitchen"
rm -rf dirKitchen
mkdir -p dirKitchen dirKitchen/serverSide dirKitchen/serverSide/main dirKitchen/serverSide/entities dirKitchen/serverSide/sharedRegions \
         dirKitchen/clientSide dirKitchen/clientSide/entities dirKitchen/clientSide/stubs dirKitchen/commInfra
cp serverSide/main/SimulPar.class serverSide/main/ServerTheRestaurantBar.class serverSide/main/ServerTheRestaurantTable.class serverSide/main/ServerTheRestaurantKitchen.class dirKitchen/serverSide/main
cp serverSide/entities/BarClientProxy.class serverSide/entities/TableClientProxy.class serverSide/entities/KitchenClientProxy.class dirKitchen/serverSide/entities
cp serverSide/sharedRegions/GeneralReposInterface.class serverSide/sharedRegions/BarInterface.class serverSide/sharedRegions/Bar.class \ 
   serverSide/sharedRegions/TableInterface.class serverSide/sharedRegions/Table.class serverSide/sharedRegions/KitchenInterface.class serverSide/sharedRegions/Kitchen.class dirKitchen/serverSide/sharedRegions
cp clientSide/entities/StudentStates.class clientSide/entities/WaiterStates.class clientSide/entities/ChefStates.class clientSide/entities/StudentCloning.class clientSide/entities/WaiterCloning.class \
   clientSide/entities/ChefCloning.class dirKitchen/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class dirKitchen/clientSide/stubs
cp commInfra/*.class dirKitchen/commInfra
echo "  Student"
rm -rf dirStudent
mkdir -p dirStudent dirStudent/serverSide dirStudent/serverSide/main dirStudent/clientSide dirStudent/clientSide/main dirStudent/clientSide/entities \
         dirStudent/clientSide/stubs dirStudent/commInfra
cp serverSide/main/SimulPar.class dirStudent/serverSide/main
cp clientSide/main/ClientTheRestaurantStudent.class dirStudent/clientSide/main
cp clientSide/entities/Student.class clientSide/entities/StudentStates.class dirStudent/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/BarStub.class clientSide/stubs/TableStub.class clientSide/stubs/KitchenStub.class dirStudent/clientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirStudent/commInfra
echo "  Waiter"
rm -rf dirWaiter
mkdir -p dirWaiter dirWaiter/serverSide dirWaiter/serverSide/main dirWaiter/clientSide dirWaiter/clientSide/main dirWaiter/clientSide/entities \
         dirWaiter/clientSide/stubs dirWaiter/commInfra
cp serverSide/main/SimulPar.class dirWaiter/serverSide/main
cp clientSide/main/ClientTheRestaurantWaiter.class dirWaiter/clientSide/main
cp clientSide/entities/Waiter.class clientSide/entities/WaiterStates.class dirWaiter/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/BarStub.class clientSide/stubs/TableStub.class clientSide/stubs/KitchenStub.class dirStudent/clientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirWaiter/commInfra
echo "  Chef"
rm -rf dirChef
mkdir -p dirChef dirChef/serverSide dirChef/serverSide/main dirChef/clientSide dirChef/clientSide/main dirChef/clientSide/entities \
         dirChef/clientSide/stubs dirChef/commInfra
cp serverSide/main/SimulPar.class dirChef/serverSide/main
cp clientSide/main/ClientTheRestaurantChef.class dirChef/clientSide/main
cp clientSide/entities/Chef.class clientSide/entities/ChefStates.class dirChef/clientSide/entities
cp clientSide/stubs/GeneralReposStub.class clientSide/stubs/BarStub.class clientSide/stubs/TableStub.class clientSide/stubs/KitchenStub.class dirStudent/clientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirChef/commInfra
echo "Compressing execution environments."
echo "  General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
echo "  Bar"
rm -f  dirBar.zip
zip -rq dirBar.zip dirBar
echo "  Table"
rm -f  dirTable.zip
zip -rq dirTable.zip dirTable
echo "  Kitchen"
rm -f  dirKitchen.zip
zip -rq dirKitchen.zip dirKitchen
echo "  Student"
rm -f  dirStudent.zip
zip -rq dirStudent.zip dirStudent
echo "  Waiter"
rm -f  dirWaiter.zip
zip -rq dirWaiter.zip dirWaiter
echo "  Chef"
rm -f  dirChef.zip
zip -rq dirChef.zip dirChef
echo "Deploying and decompressing execution environments."
mkdir -p /home/ruib/test/SleepingBarbers
rm -rf /home/ruib/test/SleepingBarbers/*
cp dirGeneralRepos.zip /home/ruib/test/SleepingBarbers
cp dirBarberShop.zip /home/ruib/test/SleepingBarbers
cp dirBarbers.zip /home/ruib/test/SleepingBarbers
cp dirCustomers.zip /home/ruib/test/SleepingBarbers
cd /home/ruib/test/SleepingBarbers
unzip -q dirGeneralRepos.zip
unzip -q dirBarberShop.zip
unzip -q dirBarbers.zip
unzip -q dirCustomers.zip