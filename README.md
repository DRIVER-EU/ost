# OST documentation

## 1. Component diagram

Backend, frontend and database can be launched by one docker compose.  Backend can connect with Testbed (Kafka) and with Keycloak (system to authorization). 


![Figure 1. Component diagram of OST.](./doc/fig01.png)

### 2. User Manual

User can login to server as admin or observer. Admin can manage trials, session, stages, roles, add question to roles and stages, add and edit users and control active session.  Observer can answer on question and comment it.

#### Admin

Goal of admin is creating working session for observers. This process contains three stages: creating trial with questions, creating roles and assign them to question set and preparing session with users. To create questions admin should create trial, stages in trial, question sets in stages and question in question sets. Description can be find in subsection named ,,Creating trial with questions&quot;. Second part is creating roles and assign them to question set (which already are assign to stage). It means, that question, which are available for observer, depends on stage and on role of logged observer. Last step is creating session with observers. Observers in other sessions can have other roles, so assignment user to role in session. Admin needn&#39;t create new users, if observers have their account in system. Description of this step can be find in subsection ,,Managing users and sessions&quot;.

Creating trial with questions

#### Trial list

If we choose &quot;TRIALS&quot; from launch window we will be redirected to Trial List.

There is a list of trials, which can be managed also we can upload after choosing &quot;Import trial&quot; button.

![Figure 2. Trial list view](./doc/fig02.png)

#### Trial details

After choosing trial from trial list and click edit or after double clicking left mouse button on trial from trial list, system redirects to trial details. There are basic details about trial and data about sessions, stages and roles in trial.

Also trial can be edited and removed there.

![Figure 3. Trial detail view](./doc/fig03.png)

#### Stage details

After choosing stage from trial details and click edit or after double clicking left mouse button on stage from trial details, system redirects to stage details. There are basic details about stage, which can be edited. Also stage can be deleted by admin. Question set list of chosen stage is below basic stage details. You can redirect to trial by clicking trial name above save button.

![Figure 4. Stage detail view.](./doc/fig04.png)

### Question set details

In question set details windows admin can change name or description also list of questions of this question set is shown. User can redirect to the chosen question by double clicking on question or clicking question and edit. You can redirect to trial or stage by clicking trial name or stage name above save button.
![Figure 5. Question set detail view.](./doc/fig05.png)

#### Question details

In question details admin can modify question, description or type of answer. Also admin can add new options of choice, show them or remove. In question details parameters like commented and required can be set. You can redirect to trial, stage or question set by clicking trial name, stage name or question set name  above save button.

#### Question option can be add after saving question.
![Figure 6. Question detail view.](./doc/fig06.png)

#### Creating roles and assign it to question set

##### Role details

After choosing role from trial details and click edit or after double clicking left mouse button on role from trial details, system redirects to role details. There are basic details about role, which can be edited. Also in  role details we can manage of user assignment to role and question set, which should be show, when right stage is chosen. You can redirect to trial by clicking trial name above save button.

![Figure 7. Role detail view.](./doc/fig07.png)

#### Managing users and sessions

##### Session details

New session can be created by clicking +New, when session window in trial detail is active.

After choose session from trial details and click edit or after double clicking left mouse button on session from trial details, system redirects to session details. There are basic details about session, which can be edited or deleted, also data from session can be downloaded. In the user role table admin can assign role to user in this session. User can answer on question only if session is active.

![Figure 8. Session details view.](./doc/fig08.png)

#### Session tracking details

After clicking session from launch window, system redirects to session tracking detail. In session tracking details admin can control active sessions. There are informations, which trial is currently used, if session is controlled by testbed  or manually (manual column) and which stage is active at this moment.

![Figure 9. Session tracking details view.](./doc/fig09.png)

#### User list

If we choosing &quot;USERS&quot; in launch window we will be redirected to User list. There are some basic data about user there.

![Figure 9. Session tracking details view.](./doc/fig09.png)

#### User details

After choosing user from user list and click edit or after double clicking left mouse button on user from user list, system redirects to user details. There are basic details about user and data about sessions, stages and roles in trial.

Also user can be edited and removed there.

If we want to add new user, we should click +New on User list. Then system redirects to user detail and after filling data, system create new user. All fields are required.

