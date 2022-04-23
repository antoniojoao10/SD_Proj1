for i in $(seq 1 1000)
do
echo -e "\nRun n.o " $i
   cd /home/joao/Documents/UA/SD/SD_Proj1 ; /usr/bin/env /usr/lib/jvm/jdk-17/bin/java -XX:+ShowCodeDetailsInExceptionMessages @/tmp/cp_3l7fi4vllk2eyxnifl0aqjpd8.argfile main.TheRestaurant 
done
