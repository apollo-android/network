/*
 * Apollo-11 Team 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apollo_lib.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    private static final String CONTENT_TYPE = "Content-type";
    private static final String AUTH_TOKEN = "x-access-token";

    public static class Builder {
        private final Charset DEFAULT_CHARSET = Charset.UTF8;
        private final ContentType DEFAULT_CONTENTTYPE = ContentType.OCTECTSTREAM;

        private String url;
        private ContentType contentType;
        private Charset charset;
        private String authToken;

        private void testNullOrEmpty(String field, String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException(String.format("The %s cannot be null or empty.", field));
            }
        }

        private <T> void testNull(String field, T value) {
            if (value == null) {
                throw new IllegalArgumentException(String.format("The %s cannot be null.",field));
            }
        }

        private HttpRequestConfig createDefaultConfig() {
            HttpRequestConfig config = new HttpRequestConfig();

            config.setUrl(url);

            if (contentType == null) {
                config.setContentType(DEFAULT_CONTENTTYPE);
            }
            else {
                config.setContentType(contentType);
            }

            if (charset == null) {
                config.setCharset(DEFAULT_CHARSET);
            }
            else {
                config.setCharset(charset);
            }

            if (authToken != null) {
                config.setAuthToken(authToken);
            }

            return config;
        }

        private HttpRequestConfig method(HttpMethod method, String params) {
            HttpRequestConfig config = createDefaultConfig();

            if (params != null && !params.isEmpty()) {
                config.setParams(params);
            }

            config.setMethod(method);

            return config;
        }

        public Builder(String url) {
            this(url, null, null, null);
        }

        public Builder(String url, ContentType contentType) {
            this(url, contentType, null, null);
        }

        public Builder(String url, ContentType contentType, Charset charset) {
            this(url, contentType, charset, null);
        }

        public Builder(String url, ContentType contentType, String authToken) {
            this(url, contentType, null, authToken);
        }

        public Builder(String url, String authToken) {
            this(url, null, null, authToken);
        }

        public Builder(String url, ContentType contentType, Charset charset, String authToken) {
            testNullOrEmpty("URL", url);

            this.url = url;
            this.contentType = contentType;
            this.charset = charset;
            this.authToken = authToken;
        }

        public HttpRequestConfig get() {
            return get(null);
        }

        public HttpRequestConfig get(String params) {
            return method(HttpMethod.GET, params);
        }

        public HttpRequestConfig post() {
            return post(null);
        }

        public HttpRequestConfig post(String params) {
            return method(HttpMethod.POST, params);
        }

        public HttpRequestConfig put() {
            return put(null);
        }

        public HttpRequestConfig put(String params) {
            return method(HttpMethod.PUT, params);
        }

        public HttpRequestConfig delete() {
            return delete(null);
        }

        public HttpRequestConfig delete(String params) {
            return method(HttpMethod.DELETE, params);
        }

        public HttpRequestConfig upload(byte[] attachmentFile) {
            return upload(null, null, attachmentFile);
        }

        public HttpRequestConfig upload(String attachmentName , String attachmentFileName, byte[] attachmentFile) {
            testNull("attachment file", attachmentFile);

            HttpRequestConfig config = createDefaultConfig();

            config.setContentType(ContentType.MULTPART);

            if (attachmentName != null && !attachmentName.isEmpty()) {
                config.setAttachmentName(attachmentName);
            }

            if (attachmentFileName != null && !attachmentFileName.isEmpty()) {
                config.setAttachmentFileName(attachmentFileName);
            }

            config.setAttachmentFile(attachmentFile);

            return config;
        }
    }

    private static String readInputStream(InputStream inputStream, Charset charset) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset.getValue()));

        String line;

        StringBuilder sb = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }

    public static HttpResult send(HttpRequestConfig request) throws IOException {
        URL url = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        HttpResult result;

        try {
            for (HttpHeaderField header: request.getHeaders()) {
                conn.setRequestProperty(header.getFieldName(), header.getValue());
            }

            conn.setRequestProperty(CONTENT_TYPE, request.getContentType().getValue());

            if (request.getContentType() == ContentType.MULTPART) {
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestMethod(HttpMethod.POST.getValue());
                conn.setUseCaches(false);
                conn.setDoOutput(true);
            }
            else {
                conn.setRequestMethod(request.getMethod().getValue());
            }

            if (request.getAuthToken() != null && !request.getAuthToken().isEmpty()) {
                conn.setRequestProperty(AUTH_TOKEN, request.getAuthToken());
            }

            conn.connect();

            if (request.getContentType() == ContentType.MULTPART) {
                if (request.getAttachmentFile() != null) {
                    DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());

                    outputStream.writeBytes("--*****\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + request.getAttachmentName() + "\";filename=\"" + request.getAttachmentFileName() + "\"\r\n");
                    outputStream.writeBytes("\r\n");

                    outputStream.write(request.getAttachmentFile());

                    outputStream.writeBytes("\r\n");
                    outputStream.writeBytes("--*****--\r\n");
                    outputStream.flush();
                    outputStream.close();
                }
            }
            else {
                if (request.getParams() != null) {
                    OutputStream outputStream = conn.getOutputStream();
                    byte[] buffer = request.getParams().toString().getBytes(request.getCharset().getValue());
                    outputStream.write(buffer);
                    outputStream.close();
                }
            }

            int statusCode = conn.getResponseCode();

            String response = "";
            String responseError = "";

            if (statusCode < HttpStatusCode.BAD_REQUEST) {
                response = readInputStream(conn.getInputStream(), request.getCharset());
            } else {
                responseError = readInputStream(conn.getErrorStream(), request.getCharset());
            }

            result = new HttpResult(statusCode, response, responseError);
        } finally {
            conn.disconnect();
        }

        return result;
    }
}