![Figure 10. User overview.](./doc/fig10.png)

![Figure 11. User details view.](./doc/fig11.png)

#### Observer

Observer can login to server, when admin send him his login and password. After it he can login to system.

##### Question Set view

Observer after login to system can choosing from question set from active sessions (if question sets, during present stage in active session, are available to logged user).
![Figure 12. Question set view.](./doc/fig12.png)

##### Question view

Observer after choosing question set is redirected to questions from chosen question set. Observer can answer to question from this question set by writing text (textfield) or choosing option (checkbox, radiobutton, slider).

![Figure 13. Question view.](./doc/fig13.png)

###  3.  Deployment Manual

1. Download docker from [https://www.docker.com/](https://www.docker.com/).
2. Create file and name it _docker-compose.yml_
3. Open this file and paste to this file text below:

```yml
version: 3
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    hostname: zookeeper
    ports:
     - &quot;3500:3500&quot;
    environment:
      ZOOKEEPER\_CLIENT\_PORT: 3500
      ZOOKEEPER\_TICK\_TIME: 2000

  broker:
    image: confluentinc/cp-kafka:latest
    hostname: broker
    depends_on:
      - zookeeper
    ports:
      - &quot;3501:3501&quot;
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:3500
      KAFKA_ADVERTISED_LISTENERS: EXTERNAL://localhost:3501,PLAINTEXT://broker:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_LISTENERS: EXTERNAL://0.0.0.0:3501,PLAINTEXT://0.0.0.0:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_DEFAULT_REPLICATION_FACTOR: 1
      KAFKA_MESSAGE_MAX_BYTES: 100000000
      KAFKA_REPLICA_FETCH_MAX_BYTES: 100000000

  schema_registry:
    image: confluentinc/cp-schema-registry:latest
    hostname: schema\_registry
    depends_on:
      - zookeeper
      - broker
    ports:
      - &quot;3502:3502&quot;
    environment:
      SCHEMA_REGISTRY_HOST\_NAME: schema_registry
      SCHEMA_REGISTRY_LISTENERS: http://0.0.0.0:3502
      SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL: zookeeper:3500
      SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS: PLAINTEXT://broker:9092

  kafka_rest:
    image: confluentinc/cp-kafka-rest:latest
    hostname: kafka\_rest
    depends_on:
      - zookeeper
      - schema_registry
      - broker
    ports:
      - 8082:8082;
    environment:
      KAFKA_REST_HOST\_NAME: kafka_rest
      KAFKA_REST_BOOTSTRAP_SERVERS: PLAINTEXT://broker:9092
      KAFKA_REST_ZOOKEEPER_CONNECT: zookeeper:3500
      KAFKA_REST_LISTENERS: http://0.0.0.0:8082
      KAFKA_REST_SCHEMA_REGISTRY_URL: http://schema\_registry:3502&#39
      KAFKA_CONSUMER_REQUEST_TIMEOUT_MS: 30000
      KAFKA_REST_ACCESS_CONTROL_ALLOW_METHODS: GET,POST,PUT,DELETE,OPTIONS
      KAFKA_REST_ACCESS_CONTROL_ALLOW_ORIGIN: \*

  kafka_topics_ui:
    image: landoop/kafka-topics-ui:latest
    hostname: kafka_topics_ui
    depends_on:
      - kafka_rest
    ports:
      - 3600:8000;
    environment:
      KAFKA_REST_PROXY_URL: http://kafka\_rest:8082
      PROXY: "true" 

  kafka_schema_registry_ui:
    image: landoop/schema-registry-ui:latest
    hostname: kafka_schema_registry_ui
    depends_on:
      - schema_registry
    ports:
      - 3601:8000
    environment:
      SCHEMAREGISTRY_URL: http://schema_registry:3502
      PROXY: "true"

  postgres:
    image: postgres:9.6
    hostname: postgres
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: TRIAL_ADMIN
    volumes:
       - postgres-data:/var/lib/postgresql/data
_#  restart: unless-stopped_
 
  admintool:
    image: drivereu/test-bed-admin:latest
    depends_on:
      - postgres
      - broker
      - schema_registry
    ports:
      - 8090:8090;
    volumes:
      - ./admintool-config/gateways.json:/opt/application/config/gateways.json
      - ./admintool-config/solutions.json:/opt/application/config/solutions.json
      - ./admintool-config/topics.json:/opt/application/config/topics.json
      - ./admintool-config/standards.json:/opt/application/config/standards.json
      - ./admintool-config/testbed-solutions.json:/opt/application/config/testbed-solutions.json
      - ./admintool-config/testbed-topics.json:/opt/application/config/testbed-topics.json
      - ./admintool-config/configurations.json:/opt/application/config/configurations.json
    environment:
      KAFKA_BROKER_URL: broker:9092
      SCHEMA_REGISTRY_URL: http://schema\_registry:3502
      zookeeper_host: zookeeper
      zookeeper_port: 3500
      schema_registry_url: http://schema\_registry:3502
      testbed_secure_mode: DEVELOP
      testbed_init_auto: "false"
      management_ca_cert_path: http://localhost:9090
      cert_handler_url: https://localhost:8443
      cert_pem_handler_url: https://localhost:8443
      security_rest_path_group: https://localhost:9443
      security_rest_path_topic: https://localhost:9443

  afteractionreview:
    image: drivereu/after-action-review:latest
    depends_on:
      - postgres
      - broker
      - schema\_registry
    ports:
      - 8095:8095
    environment:
      KAFKA_BROKER_URL: broker:9092
      SCHEMA_REGISTRY_URL: http://schema_registry:3502
      zookeeper_host: zookeeper
      zookeeper_port: 3500
      schema_registry_url: http://schema_registry:3502

  pgadmin:
    image: fenglc/pgadmin4
    depends_on:
      - postgres
    ports:
      - 5050:5050
_#   restart: unless-stopped_

  time_service:
    image: drivereu/test-bed-time-service:latest
    depends_on:
      - broker
      - schema_registry
    ports:
      - 8100:8100
    environment:
      KAFKA_BROKER_URL: broker:9092
      SCHEMA_REGISTRY_URL: http://schema\_registry:3502
      AUTO_REGISTER_SCHEMAS: "true"

  large_file_service:
    image: drivereu/large-file-service:latest
    ports:
      - 9090:9090;
    environment:
      HOST: localhost
      PORT: 9090
 
  trial_management_tool:
    image: drivereu/trial-management-tool:latest
    depends_on:
      - broker
      - schema_registry
    ports:
      - 3210:3210
    environment:
      CLIENT_ID: TB-TrialMgmt
      KAFKA_HOST: broker:9092
      SCHEMA_REGISTRY: http://schema\_registry:3502
      TRIAL_MANAGER_SERVER_PORT: 3210
      PRODUCE: system_request_change_of_trial_stage,system_tm_phase_message,system_tm_role_player,system_tm_session_mgmt
      SSL: "false"
      SSL_PFX: certs/TB-TrialMgmt.p12
      SSL_PASSPHRASE: changeit
      SSL_CA: certs/test-ca.pem
    volumes:
      - trial-data:/app/trials
 
   ost_db:
     image: janbalbierzitti/ost_database:fddr2_2
     ports:
       - 5437:5432
     volumes:
       - database-OST:/var/lib/postgresql/data
_#    restart: always_
  ost_web:
    image: janbalbierzitti/ost_frontend:fddr2
    links:
      - ost_api
    ports:
      - 127.0.0.1:85:80
      - 127.0.0.1:445:443
_#        restart: always_
  ost_api:
    image: janbalbierzitti/ost_backend:fddr2_2
    links:
      - ost_db
    ports:
      - 8080:8080
_#    restart: always_

  silent-producer:
    image: silent-producer
    depends_on:
      - broker
      - schema_registry
    environment:
      KAFKA_HOST: broker:9092
      SCHEMA_REGISTRY: http://schema_registry:3502
      PRODUCE_TOPICS: simulation_request_unittransport,simulation_request_startinject,simulation_entity_item,sumo_AffectedArea,standard_cap,system_timing,system_topic_access_invite

volumes:
  database-OST:
  postgres-data:
  trial-data:
```

1. Go back to the required folder.
2. If you are windows user
  1. 1.Press **Shift + right click** mouse button anywhere on the folder window (this folder must have file docker-compose.yml).
  2. 2.Choose open powershell
  3. 3.Write in terminal **docker-compose up** and press enter
3. If you are linux/ubuntu user
  1. 1.Press **right click** mouse button anywhere on the folder window (this folder must have file docker-compose.yml).
  2. 2.Choose open terminal
  3. 3.Write there **docker-compose up** and press enter
4. Congratulation, your application has been just turned on.

### 4. API

1.  Get  /api/answers

Api let us see answer of trial session including some text inside.

@RequestParam(&quot;trialsession\_id&quot;) long trialSessionId,

@RequestParam(&quot;search&quot;) String text

**Produces Json**

**           ** TrialUserDTO.ListItem user

            String Name

String observationTypeName

String observationTypeDescription


1. Get  /api/answers/csv-file

           Api let us load CSV file with answers of session.

           @RequestParam(value = &quot;trialsession\_id&quot;) long trialSessionId

  **Return null**

1. Delete /api/answers/{answer\_id:\\d+}/remove

Api let us delete answer.

@PathVariable(value = &quot;answer\_id&quot;) long answerId,

               @RequestParam(&quot;comment&quot;) String comment

**Return null**

1. GET /api/answers-events

Api let us see all answers and events in session of current user.

@RequestParam(value = &quot;trialsession\_id&quot;) long trialSessionId

**Produces JSON**

long id;

long observationTypeId;

String name;

String description;

ZonedDateTime time;

ZonedDateTime trialTime;

String type;

1. Get  /api/event/search

Api returns list of events in session.

@RequestParam(value = &quot;trialsession\_id&quot;) long trialSessionId

**Produces JSON**

public String firstName;

public String lastName;

public String trialRoleName;



1. GET /api/observationtypes

Api returns list of question

sets

@RequestParam(&quot;trialsession\_id&quot;) Long trialSessionId

Long answersId

String name

String description

1. GET /api/observationtypes/form

Api returns roles, which can answer on question set.

@RequestParam(&quot;observationtype\_id&quot;) Long observationTypeId,

            @RequestParam(&quot;trialsession\_id&quot;) Long trialSessionId

**Return json**

List\&lt;TrialRoleDTO.ListItem\&gt; roles

 JsonNode jsonSchema,

             where in roles are two variables:

              long id,

             String name

1. /api/observationtypes/admin/addNewQuestionSet

@RequestBody

 String name

 String description

 long trailStageId;

 long trailId;

 boolean multiplicity;

 boolean withUsers;

 int position;

 List\&lt;AdminQuestionDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;();

** Return response with http status and json**

 long id

 String name

 String description

 long trailStageId;

 long trailId;

 boolean multiplicity;

 boolean withUsers;

 int position;

 List\&lt;AdminQuestionDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;();

1. GET /api/observationtypes/admin/getNewQuestionSet

Api let us get question set from chosen stage.

@RequestParam(value = &quot;id&quot;) long id

** Return response with http status and json**

 long id

 String name

 String description

 long trailStageId;

 long trailId;

 boolean multiplicity;

 boolean withUsers;

 int position;

 List\&lt;AdminQuestionDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;();



1. PUT /api/observationtypes/admin/updateQuestionSet

Api let admin update question set.

@RequestBody

 String name

 String description

 long trailStageId;

 long trailId;

 boolean multiplicity;

 boolean withUsers;

 int position;

 List\&lt;AdminQuestionDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;();

** Return response with http status and json**

 long id

 String name

 String description

 long trailStageId;

 long trailId;

 boolean multiplicity;

 boolean withUsers;

 int position;

 List\&lt;AdminQuestionDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;();



1. DELETE /api/observationtypes/admin/deleteQuestionSet

        Api let admin delete question set from a stage.

 @RequestParam(value = &quot;id&quot;) long id

  **Return response with http status and string**

**       **

1. GET /api/questions-answers

Api returns data about answer and question.

@RequestParam(value = &quot;answer\_id&quot;) long answerId

@Produces JSON

         long answerId

        String name (of observatory type)

        String description (of observatory type)

        String time

        String trialTime

        JsonNode questionSchema

        JsonNode formData

         JsonNode trialRoles

1. POST /api/questions-answers/{answer\_id:\d+}/comment

Api let observer add comment to answer.

@PathVariable(value = &quot;answer\_id&quot;) long answerId,

@RequestParam(&quot;comment&quot;) String comment

**Return null**

1. Get /api/trial-time

Api let us know server time.

**Return String with date**

1. Get /api/time-elapsed

        Api let us know local time.

**Return String with date**

1. GET /api/role

Api shows all roles in chosen trial.

            ** Return list of roles with:**

            long id

           String name

           String roleType

1. GET api/stages

Api shows all stages

          **  Return list of stages with:**

**       ** long trialId

           String name

           LocalDateTime simulationTime

1. POST api/stages/admin/addNewTrialStage

Api let us add stage to chosen trial.

@RequestBody(

long id

long trialId

String name)

**Return response with http request status and in body are:**

Long trialId

Long id

String name

1. GET api/stages/admin/trialStageWithQuestions

Api returns stage with id

@RequestParam(value = &quot;id&quot;) long id

**Return response with http request status and in body are:**

Long trialId

Long id

String name

LocalDateTime simulationTime;

List\&lt;AdminObservationTypeDTO.ListItem\&gt; questions = new ArrayList\&lt;\&gt;()

1. DELETE api/stages/admin/deleteTrialStage

Api delete a trial&#39;s stage.

@RequestParam(value = &quot;id&quot;) long id

**Return response with a http request status and a String**

**&quot;Trial Stage id =&quot; + id + &quot; is deleted&quot;.**

1. PUT api/stages/admin/updateTrialStage

Api update a trial&#39;s stage.

  long id

  long trialId

  String name

**Return**

** long id**

** long trialId**

** String name**



1. GET /api/role

Api shows all roles in chosen trial.

            ** Return list of roles with:**

            long id

            String name

            String roleType



1. GET /api/trialsessions/{trialsession\_id:\d+}

Api shows data about chosen trial session.

@PathVariable(value = &quot;trialsession\_id&quot;) long answerId

**Return json with:**

long trialId

String trialName

String trialDescription

String lastTrialStage

1. GET /api/trialsessions

           Api shows list of trial session.

Api shows all trials, which are available for logged user.

**Return list of trials with:**

long trialId

String trialName

String trialDescription

String lastTrialStage

1. GET /api/trialsessions/active

Api shows list of active trial session avaiable from logged user.

**Return list of trials:**

long trialId

String trialName

String trialDescription

String lastTrialStage

long initId

Boolean initHasAnswer

String name

1. PUT /api/trialsessions/{trialsession\_id:\d+}/changeStatus

Api let us change status of trial session.

@PathVariable(value = &quot;trialsession\_id&quot;) long trialSessionId,

@RequestParam(value = &quot;status&quot;) String status

**Return null**

1. GET /api/trialsessions/manual/{trialsession\_id}/{is\_manual}

Api let us choose if stages of trial will be change manually or automatically.

@PathVariable long trialsession\_id,

@PathVariable boolean is\_manual

**Return String**

&quot;current stage in session &quot; +trialSession.getId() + &quot; is: &quot; +trialSession.getLastTrialStage().getId() + &quot;/&quot;+trialSession.getLastTrialStage().getName()

                + &quot;  manual mode is &quot; +isManual

1. PUT /api/trialsessions

Api let change stage of trial session

@PathVariable(value = &quot;id&quot;) Long trialSessionId,

@RequestBody @Validated TrialStageDTO.MinimalItem minimalItem,

Where minimalItem is json {id:value}

**Return json**

long trialId;

            Long lastTrialStageId;

LocalDateTime startTime;

LocalDateTime pausedTime;



1. POST /api/trialsessionscreateNewSessionFile

Api let administrator create new session users and create file with their usernames and passwords.

@RequestBody NewSessionForm newSessionForm,

Where newSessionForm is a json

{long trialId

String initialStage

String prefix

String status

 List\&lt;UserForm\&gt; users}, where

UserForm is a json

{String email

List\&lt;String\&gt; role}

**Return null**

1. GET /api/trialsessions/trials

Api shows all trails, which current user is a session manager.

**Return Map(Id : Long, name : String)**



1. POST /api/trialsessionsnewSessionValues

Api returns data about trial.

@RequestParam(value = &quot;trial\_id&quot;) long trialId

**Return trial node** , in trial node we can find

trialStages, trialRoles, authUsers

1. POST  /api/trialsession/admin/addNewUserRoleSession

Api let us add user to role in trial&#39;s session.

@RequestBody

 long trialUserId

 long trialRoleId

 long trialSessionId

**Return adminUserRoleDTO**

1.  DELETE /admin/deleteUserRoleSession

Api let us delete user from trial&#39;s session.

@RequestParam(value = &quot;trialRoleId&quot;) long trialRoleId,

@RequestParam(value = &quot;trialUserId&quot;) long trialUserId,

            @RequestParam(value = &quot;trialSessionId&quot;) long trialSessionId

**            Return Response with http status and string &quot;Trial user session is deleted&quot;**



1. GET /api/ostAllQuestionsForMobile

Api returns data about questions set.

**Return List\&lt;ObservationTypeDTO.SchemaItem\&gt;**

List\&lt;Long\&gt; answersId

String name

String description

long id

List\&lt;TrialRoleDTO.ListItem\&gt; roles

JsonNode jsonSchema, where jsonSchema is schema of all questions in question set.

1. GET api/ostTrialId

Api returns id of Trial.

**Return Long**

1. GET api/ostTrialSessionId

Api returns id of Trial Session.

**Return Long**

1. GET api/ostTrialStageId

Api returns id of Trial Stage.

**Return Long**

1. POST api/admin/addNewTrial

Api let user create trial with name and default parameters

       private long trialIdString

        trialName

        String trialDescription

        String lastTrialStage

        private Boolean archived

**Return json**

**   **

**       **  long trialIdString

        trialName

        String trialDescription

        String lastTrialStage

        Boolean archived



1. POST api/admin/updateTrial

        Api let edit trial.

 Request Body AdminTrialDTO.ListItem

 long trialId;String trialName

            String trialDescription

            String lastTrialStage

            Boolean archived

**            Return json**

**       ** long trialId

            String trialName

            String trialDescription

            String lastTrialStage

            Boolean archived



1. GET api/ostTrail

Api returns active Sessions and their actual stages.

RequestParam(value = &quot;trial\_name&quot;) String trialName

Return String of actual sessions and stages.

1. GET api/stages

Api returns stages of chosen session.

@RequestParam(value = &quot;trial\_id&quot;) long trialId, Pageable pageable)

