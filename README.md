###

# DRIVER Ost

# Table of Contents
1. [About](#About)
2. [Technologies](#technologies)
3. [Getting Started](#getting-started)
4. [Usage](#usage)
5. [Administration](#administration)
6. [API](#API)
7. [License](#license)

# About
Project meant to drive innovation in crisis management for European Resilience. Ost tool is meant to help simulate solutions in crisis management trough trials.

# Technologies
Backed was written in Spring. Frontend was written in React.

# Getting started
In root directory of this project is docker-compose.yml meant for building and running Ost frontend and backend in debug mode. There is also an installation package also in root directory which contains all that is needed to run demo on remote server.

## Prerequisites
Local environment for manual build needs:
* gradle 7.2
* nodejs 6.10

Using automated building with docker needs:
* docker
* docker-compose

## Installation
Building frontend after getting nodejs is achievable from web directory with commands:
```
npm install
npm run deploy:prod
```
After building of frontend is done it will be in web/dist directory.

To build backend go to api directory and use:
```
./gradlew -g ./cache/gradle clean assemble -i
```
Built war file after running this command will be in build/libs/driver-api-0.0.1-SNAPSHOT.war.

Running `docker compose up -d` in root directory will build and run backend and frontend id docker containers with the whole testbed for testing and debugging purposes. After running ost will be accessible under https://127.0.0.1 in the browser. If you can't log in to default admin account it probably means that backend is still setting up and needs more time for it.

# Usage
Usage is described in Documentation.pdf in root directory of this project.

# Administration
Default admin account has credentials:

login: `admin`

password: `admin`

To check backend's logs use:

```
docker logs ost_api
```

# API



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

# License

MIT License

Copyright (c) 2017 DRIVER+

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
