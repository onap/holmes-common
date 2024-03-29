/**
 * Copyright 2021 ZTE Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.holmes.common.exception;

import org.junit.Test;

public class HttpExceptionTest {

    @Test
    public void exception_without_cause() {
        HttpException e = new HttpException(200, "OK");
    }

    @Test
    public void exception_with_cause() {
        HttpException e = new HttpException(404, "Not Found", new Exception());
    }
}