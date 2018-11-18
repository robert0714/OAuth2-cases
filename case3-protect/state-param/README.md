## Protecting the client against Authorization Code injection

p.381 ~ p.389

Chapter08/state-param/oauth2-provider-state

Chapter08/state-param/client-state

To better understand what's going on here, start the application, open your browser and go to the following authorization URL:

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/resource&response_type=code&scope=read+write


http://localhost:8080

'victim', '123'
'attacker', '123'

http://localhost:9000

security.user.name=adolfo
security.user.password=123


```
]$   curl -X POST "http://localhost:8080/oauth/token" --user attacker:123   \
-H "content-type: application/x-www-form-urlencoded"  \
-d  "code=m5wcA8&grant_type=authorization_code&redirect_uri=http://localhost:9000/resource&response_type=code&scope=read+write"

{"access_token":"ec1da1e2-a809-49fb-8f0a-ef84d06590d1","token_type":"bearer","expires_in":43199,"scope":"read_x"}

]$  curl -X GET http://localhost:8080/api/x -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

resource X

]$  curl -X GET http://localhost:8080/api/y -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

{"error":"access_denied","error_description":"Access is denied"}


```
