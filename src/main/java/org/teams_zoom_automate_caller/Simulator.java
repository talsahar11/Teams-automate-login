package org.teams_zoom_automate_caller;

import org.teams_zoom_automate_caller.teams.TeamsSelenium;
import org.teams_zoom_automate_caller.zoom.ZoomSelenium;

import javax.swing.*;

public class Simulator {
    private Selenium selenium ;
    private ClientType clientType ;
    public Simulator(ClientType clientType) throws InterruptedException {
        Object[] options = {"Teams", "Zoom"} ;
        int option = JOptionPane.showOptionDialog(null, "Please choose platform:", "Simulator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) ;
        if(option == 0){
            selenium = new TeamsSelenium(clientType) ;
        }else{
            selenium = new ZoomSelenium(clientType) ;
        }
    }
}
