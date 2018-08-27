# Requirements

## Class Model

https://drive.google.com/file/d/1pU1wFpAuTKMu_bh-PEDvUF47ANxzusgk/view

Below there are described particular tables of OST Database. 

- Trial - main class representing particular Trial described by ID, name, description and language_version. Additional attribute of this table is boolean id_defined and init_id - this attibute shows which questionnaire will appear as a first - for example this one connected with Metrics. 

- Trial Stage - table representing data about stages of a Trial. They are described by TrialID, name and simulationTime.

- Trial Session - one Trial includes many sessions. Trial Session is described in DB by trail_ID, startTime, status (ACTIVE,SUSPENDED, END), pausedTime and last_trial_stage_id.

- ObservationType - this class represents Questionnaires. Each questionnaire is assigned to the Trial and TrialStage. It is defined by description and name. There is also boolean attribute - multiplicity, which says if that particular obvservationType appears to the user only once, or more. Next boolean attribute is with_users. It says whether in that ObservationType user has to mark which Participants he was observing.

- Question -  particular question in each Questionnaire. It is described by observationType_ID, name, description and answer_Type (TEXT_FIELD, RADIO_BUTTON). It is possible that Question has a text field, where user can enter a comment. It is defined by boolean attibute - commented. 
Each question is saved in  JSON_SCHEMA Form. 

EXAMPLE: 

{"title":"The number of participants involved in the Trial was adequate to given tasks and enough to evaluate the solutions and their impact on the crisis management.","description":"","type":"string","enum":["Strongly disagree   ","Disagree", "Neutral", "Agree ", "Strongly agree   ", "Not applicable (add rationale for that)"],"required":true}


- Answer - this table represents filled questionnaire - answers for each questions. Answers  are also defined in JSON_SCHEMA Form. They are also described by TrialSession_ID, TrialUserID, simulationTime, sent_simulation_Time, observationTypeID and comment. 
Attribute "delete_comment" saves data connected with removing this answer.

- Event - this table saves data about events sent by Trial Manager to the User. It is defined by TrialSession_ID, description, language_version, name, event_time, trial_user_id, trial_role_id. 

- Auth_user - data about users and their accounts.
- Trial_user - maps table auth_user to real users of an application.
- Trial role - stores data about user roles. Role is defined by Trial_ID, name and role_type (OBSERVER, PARTICIPANT).
- Trial_role_m2m - this table shows assignment of participants to the observers. 
- Answer_trial_role - this table stores data about which Participants markes Observer in particular answer. 
- Attachment - each answer can have additional attachment added by user. Attachment may be a picture, sound or localization. In database, attachment is described by URI, Type, Description, longitude, latitude and altitude. 
- ObservationType_TrialRole - this table shows assigment of observationType to roles. 
- Trial Sesssion Manager - stores data about which user is a Trial Manager in particular session. 
- User_role_session - table shows which user has which role in particular session. 






