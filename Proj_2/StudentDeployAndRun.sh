echo "Transfering data to the Student node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'mkdir -p test/TheRestaurant'
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'rm -rf test/TheRestaurant/*'
sshpass -f password scp dirStudent.zip sd303@l040101-ws07.ua.pt:test/TheRestaurant
echo "Decompressing data sent to the Student node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'cd test/TheRestaurant ; unzip -uq dirStudent.zip'
echo "Executing program at the Student node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'cd test/TheRestaurant/dirStudent ; java clientSide.main.ClientTheRestaurantStudent l040101-ws02.ua.pt 22521 l040101-ws03.ua.pt 22522 l040101-ws01.ua.pt 22520'
