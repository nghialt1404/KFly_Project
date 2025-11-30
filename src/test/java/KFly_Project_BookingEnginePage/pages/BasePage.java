package KFly_Project_BookingEnginePage.pages;

import keyword.WebUI;
import org.openqa.selenium.By;

public class BasePage {

    // Elements chung cho tất cả các page
    private LoginPage_BKEG loginPage_bkeg;

    public By buttonSignIn = By.xpath("//button[normalize-space()='Sign in']");
    public By buttonSignUp = By.xpath("//button[normalize-space()='Sign up']");



    // methods

    public LoginPage_BKEG getLoginPage() {
        if (loginPage_bkeg == null) {
            loginPage_bkeg = new LoginPage_BKEG();
        }
        return loginPage_bkeg;
    }
}

