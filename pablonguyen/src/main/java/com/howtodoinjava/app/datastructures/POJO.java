package com.howtodoinjava.app.datastructures;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class POJO {
    private String name = "Cao";
    private int age;
    private String sex = "male";

//    public POJO(String name, int age, String gender){
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//    }

    public POJO(){}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("gender")
    public String getSex() {
        return sex;
    }

    public void setSex(String gender) {
        this.sex = gender;
    }

    public String getName() {
        return name;
    }

    public void setFooname(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws JsonProcessingException {
//        POJO p = new POJO("Pablo", 21, "Male");
        String s = "{\"name\":\"Cao\",\"age\":21,\"gender\":\"Male\"}";
        POJO p = new POJO();
        ObjectMapper mapper = new ObjectMapper();
//        String ss = mapper.writeValueAsString(p);

//        String s = mapper.writeValueAsString(p);

        POJO p1 = mapper.readValue(s, POJO.class);

    }
}
