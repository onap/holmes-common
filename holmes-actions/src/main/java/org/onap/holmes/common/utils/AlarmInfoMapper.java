/**
 * Copyright 2017-2021 ZTE Corporation.
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

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.onap.holmes.common.api.entity.AlarmInfo;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AlarmInfoMapper implements RowMapper<AlarmInfo> {
    @Override
    public AlarmInfo map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setAlarmIsCleared(resultSet.getInt("alarmiscleared"));
        alarmInfo.setRootFlag(resultSet.getInt("rootflag"));
        alarmInfo.setEventId(resultSet.getString("eventid"));
        alarmInfo.setEventName(resultSet.getString("eventname"));
        alarmInfo.setSourceId(resultSet.getString("sourceid"));
        alarmInfo.setSourceName(resultSet.getString("sourcename"));
	alarmInfo.setSequence(resultSet.getInt("sequence"));
        alarmInfo.setStartEpochMicroSec(resultSet.getLong("startepochmicrosec"));
        alarmInfo.setLastEpochMicroSec(resultSet.getLong("lastepochmicrosec"));
        return alarmInfo;
    }
}
