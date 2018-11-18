## Protecting Resource Server with scope validation
p.372  ~ p.375

Chapter08/scope-validation

To better understand what's going on here, start the application, open your browser and go to the following authorization URL:

*http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_x+read_y*



Then click on Authorize and retrieve the *Authorization* Code that will be present on the browser address bar (the parameter is *code* as you might already know). Request an access token using this Authorization Code as follows:

```
]$   curl -X POST "http://localhost:8080/oauth/token" --user clientapp:123   \
-H "content-type: application/x-www-form-urlencoded"  \
-d  "code=ZODTbc&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_x"

{"access_token":"41b8dff9-e542-4be7-8895-f3a52bb745a6","token_type":"bearer","expires_in":43199,"scope":"read_x"}

]$  curl -X GET http://localhost:8080/api/x -H "authorization: Bearer 41b8dff9-e542-4be7-8895-f3a52bb745a6 "

resource X

]$  curl -X GET http://localhost:8080/api/y -H "authorization: Bearer 41b8dff9-e542-4be7-8895-f3a52bb745a6 "

{"error":"access_denied","error_description":"Access is denied"}
