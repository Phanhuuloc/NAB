package com.example.phoenix.nab.common;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;

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

    public void ReadFile() {
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "path/to/the/file/inside_the_sdcard/textarabics.txt");
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting data JSON Array nodes
            JSONArray data = jsonObj.getJSONArray("data");

            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                String id = c.getString("id");
                String title = c.getString("title");
                String duration = c.getString("duration");
                HashMap<String, String> parsedData = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                parsedData.put("id", id);
                parsedData.put("title", title);
                parsedData.put("duration", duration);


                // do what do you want on your interface
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
