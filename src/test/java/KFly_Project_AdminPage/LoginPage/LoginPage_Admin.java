package KFly_Project_AdminPage.LoginPage;

import Base.WebUI;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.SubjectTerm;
import keyword.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage_Admin {

    private String url_login_admin = "https://dev-admin.eluxia.org/auth/login";

    String mailHost = "imap.gmail.com";// e.g. imap.gmail.com
    int mailPort = 993;
    String mailProtocol = "imaps";
    String mailUsername = "ray@airfeedkh.com";
    String mailPassword = "hudybwpxtpzklbhj"; // use secure storage, not hard-coded
    String subjectKeyword_Signin = "K-FLY - Admin OTP";
    int timeoutSeconds = 60;

    // Element Login With OTP
    private By headerLoginPage = By.xpath("//h1[normalize-space()='Login with Email']");
    private By inputEmail = By.xpath("//input[@placeholder='example@gmail.com']");
    private By buttonContinue = By.xpath("//button[@type='submit']");
    private By inputOTP = By.xpath("//div[@class = 'flex justify-center']/descendant::div//input");
    private By buttonVerify = By.xpath("//button[normalize-space()='Verify']");
    private By buttonResendOTP = By.xpath("//button[normalize-space()='RESEND OTP']");
    private By alertLoginSuccess = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By alertInvalidEmail = By.xpath("//p[normalize-space()='Please enter a valid email address']");
    private By alertOTPWrong5Times = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By alertResendOTP5Times = By.xpath("//div[@role='alert']/descendant::div[@class='[&_p]:leading-relaxed text-sm font-medium m-0']");
    private By SigninWithOTP_alertEmailInactive = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By SigninWithGoogle_alertEmailNotExist = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");


    // *****METHOD *******
    public void navigatetourl() {
        WebUI.openURL(url_login_admin);
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


    public void enterEmail(String mail) {
        WebUI.setText(inputEmail, mail);
    }

    public void clickButtonContinue() {
        WebUI.clickElement(buttonContinue);
    }

    public void enterOTPFromMail() throws Exception {
        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, subjectKeyword_Signin, timeoutSeconds);
        WebUI.setText(inputOTP, otp);
        WebUI.clickElement(buttonVerify);
    }

    public void SigninWithOTP_Success() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();
        enterOTPFromMail();

        // Verify
        WebUI.waitForElementVisible(alertLoginSuccess);
        String messageLoginSuccess = WebUI.getElementText(alertLoginSuccess);
        WebUI.assertEquals(messageLoginSuccess,
                "Welcome back, System Administrator! Please wait while we redirect you to the homepage.", "Message not match");
    }

    public void SigninWithOTP_EmailInvalid() throws Exception {
        navigatetourl();
        enterEmail("ray@@airfeedkh.com");
        clickButtonContinue();

        // Verify
        WebUI.waitForElementVisible(alertInvalidEmail);
        String alertEmailInvalid = WebUI.getElementText(alertInvalidEmail);
        WebUI.assertEquals(alertEmailInvalid, "Please enter a valid email address", "Message not match");
    }

    public void SigninWithOTP_EmailInactive() throws Exception {
        navigatetourl();
        enterEmail("nghialt1404@gmail.com");
        clickButtonContinue();

        // Verify
        WebUI.waitForElementVisible(SigninWithOTP_alertEmailInactive);
        String alertEmailInactive = WebUI.getElementText(SigninWithOTP_alertEmailInactive);
        WebUI.assertEquals(alertEmailInactive, "Your account is suspended. Please contact the administrator.", "Message not match");
    }


    public void SigninWithOTP_WrongOTP5Times() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        for (int i = 0; i <= 5; i++) {
            WebUI.setText(inputOTP, "439143");
            WebUI.waitForElementToBeClickAble(buttonVerify);
            WebUI.clickElement(buttonVerify);
        }

        WebUI.waitForElementVisible(alertOTPWrong5Times);
        String alertOTPWrong = WebUI.getElementText(alertOTPWrong5Times);
        WebUI.assertEquals(alertOTPWrong, "The OTP code is invalid. You have reached the maximum number of attempts. Please request a new OTP after 60 minutes.", "Message not match");

    }

    public void SigninWithOTP_ResendOTP5Times() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        // Chờ trang hiển thị nút Resend lần đầu
        WebUI.waitForElementVisible(buttonResendOTP, 70);

        for (int i = 1; i <= 5; i++) {
            try {
                WebUI.waitForElementToBeClickAble(buttonResendOTP, 10);
                WebUI.clickElement(buttonResendOTP);

                System.out.println("✅ Click Resend OTP lần " + i + " thành công");

            } catch (TimeoutException e) {
                System.out.println("⚠️ Timeout ở lần " + i);
                break;
            }

            // 🔹 Kiểm tra lại ngay sau khi click — nếu hệ thống hiện lỗi thì dừng test luôn
            // 🕑 Chờ 3 giây để hệ thống hiển thị alert nếu có
            Thread.sleep(2000);
            if (WebUI.checkElementExist(alertResendOTP5Times)) {
                break;
            }
            // Nếu chưa thấy lỗi → chờ countdown rồi thử lại
            Thread.sleep(60000);

        }
        System.out.println("🎯 Kết thúc test resend OTP (tối đa 5 lần hoặc khi có lỗi).");

        // Verify

        WebUI.waitForElementVisible(alertResendOTP5Times);
        String alertResendOTP = WebUI.getElementText(alertResendOTP5Times);
        WebUI.assertEquals(alertResendOTP, "You've requested too many OTP codes. Please wait for 59 minutes before trying again.", "Message not match");

    }

    public void SigninWithOTP_OTPExpried5Minutes() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        WebUI.waitForElementVisible(inputOTP,10);
        // Chờ 5 phút
        Thread.sleep(300000); // 300000 ms = 5 phút

        // Verify
        boolean isEnabled = DriverManager.getDriver().findElement(inputOTP).isEnabled();
        Assert.assertFalse(isEnabled,"❌ Ô nhập OTP vẫn cho phép nhập sau 5 phút");
    }


}

