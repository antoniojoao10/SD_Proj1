echo "Transfering data to the Table node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirTable.zip sd303@l040101-ws03.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Table node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/TheRestaurant ; unzip -uq dirTable.zip'
echo "Executing program at the Table node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/TheRestaurant/dirTable ; java serverSide.main.ServerTheRestaurantTable 22522 l040101-ws01.ua.pt 22520'
