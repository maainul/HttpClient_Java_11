# HttpClient_Java_11

# Http/2 Client

## What is the purpose of Http/2 Client:

Http/2 client is one of the most exciting features for which developers are waiting for long time.
By using this new Http/2 client form java application we can send HTTP request and we can process HTTP response.

![http communication](https://user-images.githubusercontent.com/37740006/111470785-6a92b680-8752-11eb-9a75-0f1c4f6e9e56.png)

prior to java 9 we are using HttpURLConnection class to send HTTP Request and to process HTTP request. But there are several problem with HttpURLConnection class.

## Problems with traditional HttpURLConnection Class :

1. It is very difficult to use.
2. it supports only HTTP?1.1 protocol but not HTTP/2(2015) where
3. We can send only one request at a time per TCP connection, which creates network traffice problems and performance problem
4. It supports only text data but not binary data
5. It works only blocking mode(Synchronous Mode), which creates performance problems.

Beacuse of this problem slowly developers started using 3rd party HttpClient like Apache HttpClient and Google HttpClient etc.

JDK 9 Engineers addresses these issues and introduced a brand new HTTP/2 Client in java 9

## Advantages of Java 9 HTTP/2 Client:

1. It is lightweight and very easy to use.
2. It supports both HTTP/1.1 and HTTP/2
3. It supports both text and binary data(Stream).
4. It can work in both blocking and Non-blocking Modes(Synchronous communication and Asynchronous Communication)
5. It provides better performance and scalability when compared with traditional HttpClientConnection


## Important Components of Java 9 HTTP/2 Client :

In java 9 Http/2 client provides as incubator module

Module : jdk.incubator.httpclient
Package : jdk.incubator.http

Mainly 3 important Classes are available 

1. HttpClient
2. HttpRequest
3. HttpResponse

Note :

Incubator module is by default not available to our java application. Hence compolsory we should read explicitly by using requires directive


```java
module demoModule{
	requires jdk.incubator.httpclient;
}
```


## Steps to Send Http Request and Process Http Response from java Application:

1. Create HttpClient Object
2. Create HttpRequest Object
3. Send HttpRequest by using HttpClient and get the HttpResponse
4. Process HttpResponse


1. Creation of HttpClient Object :

We can use HttpClient object to send HttpRequest to the web server. We can create HttpClient object by using factory method : newHttpClient()

	HttpClient client = HttpClient.newHttpClient();

2. Creation of HttpRequest Object :

We can use HttpRequest object ad follows :

  String url  = "http://www.testwebsite.com";
  
  HttpRequest req = HttpRequest.newBuilder(new URL(url)).GET.build();


Note :

newBuilder() method returns Builder object
GET() method sets the request method of this builder to GET
build() method builds and returns a HttpRequest

  public static HttpRequest.Builder newBuilder(URL uri)
  public static HttpRequest.Builder GET()
  public abstract HttpRequest build()

3. Send HttpRequest by using HttpClient and Get the HttpResponse :

HttpClient contains the following methods:

1. send() to send synchronous request(blocking mode)
2. sendAsync() to send asynchronous Request(Non blocking mode)

  HttpResponse res = client.send(req,HttpResponse.BodyHandler.asString());

  HttpResponse res = client.send(req,HttpResponse.BodyHandler.asFile(Paths.get("abc.txt")));

Note : 

BodyHandler is a functional Interface present inside HttpResponse. It can be used to handle body of th HttpResponse.

4. Process HttpResponse :

HttpResponse Contains the status code, response header, and body

				_________________
				|Status line     |
				|________________|
				|Response Header |
				|________________|
				|ResponseBody    |
				|________________|

1. Status line /Status Code():

Returns status code of the response

1XX - Information
2XX - Successful
3XX - Redirection
4XX - Client Error
5XX - Server Error

2. body:

Return body of the response

3. Headers :
Returns header information of the response


# Complete program for HttpClient:

Request a specific url and retrieve url data to the console

~~Project Structure~~

java project :
	|_____src
			|____ demoModule
					|________module-info.java
					|
					|________packA
								|____HttpTestDemo

```java
module demoModule{
	requires jdk.incubator.httpclient;
	
	
}
```


```java
package packA;

import java.net.URI;
import java.util.List;
import java.util.Map;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpTestDemo {

	public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient;

		String url = "http://www.redbus.in/info/aboutus";

		HttpRequest req = HttpRequest.newBuilder(new URI(url))
									 .version(HttpClient.Version.HTTP_2)
									 .GET()
									 .build();

		HttpResponse resp = client.send(req, HttpResponse.BodyHandler.asString());

		System.out.println("Status Code : " + resp.statusCode());
		System.out.println("Response Body :" + resp.doby());

		HttpHeaders header = resp.headers();

		Map<String, List<String>> map = header.map();

		System.out.println("Response Headers");

		map.forEach((k, v) -> System.out.println("\t" + k + ":" + v));

	}

}
```
## But is java 11 incubator is remove and replaced with java.net.Http


## Major Changes as Part of  JEP 321

1. The incubated HTTP API from Java 9 is now officially incorporated into the Java SE API. The new HTTP APIs can be found in ~~java.net.HTTP.*~~
2. The newer version of the HTTP protocol is designed to improve the overall performance of sending requests by a client and receiving responses from the server. This is achieved by introducing a number of changes such as stream multiplexing, header compression and push promises.
3. As of Java 11, the API is now fully asynchronous (the previous HTTP/1.1 implementation was blocking). Asynchronous calls are implemented using  CompletableFuture.The CompletableFuture implementation takes care of applying each stage once the previous one has finished, so this whole flow is asynchronous.
4. The new HTTP client API provides a standard way to perform HTTP network operations with support for modern Web features such as HTTP/2, without the need to add third-party dependencies.
5. The new APIs provide native support for HTTP 1.1/2 WebSocket. The core classes and interface providing the core functionality include:
	
	The HttpClient class, java.net.http.HttpClient
	
	The HttpRequest class, java.net.http.HttpRequest
	
	The HttpResponse<T> interface, java.net.http.HttpResponse
	
	The WebSocket interface, java.net.http.WebSocket


So instead of jdk.incubator.httpclient we have to replce with this:


in Pre Java 11 we had to do something like this:

	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());

Which we can now simplify as:

	HttpResponse<String> response = client.send(request, BodyHandlers.ofString());


~~Project Structure~~

java project :
	|_____src
			|____ demoModule
					|________module-info.java
					|
					|________packA
								|____HttpTestDemo

## module-info.java

```java
module demoModule{
	requires java.net.Http;
	
}
```

## HttpTestDemo.java

Repl



```java
package packA;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class HttpTestDemo {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		HttpClient client = HttpClient.newHttpClient();

		String url = "http://www.redbus.in/info/aboutus";

		HttpRequest req = HttpRequest.newBuilder(new URI(url))
									 .GET()
									 .build();

		HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
		System.out.println("Status Code : " + resp.statusCode());
		System.out.println("Response Body :" + resp.body());

		HttpHeaders header = resp.headers();

		Map<String, List<String>> map = header.map();

		System.out.println("Response Headers");

		map.forEach((k, v) -> System.out.println("\t" + k + ":" + v));

	}

}
```


