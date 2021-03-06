# Poor man's logging for inter Microservice requests.

In a microservice system, it s often not clear which way a specific user request took and debugging can be a nightmare.
This module helps you in assigning a unique identifier to a request and passing it along. The identifier is stored in the MDC and can be used in logback as a user variable in the log pattern.

Dependency:

```xml
<dependency>
  <groupId>de.codeboje</groupId>
  <artifactId>request-logging</artifactId>
  <version>0.0.1</version>
</dependency>
```

Usage:

Filter:

1. Add the RequestContextLoggingFilter as a Servlet Filter
2. add _requestId_ as a user variable to your logback log pattern
3. Send the request header _X-REQUEST-ID_ with each request

Interceptor:

1. Add the RequestContextLoggingInterceptor to your Spring RestTemplate
2. Set _requestId_ in the MDC or use an autogenerated value per request
