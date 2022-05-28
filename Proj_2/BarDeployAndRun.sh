echo "Transfering data to the Bar node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirBar.zip sd303@l040101-ws02.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Bar node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'cd test/TheRestaurant ; unzip -uq dirBar.zip'
echo "Executing program at the Bar node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'cd test/TheRestaurant/dirBar ; java serverSide.main.ServerTheRestaurantBar 22521 l040101-ws01.ua.pt 22520'
