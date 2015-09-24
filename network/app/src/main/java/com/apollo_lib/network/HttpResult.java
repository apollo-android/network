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

public class HttpResult {
    private int status;
    private String response;
    private String responseError;

    public HttpResult(int status, String response, String responseError) {
        this.status = status;
        this.response = response;
        this.responseError = responseError;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public String getResponseError() {
        return responseError;
    }
}