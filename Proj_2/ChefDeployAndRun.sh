echo "Transfering data to the Chef node."
sshpass -f password ssh sd303@l040101-ws06.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws06.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirChef.zip sd303@l040101-ws06.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Chef node."
sshpass -f password ssh sd303@l040101-ws06.ua.pt 'cd test/TheRestaurant ; unzip -uq dirChef.zip'
echo "Executing program at the Chef node."
sshpass -f password ssh sd303@l040101-ws06.ua.pt 'cd test/TheRestaurant/dirChef ; java clientSide.main.ClientTheRestaurantChef l040101-ws04.ua.pt 22523 l040101-ws01.ua.pt 22520'
