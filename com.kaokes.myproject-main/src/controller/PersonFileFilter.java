package controller;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PersonFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String name = f.getName();
        String fileExtension = Utils.getFileExtension(name);
        return fileExtension != null && fileExtension.equals("per");
    }

    @Override
    public String getDescription() {
        return "Person database files (*.per)";
    }
}
