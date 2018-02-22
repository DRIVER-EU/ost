For the full specifications, please go [here](https://driver-eu.gitbooks.io/specification-of-the-online-observer-support-tool).

# Online Observation Support Tool
An online Observation Support Tool. Part of the DRIVER+ Test-bed software intended to gather observations during a Trial.
Observation Support Tool is the part of the DRIVER+ Test-bed software intended to gather observations during a Trial.

It will allow a registered observer to input their observations to the application defining the context e.g. type of event it relates to, phase of the crisis, type of the problem, its importance, and specifying the observed issue itself possibly indicating other related observations from the system database. An example of context-building data would be scenario description and events description. It will attach automatically an author of the observation, a timestamp, location. An observer will be able to illustrate the observation by taking picture, recording video or audio. All the data will be stored in the central database.

OST will be also available using Internet browser on the desktop computer or laptop. A registered Analyst will be able to access the gathered observations in the tabular form, orchestrate them on the timeline and/or a situation map group them and develop lessons learned out of them. An analyst will be also able to formulate recommendations as the final stage of the process. An application will allow to generate the reports containing observations, lessons as well as recommendations.

## Definitions

<dl>
<dt><b>Build-mode</b></dt>
<dd>Phase in which a Trial is being prepared. In this mode the OST is configured by the Observation Manager for use in a specific Trial.</dd>
<dt><b>Observation Form</dt>
<dd>Form in the Observer UI that is dynamically composed containing the one or more Observation Types which are being observed, based on the Templates of these Observation Type(s), section to mark which of the Participants the Observation applies to and room to add free text and/or photo/video/audio.   </dd>
<dt>Observation Manager</dt>
<dd>Person who configures the OST up front of a Trial, manages all Observers during the Trial and has overall control over the OST system during a Trial.</dd>
<dt>Observation Manager UI</dt>
<dd>The OST’s User Interface available for Observation Managers (i.e. in Build-, Trial- and potentially also in Review-mode)</dd>
<dt>Observation Type</dt>
<dd>A specific thing that can be observed during a Trial (e.g. a behaviour, communication outing, or use-case of a solution), can be rated/answered (e.g. “did occur”, “yes”/”no”, “poor”/”average”/”good” or assessment in free text) and can be assigned to one or more Participants.</dd>
<dt>Observation Type Template</dt>
<dd>Template of a Form for a specific Observation Type, which includes how to rate/answer the Observation Type and to which Participant Roles it is likely to be assigned to.</dd>
<dt>Observer</dt>
<dd>Person observing during a Trial regarding how the crisis is managed and/or how a solution performs and/or how the Trial is run.  </dd>
<dt>Observer Role</dt>
<dd>Role of an Observer indicating which (group of) Participant(s) he/she is observing (e.g. City hall crisis room, On scene command team, Police station officers, etc).</dd>
<dt>Observer UI</dt>
<dd>The OST’s User Interface available for Observers (i.e. only in Trial-mode)</dd>
<dt>OST</dt>
<dd>Observation Support Tool. Part of the DRIVER+ Test-bed software intended to gather observations during a Trial.</dd>
<dt>OST Configuration</dt>
<dd>The configuration of OST of Observer Roles, Participant Roles, the links between these Roles,  Observation Type Templates, et cetera, specific for one Trial. An OST Configuration can be saved under a specific name and re-opened, edited, re-saved and used in multiple Trial Sessions.</dd>
<dt>Participant</dt>
<dd>Person participating as end-user/practitioner in a Trial Session and using the OST during Trial-breaks and in the evaluation phase to answer observation types (i.e. questions) about the trial, the crisis management performance and/or the solution(s) used. </dd>
<dt>Participant Role</dt>
<dd>Role (in the theatrical definition of an actor’s role) that is taken up by a Participant (e.g. Fire chief, Police Officer, Mayor, etc).</dd>
<dt>Participant UI</dt>
<dd>The OST’s User Interface available for Participants (i.e. only in breaks in Trial-mode and in Review-mode)</dd>
<dt>Phase specific Observation Type</dt>
<dd>An Observation Type that can be observed only during certain phase(s) of the Trial Session. During the progression of a Trial Session, Phase specific Observation Types therefore appear and disappear in the Observer UI.</dd>
<dt>Review-mode</dt>
<dd>Phase in which a Trial is being evaluated. In this mode the OST could be used by the Observation Manager and all Observers.</dd>
<dt>Static Observation Type</dt>
<dd>An Observation Type that can be observed during the entire Trial Session. Static Observation Types are thus always shown in the Observer UI.</dd>
<dt>Trial-mode</dt>
<dd>Phase in which a Trial is being executed. In this mode the OST is used by both the Observation Manager and all Observers.</dd>
<dt>Trial Event</dt>
<dd>Message from the Trial Manager to the OST that the Trial’s scenario timeline has entered a specific phase. This might trigger the appearance/disappearance of certain groups of Phase specific Observation Types in the Observer UI.</dd>
<dt>Trial Session</dt>
<dd>A specific run of a Trial Scenario at a specific date-time and with a specific set of Participants, Observers and other staff.</dd>
</dl>
