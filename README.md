# Newtypes Tapir Example

The goal of this project is to showcase how we can use newtypes with tapir to:
* Enhance type descriptions
* Add a basic implementation of validation rules
* Reference field types to external documentation

The used dependencies are
[sprout (newtypes)](https://github.com/lorandszakacs/sprout), 
[tapir](https://github.com/softwaremill/tapir), 
[http4s](https://github.com/http4s/http4s), 
[cats](https://github.com/typelevel/cats), 
[cats-effect](https://github.com/typelevel/cats-effect) & 
[circe](https://github.com/circe/circe).

To run the project just do `sbt run`. You should see:

```bash
INFO  yyyy-MM-ddTHH:mm:ss.SSSZ o.h.b.c.n.NIO1SocketServerGroup - Service bound to address /127.0.0.1:9001 
INFO  yyyy-MM-ddTHH:mm:ss.SSSZ o.h.b.s.BlazeServerBuilder - http4s v0.23.6 on blaze v0.15.2 started at http://127.0.0.1:9001/ 
INFO  yyyy-MM-ddTHH:mm:ss.SSSZ busymachines.Main - Swagger at http://localhost:9001/api/public/swagger 
INFO  yyyy-MM-ddTHH:mm:ss.SSSZ busymachines.Main - Redoc at http://localhost:9001/api/public/redoc  
```

You can now check the documentation at 
[localhost:9001/api/public/docs/index.html](http://localhost:9001/api/public/docs/index.html).  
