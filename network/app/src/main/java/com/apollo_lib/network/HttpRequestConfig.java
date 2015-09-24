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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HttpRequestConfig {

    private ContentType contentType;
    private HttpMethod method;
    private String url;
    private String authToken;
    private String params;
    private String attachmentName;
    private String attachmentFileName;
    private byte[] attachmentFile;
    private Charset charset;
    private ArrayList<HttpHeaderField> headerFields;

    public HttpRequestConfig() {
        headerFields = new ArrayList<>();
    }

    public void clearHeaders() {
        this.headerFields.clear();
    }

    public HttpHeaderField getHeader(String fieldname) {
        int i = 0;

        HttpHeaderField result = null;

        while(headerFields.size() > i && result == null)
        {
            if (headerFields.get(i).getFieldName().toLowerCase() == fieldname.toLowerCase()) {
                result = headerFields.get(i);
            }
            i++;
        }

        return result;
    }

    public void removeHeader(String fieldName) {
        HttpHeaderField header = this.getHeader(fieldName);

        if (header == null) {
            throw new NoSuchElementException("Header field not found.");
        }

        headerFields.remove(header);
    }

    public void addHeader(String fieldName, String value) {
        headerFields.add(new HttpHeaderField(fieldName, value));
    }

    List<HttpHeaderField> getHeaders() {
        return headerFields;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(byte[] file) {
        this.attachmentFile = file;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}