Apollo-lib
==========


Android libraries to make your life easier!


Network 0.0.4
-------------

**Gradle**

```groovy
compile 'com.apollo-lib:network:0.0.4'
```

####HTTP

#####GET

```java
Http.Builder builder = new Http.Builder("http://dummyhost.com/posts/1");

HttpRequestConfig config = builder.get();

Http.send(config);
```

#####POST

```java
Http.Builder builder = new Http.Builder("http://dummyhost.com/posts");

JSONObject json = new JSONObject();
	
json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.post(json.toString());

Http.send(config);
```

#####PUT

```java
Http.Builder builder = new Http.Builder("http://dummyhost.com/posts/1");

JSONObject json = new JSONObject();

json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.put(json.toString());

Http.send(config);
```

#####DELETE

```java
Http.Builder builder = new Http.Builder("http://dummyhost.com/posts/1");

HttpRequestConfig config = builder.delete();

Http.send(config);
```

#####UPLOAD

```java
byte[] file = getFile();

Http.Builder builder = new Http.Builder("http://dummyhost.com/images");

HttpRequestConfig config = builder.upload(file);

Http.send(config);
```

#####HEADERS

```java
Http.Builder builder = new Http.Builder("https://dummyhost.com/posts");

JSONObject json = new JSONObject();

json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.post(json.toString());

config.addHeader("X-Application-Id", "T1zX12r2dasdareeafSasda2zG8QxCkaOQ2jIFVQLA3HZ7J");
config.addHeader("X-REST-API-Key", "Wn5rpappoDAsdq34DAAsdasasA6R4ZUnzdubMc");

HttpResult result =  Http.send(config);
```

#####INTERCEPTORS

```java

public static class DefaultInterceptor implements HttpInterceptor {

    @Override
    public void onOpening(HttpRequestConfig httpRequestConfig) {
		httpRequestConfig.addHeader("X-Application-Id", "T1zX12r2dasdareeafSasda2zG8QxCkaOQ2jIFVQLA3HZ7J");
		httpRequestConfig.addHeader("X-REST-API-Key", "Wn5rpappoDAsdq34DAAsdasasA6R4ZUnzdubMc");        
    }

    @Override
    public void onConnecting(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {  }

    @Override
    public void onConnected(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {  }

    @Override
    public HttpResult onResult(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig, HttpResult httpResult) {
        return httpResult;
    }
}

...

Http.getInterceptors().add(new DefaultInterceptor());

Http.Builder builder = new Http.Builder("https://dummyhost.com/posts");

JSONObject json = new JSONObject();

json.accumulate("title", "foo");
json.accumulate("body", "bar");
json.accumulate("userId", 1);

HttpRequestConfig config = builder.post(json.toString());

HttpResult result =  Http.send(config);

```

#####REQUEST INTERCEPTORS

```java

public static class RequestInterceptor implements HttpInterceptor {

    @Override
    public void onOpening(HttpRequestConfig httpRequestConfig) {
    }

    @Override
    public void onConnecting(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {  }

    @Override
    public void onConnected(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig) {  }

    @Override
    public HttpResult onResult(HttpURLConnection httpURLConnection, HttpRequestConfig httpRequestConfig, HttpResult httpResult) {
        return new HttpResult(httpResult.getStatus(), "{ 'name': 'INTERCEPTED'} ", null) ;
    }
}

...

Http.Builder builder = new Http.Builder("https://dummyhost.com/posts");

HttpRequestConfig config = builder.get();

config.getInterceptors().add(new RequestInterceptor());

HttpResult result =  Http.send(config);

```

License
-------

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```