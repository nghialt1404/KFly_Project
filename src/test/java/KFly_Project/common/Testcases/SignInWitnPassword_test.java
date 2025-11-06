package KFly_Project.common.Testcases;

import KFly_Project.common.BookingEngine_Web.LoginPage;
import KFly_Project.common.BasePage;
import KFly_Project.common.BaseTest;
import org.testng.annotations.Test;

public class SignInWitnPassword_test extends BaseTest {

    private LoginPage loginPage;
    private BasePage basePage;

    @Test
    public void testSigninWithPasswordSuccess() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_Success();

    }

    @Test
    void testSigninWithPassword_InvalidEmail() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_InvalidEmail();

    }

    @Test
    void testSigninWithPassword_EmailNull() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_EmailNull();

    }

    @Test
    void testSigninWithPassword_PasswordNull() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_PasswordNull();

    }

    @Test
    void testSigninWithPassword_IncorrectMail() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_IncorrectEmail();

    }

    @Test
    void testSigninWithPassword_IncorrectPassword() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_IncorrectPassword();

    }

    @Test
    void testSigninWithPassword_enterWrongOTP5Times() throws Exception {
        loginPage = new LoginPage();
        loginPage.loginWithPassword_enterWrongOTP5Times();

    }

    @Test
    void testSigninWithPassword_FGPW_EmailInActive() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_EmailInActive();

    }
}
