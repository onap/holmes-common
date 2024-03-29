/**
 * Copyright 2017-2022 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.holmes.common.utils;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import jakarta.servlet.http.HttpServletRequest;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
public class UserUtilTest {

    private HttpServletRequest request;

    @Before
    public void before() throws Exception {
        request = PowerMock.createMock(HttpServletRequest.class);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void getUserName_header_name_empty() {
        EasyMock.expect(request.getHeader("username")).andReturn(null);

        PowerMock.replayAll();

        assertThat("admin", equalTo(UserUtil.getUserName(request)));

        PowerMock.verifyAll();
    }

    @Test
    public void getUserName_normal() {
        EasyMock.expect(request.getHeader("username")).andReturn("Name").times(2);

        PowerMock.replayAll();

        assertThat("Name", equalTo(UserUtil.getUserName(request)));

        PowerMock.verifyAll();
    }

} 
