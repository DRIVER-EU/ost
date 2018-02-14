# Constraints

Mention the constraints of the technical solution:
- the app must be a progressive web app, for mobile, tablet and desktop (
- will only aim to work on modern browsers
- has a standalone server that can be run without the test-bed
- the server can communicate with the test-bed (Apache Kafka) to send and receive observations
- ...

## Observer Support Tool - Mobile
- Web browser
- Android (tablet and smartphone)
- iOS (tablet and smartphone)

![](/doc/img/obsView.png)
Sample view for User

## Observer Management Tool - Desktop
- Web browser
![](/doc/img/TMView.png)
Sample view for Trial Manager

## Server
Observer Support Tool has its own server, which can be run independently from test-bed. However, it can communicate in order to exchnage some data based on publish-subscribe pattern. 










