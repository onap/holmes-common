/**
 * Copyright 2017 ZTE Corporation.
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

package org.onap.holmes.common.aai;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@PrepareForTest(AaiQuery.class)
@RunWith(PowerMockRunner.class)
public class AaiQueryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AaiQuery aaiQuery;
    private AaiResponseUtil aaiResponseUtil;

    @Before
    public void setUp() {
        aaiQuery = PowerMock.createMock(AaiQuery.class);
        aaiResponseUtil = PowerMock.createMock(AaiResponseUtil.class);
        PowerMock.replayAll();
    }

    @Test
    public void testVnfQuery_success_query() throws Exception {
//        VnfEntity vnfEntity = new VnfEntity();
//        vnfEntity.setVnfName("test1");
//
//        PowerMock.createMock(MicroServiceConfig.class);
//        PowerMock.expectPrivate(MicroServiceConfig.class, "getMsbServerAddr")
//                .andReturn("http://10.74.148.80:8443").anyTimes();
//
//        PowerMock.expectPrivate(aaiResponseUtil, "convertJsonToVnfEntity", anyObject())
//                .andReturn(vnfEntity).anyTimes();
//
//        PowerMock.replayAll();
//        VnfEntity vnfEntity1 = aaiQuery
//                .getAaiVnfData("example-vnf-id-val-92494", "example-vnf-name-val-94632");
//        assertThat(vnfEntity.getVnfName(), equalTo(vnfEntity1.getVnfName()));
//        PowerMock.verifyAll();
    }
}
