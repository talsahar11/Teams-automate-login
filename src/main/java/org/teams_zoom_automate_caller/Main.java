package org.teams_zoom_automate_caller;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //-----Please change the clientType due to the usage you prefer (ANSWERER \ CALLER)------
        ClientType clientType = ClientType.ANSWERER ;

        Simulator simulator = new Simulator(clientType) ;
    }
}