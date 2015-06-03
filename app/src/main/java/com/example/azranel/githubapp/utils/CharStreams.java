package com.example.azranel.githubapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by azranel on 01.06.15.
 */
public class CharStreams {
    public static String toString(InputStreamReader reader) {
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();

        bufferedReader = new BufferedReader(reader);
        String line;

        try {
            while((line = bufferedReader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            Log.e("GITHUB", "Failed CharStreams.toString(InputStreamReader)");
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static String toString(InputStream is) {
        return toString(new InputStreamReader(is));
    }
}
