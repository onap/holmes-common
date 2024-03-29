/*
 * Copyright 2017 ZTE Corporation.
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

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Properties;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

public class CorrelationRuleMapperTest {

    @Test
    public void map() throws Exception {
        CorrelationRuleMapper mapper = new CorrelationRuleMapper();
        ResultSet resultSet = PowerMock.createMock(ResultSet.class);
        expect(resultSet.getString("name")).andReturn("");
        expect(resultSet.getString("rid")).andReturn("");
        expect(resultSet.getString("description")).andReturn("");
        expect(resultSet.getInt("enable")).andReturn(0);
        expect(resultSet.getInt("templateID")).andReturn(1);
        expect(resultSet.getString("engineID")).andReturn("");
        expect(resultSet.getString("engineType")).andReturn("");
        expect(resultSet.getString("creator")).andReturn("");
        expect(resultSet.getDate("createTime")).andReturn(new Date(System.currentTimeMillis()));
        expect(resultSet.getString("updator")).andReturn("");
        expect(resultSet.getDate("updateTime")).andReturn(new Date(System.currentTimeMillis()));
        expect(resultSet.getObject("params")).andReturn(new Properties());
        expect(resultSet.getString("content")).andReturn("");
        expect(resultSet.getString("vendor")).andReturn("");
        expect(resultSet.getString("package")).andReturn("");
        expect(resultSet.getString("ctrlloop")).andReturn("");
        expect(resultSet.getString("engineinstance")).andReturn("");
        PowerMock.replay(resultSet);
        mapper.map(resultSet, null);
        PowerMock.verify(resultSet);
    }
}