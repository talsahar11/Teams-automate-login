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
    }

    @Override
    public void login() throws InterruptedException {
        passUsernamePage() ;
        passPasswordPage() ;
        passKeepLoggedInPage();
        passMainPage();
        startCall();
    }
    private void passUsernamePage() {
        waitForUsernamePage() ;
        List<WebElement> userPageElements = getUsernamePageElements() ;
        fillAndSendUserPage(userPageElements);
    }

    private void waitForUsernamePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("loginfmt")));
    }

    private void passPasswordPage() {
        waitForPasswordPage() ;
        List<WebElement> passwordPageElements = getPasswordPageElements() ;
        fillAndSendPasswordPage(passwordPageElements) ;
    }

    private void waitForPasswordPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwd")));

    }

    private void passKeepLoggedInPage(){
        waitForLogggedInPage() ;
        driver.findElement(By.id("idSIButton9")).click();

    }

    private void waitForLogggedInPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSIButton9")));
    }

    private void passMainPage() throws InterruptedException {
        Random rand = new Random() ;
        WebElement searchBox = waitForMainPage() ;

        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox) ;
        System.out.println("Clicked");
        actions.click().perform();
        for(int i = 0 ; i < teammateName.length() ; i++){
            searchBox.sendKeys(teammateName.substring(i, i+1));
            Thread.sleep(rand.nextInt(200) + 200);
        }
        Thread.sleep(500);
        System.out.println("Keys!");
        actions.sendKeys(Keys.ARROW_DOWN).perform(); ;
        actions.sendKeys(Keys.ENTER).perform();
    }

    private void startCall(){
        WebElement callButton = waitForFriendPage() ;
        callButton.click() ;
    }

    private WebElement waitForFriendPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class, 'ts-btn-fluent') and contains(@class, 'ts-btn-fluent-with-icon') and contains(@class, 'ts-btn-fluent-primary') and contains(@class, 'ts-btn-fluent-split') and contains(@class, 'ts-btn-fluent-split-left') and contains(@class, 'start-call') and contains(@class, 'dropdown-action-btn') and contains(@class, 'three-split-btn-enabled')]")));
    }

    /***
     * <button class="ts-btn ts-btn-fluent ts-btn-fluent-with-icon ts-btn-fluent-primary ts-btn-fluent-split ts-btn-fluent-split-left start-call dropdown-action-btn three-split-btn-enabled" title="Video call" ng-click="ctrl.onCallingButtonClickedFromDropdown(true, true, $event)" ng-disabled="ctrl.isVideoReallyDisabled" data-tid="dropdown-video-call-primary-btn" tabindex="-1"><!----><ng-include aria-hidden="true" class="icon-wrapper" src="'svg/icons-call-video.html'"><svg viewBox="-6 -6 32 32" class="app-svg icons-call-video icons-video" role="presentation" focusable="false"><g class="icons-default-fill"><path class="icons-unfilled" d="M4.5 4C3.11929 4 2 5.11929 2 6.5V13.5C2 14.8807 3.11929 16 4.5 16H11.5C12.8807 16 14 14.8807 14 13.5V12.5L16.4 14.3C17.0592 14.7944 18 14.324 18 13.5V6.49998C18 5.67594 17.0592 5.20556 16.4 5.69998L14 7.49998V6.5C14 5.11929 12.8807 4 11.5 4H4.5ZM14 8.74998L17 6.49998V13.5L14 11.25V8.74998ZM13 6.5V13.5C13 14.3284 12.3284 15 11.5 15H4.5C3.67157 15 3 14.3284 3 13.5V6.5C3 5.67157 3.67157 5 4.5 5H11.5C12.3284 5 13 5.67157 13 6.5Z"></path><g class="icons-filled"><path d="M13 6.5C13 5.11929 11.8807 4 10.5 4H4.5C3.11929 4 2 5.11929 2 6.5V13.5C2 14.8807 3.11929 16 4.5 16H10.5C11.8807 16 13 14.8807 13 13.5V6.5Z"></path><path d="M14 7.93082V12.0815L16.7642 14.4319C17.2512 14.8461 18 14.4999 18 13.8606V6.19315C18 5.55685 17.2575 5.20962 16.7692 5.61756L14 7.93082Z"></path></g></g></svg></ng-include></button>
     *
     */
    private WebElement waitForMainPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchInputField")));
        return searchBox ;
    }



    private List<WebElement> getPasswordPageElements() {
        List<WebElement> passwordPageElements = new ArrayList<>() ;
        passwordPageElements.add(driver.findElement(By.name("passwd"))) ;
        passwordPageElements.add(driver.findElement(By.id("idSIButton9"))) ;
        return passwordPageElements ;
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
