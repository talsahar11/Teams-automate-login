package org.teams_zoom_automate_caller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.List;


/**
 * This class is a prototype of the selenium automation for starting a video call for teams or
 * zoom web applications.
 * The class holds all the common attributes and methods applying to both zoom and teams.
 */
public abstract class Selenium {

    //-----Web driver for interacting with chrome-----
    protected WebDriver driver ;

    //-----Configuration file will hold the client login details and the friend nickname-----
    protected File config ;

    //-----Client type to know which side is needed (called\answerer)-----
    protected ClientType clientType ;

    //-----The fields to be loaded from the config file-----
    protected String username, password, teammateName ;

    public Selenium(ClientType clientType) throws InterruptedException {

        //-----Vars initialization-----
        this.clientType = clientType ;
        driver = new ChromeDriver(allowCamera()) ;

        loadConfigurations();
        openApplication();
        login();
        act() ;
    }

    /**
     * act() method determines whether the call() or the answer() method will be called after the log in.
     */
    private void act(){
        switch (clientType){
            case CALLER:
                call();
                break ;
            case ANSWERER:
                answer();
                break ;
            default:
                System.err.println("Error in client type switch!");
        }
    }

    /**
     * loadConfigurations will handle the loading of the config file, parsing it, and call the create
     * configurations method if the file does not exist.
     */
    protected abstract void loadConfigurations() ;

    /**
     * createConfiguration gets the details from the user input and writes a new config file with those details.
     */
    protected abstract void createConfigurations() ;

    /**
     * openApplication will open the wanted app in the chrome browser.
     */
    protected abstract void openApplication() ;

    protected abstract void login() throws InterruptedException;
    protected abstract void call() ;

    protected abstract void close() ;

    protected abstract void answer() ;

    /**
     * creates options for the chrome driver which enables camera and mic automatically
     * @return options to set to the driver.
     */
    protected ChromeOptions allowCamera(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("use-fake-ui-for-media-stream");
        return options ;
    }

    protected void printPageFields(){

        String separator = "-----------------------------------------------------------------------------------" ;
        java.util.List<WebElement> allFields = driver.findElements(By.tagName("*"));
        System.out.println(separator);
        System.out.println(separator);
        for (WebElement element : allFields) {
            System.out.println("Tag Name: " + element.getTagName() +
                    ", Text: " + element.getText() +
                    ", Attribute (ID): " + element.getAttribute("id") +
                    ", Attribute (Class): " + element.getAttribute("class"));
        }


        System.out.println(separator);
        System.out.println(separator);
    }

    void printDifferences(List<WebElement> lastList){
        List<WebElement> allElements = driver.findElements(By.cssSelector("*"));
        for(WebElement webElement: allElements){
            if(!lastList.contains(webElement)){
                System.out.println(webElement.getText());
            }
        }
    }
}
