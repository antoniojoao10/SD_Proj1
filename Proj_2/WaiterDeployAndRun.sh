echo "Transfering data to the Waiters node."
sshpass -f password ssh sd303@l040101-ws05.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws05.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirWaiter.zip sd303@l040101-ws05.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Waiters node."
sshpass -f password ssh sd303@l040101-ws05.ua.pt 'cd test/TheRestaurant ; unzip -uq dirWaiter.zip'
echo "Executing program at the Waiters node."
sshpass -f password ssh sd303@l040101-ws05.ua.pt 'cd test/TheRestaurant/dirWaiter ; java clientSide.main.ClientTheRestaurantWaiter l040101-ws02.ua.pt 22521 l040101-ws03.ua.pt 22522 l040101-ws04.ua.pt 22523 l040101-ws01.ua.pt 22520'
