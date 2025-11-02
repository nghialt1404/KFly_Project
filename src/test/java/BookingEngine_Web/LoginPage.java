package BookingEngine_Web;

import Base.WebUI;
import common.BasePage;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.SubjectTerm;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends BasePage {

    private WebDriver driver;
    private String url_login_admin = "https://dev.eluxia.org/en?currency=USD";

    String mailHost = "imap.gmail.com";// e.g. imap.gmail.com
    int mailPort = 993;
    String mailProtocol = "imaps";
    String mailUsername = "ray@airfeedkh.com";
    String mailPassword = "hudybwpxtpzklbhj"; // use secure storage, not hard-coded
    String subjectKeyword = "Sign in/Sign up with OTP"; // adjust to the actual subject text
    int timeoutSeconds = 60;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        new WebUI(driver);
    }

    // Khai báo đối tượng element thuộc về trang Login
    // Login With OTP
    private By headerLoginPage = By.xpath("//h1[normalize-space()='Sign in or Create an account']");
    private By inputEmail = By.xpath("//div[@data-slot='form-item']//input");
    private By buttonContinue = By.xpath("//button[@type='submit']");
    private By inputOTP_Signin = By.xpath("//div[@data-slot='input-otp-group']/following-sibling::div//input");
    private By buttonContinue_SigninOTP = By.xpath("//button[normalize-space()='Continue']");
    private By alertSigninOTPSuccess = By.xpath("//div[@class='w-full max-w-md']/descendant::div//span");
    private By alertInvalidEmail = By.xpath("//span[normalize-space()='Enter a valid email']");
    private By alertResendOTP5Times = By.xpath("//div[@class='w-full max-w-md']/descendant::div//span");
    private By buttonContinueWithGoogle = By.xpath("//button[@data-slot='button']//span[normalize-space()='Continue with Google']");
    private By buttonContinueWithPassword = By.xpath("//button[@data-slot='button']//span[normalize-space()='Continue with Password']");

    //Login Password
    private By headerSigninWithPassword = By.xpath("//p[normalize-space()='Sign in with password']");
    private By emailSigninWithPassword = By.xpath("//input[@id='«r26»-form-item']");
    private By password = By.xpath("//input[@type='password']");
    private By buttonBackToSignin = By.xpath("//button[normalize-space()='Sign in']");
    private By textlinkForgotPassword = By.xpath("//button[normalize-space()='Forgot Password?']");
    private By buttonSigninWithOTP = By.xpath("//button[@data-slot='button']//span[normalize-space()='Sign in with OTP']");


    // Forgot Password
    private By emailForgetPassword = By.xpath("//input[@id='«r28»-form-item']");
    private By getButtonSendCode = By.xpath("//button[normalize-space()='Send code']");
    private By alertEmailNotLinkAccount = By.xpath("//div[@class='grid gap-2']/preceding-sibling::div[@xpath='2']");
    private By alertEmailNullAndInValid = By.xpath("//p[@id='«r28»-form-item-message']");
    private By inputOTPForgotPassWord = By.xpath("//input[@id='«r29»-form-item']");
    private By buttonContinueForgotPassword = By.xpath("//button[normalize-space()='Continue']");
    private By buttonResendOTPCode = By.xpath("//button[normalize-space()='Resend OTP code']");
    private By alertOTPWrong = By.xpath("//div[@data-slot='form-item']/preceding-sibling::div[@class='w-full max-w-md']");
    private By alertOTPWrong5Times = By.xpath("//div[@data-slot='form-item']/preceding-sibling::div[@class='w-full max-w-md']");


    private By inputNewPassword = By.xpath(" //input[@name='newPassword']");
    private By inputConFirmNewPassword = By.xpath("//input[@name='confirmPassword']");
    private By buttonConfirm = By.xpath("//input[@name='confirmPassword']");
    private By alertPasswordNotMatch = By.xpath("//p[@id='«r2b»-form-item-message']");
    private By alertPassWordNull = By.xpath("//p[@id='«r2a»-form-item-message']");
    private By alertSetNewPasswordSuccess = By.xpath("//div[@class='space-y-6']/descendant::div[contains(@class,'flex items-center')]");

    // Method
    public void navigatetourl() {
        WebUI.openURL(url_login_admin);
    }

    public void clickButtonSignin() {
        WebUI.clickElement(buttonSignIn);
    }

    public void enterEmailWithOTP(String email) {
        WebUI.setText(inputEmail, email);
    }

    public void clickButtonContinue() {
        WebUI.clickElement(buttonContinue);
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

    public void enterOTPFromMail() throws Exception {
        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, subjectKeyword, timeoutSeconds);
        WebUI.setText(inputOTP_Signin, otp);
        WebUI.clickElement(buttonContinue_SigninOTP);

    }

    public void loginElux() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@airfeedkh.com");
        clickButtonContinue();
        enterOTPFromMail();
        // fetch OTP using host + port + protocol
    }

    public void loginEluxWithInvalidEmail() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@@airfeedkh.com");
        clickButtonContinue();
    }

    public void loginEluxWithEmailNull() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("");
        clickButtonContinue();
    }

    public void enterWrongOTP() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@airfeedkh.com");
        clickButtonContinue();
        WebUI.setText(inputOTP_Signin, "443455");
        WebUI.clickElement(buttonContinue_SigninOTP);

    }

    public void enterWrongOTP5Times() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@airfeedkh.com");
        clickButtonContinue();

        // Nhập OTP sai một lần
        WebUI.waitForElementVisible(inputOTP_Signin, 10);
        WebUI.setText(inputOTP_Signin, "443455");

        // Lặp 5 lần click
        for (int i = 1; i <= 5; i++) {
            System.out.println("🔁 Lần thử thứ " + i + ": Nhấn Continue");
            WebUI.waitForElementToBeClickAble(buttonContinue_SigninOTP);
            WebUI.clickElement(buttonContinue_SigninOTP);
            System.out.println("✅ Click lần " + i + " thành công");

        }

        System.out.println("🎯 Đã nhấn Continue 5 lần với cùng OTP sai.");
    }


    public void clickResendButton() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@airfeedkh.com");
        clickButtonContinue();
        WebUI.waitForElementToBeClickAble(buttonResendOTPCode, 70);
        WebUI.clickElement(buttonResendOTPCode);
        enterOTPFromMail();
    }

    public void clickResendButton5Times() throws Exception {
        navigatetourl();
        clickButtonSignin();
        enterEmailWithOTP("ray@airfeedkh.com");
        clickButtonContinue();

        // Chờ trang hiển thị nút Resend lần đầu
        WebUI.waitForElementVisible(buttonResendOTPCode, 70);

        for (int i = 1; i <= 5; i++) {
            System.out.println("🔁 Lần thử thứ " + i + ": chờ nút Resend bật lại...");

            // ✅ Nếu hiển thị thông báo lỗi → dừng test
            if (WebUI.checkElementExist(alertResendOTP5Times)) {
                break;
            }

            try {
                // ✅ Mỗi vòng đều tìm lại element mới (tránh lỗi stale element)

                WebUI.waitForElementToBeClickAble(buttonResendOTPCode, 70); // chờ countdown 60s
                WebUI.clickElement(buttonResendOTPCode);

                System.out.println("✅ Click Resend OTP lần " + i + " thành công");
                Thread.sleep(2000); // chờ UI cập nhật

            } catch (TimeoutException e) {
                System.out.println("⚠️ Timeout: Nút resend chưa bật lại trong 70s, dừng ở lần " + i);
                break;
            } catch (Exception e) {
                System.out.println("⚠️ Lỗi ở lần " + i + ": " + e.getMessage());
                break;
            }
        }

        System.out.println("🎯 Kết thúc test resend OTP (tối đa 5 lần hoặc khi có lỗi).");
    }


    // Lặp 5 lần resend

    public void verifySignInOTPSuccess() {
        WebUI.waitForElementVisible(alertSigninOTPSuccess);
        String messageLoginSuccess = WebUI.getElementText(alertSigninOTPSuccess);
        WebUI.assertEquals(messageLoginSuccess,
                "Sign in successfully! Please wait, we will redirect you to homepage in a second", "Message not match");


    }

    public void verifySignInFailWithInvalidEmailOrEmailNull() {
        WebUI.waitForElementVisible(alertInvalidEmail);
        String messageInvalidEmail = WebUI.getElementText(alertInvalidEmail);
        WebUI.assertEquals(messageInvalidEmail, "Enter a valid email", "Message not match");
    }

    public void verifyAlertWrongOTP() {
        WebUI.waitForElementVisible(alertOTPWrong);
        String alertOTPFail = WebUI.getElementText(alertOTPWrong);
        WebUI.assertEquals(alertOTPFail, "The OTP code is invalid. Please re-enter or request a new OTP", "Message not match");
    }

    public void verifyAlertWrongOTP5Times() {
        WebUI.waitForElementVisible(alertOTPWrong5Times);
        String alertOTPFail = WebUI.getElementText(alertOTPWrong5Times);
        WebUI.assertEquals(alertOTPFail, "You have entered the OTP incorrectly too many times. Please request a new OTP.", "Message not match");
    }

    public void verifyResendOTPCode5Times() {
        WebUI.waitForElementVisible(alertResendOTP5Times);
        String alertMessageOTP5Times = WebUI.getElementText(alertResendOTP5Times);
        WebUI.assertEquals(alertMessageOTP5Times, "You've requested too many OTP codes. Please wait for 1 hour before trying again.", "Message not match");
    }


}
