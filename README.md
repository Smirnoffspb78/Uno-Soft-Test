
Данная задача решена в рамках тестового задания для компании Уно-Софт. После выполнения программы в консоль выводятся 
логи о количестве групп с размером больше, чем одна строка, а также общее время выполнения программы.

Код, решающий задачу сортировки строк, можно запустить из командной строки, указав путь на исполняемый jar файл.

Исполняемый jar файл находится в папке target данного проект и собран с помощью maven.

Для запуска исполняемого jar файла с ограничением по памяти необходимо указать путь к нему в следующем виде:

java -Xmx1g –jar {название проекта}.jar {тестовый-файл}.txt 

ПРИМЕР:
java -Xmx1g -jar C:/UnoSoft/unosofttest/target/unosofttest-1.0-SNAPSHOT.jar C:/UnoSoft/unosofttest/src/main/resources/lng.txt

Результаты сортировки записываются в файл result.txt