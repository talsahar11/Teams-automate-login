package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamsSelenium extends Selenium{

    public TeamsSelenium() {
        super();
    }


    protected void loadConfigurations(){
        config = new File("teamsConfig.txt") ;
        if(!config.exists()){
            createConfigurations() ;
        }else{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(config)) ;
                username = reader.readLine() ;
                password = reader.readLine() ;
                teammateName = reader.readLine() ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected void createConfigurations() {
        try {
            config.createNewFile() ;
            BufferedWriter writer = new BufferedWriter(new FileWriter(config)) ;
            JOptionPane.showMessageDialog(null, "Hello, its your first time with Microsoft Teams here, please enter your details so we can connect you on the next times also :-) ");
            username = JOptionPane.showInputDialog("Please enter your mail:") ;
            password = JOptionPane.showInputDialog("Please enter your password:") ;
            teammateName = JOptionPane.showInputDialog("Please enter your friend's identifier (nickname, mail):") ;

            writer.write(username+"\n");
            writer.write(password+"\n");
            writer.write(teammateName + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void openApplication() {
        driver.get("https://teams.microsoft.com/");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void login() throws InterruptedException {
        printPageFields();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("loginfmt")));
        passUsernamePage() ;

        Thread.sleep(1000);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("passwd")));
        passPasswordPage() ;

        Thread.sleep(3000);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSIButton9")));

        passThirdPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchInputField")));
        for(int i = 0 ; i < teammateName.length() ; i++){
            searchBox.sendKeys(teammateName.substring(i, i+1));
            Thread.sleep(150);
        }
        Thread.sleep(5000);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.ARROW_DOWN) ;
        actions.keyDown(Keys.ENTER) ;
        WebElement galiliElement1 = driver.findElement(By.id("angucomplete-custom")) ;
        galiliElement1.click();
//        WebElement galiliElement2 = driver.findElement(By.id("autosuggest-dropdown ng-hide")) ;
//        galiliElement2.click();
    }

    private void passPasswordPage() {
        List<WebElement> passwordPageElements = getPasswordPageElements() ;
        fillAndSendPasswordPage(passwordPageElements) ;
    }

    private List<WebElement> getPasswordPageElements() {
        List<WebElement> passwordPageElements = new ArrayList<>() ;
        passwordPageElements.add(driver.findElement(By.name("passwd"))) ;
        passwordPageElements.add(driver.findElement(By.id("idSIButton9"))) ;
        return passwordPageElements ;
    }

    private void passUsernamePage() {
        List<WebElement> userPageElements = getUsernamePageElements() ;
        fillAndSendUserPage(userPageElements);
    }

    private void passThirdPage(){
        driver.findElement(By.id("idSIButton9")).click();

    }
    private List<WebElement> getUsernamePageElements(){
        List<WebElement> userPageElements = new ArrayList<>() ;
        userPageElements.add(driver.findElement(By.name("loginfmt"))) ;
        userPageElements.add(driver.findElement(By.id("idSIButton9"))) ;
        return userPageElements ;
    }

    public void fillAndSendUserPage(List<WebElement> userPageElements) {
        userPageElements.get(0).sendKeys(username);
        userPageElements.get(1).click();
    }
    public void fillAndSendPasswordPage(List<WebElement> passwordPageElements) {
        passwordPageElements.get(0).sendKeys(password);
        passwordPageElements.get(1).submit();
    }

    @Override
    public void startConversation() {

    }

    @Override
    public void findCallButton() {

    }

    @Override
    public void call() {

    }

    @Override
    public void allowCamera() {

    }

    @Override
    public void close() {

    }
}
