# Scrobbler_frontend
Scrobbler from your Spotify application, which shows music history, creates statistics and shows lyrics to currently played track. Used API:

Spotify: https://developer.spotify.com/


APISEEDS: https://apiseeds.com/


Backend for this project available at: https://github.com/kingkara/Scrobbler_backend

DESCRIPTION:
1. First clone or download this and backend repositories (link above).

2. Backend project runs on http://localhost:8088 (if you need to change it, you can do it in application.properties file).

3. Frontend runs on http://localhost:7077/home (again if you need to change, do it in application.properties file).

4. To run application in full functionality you need to start backend project first, after its initiation you can run frontend project and
  go to http://localhost:7077/home where you will see main view of the application.
  
5. Its only demo version which operates on authors Spotify account, cause every user needs to create their REFRESH_TOKEN at their site
  (to personalize data with your account go to: https://developer.spotify.com/documentation/general/guides/authorization-guide/
  and change in SpotifyAuthorize class values clientId, clientSecret, refreshToken with the values you get from authorization process).
  
6. At the start application gets last 50 played track (its maximum number you can get from your history) and in every two minutes of its
working apllication refresh this data, so adding new tracks is possible.

7. In this app you can:
- see info and lirycs to currently played track
- add, edit and delete users
- see and edit users library (top played tracks and artists, lastly played tracks, all your tracks and artist)
- search tracks from all database (tracks from all users) and comment them
- search artists from all database


REQUIRES: Java, gradle, any internet browser (recomended Google Chrome)

DEVELOPMENT:
This kind of application allows to create many functionalities. In the future author thinks about addidng photos of every artist.
Creating lirycs to songs which are not available at APISEEDS lyrics and adding them to app database (then every lyrics from outside api can
be added to database too).
Add informations about albums and year of publishing.
And many more... Once again this is only demo version for study purpose so author focused on basic functionalities in the time which was given.
