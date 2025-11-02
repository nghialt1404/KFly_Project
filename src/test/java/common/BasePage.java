package common;

import Base.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    private WebDriver driver;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        new WebUI(driver);
    }

    // Elements chung cho tất cả các page

    public By buttonSignIn = By.xpath("//button[normalize-space()='Sign in']");
    public By buttonSignUp = By.xpath("//button[normalize-space()='Sign up']");


    // methods

    public void clickbuttonSignIn() {
        WebUI.clickElement(buttonSignIn);
    }

}
