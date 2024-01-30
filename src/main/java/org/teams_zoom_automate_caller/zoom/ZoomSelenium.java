package org.teams_zoom_automate_caller.zoom;

import org.teams_zoom_automate_caller.Role;
import org.teams_zoom_automate_caller.Selenium;

public class ZoomSelenium extends Selenium {
    public ZoomSelenium(Role role) throws InterruptedException {
        super(role);
    }

    @Override
    protected int loadConfigurations() {
        return 0 ;
    }

    @Override
    protected int createConfigurations() {
        return 0 ;
    }

    @Override
    public void openApplication() {

    }

    @Override
    public void login() {

    }


    @Override
    public void call() {

    }

    @Override
    public void close() {

    }

    @Override
    protected void answer() {

    }
}
