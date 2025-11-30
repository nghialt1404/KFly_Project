package constants;

import helpers.PropertiesHelper;

public class DataConfig {

    // final có nghĩa là hằng giá trị, không thể cập nhật lại

    public static int WAIT_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("WAIT_TIMEOUT"));
    public static double STEP_TIME = Double.parseDouble(PropertiesHelper.getValue("STEP_TIME"));
    public static int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));
    public static final String EXCEL_DATA_FILE_PATH = PropertiesHelper.getValue("EXCEL_DATA_FILE_PATH");
    public static final String SCREENSHOT_ALL_STEP = PropertiesHelper.getValue("SCREENSHOT_ALL_STEP");
    public static final String SCREENSHOT_FAILURE = PropertiesHelper.getValue("SCREENSHOT_FAILURE");
    public static final String SCREENSHOT_SUCCESS = PropertiesHelper.getValue("SCREENSHOT_SUCCESS");
    public static final String VIDEO_RECORD = PropertiesHelper.getValue("VIDEO_RECORD");
}
