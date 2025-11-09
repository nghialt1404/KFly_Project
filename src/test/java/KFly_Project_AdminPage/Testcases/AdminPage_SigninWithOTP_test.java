package KFly_Project_AdminPage.Testcases;

import KFly_Project_AdminPage.common.BaseTest;
import KFly_Project_AdminPage.LoginPage.LoginPage_Admin;
import org.testng.annotations.Test;

public class AdminPage_SigninWithOTP_test extends BaseTest {

    private LoginPage_Admin loginPageAdmin;

    @Test

    public void testLoginWithOTP_Success() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_Success();

    }


    @Test

    public void testLoginWithOTP_InvalidEmail() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_EmailInvalid();

    }

    @Test

    public void testLoginWithOTP_EmailInactive() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_EmailInactive();

    }

    @Test

    public void testLoginWithOTP_WrongOTP5Times() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_WrongOTP5Times();

    }

    @Test

    public void testLoginWithOTP_ResendOTP5Times() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_ResendOTP5Times();

    }

    @Test

    public void testLoginWithOTP_OTPExpired5Minutes() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_OTPExpried5Minutes();

    }


}
