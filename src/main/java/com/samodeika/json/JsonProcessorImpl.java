package com.samodeika.json;

import com.samodeika.common.Dog;
import com.samodeika.common.Processor;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonProcessorImpl implements Processor {

    @Override
    public List<Dog> processFile(InputStream in) {
        System.out.println("Read json file");
        String fileContent = null;
        try {
            fileContent = IOUtils.toString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(fileContent);
        JSONArray array = json.getJSONArray("dogs");
        List<Dog> dogs = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Dog dog = new Dog(jsonObject.getString("name"), jsonObject.getInt("age"), jsonObject.getDouble("weight"));
            dogs.add(dog);
        }

        return dogs;
    }

    @Override
    public String downloadFile(List<Dog> dogs) {
        List<Dog> dogList = dogs;
        JSONObject result = new JSONObject();
        JSONArray dogArray = new JSONArray();
        int i = 0;
        for (Dog dog : dogList) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", dog.getName());
            tmp.put("age", dog.getAge());
            tmp.put("weight", dog.getWeight());
            dogArray.put(i++, tmp);
        }

        result.put("dogs", dogArray);
        return result.toString();
    }
}
