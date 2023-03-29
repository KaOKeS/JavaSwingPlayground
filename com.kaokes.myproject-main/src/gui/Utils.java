package gui;

import javax.swing.*;
import java.net.URL;

public class Utils {
    private Utils() {
    }

    public static String getFileExtension(String name){
        int pointIndex = name.lastIndexOf(".");

        if(pointIndex == -1 || pointIndex == name.length()-1){
            return null;
        }
        return name.substring(pointIndex+1);
    }

    public static ImageIcon createIcon(String path) {
        URL url = Utils.class.getResource(path);
        if (url == null) {
            throw new NullPointerException("Unable to load image: " + url);
        }
        return new ImageIcon(url);
    }
}
