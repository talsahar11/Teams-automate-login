package org.teams_zoom_automate_caller.teams;

import org.teams_zoom_automate_caller.Role;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.teams_zoom_automate_caller.Selenium;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamsSelenium extends Selenium {

    public TeamsSelenium(Role role) throws InterruptedException {
        super(role);
    }

    protected int loadConfigurations(){
        config = new File("teamsConfig.txt") ;
        if(!config.exists()){
            return -1 ;
        }else{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(config)) ;
                username = reader.readLine() ;
                password = reader.readLine() ;
                if(username == null || password == null){
                    return -1 ;
                }
                if(role == Role.CALLER) {
                    teammateName = reader.readLine();
                    if(teammateName == null){
                        return -1 ;
                    }
                }
                return 0 ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected int createConfigurations() {
        try {
            config.createNewFile() ;
            BufferedWriter writer = new BufferedWriter(new FileWriter(config)) ;
            JOptionPane.showMessageDialog(null, "Hello, its your first time with Microsoft Teams here, please enter your details so we can connect you on the next times also :-) ");
            username = JOptionPane.showInputDialog("Please enter your mail:") ;
            // Create a password field
            JPasswordField passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(200, 25)); // Set preferred size

            // Create a panel to hold the password field
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel("Enter password:"));
            panel.add(passwordField);

            // Set the preferred size for the panel
            panel.setPreferredSize(new Dimension(250, 80)); // Set preferred size
            passwordField.setFocusable(true);
            // Show the JOptionPane with the password field
            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Password Dialog",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            // Check if the user clicked OK
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve the password from the password field
                char[] tempPass = passwordField.getPassword();
                password = new String(tempPass );

            } else {
                System.out.println("Password entry canceled");
            }



            if(role == Role.CALLER) {
                teammateName = JOptionPane.showInputDialog("Please enter your friend's identifier (nickname, mail):");
                if(teammateName == null){
                    return -1 ;
                }
                writer.write(teammateName + "\n");
            }
            if(username == null || password == null) {
                return -1;
            }else {
                writer.write(username + "\n");
                writer.write(password + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0 ;
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
        driver.findElement(By.id("declineButton")).click();

    }

    private void waitForLogggedInPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("declineButton")));
    }

    private void passMainPage() throws InterruptedException {
        Random rand = new Random() ;
        WebElement searchBox = waitForMainPage() ;

        Actions actions = new Actions(driver);
        actions.moveToElement(searchBox) ;
        actions.click().perform();
        for(int i = 0 ; i < teammateName.length() ; i++){
            searchBox.sendKeys(teammateName.substring(i, i+1));
            Thread.sleep(rand.nextInt(200) + 200);
        }
        Thread.sleep(500);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();
    }

    private WebElement waitForFriendPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class, 'ts-btn-fluent') and contains(@class, 'ts-btn-fluent-with-icon') and contains(@class, 'ts-btn-fluent-primary') and contains(@class, 'ts-btn-fluent-split') and contains(@class, 'ts-btn-fluent-split-left') and contains(@class, 'start-call') and contains(@class, 'dropdown-action-btn') and contains(@class, 'three-split-btn-enabled')]")));
    }

    private WebElement waitForMainPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchInputField")));
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
    public void call() {
        try {
            passMainPage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement callButton = waitForFriendPage() ;
        callButton.click() ;
    }

    @Override
    public void close() {

    }

    @Override
    protected void answer() {
        WebElement answerButton = waitForCall() ;
        answerButton.click();
    }

    private WebElement waitForCall(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".app-svg.icons-call-video.icons-video")));
    }
}
