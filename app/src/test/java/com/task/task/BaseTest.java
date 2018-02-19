package com.task.task;


import java.io.IOException;
import java.io.InputStream;

public class BaseTest {

    public String readJsonFile(String filename) throws IOException {
        final InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        final byte[] buffer = new byte[is.available()];
        is.read(buffer);
        final String json = new String(buffer, "UTF-8");

        return json;
    }
}
