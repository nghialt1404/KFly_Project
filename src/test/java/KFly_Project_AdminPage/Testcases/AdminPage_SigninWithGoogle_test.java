package KFly_Project_AdminPage.Testcases;

import KFly_Project_AdminPage.LoginPage.LoginPage_Admin;
import KFly_Project_AdminPage.common.BaseTest;
import org.testng.annotations.Test;

public class AdminPage_SigninWithGoogle_test extends BaseTest {

    private LoginPage_Admin loginPageAdmin;

    @Test

    public void testLoginWithGoogle_Success() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithGoogle_Success();

    }
    @Test
    public void testLoginWithGoogle_EmailInactive() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithGoogle_EmailInActive();

    }

    @Test
    public void testLoginWithGoogle_EmailNotExist() throws Exception {
        loginPageAdmin = new LoginPage_Admin();
        loginPageAdmin.SigninWithGoogle_EmailNotExist();

    }




}
