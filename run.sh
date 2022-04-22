for i in $(seq 1 100)
do
echo -e "\nRun n.o " $i
  & 'C:\Program Files\Java\jdk-16\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '@C:\Users\joao2\AppData\Local\Temp\cp_3kpeyxjr9r68lw5a7supk26e2.argfile' 'main.TheRestaurant' 
done
