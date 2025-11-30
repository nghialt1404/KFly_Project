package KFly_Project_BookingEnginePage.Testcases;

import KFly_Project_BookingEnginePage.pages.LoginPage_BKEG;
import KFly_Project_BookingEnginePage.pages.BasePage;
import KFly_Project_BookingEnginePage.common.BaseTest;
import org.testng.annotations.Test;

public class BKEG_SignInWitnOTP_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;

    @Test(priority = 1)
    public void testSigninWithOTPSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_Success();
    }

    @Test(priority = 2)
    public void testSigninWithOTP_EmailInvalid() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailInvalid();

    }

    @Test(priority = 3)
    public void testSigninWithOTP_EmailNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailNull();

    }

    @Test(priority = 4)
    public void testEnterOTPWrong() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_enterWrongOTP();
    }

    @Test(priority = 5)
    public void testEnterOTPWrong5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_enterWrongOTP5Times();

    }

    @Test(priority = 6)
    public void testResendOTPCode5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_clickResendButton5Times();

    }

    @Test(priority = 7)
    public void testSigninWithOTP_EmailInactive() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailInActive();

    }

    @Test(priority = 8)
    public void testLoginWithOTP_OTPExpired10Minutes() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.SigninWithOTP_OTPExpired10Minutes();

    }


}
