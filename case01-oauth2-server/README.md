自訂Authentication server的authentication方式（目標是多種的authentication）

登入，認證通過則會取得auth_code
http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile



在時間限制內使用auth_code取token
curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  "content-type: application/x-www-form-urlencoded" -d   "code=u9XR2x&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_profile"
 
 
 
使用token存取resource( microservices)
curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer 3939942a-877a-4bc9-991f-7763137853ed"


拆掉authentication server ,resource server就無法使用