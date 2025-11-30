package helpers;

import java.io.File;

public class SystemHelper {
    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }

    public static String getDateTimeFormatted() {
        // Lấy thời gian hiện tại
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        // Format với pattern chuẩn (không chứa ký tự cấm)
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        // Trả về chuỗi đã format
        return now.format(formatter);
    }
}

