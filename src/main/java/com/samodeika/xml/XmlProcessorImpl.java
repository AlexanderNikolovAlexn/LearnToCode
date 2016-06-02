package com.samodeika.xml;

import com.samodeika.common.Dog;
import com.samodeika.common.Processor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class XmlProcessorImpl implements Processor {

    @Override
    public List<Dog> processFile(InputStream in) {
        DogList dogList = getDogList(in);
        return dogList.getDogList();
    }

    private DogList getDogList(InputStream in) {
        DogList dogList = new DogList();
        try {
            JAXBContext context = JAXBContext.newInstance(DogList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            dogList = (DogList) unmarshaller.unmarshal(in);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return dogList;
    }

    @Override
    public String downloadFile(List<Dog> dogs) {
        String s = null;
        DogList p = new DogList(dogs);
        try {
            JAXBContext context = JAXBContext.newInstance(DogList.class);
            Marshaller marshaller = context.createMarshaller();
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            marshaller.marshal(p, buff);
            s = new String(buff.toByteArray());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return s;
    }
}
