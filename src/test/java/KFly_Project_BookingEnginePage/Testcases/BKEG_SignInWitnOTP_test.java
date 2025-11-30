package KFly_Project_BookingEnginePage.Testcases;

import KFly_Project_BookingEnginePage.pages.LoginPage_BKEG;
import KFly_Project_BookingEnginePage.pages.BasePage;
import KFly_Project_BookingEnginePage.common.BaseTest;
import org.testng.annotations.Test;

public class BKEG_SignInWitnOTP_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;

    @Test
    public void testSigninWithOTPSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.loginWithOTP_Success();
    }

    @Test
    void testSigninWithOTP_EmailInvalid() throws Exception {
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

    @Test

    public void testLoginWithOTP_OTPExpired10Minutes() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.SigninWithOTP_OTPExpired10Minutes();

    }


}
