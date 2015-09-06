# Banner
made by <http://patorjk.com/software/taag/#p=display&f=Graffiti&t=doppelt>

# OAuth2

## Ermittlung des Token

### Aufruf
 
    curl http://acme:acmesecret@localhost:8080/oauth/token -H "Accept: application/json" -d grant_type=client_credentials

#### CURL-Parameter
* -d POST (HTTP)

  It's easy to post data using curl. This is done using the -d <data> option. The post data must 
  be urlencoded.
  
* -H EXTRA HEADERS

  When using curl in your own very special programs, you may end up needing to pass on your own 
  custom headers when getting a web page. You can do this by using the -H flag.

### Rückgabe

    {"access_token":"829d21e3-94f4-4bc7-b846-89b687ceeba1","token_type":"bearer","expires_in":43199,"scope":"read"}

## Greeting-Service mit Token aufrufen

Aufruf

    curl http://localhost:8080/api/greeting -H "Authorization: Bearer 829d21e3-94f4-4bc7-b846-89b687ceeba1" 
 
Rückgabe

    {"id":1,"content":"Hello, World!"}

## Referenzen
* <https://github.com/dsyer/sparklr-boot>
* <https://raymondhlee.wordpress.com/2014/12/21/implementing-oauth2-with-spring-security/>
