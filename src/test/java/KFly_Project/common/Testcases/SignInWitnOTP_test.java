package KFly_Project.common.Testcases;

import KFly_Project.common.BookingEngine_Web.LoginPage;
import KFly_Project.common.BasePage;
import KFly_Project.common.BaseTest;
import org.testng.annotations.Test;

public class SignInWitnOTP_test extends BaseTest {

    private LoginPage loginPage;
    private BasePage basePage;

    @Test
    public void testSigninWithOTPSuccess() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_Success();
    }

    @Test
    void testSigninWithOTP_InvalidEmail() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_EmailInvalid();

    }

    @Test
    void testSigninWithOTP_EmailNull() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_EmailNull();

    }

    @Test
    void testEnterOTPWrong() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_enterWrongOTP();
    }

    @Test
    void testEnterOTPWrong5Times() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_enterWrongOTP5Times();

    }

    @Test
    void testResendOTPCode() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_clickResendButton();

    }

    @Test
    void testResendOTPCode5Times() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_clickResendButton5Times();

    }

    @Test
    void testSigninWithOTP_EmailInactive() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithOTP_EmailInActive();

    }


}
