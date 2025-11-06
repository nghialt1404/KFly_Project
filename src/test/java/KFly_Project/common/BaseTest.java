package KFly_Project.common;

import keyword.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

public class BaseTest {

    public SoftAssert softAssert;

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chorme") String browserName) {
        WebDriver driver;
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                System.out.println("Launching Chrome browser...");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                System.out.println("Launching Firefox browser...");
                break;
            case "edge":
                driver = new EdgeDriver();
                System.out.println("Launching Edge browser...");
                break;
            default:
                System.out.println("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }
        DriverManager.setDriver(driver);

        DriverManager.getDriver().manage().window().maximize();
        softAssert = new SoftAssert();

    }

    @AfterMethod
    public void closeDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quit();
            softAssert.assertAll();
        }
    }
}

