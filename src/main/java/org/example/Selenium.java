package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.io.*;
import java.util.List;

public abstract class Selenium {
    protected WebDriver driver ;
    protected File config ;

    protected String username, password, teammateName ;
    public Selenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("use-fake-ui-for-media-stream");
        driver = new ChromeDriver(options) ;
        loadConfigurations();
        openApplication();
        try {
            login();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void loadConfigurations() ;

    protected abstract void createConfigurations() ;

    protected abstract void openApplication() ;
    protected abstract void login() throws InterruptedException;
    protected abstract void startConversation() ;
    protected abstract void findCallButton() ;
    protected abstract void call() ;
    protected abstract void allowCamera() ;
    protected abstract void close() ;

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
