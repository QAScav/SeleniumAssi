package MyStepDefinitions;

import Logic.mailLogic;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class mystepdefs {
    WebDriver driver;
    WebDriverWait wait;
    mailLogic mL = new mailLogic();

    private void sendKeys(WebDriver driver, By by, String text) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.sendKeys(text);
    }

    @Given("I start a {string}")
    public void iStartA(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32 (1)\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\edgedriver_win32\\msedgedriver.exe");
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new EdgeDriver(edgeOptions);
            driver.manage().window().maximize();
        }
        driver.get("https://login.mailchimp.com/signup/");
    }

    @Given("I have input a valid {string}")
    public void iHaveInputAValid(String email) {
        WebElement emailField = driver.findElement(By.id("email"));
        if (!(email.equalsIgnoreCase(""))) {
            String newEmail = mL.appendFormatAndNumbers(email);
            emailField.sendKeys(newEmail);
        } else {
            emailField.sendKeys("");
        }
    }

    public String Used = "THISISATESTIPROMISE";

    @Given("I have input a {string}")
    public void iHaveInputA(String username) {
        WebElement UNField = driver.findElement(By.id("new_username"));
        String newUN = mL.appendFormatAndNumbers(username);
        if (username.equalsIgnoreCase("long")) {
            newUN = mL.appendLong(username);
            UNField.click();
            UNField.clear();
            UNField.sendKeys(newUN);
        } else if (username.equalsIgnoreCase("same")) {
            UNField.click();
            UNField.clear();
            UNField.sendKeys(Used);
        } else {
            UNField.click();
            UNField.clear();
            UNField.sendKeys(newUN);
        }
    }

    @Given("I have input a secure {string}")
    public void iHaveInputASecure(String password) {
        String newPass = mL.appendFormatAndNumbers(password);
        sendKeys(driver, By.id("new_password"), newPass);
    }

    @When("I press Sign Up")
    public void iPressSignUp() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions action = new Actions(driver);
        WebElement marketing = driver.findElement(By.id("marketing_newsletter"));
        marketing.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("create-account-enabled")));
        WebElement SignUp = driver.findElement(By.id("create-account-enabled"));
        action.moveToElement(SignUp).perform();
        SignUp.click();

    }

    @Then("check email page {string}")
    public void checkEmailPage(String works) {
        if (works.equalsIgnoreCase("true")) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".\\!margin-bottom--lv3")));
            WebElement CheckEmail = driver.findElement(By.cssSelector(".\\!margin-bottom--lv3"));
            String actual = CheckEmail.getText();
            String expected = "Check your email";
            assertEquals(actual, expected);
        } else if (works.equalsIgnoreCase("f1")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error")));
            WebElement longError = driver.findElement(By.className("invalid-error"));
            String Expected = longError.getText();
            String Actual = "Enter a value less than 100 characters long";
            assertEquals(Expected, Actual);
        } else if (works.equalsIgnoreCase("f2")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error")));
            WebElement sameError = driver.findElement(By.className("invalid-error"));
            String Expected = sameError.getText();
            String Actual = "Great minds think alike - someone already has this username. If it's you, log in.";
            assertEquals(Expected, Actual);
        } else if (works.equalsIgnoreCase("f3")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error")));
            WebElement emptyError = driver.findElement(By.className("invalid-error"));
            String Expected = emptyError.getText();
            String Actual = "An email address must contain a single @.";
            assertEquals(Expected, Actual);
        }
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();

    }
}

