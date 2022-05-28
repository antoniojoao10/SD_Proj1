echo "Transfering data to the general repository node."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirGeneralRepos.zip sd303@l040101-ws01.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'cd test/TheRestaurant ; unzip -uq dirGeneralRepos.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'cd test/TheRestaurant/dirGeneralRepos ; java serverSide.main.ServerTheRestaurantGeneralRepos 22520'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'cd test/TheRestaurant/dirGeneralRepos ; less logger'
