package KFly_Project_BookingEnginePage.common;

import KFly_Project_BookingEnginePage.pages.BasePage;
import Utils.LogUtils;
import drivers.DriverManager;
import helpers.PropertiesHelper;
import listener.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

@Listeners(TestListener.class)
public class BaseTest extends BasePage {

    public SoftAssert softAssert;

    @BeforeSuite
    public void setupBeforeSuite() {
        PropertiesHelper.loadAllFiles();
    }


    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chorme") String browserName) {
        WebDriver driver;
        if (PropertiesHelper.getValue("BROWSER").isEmpty() || PropertiesHelper.getValue("BROWSER") == null) {
            browserName = browserName;
        } else {
            browserName = PropertiesHelper.getValue("BROWSER");
        }
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                LogUtils.info("Launching Chrome browser...");
                driver = new ChromeDriver();
                break;
            case "firefox":
                LogUtils.info("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                LogUtils.info("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                LogUtils.info("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();

        }
        DriverManager.setDriver(driver);
        DriverManager.getDriver().manage().window().maximize();
        softAssert = new SoftAssert();

    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult result) {

        if (DriverManager.getDriver() != null) {
            DriverManager.quit();
            softAssert.assertAll();
        }
    }
}
