/**
 * Copyright 2018 ZTE Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.onap.holmes.common.aai;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.onap.holmes.common.aai.config.AaiConfig;
import org.onap.holmes.common.config.MicroServiceConfig;
import org.onap.holmes.common.exception.CorrelationException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Slf4j
public class AaiJsonParserUtil {

    public static String getPath(String urlTemplate, String paramName, String paramValue) {
        return urlTemplate.replaceAll("\\{" + paramName + "\\}", paramValue);
    }

    public static String getPath(String serviceInstancePath) {
        Pattern pattern = Pattern.compile("/aai/(v\\d+)/([A-Za-z0-9\\-]+[^/])(/*.*)");
        Matcher matcher = pattern.matcher(serviceInstancePath);
        String ret = "/api";
        if (matcher.find()) {
            ret += "/aai-" + matcher.group(2) + "/" + matcher.group(1) + matcher.group(3);
        }

        return ret;
    }

    public static Response get(String host, String path) throws CorrelationException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(host).path(path);
        try {
            Response response = target.request().headers(getAaiHeaders()).get();
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new CorrelationException("Failed to connect to AAI. \nCause: "
                                                       + response.getStatusInfo().getReasonPhrase() + "\nDetails: \n"
                                                       + getErrorMsg(String.format("%s%s", host, path), null, response));
            }
            return response;
        } catch (CorrelationException e) {
            throw e;
        } catch (Exception e) {
            throw new CorrelationException(e.getMessage() + "More info: "
                                                   + getErrorMsg(String.format("%s%s", host, path), null, null), e);
        }
    }

    public static JSONObject getInfo(String response, String field) {
        JSONObject jObject = JSONObject.parseObject(response);
        JSONObject relationshipList = extractJsonObject(jObject, "relationship-list");
        JSONArray relationShip = extractJsonArray(relationshipList, "relationship");
        if (relationShip != null) {
            for (int i = 0; i < relationShip.size(); ++i) {
                final JSONObject object = relationShip.getJSONObject(i);
                if (object.getString("related-to").equals(field)) {
                    return object;
                }
            }
        }
        return null;
    }

    public static JSONObject extractJsonObject(JSONObject obj, String key) {
        if (obj != null && key != null && obj.containsKey(key)) {
            return obj.getJSONObject(key);
        }
        return null;
    }

    public static JSONArray extractJsonArray(JSONObject obj, String key) {
        if (obj != null && key != null && obj.containsKey(key)) {
            return obj.getJSONArray(key);
        }
        return null;
    }

    public static String getHostAddr() {
        return MicroServiceConfig.getMsbServerAddrWithHttpPrefix();
    }

    public static String getErrorMsg(String url, Map<String, Object> body, Response response) {
        StringBuilder sb = new StringBuilder();
        sb.append("Rerquest URL: ").append(url).append("\n");
        sb.append("Request Header: ").append(JSONObject.toJSONString(getAaiHeaders())).append("\n");
        if (body != null) {
            sb.append("Request Body: ").append(JSONObject.toJSONString(body)).append("\n");
        }
        if (response != null) {
            sb.append("Request Body: ").append(response.readEntity(String.class));
        }
        return sb.toString();
    }

    public static MultivaluedMap getAaiHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("X-TransactionId", AaiConfig.X_TRANSACTION_ID);
        headers.add("X-FromAppId", AaiConfig.X_FROMAPP_ID);
        headers.add("Authorization", AaiConfig.getAuthenticationCredentials());
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
