# Banner
made by <http://patorjk.com/software/taag/#p=display&f=Graffiti&t=doppelt>

# OAuth2

## Ermittlung des Token

Aufruf
 
    curl -H "Accept: application/json" my-client-with-secret:secret@localhost:8080/oauth/token -d grant_type=client_credentials
 
Rückgabe

    {"access_token":"829d21e3-94f4-4bc7-b846-89b687ceeba1","token_type":"bearer","expires_in":43199,"scope":"read"}

## Greeting-Service mit Token aufrufen

Aufruf

    curl -H "Authorization: Bearer 829d21e3-94f4-4bc7-b846-89b687ceeba1" localhost:8080/api/greeting
 
Rückgabe

    {"id":1,"content":"Hello, World!"}

## Referenzen
* <https://github.com/dsyer/sparklr-boot>
* <https://raymondhlee.wordpress.com/2014/12/21/implementing-oauth2-with-spring-security/>
