package com.samodeika.common;

import java.io.InputStream;
import java.util.List;

public interface Processor {
    List<Dog> processFile(InputStream in);
    String downloadFile(List<Dog> persons);
}
