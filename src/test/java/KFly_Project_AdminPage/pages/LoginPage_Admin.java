package KFly_Project_AdminPage.pages;


import Utils.LogUtils;
import com.sun.mail.imap.IMAPFolder;
import helpers.PropertiesHelper;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import keyword.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage_Admin {

    String mailHost = "imap.gmail.com";
    int mailPort = 993;
    String mailProtocol = "imaps";
    String mailUsername = "ray@airfeedkh.com";
    String mailPassword = "ilwakvjpmqqzxkcs";
    int timeoutSeconds = 60;

    // Element Login With OTP
    private By headerLoginPage = By.xpath("//h1[normalize-space()='Login with Email']");
    private By inputEmail = By.xpath("//input[@placeholder='example@gmail.com']");
    private By buttonContinue = By.xpath("//button[@type='submit']");
    private By inputOTP = By.xpath("//div[@class = 'flex justify-center w-[448px] h-16 mx-auto']/descendant::div//input");
    private By buttonVerify = By.xpath("//button[normalize-space()='Verify']");
    private By buttonResendOTP = By.xpath("//button[normalize-space()='RESEND OTP']");
    private By alertLoginSuccess = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By alertInvalidEmail = By.xpath("//p[normalize-space()='Please enter a valid email address']");
    private By alertOTPWrong5Times = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By alertResendOTP5Times = By.xpath("//div[@role='alert']/descendant::div[@class='[&_p]:leading-relaxed text-sm font-medium m-0']");
    private By alertEmailInactive = By.xpath("//div[@class='flex items-center justify-center gap-2']/descendant::div//div");
    private By alertOTPExpired = By.xpath("//div[@role='alert']");

    // *****METHOD *******
    public void navigatetourl() {
        WebUI.openURL(PropertiesHelper.getValue("url_admin"));
    }

    public String fetchOtpFromEmail(String host, int port, String protocol,
                                    String username, String password,
                                    int timeoutSeconds) throws Exception {

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.port", String.valueOf(port));
        props.put("mail.imaps.connectiontimeout", "15000");
        props.put("mail.imaps.timeout", "15000");
        props.put("mail.imaps.ssl.trust", "*");

        Session session = Session.getInstance(props);
        Store store = null;
        IMAPFolder inbox = null;

        // Subject: "{{OTPcode}} is your ELUX-RMS verification code"
        Pattern subjectOtpPattern = Pattern.compile(
                "\\b(\\d{4,8})\\b\\s*is\\s*your\\s*(?:ELUX\\s*-\\s*RMS|RMS\\s*-\\s*ELUX)\\s*verification\\s*code",
                Pattern.CASE_INSENSITIVE
        );

        // Body có thể là "7 0 5 7 1 3" hoặc "705713"
        Pattern bodyOtpPattern = Pattern.compile("(?<!\\d)(?:\\d\\s*){4,8}(?!\\d)");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeoutSeconds * 1000L;

        try {
            store = session.getStore("imaps");
            store.connect(host, port, username, password);

            inbox = (IMAPFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // ✅ baseline UID: tất cả mail có UID <= baseline là "cũ"
            long baselineUid = inbox.getUIDNext() - 1;
            LogUtils.info("⏳ Waiting NEW OTP after: " + new Date(startTime)
                    + " | baselineUid=" + baselineUid);

            while (System.currentTimeMillis() < endTime) {

                // ✅ Refresh folder để thấy mail mới
                inbox.getMessageCount();

                int total = inbox.getMessageCount();
                int from = Math.max(1, total - 30);
                Message[] messages = inbox.getMessages(from, total);

                // duyệt từ mới -> cũ
                for (int i = messages.length - 1; i >= 0; i--) {
                    Message msg = messages[i];
                    long uid = inbox.getUID(msg);

                    // ✅ Chỉ xử lý mail thật sự mới sau khi bắt đầu
                    if (uid <= baselineUid) continue;

                    String subject = msg.getSubject() == null ? "" : msg.getSubject();

                    // 1) OTP trong subject
                    Matcher sm = subjectOtpPattern.matcher(subject);
                    if (sm.find()) {
                        String otp = sm.group(1);
                        LogUtils.info("✅ OTP from SUBJECT: " + otp + " | uid=" + uid);
                        return otp;
                    }

                    // 2) OTP trong body (gom số lại)
                    String body = getMailText(msg);
                    if (body != null && !body.isBlank()) {
                        Matcher bm = bodyOtpPattern.matcher(body);
                        if (bm.find()) {
                            String otp = bm.group().replaceAll("\\s+", "");
                            LogUtils.info("✅ OTP from BODY: " + otp + " | uid=" + uid);
                            return otp;
                        }
                    }
                }

                WebUI.sleep(2);
            }

            throw new RuntimeException("❌ OTP not found within timeout.");

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
                if (content != null) result.append(content.toString().replaceAll("\\<.*?\\>", " "));
            } else if (part.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) part.getContent()));
            }
        }
        return result.toString();
    }


    public void enterEmail(String mail) {
        WebUI.setText(inputEmail, mail);
    }

    public void clickButtonContinue() {
        WebUI.clickElement(buttonContinue);
    }

    public void SigninWithOTP_Success() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();
        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, timeoutSeconds);
        WebUI.setText(inputOTP, otp);
        WebUI.clickElement(buttonVerify);

        // Verify
        WebUI.waitForElementVisible(alertLoginSuccess);
        String messageLoginSuccess = WebUI.getElementText(alertLoginSuccess);
        WebUI.assertEquals(messageLoginSuccess,
                "Welcome back, Ray QC! Please wait while we redirect you to the homepage.", "Message not match");
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
        WebUI.waitForElementVisible(alertEmailInactive);
        String check = WebUI.getElementText(alertEmailInactive);
        WebUI.assertEquals(check, "Your account is suspended. Please contact the administrator.", "Message not match");
    }


    public void SigninWithOTP_WrongOTP5Times() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();
        WebUI.sleep(2);

        for (int i = 1; i <= 5; i++) {
            WebUI.setText(inputOTP, "439143");
            WebUI.waitForElementToBeClickAble(buttonVerify);
            WebUI.clickElement(buttonVerify);
        }

        WebUI.waitForElementVisible(alertOTPWrong5Times);
        String alertOTPWrong = WebUI.getElementText(alertOTPWrong5Times);
        WebUI.assertEquals(alertOTPWrong, "The OTP code is invalid. You have reached the maximum number of attempts. Please request a new OTP after 60 minutes.", "Message not match");

    }

    public void SigninWithOTP_ResendOTP5Times() {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        // Chờ trang hiển thị nút Resend lần đầu
        WebUI.waitForElementVisible(buttonResendOTP, 70);

        for (int i = 1; i <= 5; i++) {
            try {
                WebUI.waitForElementToBeClickAble(buttonResendOTP, 10);
                WebUI.clickElement(buttonResendOTP);

                LogUtils.info("✅ Click Resend OTP lần " + i + " thành công");

            } catch (TimeoutException e) {
                LogUtils.info("⚠️ Timeout ở lần " + i);
                break;
            }

            // 🔹 Kiểm tra lại ngay sau khi click — nếu hệ thống hiện lỗi thì dừng test luôn
            // 🕑 Chờ 2 giây để hệ thống hiển thị alert nếu có
            WebUI.sleep(2);
            if (WebUI.checkElementExist(alertResendOTP5Times)) {
                break;
            }
            // Nếu chưa thấy lỗi → chờ countdown rồi thử lại
            WebUI.sleep(60);

        }
        LogUtils.info("🎯 Kết thúc test resend OTP (tối đa 5 lần hoặc khi có lỗi).");

        // Verify

        WebUI.waitForElementVisible(alertResendOTP5Times);
        String alertResendOTP = WebUI.getElementText(alertResendOTP5Times);
        WebUI.assertEquals(alertResendOTP, "You have requested too many OTP codes. Please wait for 59 minutes before trying again.", "Message not match");

    }

    public void SigninWithOTP_OTPExpried5Minutes() throws Exception {
        navigatetourl();
        enterEmail("ray@airfeedkh.com");
        clickButtonContinue();

        // LẤY OTP NGAY SAU KHI REQUEST
        String otp = fetchOtpFromEmail(mailHost, mailPort, mailProtocol, mailUsername, mailPassword, timeoutSeconds);
        // Chờ 5 phút
        WebUI.sleep(310);
        WebUI.setText(inputOTP, otp);
        WebUI.clickElement(buttonVerify);

        // Verify
        WebUI.waitForElementVisible(alertOTPExpired);
        String check = WebUI.getElementText(alertOTPExpired);
        WebUI.assertEquals(check, "The OTP code is invalid. Please re-enter or request a new OTP.", "Message not match");
    }
}

