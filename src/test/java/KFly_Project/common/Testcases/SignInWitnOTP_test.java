package KFly_Project.common.Testcases;

import KFly_Project.common.BookingEngine_Web.LoginPage_BKEG;
import KFly_Project.common.BasePage;
import KFly_Project.common.BaseTest;
import org.testng.annotations.Test;

public class SignInWitnOTP_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;
    private BasePage basePage;

    @Test
    public void testSigninWithOTPSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_Success();
    }

    @Test
    void testSigninWithOTP_InvalidEmail() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailInvalid();

    }

    @Test
    void testSigninWithOTP_EmailNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailNull();

    }

    @Test
    void testEnterOTPWrong() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_enterWrongOTP();
    }

    @Test
    void testEnterOTPWrong5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_enterWrongOTP5Times();

    }

    @Test
    void testResendOTPCode() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_clickResendButton();

    }

    @Test
    void testResendOTPCode5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_clickResendButton5Times();

    }

    @Test
    void testSigninWithOTP_EmailInactive() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_EmailInActive();

    }


}
