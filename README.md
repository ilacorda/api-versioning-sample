# API Versioning sample
This is a project created to show how we version our APIs using the "accept" header and a "Accept-Version" custom header.

## TUI DX reference architecture for API versioning
This is what is defined in our API design reference architecture:

**8.4. Must:  Use the Accept & the Accept-Version headers**

There is a well-known HTTP header called Accept which is sent on a request from a client to a server. For instance 

**Accept: application/json** 

This notation is saying that I, the client, would like the response to be in json, please. 

For reactive communication you can use:

**Accept: application/stream+json  or  Accept: text/event-stream**

For defining the version a custom header will be used.The accept headers is using this header to make up your own resource types, for example: 

**Accept-Version: vnd.myapi.v2**

Using headers is more correct for many reasons: it leverages existing HTTP standards, it’s intellectually consistent with Fielding’s vision, it solves some hard real-world problems related to inter-dependent APIs, and more.

Example of versioning strategy:
```
http://company.com/api/customer/123
===>
GET /customer/123 HTTP/1.1
Accept: application/xml
Accept-Version: vnd.myapi.v2
  
<===
HTTP/1.1 200 OK
Content-Type: application/xml
<customer>
  <name>Neil Armstrong</name>
</customer>
```
## How is implemented

We have 2 controllers, one for each version:
- FooControllerV1
- FooControllerV2

For the first version we defined a **/foo** resource with the **GET** verb that can be called in two ways:

- Setting the **"accept"** header to "**application/json**" as usual
```java
    @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      }
    )
    public ResponseEntity<?> retrieve() {
        return new ResponseEntity<>(new FooV1DTO(), HttpStatus.OK);
    }
```
- Setting the **"accept"** header to "**application/stream+json**" for reactive communication
```java
    @GetMapping(
      path = "/foos/",
      produces = {
          MediaType.APPLICATION_STREAM_JSON_VALUE
      }
    )
    public Flux<?> retrieveReactive() {
        return Flux.just(new FooV1DTO());
    }
```
For versioning we added a custom header called **"Accept-Version"** that must be provided. 
We put it at Controller level because we don't version at resource level.
```java 
    @RestController
    // FooV1DTO.MEDIA_TYPE value is "vnd.api-versioning-sample.v1"
    @RequestMapping(value = "/", headers = "Accept-Version=" + FooV1DTO.MEDIA_TYPE)
    @Api(
        value = "Foo Entity",
        description = "Foo operations",
        tags = {"Foo Entity"},
        basePath = "/"
    )
    public class FooControllerV1 {
```

So we have two ways to get the foo resource with version 1:
- **Expecting a json response:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/json" -H "Accept-Version: vnd.api-versioning-sample.v1"```
- **Expecting a reactive json stream:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/stream+json" -H "Accept-Version: vnd.api-versioning-sample.v1"```

Both of them will return:

```json 
{
  "text" : "Version-1"
}
```

For the version 2 we have a FooControllerV2 extending FooControllerV1 and setting the **"Accept-Version"** header to the new version.  
```java
@RestController
@RequestMapping(value = "/", headers = "Accept-Version=" + FooV2DTO.MEDIA_TYPE)
@Api(
    value = "Foo Entity",
    description = "Foo operations",
    tags = {"Foo Entity"},
    basePath = "/"
)
public class FooControllerV2 extends FooControllerV1 {
```

Extending the controller from the previous version, all methods that are not overrided will return the same as the previous version.
In the version 2 we override the **GET /foo** with **"Accept=application/json"** returning the FooV2DTO
```java
@GetMapping(
  path = "/foos/",
  produces = {
      MediaType.APPLICATION_JSON_VALUE
  }
)
@Override
public ResponseEntity<?> retrieve() {
    return new ResponseEntity<>(new FooV2DTO(), HttpStatus.OK);
} 
```
So we have two ways to get the foo resource with version 2:
- **Expecting a json response:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/json" -H "Accept-Version: vnd.api-versioning-sample.v2"```
- **Expecting a reactive json stream:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/stream+json" -H "Accept-Version: vnd.api-versioning-sample.v2"```

The **"accept: application/json"** will return v2 DTO because this method is overrided:

```json
{
  "text" : "Version-2"
}
```
The **"accept: application/stream+json"** will return v1 DTO because this method is not overrided:

```json 
{
  "text" : "Version-1"
}
```

### Configure generation of swagger with OAS3
In this sample we use springdoc-openapi-webflux-ui library to generate the Swagger with OAS3. In order to do that, is needed to use **io.swagger.v3.oas.annotations** in DTOs and controllers.

A folder for each version is created in **com.tui.apiversioningsample.ws.controller**. We must remind that the versioning is done by application, not by each controller.

In the com.tui.apiversioningsample.ws.configuration.OpenApiConfig we do the following:

```java
 @Bean
   public OpenAPI customOpenAPI() {
     return new OpenAPI()
         .components(new Components())
         .info(new Info().title(applicationName).description(description).version(version));
   }
 
   @Bean
   public GroupedOpenApi v1() {
         return GroupedOpenApi.builder().setGroup(FooV1DTO.MEDIA_TYPE).packagesToScan("com.tui.apiversioningsample.ws.controller.v1")
         .build();
   }
   @Bean
   public GroupedOpenApi v2() {
     return GroupedOpenApi.builder().setGroup(FooV2DTO.MEDIA_TYPE).packagesToScan("com.tui.apiversioningsample.ws.controller.v2")
         .build();
   }
```

A GroupedOpenAPI bean is created for each version, pointing to tha package where that version controllers are. Doing this, the generated swagger will have a definition for each version.



## Getting started
1. Compile the project using maven.
    ```
    mvn clean package -U
    ```
2. Run the jar
    ```
    java -jar target/api-versioning-sample-exec.jar
    ``` 
3. Make the calls to the **/foo** resource
    - Version 1
        - **Expecting a json response:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/json" -H "Accept-Version: vnd.api-versioning-sample.v1"```
        - **Expecting a reactive json stream:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/stream+json" -H "Accept-Version: vnd.api-versioning-sample.v1"```
    - Version 2
        - **Expecting a json response:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/json" -H "Accept-Version: vnd.api-versioning-sample.v2"```
        - **Expecting a reactive json stream:** ```curl -X GET "http://localhost:8080/foos/" -H "accept: application/stream+json" -H "Accept-Version: vnd.api-versioning-sample.v2"```
4. You can also access to the swagger: http://localhost:8080/swagger-ui.html

## Authors
- Antoni Bujosa