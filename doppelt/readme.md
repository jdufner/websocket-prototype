# Banner
made by <http://patorjk.com/software/taag/#p=display&f=Graffiti&t=doppelt>

# OAuth2

## Ermittlung des Token

### Aufruf
 
    curl http://acme:acmesecret@localhost:8080/oauth/token -H "Accept: application/json" -d grant_type=password -d username=dave -d password=secret

#### Client-Credentials

Die Client-ID (acme) und das Client-Secret (acmesecret) sind im AuthorizationServer konfiguriert.

#### Username und Password

Der Benutzername (dave) und das Passwort (secret) sind in nicht mehr application.properties konfiguriert, sondern in der AuthenticationManagerConfiguration.

#### CURL-Parameter
* -d POST (HTTP)

  It's easy to post data using curl. This is done using the -d <data> option. The post data must 
  be urlencoded.
  
* -H EXTRA HEADERS

  When using curl in your own very special programs, you may end up needing to pass on your own 
  custom headers when getting a web page. You can do this by using the -H flag.

### Rückgabe

    {"access_token":"accdb825-9d99-4d33-9749-c097a4553706","token_type":"bearer","refresh_token":"d8990ea1-3656-4e94-8b9e-3d975df0255b","expires_in":59,"scope":"read"}
    
## Erneuerung des Tokens

### Aufruf

    curl http://acme:acmesecret@localhost:8080/oauth/token -H "Accept: application/json" -d grant_type=refresh_token -d refresh_token=d8990ea1-3656-4e94-8b9e-3d975df0255b
    
### Rückgabe

    {"access_token":"2dc53b92-4fd1-43db-be66-e6cb1cc0b508","token_type":"bearer","refresh_token":"d8990ea1-3656-4e94-8b9e-3d975df0255b","expires_in":59,"scope":"read"}

## Greeting-Service mit Token aufrufen

Aufruf

    curl http://localhost:8080/api/greeting -H "Authorization: Bearer 829d21e3-94f4-4bc7-b846-89b687ceeba1" 
 
Rückgabe

    {"id":1,"content":"Hello, World!"}

## Referenzen
* <https://github.com/dsyer/sparklr-boot>
* <https://raymondhlee.wordpress.com/2014/12/21/implementing-oauth2-with-spring-security/>

# Programmierung

## Remote-Debugging mit Maven

Maven läuft in einem eigenen Programm. Debugging ist nur "remote" möglich. Der Befehl für Unit- und Componenttests lautet:

    mvn -Dmaven.surefire.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -Xnoagent -Djava.compiler=NONE" verify

Der Befehl für API- und Integrationstests lautet:

    mvn -Dmaven.failsafe.debug="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -Xnoagent -Djava.compiler=NONE" verify

