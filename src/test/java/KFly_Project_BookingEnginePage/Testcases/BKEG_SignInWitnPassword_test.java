package KFly_Project_BookingEnginePage.Testcases;

import KFly_Project_BookingEnginePage.LoginPage.LoginPage_BKEG;
import KFly_Project_BookingEnginePage.common.BasePage;
import KFly_Project_BookingEnginePage.common.BaseTest;
import org.testng.annotations.Test;

public class BKEG_SignInWitnPassword_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;
    private BasePage basePage;

    @Test
    public void testSigninWithPasswordSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_Success();

    }

    @Test
    void testSigninWithPassword_InvalidEmail() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_InvalidEmail();

    }

    @Test
    void testSigninWithPassword_EmailNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_EmailNull();

    }

    @Test
    void testSigninWithPassword_PasswordNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_PasswordNull();

    }

    @Test
    void testSigninWithPassword_IncorrectMail() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_IncorrectEmail();

    }

    @Test
    void testSigninWithPassword_IncorrectPassword() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_IncorrectPassword();

    }

    @Test
    void testSigninWithPassword_EmailInactive() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithPassword_EmailInactive();

    }



}
