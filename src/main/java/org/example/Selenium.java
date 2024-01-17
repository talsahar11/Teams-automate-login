package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.*;
import java.util.List;

public abstract class Selenium {
    protected WebDriver driver ;
    protected File config ;

    protected String username, password, teammateName ;
    public Selenium() {
        driver = new ChromeDriver() ;
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
//        List<WebElement> allElements = driver.findElements(By.cssSelector("*"));
//        for (WebElement element : allElements) {
//            System.out.println(element.getText());
//        }

        java.util.List<WebElement> allFields = driver.findElements(By.tagName("input"));
        for (WebElement field : allFields) {
            System.out.println("Field Name: " + field.getAttribute("name") + ", Field Value: " + field.getAttribute("value") +
                    ", Type: " + field.getAttribute("type") + ", ID: " + field.getAttribute("id"));
        }
    }
}
