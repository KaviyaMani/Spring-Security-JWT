# Spring Security using JWT 
### JWT - JSON Web Tokens
    Token Based Authentication
    Flexible and stateless way to verify users identity and securing API's. 
    JSON Web Tokens consist of three parts separated by dots (.), which are: Header, Payload, Signature
    JSON is Base64Url encoded 
    Header - contains type of token and signing algorithm used { "alg": "HS256", "typ": "JWT" }
    Payload - contains claims i.e) user details+additional data(issued at, expiry) { "sub": "admin@gmail.com","name": "John Doe", "admin": true, "iat": 1727332040, "exp": 1727333840 }
    Signature - Cryptographic signature to ensure integrity contains encoded header, encoded payload, secret and the algorithm specified in header and sign that. 
    You can decode the JWT token using online decoder and check the user details. Token does not have password since token is created only after successful authentication. 
    In additional requests tokens are validated for integrity and expiry. If token is not valid we have to generate the token again and use it.
### Concept behind: 
    Instead of sending user details again and again with the request using JWT we will generate token which can be sent with the request.
    Register user -> generate token using credentials -> call api's using token -> If invalid -> generate token again and use

### Steps involved
    1. Add web, jpa, security, h2 dependencies
    2. Add JSON Web Token dependencies
    3. Add entity and repository for User
    4. Add CustomUserDetails and CustomUserDetailsService for checking credentials
    5. Add JwtFilter which extends OncePerRequestFilter and do filtering using the help of JwtService
    6. Add JwtService - utility class to generate token, extract details from token or validate token used by JwtFilter
    7. Add Security config using SecurityFilterChain
       * permit all users for adding user, generating token
       * Register custom authentication provider(which registers our CustomUserDetailsService for authentication)
       * Register JwtFilter in the security filter chain using addFilterBefore and with the type of authentication

Generate Token flow -
![img.png](img.png) | width=100

Validate Token flow - 
![img_1.png](img_1.png) | width=100


Testing:
Add User - 
curl --location 'http://localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"admin",
"password": "admin",
"email": "admin@gmail.com",
"roles": "ADMIN"
}'
/
Generate Token - 
curl --location 'http://localhost:8080/generateToken' \
--header 'Content-Type: application/json' \
--data-raw '{
"userName":"admin@gmail.com",
"password": "admin"
}'
/
Use token to access api - 
curl --location 'http://localhost:8080/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MjczMzIwNDAsImV4cCI6MTcyNzMzMzg0MH0.6mugvucuUVpFKan89O5Sf3ju7ppsNzq-xw6HUhXe5MU' \
--data ''