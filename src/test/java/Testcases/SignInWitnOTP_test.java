package Testcases;

import BookingEngine_Web.LoginPage;
import common.BasePage;
import common.BaseTest;
import org.testng.annotations.Test;

public class SignInWitnOTP_test extends BaseTest {

    private LoginPage loginPage;
    private BasePage basePage;

    @Test
    public void testSigninWithOTPSuccess() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.loginElux();
        loginPage.verifySignInOTPSuccess();
    }

    @Test void testSigninWithOTP_InvalidEmail() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.loginEluxWithInvalidEmail();
        loginPage.verifySignInFailWithInvalidEmailOrEmailNull();

    }

    @Test void testSigninWithOTP_EmailNull() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.loginEluxWithEmailNull();
        loginPage.verifySignInFailWithInvalidEmailOrEmailNull();

    }

    @Test void testEnterOTPWrong() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.enterWrongOTP();
        loginPage.verifyAlertWrongOTP();

    }

    @Test void testEnterOTPWrong5Times() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.enterWrongOTP5Times();
        loginPage.verifyAlertWrongOTP5Times();

    }

    @Test void testClickButtonResendOTPCode() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.clickResendButton();
        loginPage.verifySignInOTPSuccess();

    }

    @Test void testResendOTPCode5Times() throws Exception {
        loginPage = new LoginPage(driver);
        loginPage.clickResendButton5Times();
        loginPage.verifyResendOTPCode5Times();

    }


}
