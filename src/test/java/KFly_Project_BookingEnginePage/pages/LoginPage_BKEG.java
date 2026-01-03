package KFly_Project_BookingEnginePage.pages;

import helpers.PropertiesHelper;
import keyword.WebUI;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.SubjectTerm;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage_BKEG extends BasePage {

    String mailHost = "imap.gmail.com";// e.g. imap.gmail.com
    int mailPort = 993;
    String mailProtocol = "imaps";
    String mailUsername = "ray@airfeedkh.com";
    String mailPassword = "xcoyyiyfcnwqbpwr"; // use secure storage, not hard-coded
    String subjectKeyword_Signin = "Sign";// in/Sign up with OTP;
    String subjectKeyword_FGPW = "Reset Password with OTP";
    int timeoutSeconds = 60;

    // Sign In With OTP
    private By headerLoginPage = By.xpath("//h1[normalize-space()='Sign in or Create an account']");
    private By inputEmail = By.xpath("//div[@data-slot='form-item']//input[@type='email']");
    private By buttonContinue = By.xpath("//button[@type='submit']");
    private By inputOTP_Signin = By.xpath("//div[@data-slot='input-otp-group']/following-sibling::div//input");
    private By buttonContinue_SigninOTP = By.xpath("//button[normalize-space()='Continue']");
    private By alertLoginSuccess = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By alertEmailNull=By.xpath("//p[normalize-space()='Email is required']");
    private By alertInvalidEmail = By.xpath("//p[normalize-space()='Enter a valid email']");
    private By alertResendOTP5Times = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By AlertSigninWithOTP_FGPW_Duration60s = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By buttonSigninWithOTP = By.xpath("//button[normalize-space()='Sign in with OTP']");
    private By buttonBacktoSignIn = By.xpath("//button[normalize-space()='Sign in']");

    //Sign In With Password
    private By buttonContinueWithPassword = By.xpath("//button[normalize-space()='Continue with password']");
    private By inputpassword = By.xpath("//div[@data-slot='form-item']//input[@type='password']");
    private By textlinkForgotPassword = By.xpath("//button[normalize-space()='Forgot Password?']");
    private By alertInvalidEmail_SigninWithPassword = By.xpath("//p[normalize-space()='Enter a valid email']");
    private By alertIncorrectEmailOrPassword = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By buttonSignin = By.xpath("//button[normalize-space()='Sign in']");
    private By alertLoginPasswordSuccess = By.xpath("//span[normalize-space()='Hi, ray@airfeedkh.com']");
    private By alertEmailNull_SigninWithPassword = By.xpath("//p[normalize-space()='Email is required']");
    private By alertPasswordNull = By.xpath("//p[normalize-space()='Password is required']");
    private By alertSigninWithPasswordSuccess = By.xpath("//div[@class='w-full max-w-md']/descendant::div//span");


    // Forgot Password
    private By buttonForgetPassword = By.xpath("//button[normalize-space()='Forgot Password?']");
    private By emailForgetPassword = By.xpath("//input[@id='«r28»-form-item']");
    private By buttonSendCode = By.xpath("//button[normalize-space()='Send code']");
    private By alertEmailNotLinkAccount = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By alertEmailNullAndInValid = By.xpath("//p[normalize-space()='Enter a valid email'])");
    private By inputOTPForgotPassWord = By.xpath("//div[@data-slot='input-otp-group']/following-sibling::div//input");
    private By buttonContinueForgotPassword = By.xpath("//button[normalize-space()='Continue']");
    private By buttonResendOTPCode = By.xpath("//button[normalize-space()='Resend OTP code']");
    private By alertOTPWrong = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By alertOTPWrong5Times = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");
    private By alertFGPW_SigninWithOTP_Duration60s = By.xpath("//div[@class='w-full']/descendant::div//span");


    private By inputNewPassword = By.xpath("//input[@name='newPassword']");
    private By inputConFirmNewPassword = By.xpath("//input[@name='confirmPassword']");
    private By buttonConfirm = By.xpath("//button[normalize-space()='Confirm']");
    private By alertPasswordNotMatched = By.xpath("//div[@data-slot='form-item']/descendant::p");
    private By alertPassWordNull = By.xpath("//div[@data-slot='form-item']/descendant::p[normalize-space()='Password must be at least 8 characters long']");
    private By alertSetNewPasswordSuccess = By.xpath("//p[normalize-space()= 'Password changed successfully! Please wait, we will redirect you to homepage in a second.']");
    private By FGPW_AlertEmailNullAndInValid = By.xpath("//div[@data-slot='form-item']/descendant::p");
    private By alertEmaiLInActive = By.xpath("//div[@class='relative pl-7 flex items-center']/descendant::div");

    // ************ Method Common ***********
    public void navigatetourl() {
        WebUI.openURL(PropertiesHelper.getValue("url_bkeg"));
    }

// ************* Method SigninWithOTP *******************

    public void clickButtonContinue() {
        WebUI.clickElement(buttonContinue);
    }

    public void clickButtonSignWithOTP() {
        WebUI.clickElement(buttonSigninWithOTP);
    }


    public String fetchOtpFromEmail(String host, int port, String protocol, String username, String password, String subjectKeyword, int timeoutSeconds) throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", protocol);
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.port", String.valueOf(port));
        props.put("mail.imaps.connectiontimeout", "15000");
        props.put("mail.imaps.timeout", "15000");
        props.put("mail.imaps.ssl.trust", "*");

        Session session = Session.getInstance(props);
        Store store = null;
        Folder inbox = null;

        try {
            store = session.getStore(protocol);
            store.connect(host, port, username, password);
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // ✅ Lấy thời điểm bắt đầu để so sánh mail mới
            long startTime = System.currentTimeMillis();
            long endTime = startTime + timeoutSeconds * 1000L;
            Pattern otpPattern = Pattern.compile("\\b(\\d{4,8})\\b");

            System.out.println("⏳ Waiting for new OTP mail after: " + new java.util.Date(startTime));

            Message lastChecked = null;
            String otp = null;

            while (System.currentTimeMillis() < endTime) {
                // tìm mail có subject chứa từ khóa
                Message[] messages = inbox.search(new SubjectTerm(subjectKeyword));
                if (messages != null && messages.length > 0) {
                    Message latest = messages[messages.length - 1];

                    // Kiểm tra mail có mới hơn thời điểm start không
                    java.util.Date sentDate = latest.getSentDate();
                    java.util.Date receivedDate = latest.getReceivedDate();

                    long sentTime = sentDate != null ? sentDate.getTime() : 0;
                    long receivedTime = receivedDate != null ? receivedDate.getTime() : 0;
                    long mailTime = Math.max(sentTime, receivedTime);

                    if (mailTime > startTime) {
                        // Chỉ đọc nếu là mail mới
                        String body = getMailText(latest);
                        Matcher matcher = otpPattern.matcher(body);
                        if (matcher.find()) {
                            otp = matcher.group(1);
                            System.out.println("✅ Found NEW OTP: " + otp);
                            System.out.println("📨 Mail sent: " + sentDate + " | received: " + receivedDate);
                            System.out.println("📩 Subject: " + latest.getSubject());
                            break;
                        } else {
                            System.out.println("⚠️ Found new mail but no OTP pattern found.");
                        }
                    } else {
                        System.out.println("⌛ Old mail found (" + sentDate + "), waiting...");
                    }
                }

                Thread.sleep(3000);
                inbox.getMessageCount(); // refresh mailbox (IMAP keeps connection alive)
            }

            if (otp == null) {
                throw new RuntimeException("❌ OTP not found within timeout.");
            }
            return otp;

        } catch (MessagingException me) {
            if (me.getCause() instanceof java.net.ConnectException) {
                throw new RuntimeException("⚠️ Could not connect to IMAP host " + host + ":" + port + ". Check network or credentials.", me);
            }
            throw me;
        } finally {
            if (inbox != null && inbox.isOpen()) try {
                inbox.close(false);
            } catch (Exception ignored) {
            }
            if (store != null && store.isConnected()) try {
                store.close();
            } catch (Exception ignored) {
            }
        }
    }

    private String getMailText(Message message) throws Exception {
        if (message.isMimeType("text/*")) {
            Object content = message.getContent();
            return content != null ? content.toString() : "";
        }
        if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }


    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart part = mimeMultipart.getBodyPart(i);
            if (part.isMimeType("text/plain")) {
                Object content = part.getContent();
                if (content != null) result.append(content.toString());
            } else if (part.isMimeType("text/html")) {
                Object content = part.getContent();
                if (content != null) {
                    // crude HTML -> text fallback (you can improve with jsoup)
                    result.append(content.toString().replaceAll("\\<.*?\\>", " "));
                }
            } else if (part.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) part.getContent()));
            }
        }
        return result.toString();
    }

    // Case-insensitive subject search
    private static class SubjectContainsTerm extends jakarta.mail.search.SearchTerm {
        private final String keyword;

        SubjectContainsTerm(String keyword) {
            this.keyword = (keyword == null) ? "" : keyword.toLowerCase();
        }

        @Override
        public boolean match(Message msg) {
            try {
                String subject = msg.getSubject();
                return subject != null && subject.toLowerCase().contains(keyword);
            } catch (MessagingException e) {
                return false;
            }
        }
    }


    public void loginWithOTP_Success() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, subjectKeyword_Signin, timeoutSeconds);
        WebUI.setText(inputOTP_Signin, otp);
        WebUI.clickElement(buttonContinueForgotPassword);

        // Verify
        WebUI.waitForElementVisible(alertLoginSuccess);
        String messageLoginSuccess = WebUI.getElementText(alertLoginSuccess);
        WebUI.assertEquals(messageLoginSuccess,
                "Sign in successfully! Please wait, we will redirect you to homepage in a second", "Message not match");
    }

    public void loginWithOTP_EmailNull() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("");
        clickButtonContinue();

        // Verify
        WebUI.waitForElementVisible(alertEmailNull);
        String messageInvalidEmail = WebUI.getElementText(alertEmailNull);
        WebUI.assertEquals(messageInvalidEmail, "Email is required", "Message not match");
    }

    public void loginWithOTP_EmailInvalid() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@@gmail.com");
        clickButtonContinue();

        // Verify
        WebUI.waitForElementVisible(alertInvalidEmail);
        String messageInvalidEmail = WebUI.getElementText(alertInvalidEmail);
        WebUI.assertEquals(messageInvalidEmail, "Enter a valid email", "Message not match");
    }

    public void loginWithOTP_enterWrongOTP() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();
        WebUI.setText(inputOTP_Signin, "443455");
        WebUI.clickElement(buttonContinue_SigninOTP);


        WebUI.waitForElementVisible(alertOTPWrong);
        String alertOTPFail = WebUI.getElementText(alertOTPWrong);
        WebUI.assertEquals(alertOTPFail, "The OTP code is invalid. Please re-enter or request a new OTP", "Message not match");

    }

    public void loginWithOTP_enterWrongOTP5Times() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();
        WebUI.waitForElementVisible(inputOTP_Signin, 10);
        WebUI.setText(inputOTP_Signin, "443455");

        // Lặp 5 lần click continue
        for (int i = 1; i <= 5; i++) {
            WebUI.waitForElementToBeClickAble(buttonContinue_SigninOTP);
            WebUI.clickElement(buttonContinue_SigninOTP);
        }

        WebUI.waitForElementVisible(alertOTPWrong5Times);
        String alertOTPFail = WebUI.getElementText(alertOTPWrong5Times);
        WebUI.assertEquals(alertOTPFail, "You have entered the OTP incorrectly too many times. Please request a new OTP.", "Message not match");

    }

    public void loginWithOTP_clickResendButton5Times() {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        // Chờ trang hiển thị nút Resend lần đầu
        WebUI.waitForElementVisible(buttonResendOTPCode, 70);

        for (int i = 1; i <= 5; i++) {
            try {
                WebUI.waitForElementToBeClickAble(buttonResendOTPCode, 20);
                WebUI.clickElement(buttonResendOTPCode);
                System.out.println("✅ Click Resend OTP lần " + i + " thành công");
            } catch (TimeoutException e) {
                System.out.println("⚠️ Timeout ở lần " + i);
                break;
            }

            // 🔹 Kiểm tra lại ngay sau khi click — nếu hệ thống hiện lỗi thì dừng test luôn
            // 🕑 Chờ 3 giây để hệ thống hiển thị alert nếu có
            WebUI.sleep(2);
            if (WebUI.checkElementExist(alertResendOTP5Times)) {
                break;
            }
            // Nếu chưa thấy lỗi → chờ countdown rồi thử lại
            WebUI.sleep(60);
        }

        System.out.println("🎯 Kết thúc test resend OTP (tối đa 5 lần hoặc khi có lỗi).");

        // Verify
        WebUI.waitForElementVisible(alertResendOTP5Times);
        String alertMessageOTP5Times = WebUI.getElementText(alertResendOTP5Times);
        WebUI.assertEquals(alertMessageOTP5Times, "You've requested too many OTP codes. Please wait for 1 hour before trying again.", "Message not match");
    }

    public void loginWithOTP_EmailInActive() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmail("nghialt1404@gmail.com");
        clickButtonContinue();

        // Verify
        WebUI.waitForElementVisible(alertEmaiLInActive);
        String AlertSetNewPasswordSuccess = WebUI.getElementText(alertEmaiLInActive);
        WebUI.assertEquals(AlertSetNewPasswordSuccess, "This account is currently inactive. Please reach out to our support team to reactivate your account.", "Message not match");

    }

    public void SigninWithOTP_OTPExpired10Minutes() {
        navigatetourl();
        clickButtonSignin();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        WebUI.waitForElementVisible(inputOTP_Signin, 10);
        // Chờ 10 phút
        WebUI.sleep(600);

        // Verify
        WebUI.waitForElementVisible(headerLoginPage, 10);
        boolean headerloginpagedisplay = WebUI.checkElementExist(headerLoginPage);
        Assert.assertTrue(headerloginpagedisplay, "Not back to login page");
    }

    // ********** METHOD FOR LOGIN PASSWORD **************

    public void clickButtonContinueWithPassword() {
        WebUI.clickElement(buttonContinueWithPassword);
    }

    public void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    public void enterPassword(String password) {
        WebUI.setText(inputpassword, password);
    }

    public void clickButtonSignin() {
        WebUI.clickElement(buttonSignIn);
    }

    public void loginWithPassword_Success() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("ray@airfeedkh.com");
        enterPassword("raygay11$");
        WebUI.clickElement(buttonSignin);

        // Verify
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(alertLoginPasswordSuccess );
        Assert.assertTrue(WebUI.checkElementExist(alertLoginPasswordSuccess, 5, 1000),"Login with password not success");
    }

    public void loginWithPassword_EmailNull() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("");
        enterPassword("raygay11$");
        WebUI.clickElement(buttonSignin);

        WebUI.waitForElementVisible(alertEmailNull);
        String textAlertEmailNull = WebUI.getElementText(alertEmailNull);
        WebUI.assertEquals(textAlertEmailNull, "Email is required", "Message not match");
    }


    public void loginWithPassword_PasswordNull() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("ray@airfeedkh.com");
        enterPassword("");
        WebUI.clickElement(buttonSignin);

        WebUI.waitForElementVisible(alertPasswordNull);
        String textAlertPasswordNull = WebUI.getElementText(alertPasswordNull);
        WebUI.assertEquals(textAlertPasswordNull, "Password is required", "Message not match");
    }

    public void loginWithPassword_IncorrectEmail() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("ray123@airfeedkh.com");
        enterPassword("raygay11$");
        WebUI.clickElement(buttonSignin);

        WebUI.waitForElementVisible(alertIncorrectEmailOrPassword);
        String textAlertIncorrectEmailOrPassword = WebUI.getElementText(alertIncorrectEmailOrPassword);
        WebUI.assertEquals(textAlertIncorrectEmailOrPassword, "Incorrect email or password.", "Message not match");

    }

    public void loginWithPassword_IncorrectPassword() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("ray@airfeedkh.com");
        enterPassword("abcxyz");
        WebUI.clickElement(buttonSignin);

        WebUI.waitForElementVisible(alertIncorrectEmailOrPassword);
        String textAlertIncorrectEmailOrPassword = WebUI.getElementText(alertIncorrectEmailOrPassword);
        WebUI.assertEquals(textAlertIncorrectEmailOrPassword, "Incorrect email or password.", "Message not match");

    }

    public void loginWithPassword_InvalidEmail() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("ray@@airfeedkh.com");
        enterPassword("raygay11$");
        WebUI.clickElement(buttonSignin);

        // Verify
        WebUI.waitForElementVisible(alertInvalidEmail_SigninWithPassword);
        String messageInvalidEmail = WebUI.getElementText(alertInvalidEmail_SigninWithPassword);
        WebUI.assertEquals(messageInvalidEmail, "Enter a valid email", "Message not match");

    }

    public void loginWithPassword_EmailInactive() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        enterEmail("nghialt1404@gmail.com");
        enterPassword("raygay11$");
        WebUI.clickElement(buttonSignin);


        // Verify
        WebUI.waitForElementVisible(alertEmaiLInActive);
        String AlertSetNewPasswordSuccess = WebUI.getElementText(alertEmaiLInActive);
        WebUI.assertEquals(AlertSetNewPasswordSuccess, "This account is currently inactive. Please reach out to our support team to reactivate your account.", "Message not match");

    }

    // ************* FORGOT PASSWORD **************

    public void clickButtonBacktoSignin() {
        WebUI.clickElement(buttonBacktoSignIn);
    }


    public void clickButtonForgotPassword() {
        WebUI.clickElement(buttonForgetPassword);
    }

    public void clickButtonSendCode() {
        WebUI.clickElement(buttonSendCode);
    }

    public void clickButtonContinue_FGPW() {
        WebUI.clickElement(buttonContinueForgotPassword);
    }

    public void enterNewPassword(String password) {
        WebUI.setText(inputNewPassword, password);
    }

    public void enterConfirmNewPassword(String confirmpassword) {
        WebUI.setText(inputConFirmNewPassword, confirmpassword);
    }


    public void clickButtonConfirm_FGPW() {
        WebUI.clickElement(buttonConfirm);
    }

    public void FGPW_clickResendButton5Times() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray@airfeedkh.com");
        clickButtonSendCode();

        // Chờ trang hiển thị nút Resend lần đầu
        WebUI.waitForElementVisible(buttonResendOTPCode, 70);

        for (int i = 1; i <= 5; i++) {

            //  Hiển thị thông báo lỗi → dừng test
            if (WebUI.checkElementExist(alertResendOTP5Times)) {
                break;
            }
            try {
                WebUI.waitForElementToBeClickAble(buttonResendOTPCode, 70); // chờ countdown 60s
                WebUI.clickElement(buttonResendOTPCode);

                System.out.println("✅ Click Resend OTP lần " + i + " thành công");
                Thread.sleep(2000);

            } catch (TimeoutException e) {
                System.out.println("⚠️ Timeout: Nút resend chưa bật lại trong 70s, dừng ở lần " + i);
                break;
            } catch (Exception e) {
                System.out.println("⚠️ Lỗi ở lần " + i + ": " + e.getMessage());
                break;
            }
        }

        System.out.println("🎯 Kết thúc test resend OTP (tối đa 5 lần hoặc khi có lỗi).");

        // Verify
        WebUI.waitForElementVisible(alertResendOTP5Times);
        String alertMessageOTP5Times = WebUI.getElementText(alertResendOTP5Times);
        WebUI.assertEquals(alertMessageOTP5Times, "You've requested too many OTP codes. Please wait for 1 hour before trying again.", "Message not match");

    }

    public void FGPW_enterWrongOTP5Times() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray@airfeedkh.com");
        clickButtonSendCode();
        WebUI.waitForElementVisible(inputOTPForgotPassWord, 10);
        WebUI.setText(inputOTPForgotPassWord, "443455");

        // Lặp 5 lần click continue
        for (int i = 1; i <= 5; i++) {
            WebUI.waitForElementToBeClickAble(buttonContinue_SigninOTP);
            WebUI.clickElement(buttonContinue_SigninOTP);
        }

        WebUI.waitForElementVisible(alertOTPWrong5Times);
        String alertOTPFail = WebUI.getElementText(alertOTPWrong5Times);
        WebUI.assertEquals(alertOTPFail, "You have entered the OTP incorrectly too many times. Please request a new OTP.", "Message not match");

    }

    public void FGPWSuccess() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray@airfeedkh.com");
        clickButtonSendCode();
        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, subjectKeyword_FGPW, timeoutSeconds);
        WebUI.setText(inputOTP_Signin, otp);
        WebUI.clickElement(buttonContinueForgotPassword);

        WebUI.waitForPageLoaded();
        enterNewPassword("raygay11$");
        enterConfirmNewPassword("raygay11$");
        clickButtonConfirm_FGPW();

        // Verify
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(alertSetNewPasswordSuccess);
        String AlertSetNewPasswordSuccess = WebUI.getElementText(alertSetNewPasswordSuccess);
        WebUI.assertEquals(AlertSetNewPasswordSuccess, "Password changed successfully! Please wait, we will redirect you to homepage in a second.", "Message not match");

    }

    public void FGPW_EmailNotLink() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray123@airfeedkh.com");
        clickButtonSendCode();

        // Verify
        WebUI.waitForElementVisible(alertEmailNotLinkAccount);
        String AlertEmailNotLink = WebUI.getElementText(alertEmailNotLinkAccount);
        WebUI.assertEquals(AlertEmailNotLink, "Your email isn't linked to any account", "Message not match");
    }

    public void FGPW_EmailInvalid() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray@@airfeedkh.com");
        clickButtonSendCode();

        // Verify
        WebUI.waitForElementVisible(FGPW_AlertEmailNullAndInValid);
        String AlertEmailNotLink = WebUI.getElementText(FGPW_AlertEmailNullAndInValid);
        WebUI.assertEquals(AlertEmailNotLink, "Enter a valid email", "Message not match");
    }

    public void FGPW_EmailNull() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("");
        clickButtonSendCode();

        // Verify
        WebUI.waitForElementVisible(FGPW_AlertEmailNullAndInValid);
        String AlertEmailNotLink = WebUI.getElementText(FGPW_AlertEmailNullAndInValid);
        WebUI.assertEquals(AlertEmailNotLink, "Email is required", "Message not match");
    }

    public void FGPW_PasswordNotMatch() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("ray@airfeedkh.com");
        clickButtonSendCode();

        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, subjectKeyword_FGPW, timeoutSeconds);
        WebUI.setText(inputOTP_Signin, otp);
        WebUI.clickElement(buttonContinueForgotPassword);

        enterNewPassword("raygay11$");
        enterConfirmNewPassword("raygay11#");
        clickButtonConfirm_FGPW();

        // Verify
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(alertPasswordNotMatched);
        String AlertSetNewPasswordSuccess = WebUI.getElementText(alertPasswordNotMatched);
        WebUI.assertEquals(AlertSetNewPasswordSuccess, "Passwords are not matched", "Message not match");

    }

    public void FGPW_EmailInActive() throws Exception {
        navigatetourl();
        clickButtonSignin();
        clickButtonContinueWithPassword();
        clickButtonForgotPassword();
        enterEmail("nghialt1404@gmail.com");
        clickButtonSendCode();

        // Verify
        WebUI.waitForPageLoaded();
        WebUI.waitForElementVisible(alertEmaiLInActive);
        String AlertSetNewPasswordSuccess = WebUI.getElementText(alertEmaiLInActive);
        WebUI.assertEquals(AlertSetNewPasswordSuccess, "This account is currently inactive. Please reach out to our support team to reactivate your account.", "Message not match");

    }


}
