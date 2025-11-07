package KFly_Project.common.Testcases;

import KFly_Project.common.BookingEngine_Web.LoginPage;
import KFly_Project.common.BasePage;
import KFly_Project.common.BaseTest;
import org.testng.annotations.Test;

public class ForgotPassword_test extends BaseTest {

    private LoginPage loginPage;
    private BasePage basePage;

    @Test
    public void testFGPWSuccess() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPWSuccess();
    }

    @Test
    public void testFGPWEmailNotLink() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_EmailNotLink();
    }

    @Test
    public void testFGPWEmailInvalid() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_EmailInvalid();
    }

    @Test
    public void testFGPWEmailNull() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_EmailNull();
    }

    @Test
    public void FGPW_PasswordNotMatch() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_PasswordNotMatch();
    }

    @Test
    public void FGPW_ResendOTP5Times() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_clickResendButton5Times();
    }

    @Test
    void testFGPW_enterWrongOTP5Times() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_enterWrongOTP5Times();

    }

    @Test
    void testSigninWithPassword_EmailInActive() throws Exception {
        loginPage = new LoginPage();
        loginPage.FGPW_EmailInActive();

    }













}
