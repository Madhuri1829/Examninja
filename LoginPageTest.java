import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest {

    private WebDriver driver;

   /* @BeforeMethod
    public void setUp() {
        private WebDriver driver;*/
    //C:\Users\nimma\NINJA\src\Samplehtml.html

        @BeforeMethod
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("C:\\Users\\nimma\\NINJA\\src\\Samplehtml.html");
        }


    @Test
    public void testEmptyFields() {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        loginButton.click();
        WebElement errorMessage = driver.findElement(By.id("message"));
        Assert.assertEquals(errorMessage.getText(), "Please enter email address and password.");
    }

    @Test
    public void testIncorrectPasswordAttempts() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        WebElement errorMessage = driver.findElement(By.id("message"));

        // First attempt with incorrect password
        emailField.sendKeys("testuser@gmail.com");
        passwordField.sendKeys("wrongpassword");
        loginButton.click();
        Assert.assertEquals(errorMessage.getText(), "Please enter valid combination of id and password.");

        // Second attempt with incorrect password
        passwordField.clear();
        passwordField.sendKeys("wrongpassword2");
        loginButton.click();
        Assert.assertEquals(errorMessage.getText(), "Please enter valid combination of id and password.");

        // Third attempt with incorrect password
        passwordField.clear();
        passwordField.sendKeys("wrongpassword3");
        loginButton.click();
        Assert.assertEquals(errorMessage.getText(), "Attempts exceeded, please Reset the password.");
    }

    @Test
    public void testSuccessfulLogin() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));

        // Attempt with correct credentials
        emailField.sendKeys("testuser@gmail.com");
        passwordField.sendKeys("correctpassword");
        loginButton.click();

        // Check if redirection happens (the URL or the page title can be checked)
        String expectedUrl = "exam-category.html";
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Login redirection failed.");
    }

    @Test
    public void testRegisterButtonRedirection() {
        WebElement registerButton = driver.findElement(By.xpath("//button[text()='Register']"));
        registerButton.click();
        String expectedUrl = "register.html";
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrl), "Register button redirection failed.");
    }

    @Test
    public void testResetPasswordFunctionality() {
        WebElement resetPasswordButton = driver.findElement(By.xpath("//button[text()='Reset Password']"));
        resetPasswordButton.click();

        Assert.assertTrue(driver.switchTo().alert().getText().contains("Password reset functionality not implemented."));
        driver.switchTo().alert().accept();
    }
    @Test
    public void testBlankEmailField() {
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        WebElement message = driver.findElement(By.id("message"));

        passwordField.sendKeys("correctpassword");
        loginButton.click();

        Assert.assertEquals(message.getText(), "Please enter email address and password.");
    }
    @Test
    public void testInvalidEmailFormat() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        WebElement message = driver.findElement(By.id("message"));

        emailField.sendKeys("invalid-email");
        passwordField.sendKeys("correctpassword");
        loginButton.click();

        Assert.assertEquals(message.getText(), "Please enter a valid email address.");
    }
    @Test
    public void testRedirectionAfterSuccessfulLogin() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));

        emailField.sendKeys("testuser@gmail.com");
        passwordField.sendKeys("correctpassword");
        loginButton.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("exam-category.html"), "Redirection to Exam Category failed.");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
