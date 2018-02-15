# Use cases and actors

Observer Support Tool’s aim is to  collect observations, inform observers about trial progress and visualize collected data. There are different perspective to look at this tool.  Main user, who uses the tool to send his observations is called Observer. From the other side there exists Trial Manager, he focuses on collected data and analysing it.  Each of them has their own functionalities provided by OST. 

## Use cases

### Use case: Observer
![](./img/use-cases-observer.png)

### Use Case: Trial Manager
![](./img/use-cases-trial-manager.png)


## Actors

### Observer

Observer has a possibility to log into the app or register by entering an email and password. Next view he has displayed is view with trials. When he selects one, he sees the description and the name and then all events that have already taken place in that trial. Each event has time with date, name and short description. On the bottom of the screen there is a button, pushing on it gives Observer the opportunity to add new Observation. Observations are grouped into the types which  describe shortly and categorise all questions. Observation Types can give much more effective analyse of data that will be collected. Observer can then read question from selected type, enter the time he made observation and select which participants have been observed. Questions may have varied answers types – checkboxes, radio buttons, text fields or sliders. What is more, if Observer wants he can add something like pictures, voice record, location and some additional description. 

### Trial Manager

Trial Manager is a person who is responsible for managing not only the event but also the user. He has different view for OST than Observer, he uses Desktop version of a tool. Trial Manager is responsible for confirming user account and assigning registered users to a role in a trial. The role may be the Observer or Participant. He also has to fill in list of Participants to be observed, assign Participants to Observer roles and send messages to them. If it comes to managing an event, Trial Manager creates observation types and groups them into packages. What Trial Manager have displayed is received observations,  messages which has been send in table  and locations of collected observations on the map. What is more, he can see the summary of observations in time on a diagram and change the range of time if it is needed. There is also a table with simple summary with observations, which present collected data about them.  To make analysing more comfortable, Trial Manager can export collected data to CSV file. 
#### Trial Manager interface:
1. Summary of Observations - collected data about observations are presented in the table. It indicates observer’s role and name. It also shows observation type for each user, observation that was sent and some attachments. Trial Manager can filter results by typing only these data that he wants to obtain in his table.
2. Summary of Observations in time - it is a diagram which presents number of recieved observations in time. Trial Manager can change the range of it and for example have displayed observations in particular hour or in range of 15 minutes. 
3. Events/Messages sent to Observers - table with time that event/message was sent, user who recevied it, his role and content of a message. There is possibility, that Trial Manager sends data to all users that he manages. 



