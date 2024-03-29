/**
 * Copyright 2017 - 2022 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.holmes.common.utils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ExceptionUtil {

    private static final int EXCEPTION_CODE = 499;

    private ExceptionUtil() {

    }

    public static WebApplicationException buildExceptionResponse(String message) {
        Response response = Response.status(EXCEPTION_CODE).entity(message).type(MediaType.TEXT_PLAIN).build();
        return new WebApplicationException(response);
    }
}
