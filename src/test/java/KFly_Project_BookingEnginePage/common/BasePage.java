package KFly_Project_BookingEnginePage.common;

import Base.WebUI;
import org.openqa.selenium.By;

public class BasePage {

    // Elements chung cho tất cả các page

    public By buttonSignIn = By.xpath("//button[normalize-space()='Sign in']");
    public By buttonSignUp = By.xpath("//button[normalize-space()='Sign up']");


    // Methods

    public void clickbuttonSignIn() {
        WebUI.clickElement(buttonSignIn);
    }

}
