package com.samodeika.xml;

import com.samodeika.common.Dog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class DogList {

    private List<Dog> dogList;

    public DogList() {
    }

    public DogList(List<Dog> dogList) {
        this.dogList = dogList;
    }

    public List<Dog> getDogList() {
        return dogList;
    }

    @XmlElement(name = "dog")
    public void setDogList(List<Dog> dogList) {
        this.dogList = dogList;
    }
}
