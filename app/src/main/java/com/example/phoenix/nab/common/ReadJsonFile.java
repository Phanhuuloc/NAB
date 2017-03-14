package com.example.phoenix.nab.common;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Phoenix on 3/13/17.
 */

public class ReadJsonFile {
    public static String read(File file) {
        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            //You'll need to add proper error handling here
        }
        return text.toString();
    }

    public static JSONArray arrayFrom(String text) {
        JSONArray array = null;
        try {
            array = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
