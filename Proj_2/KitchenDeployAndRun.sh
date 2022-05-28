echo "Transfering data to the Kitchen node."
sshpass -f password ssh sd303@l040101-ws04.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws04.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirKitchen.zip sd303@l040101-ws04.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Kitchen node."
sshpass -f password ssh sd303@l040101-ws04.ua.pt 'cd test/TheRestaurant ; unzip -uq dirKitchen.zip'
echo "Executing program at the Kitchen node."
sshpass -f password ssh sd303@l040101-ws04.ua.pt 'cd test/TheRestaurant/dirKitchen ; java serverSide.main.ServerTheRestaurantKitchen 22523 l040101-ws01.ua.pt 22520'
