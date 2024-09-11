package com.howtodoinjava.app.datastructures;

import software.amazon.awssdk.services.sns.model.Topic;

import java.util.Map;

public class TopicName {
    public String name;
    public Map<String, String> tags;

    public TopicName(String name, Map<String, String> tags){
        this.name = name;
        this.tags = tags;
    }
    public TopicName(){}
}
