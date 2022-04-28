/**
 * Copyright 2017-2022 ZTE Corporation.
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

package org.onap.holmes.common.utils;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;
import org.onap.holmes.common.config.MicroServiceConfig;
import org.onap.holmes.common.exception.CorrelationException;
import org.onap.msb.sdk.discovery.entity.MicroServiceFullInfo;
import org.onap.msb.sdk.discovery.entity.MicroServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

import static org.onap.holmes.common.utils.JerseyClient.PROTOCOL_HTTP;
import static org.onap.holmes.common.utils.JerseyClient.PROTOCOL_HTTPS;

@Service
public class MsbRegister {
    private static final Logger log = LoggerFactory.getLogger(MsbRegister.class);

    private JerseyClient client = JerseyClient.newInstance();

    @Setter
    private int totalRetryTimes = 20;

    @Setter
    private int interval = 20;

    public MsbRegister() {
    }

    public void register2Msb(MicroServiceInfo msinfo) throws CorrelationException {
        String[] msbAddrInfo = MicroServiceConfig.getMsbIpAndPort();

        boolean isHttpsEnabled = StringUtils.isNotBlank(msbAddrInfo[1])
                && msbAddrInfo[1].equals("443");

        log.info("Start to register Holmes Service to MSB...");
        log.info("Registration information: {}", GsonUtil.beanToJson(msinfo));

        MicroServiceFullInfo microServiceFullInfo = null;
        int retry = 0;
        while (null == microServiceFullInfo && retry < totalRetryTimes) {
            try {
                log.info("Holmes Service Registration. Times: " + ++retry);
                int time  = interval * retry;
                microServiceFullInfo = client
                        .header("Accept", MediaType.APPLICATION_JSON)
                        .queryParam("createOrUpdate", true)
                        .post(String.format("%s://%s:%s/api/microservices/v1/services",
                                isHttpsEnabled ? PROTOCOL_HTTPS : PROTOCOL_HTTP, msbAddrInfo[0], msbAddrInfo[1]),
                                Entity.entity(msinfo, MediaType.APPLICATION_JSON),
                                MicroServiceFullInfo.class);

                if (null == microServiceFullInfo) {
                    log.warn(String.format("Failed to register the service to MSB. Sleep %ds and try again.", time));
                    threadSleep(TimeUnit.SECONDS.toSeconds(time));
                } else {
                    log.info("Registration succeeded!");
                    break;
                }
            } catch (Exception e) {
                log.warn("Unexpected exception: " + e.getMessage(), e);
            }
        }

        if (null == microServiceFullInfo) {
            throw new CorrelationException("Failed to register the service to MSB!");
        }

        log.info("Service registration completed.");
    }

    private void threadSleep(long second) {
        log.info("Start sleeping...");
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException error) {
            log.error("thread sleep error message:" + error.getMessage(), error);
            Thread.currentThread().interrupt();
        }
        log.info("Wake up.");
    }
}