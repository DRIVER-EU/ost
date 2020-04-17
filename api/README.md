## Jak odpalić projekt:
1.  Clone https://github.com/DRIVER-EU/test-bed
2.  `cd docker/local`
3.  `docker-compose up`

4. Clone http://gitlab.itti.com.pl/zwo/fp7-driver/tree/be-task-246
5. Odpalić, projekt graddlea
6. bootRun  
  
https://github.com/DRIVER-EU/java-test-bed-adapter/ nie jest wymagane ale przydatne. Aktualnie spakowane do paczki .jar. Wersna 1.2.1 to paczka Łukasza, 1.2.4 najnowsza, 1.2.4 modified - moja wzbogacona o kod od Łukasza.

## Uwagi co do postmana
Dokumentacja jest tutaj: http://gitlab.itti.com.pl/zwo/fp7-driver/tree/be-task-246/doc  
Jak nie ma gdzieś dostępu polecam zacząć od zmiany hasła w requescie z adminpass na admin.  
/api/time-elapsed powinna zwracać czas który upłynął od początku Triala  
/api/trial-time powinno zwracać aktualny czas symulacji  

## Moje uwagi
Klasa SendToTestBed zawiera wysyłanie odpowiedzi z ankiet do kafki. Odpowiedzi te muszą mieć dobry format (avro schemas). Próbowałem taki zakodzić, ale nie działało mimo iż dawało identyczny wynik jak import z XMLa. Finalnie wysyłany jest string o formacie z XMLa, bo w ten sposób działa.  
**Problem** tego fragmentu polega na tym, że jeśli jest więcej niż jedno pytanie, zapisywane jest tylko ostatnie. Rozmawiałem o tym z Łukaszem, ale nie wymyśliliśmy dobrej XMLki realizującej to założenie. Powiedziałem o tym Jankowi i finalnie zadecydowaliśmy, że zapisów do kafki będzie więcej, z tym samym początkiem ale różnymi, pojedyńczymi odpowiedziami.  
  
SimulationTime.  
Dwie metody powinny zaciągać aktualny czas triala i czas który upłynął od startu triala (w czasie wykonywania zadania, okazało się, że ta druga część nie jest potrzebna). Jeśli nie ma żadnego triala, zwracany jest czas serwera.
Byłem przekonany, że to działa, lecz kiedy Łukasz pokazał mi jak zmienić czas w TimeService, doszedłem do wniosku, że nie działa.  
Próby ustawienia czasu ręcznie z Szymonem (w JavaAdapter) rzucały błędami.  
Finalnie nie wiem, czy to działa tak jak powinno czy nie.  
TrialTime jest dodawany jako nowa kolumna do bazy danych i do CSVki.

## Dokumentacje
Java Adapter: https://github.com/DRIVER-EU/java-test-bed-adapter/tree/master/testbedadapter  
Time-service: https://github.com/DRIVER-EU/test-bed-time-service  
Test-Bed: https://github.com/DRIVER-EU/test-bed  
Test-bed Admin: https://github.com/DRIVER-EU/test-bed-admin
Avro schemas: https://github.com/DRIVER-EU/avro-schemas  

## Przydatne linki
Kafka: http://localhost:3600  
Schema registry: http://localhost:3601/  
Admin tool: http://localhost:8090/#/overview  
* kliknięcie initialise test-bed wywala 500tke w dockerze  
Time service: http://localhost:8100/time-service/#!/  
* w lewym górnym rogu jest guzik do sterowania czasem


ENVIRONMENT VARIABLES:

OST_DB_HOST=localhost
OST_DB_PORT=5437
OST_DB_NAME=itti_driver
DRIVER_IS_TESTBED_ON=[true/false]
