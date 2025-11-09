package KFly_Project_BookingEnginePage.Testcases;

import KFly_Project_BookingEnginePage.LoginPage.LoginPage_BKEG;
import KFly_Project_BookingEnginePage.common.BasePage;
import KFly_Project_BookingEnginePage.common.BaseTest;
import org.testng.annotations.Test;

public class BKEG_ForgotPassword_test extends BaseTest {

    private LoginPage_BKEG loginPageBKEG;
    private BasePage basePage;

    @Test
    public void testFGPWSuccess() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPWSuccess();
    }

    @Test
    public void testFGPWEmailNotLink() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_EmailNotLink();
    }

    @Test
    public void testFGPWEmailInvalid() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_EmailInvalid();
    }

    @Test
    public void testFGPWEmailNull() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_EmailNull();
    }

    @Test
    public void FGPW_PasswordNotMatch() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_PasswordNotMatch();
    }

    @Test
    public void FGPW_ResendOTP5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_clickResendButton5Times();
    }

    @Test
    void testFGPW_enterWrongOTP5Times() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_enterWrongOTP5Times();

    }

    @Test
    void testFGPW_EmailInActive() throws Exception {
        loginPageBKEG = new LoginPage_BKEG();
        loginPageBKEG.FGPW_EmailInActive();

    }













}