**Return**  **PageDTO\&lt;**** TrialStageDTO.ListItem\&gt;.**

In the json are:

long trialId

String name

LocalDateTime simulationTime,

  long id.

1. GET api/user

           Api shows users in chosen trial session.

           RequestParam(value = &quot;trialsession\_id&quot;) long trialSessionId, Pageable pageable

  **          Return PageDTO\&lt;TrialUserDTO.ListItem\&gt;**

**           ** In the json are:

            long id

            String firstName

            String lastName

1.  GET api/user/version

            Api shows actual version of application.

**            Return String**

1. POST api/questions/admin/addNewQuestion

Api let add question to question set.

@RequestBody

String name

String description

long observationTypeId

AnswerType answerType

int position

String jsonSchema

boolean commented

**Return response with http status and json**

**       ** long id;

         String name;

        String description;

             long observationTypeId;

 AnswerType answerType

             int position

             String jsonSchema

             boolean commented



1. GET api/questions/admin/getFullQuestion

Api let us get details about question.

@RequestParam(value = &quot;id&quot;) long id

**Return response with http status and json**

**       ** long id;

         String name;

        String description;

             long observationTypeId;

 AnswerType answerType

             int position

             String jsonSchema

             boolean commented



1. DELETE api/questions/admin/deleteQuestion

Api let us delete question from question set.

(@RequestParam(value = &quot;id&quot;) long id)

**Return response with http status and string**

&quot;Question id =&quot; + id + &quot; is deleted&quot;

1. PUT api/questions/admin/updateQuestion

Api let us update question.

@RequestBody

String name

String description

long observationTypeId

AnswerType answerType

int position

String jsonSchema

boolean commented

**Return response with http status and json**

**       ** long id;

         String name;

        String description;

             long observationTypeId;

 AnswerType answerType

             int position

             String jsonSchema

             boolean commented



### 5. Database

This database is used in OST system.

![Figure 14. Schema of database](./doc/fig14.png)

In this model ObservationType=Question Set.
