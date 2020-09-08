/*
 * Copyright 2020 InfAI (CC SES)
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

package org.infai.ses.senergy.operators;

import org.infai.ses.senergy.utils.TimeProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class BaseBuilder {

    private String pipelineId;
    private String operatorId;

    public BaseBuilder (String operatorId, String pipelineId){
        this.operatorId = operatorId;
        this.pipelineId = pipelineId;
    }

    private JSONObject createMessageWrapper(){
        return new JSONObject().
                put("pipeline_id", pipelineId).
                put("time", TimeProvider.nowUTCToString()).
                put("operator_id", operatorId).
                put("analytics", new JSONObject());
    }

    public String formatMessage (String value) {
        List<String> values = Arrays.asList(value);
        return formatMessage(values).toString();
    }

    public JSONObject formatMessage(List<String> values){
        JSONObject ob = createMessageWrapper();
        JSONArray inputs = new JSONArray();
        values.forEach((v) -> inputs.put(new JSONObject(v)));
        ob.put("inputs", inputs);
        return ob;
    }
}