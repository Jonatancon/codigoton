/*
* @CopyRight: Jonatan Stiven Restrepo Lora
* */

package reto;

import reto.resource.SelectToFilter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class RetoMain {

    public static void main(String[] args) {

        SelectToFilter select = new SelectToFilter();

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            select.cleanFile(file);
        }



    }
}
