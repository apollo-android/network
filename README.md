#Apollo-lib

##Network

###GET

```java
Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

HttpRequestConfig config = builder.get();

Http.send(config);
```

###POST

```java
Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts");

JSONObject json = new JSONObject();
	
json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.post(json.toString());

Http.send(config);
```
###PUT

```java
Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

JSONObject json = new JSONObject();

json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.put(json.toString());

Http.send(config);
```

###DELETE

```java
Http.Builder builder = new Http.Builder("http://jsonplaceholder.typicode.com/posts/1");

HttpRequestConfig config = builder.delete();

Http.send(config);
```
