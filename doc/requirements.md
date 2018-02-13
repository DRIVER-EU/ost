# Requirements

## Class Model

![](/doc/img/class-diagram.png)

## User
User Class stores data about each user of an Observer Support Tool. User is always assigned to a particular Role, which can be Observer or Participant. 
Except the fact, that User is connected with Observation, there is also association between Observation and Role, which refers to a view with Observation, where Observer can choose which Participants he observes. 
If it comes to connection Question – Role, it means that both Observers and Participants can send their observations and answer questions. 
## Trial
Trial has a description and name. Each stage of trial is an event, which are being added in time. 
Assigning roles and creating questions occur on trial level. 
## Event
Event is a particular stage of scenario. It has following attributes: 
•	Name
•	Description
•	Time
•	Trigger
Each event is assigned to a trial and many question with question types are assigned to an event. User will have displayed a notification when a new event is added. That is why, event is a trigger. It is a key, because each new event has influence on generated questions. So, questions are very strongly dependent on time. 
Trigger in Event Class on a diagram is a key, unique ID which can affect changing questions. 
All data about events can be send do tool database from external system – Test-bed.  
## Questions
Question is described by name, short description and Answer Type. Following Answer Types exist: 
•	Checkboxes
•	Radio buttons
•	Text field 
•	Sliders
Each answer types determines a value of an answer. Observations that are filled by Observer dependent on answer type are stored in QuestionItem Class.
To observation user can also add some attachments which also have types:
•	Location
•	Picture 
•	Voice
•	Description
