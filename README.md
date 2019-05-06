# Redis client for java

This redis client library is a simple way to communicate with your redis server using TCP/IP socket!
 
This README is in WIP but you can easily use by following the java doc and your IDE intuition! :smile:

## How to install ?

Add the dependency in the `pom.xml` of your project

```xml
<dependency>
    <groupId>fr.lefuturiste.redis</groupId>
    <artifactId>redis-client</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

## Simple example 

```java
import fr.lefuturiste.redis.RedisClient;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        RedisClient client = new RedisClient("127.0.0.1", 6379);
        client.set("hello", "world"); // true
        client.get("hello"); // world
        client.del("hello"); // true
    }
}
```
