package com.samodeika.dao;

import com.samodeika.common.Dog;

import java.util.List;

public interface DogDao {

    List<Dog> getDogs();
    boolean saveDog(Dog dog);
    boolean saveDogs(List<Dog> dogs);

}
