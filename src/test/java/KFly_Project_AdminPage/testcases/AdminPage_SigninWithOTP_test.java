package KFly_Project_AdminPage.testcases;

import KFly_Project_AdminPage.common.BaseTest;
import KFly_Project_AdminPage.pages.LoginPage_Admin;
import org.testng.annotations.Test;

public class AdminPage_SigninWithOTP_test extends BaseTest {

    private LoginPage_Admin loginPageAdmin;

    @Test(priority = 1)

    public void testLoginWithOTP_Success() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_Success();

    }

    @Test(priority = 2)

    public void testLoginWithOTP_EmailInvalid() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_EmailInvalid();

    }

    @Test(priority = 3)

    public void testLoginWithOTP_EmailInactive() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_EmailInactive();

    }

    @Test(priority = 4)

    public void testLoginWithOTP_WrongOTP5Times() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_WrongOTP5Times();

    }

    @Test(priority = 5)

    public void testLoginWithOTP_ResendOTP5Times() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_ResendOTP5Times();

    }

    @Test(priority = 6)

    public void testLoginWithOTP_OTPExpired5Minutes() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithOTP_OTPExpried5Minutes();

    }


}
