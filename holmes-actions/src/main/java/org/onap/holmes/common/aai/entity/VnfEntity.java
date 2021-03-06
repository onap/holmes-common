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
package org.onap.holmes.common.aai.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VnfEntity {
    private Boolean inMaint;
    private Boolean closedLoopDisabled;
    private String orchestrationStatus;
    private String provStatus;
    private String resourceVersion;
    private String serviceId;
    private String vnfId;
    private String vnfName;
    private String vnfType;
    private RelationshipList relationshipList = new RelationshipList();
}
