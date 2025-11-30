package KFly_Project_BookingEnginePage.Testcases;

import KFly_Project_BookingEnginePage.pages.LoginPage_BKEG;
import KFly_Project_BookingEnginePage.pages.BasePage;
import KFly_Project_BookingEnginePage.common.BaseTest;
import org.testng.annotations.Test;

public class BKEG_SignInWitnPassword_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;

    @Test(priority = 1)
    public void testSigninWithPasswordSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_Success();
    }

    @Test(priority = 2)
    void testSigninWithPassword_InvalidEmail() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_InvalidEmail();
    }

    @Test(priority = 3)
    void testSigninWithPassword_EmailNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_EmailNull();
    }

    @Test(priority = 4)
    void testSigninWithPassword_PasswordNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_PasswordNull();
    }

    @Test(priority = 5)
    void testSigninWithPassword_IncorrectMail() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_IncorrectEmail();

    }

    @Test(priority = 6)
    void testSigninWithPassword_IncorrectPassword() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_IncorrectPassword();
    }

    @Test(priority = 7)
    void testSigninWithPassword_EmailInactive() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_EmailInactive();
    }


}
