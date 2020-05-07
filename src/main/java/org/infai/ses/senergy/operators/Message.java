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

import org.infai.ses.senergy.utils.ConfigProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private String jsonMessage;
    private Map<String, Input> inputs = new HashMap<String, Input>();
    private Config config = ConfigProvider.getConfig();

    public Message (){}

    public Message (String jsonMessage){
        this.jsonMessage = jsonMessage;
    }

    public Message setMessage (String message){
        this.jsonMessage = message;
        return this;
    }

    public Input addInput (String name){
        Input input = new Input(name, jsonMessage, this.config.inputTopic(name));
        this.inputs.put(name, input);
        return input;
    }

    public Input getInput (String name){
        return inputs.get(name).setMessage(this.jsonMessage);
    }

    public <K> K getValue (String key){
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(this.jsonMessage);
            return (K) obj.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void output(String name, Object value){
        this.jsonMessage = Helper.setJSONPathValue(this.jsonMessage, "analytics."+name, value);
    }

    public String getMessageString(){
        return this.jsonMessage;
    }
}
