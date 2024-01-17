package org.example;

import javax.swing.*;
import java.io.File;

public class Simulator {
    public Selenium selenium ;
    public File config ;
    public Simulator(){
        Object[] options = {"Teams", "Zoom"} ;
        int option = JOptionPane.showOptionDialog(null, "Please choose platform:", "Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) ;
        if(option == 0){
            selenium = new TeamsSelenium() ;
        }else{
            selenium = new ZoomSelenium() ;
        }
    }
}
