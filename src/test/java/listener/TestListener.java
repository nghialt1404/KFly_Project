package listener;

import Utils.LogUtils;
import helpers.CaptureHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static constants.DataConfig.VIDEO_RECORD;


public class TestListener implements ITestListener {

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }


    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Setup môi trường onStart: " + result.getStartDate());
        // Initialize Report
        // Connect to Database
        // Call API get Token
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Kết thúc bộ test: " + result.getEndDate());

    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Bắt đầu chạy test case: " + result.getName());

        if (VIDEO_RECORD.equals("true")) {
            CaptureHelper.startRecord(result.getName());
        }


    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case " + result.getName() + " is passed.");
        LogUtils.info("==> Status: " + result.getStatus());

        if (VIDEO_RECORD.equals("true")) {
            CaptureHelper.stopRecord();
        }

    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test case " + result.getName() + " is failed.");
        LogUtils.info("==> Status: " + result.getStatus());

        LogUtils.error(" ==> Reason : " + result.getThrowable());
        CaptureHelper.takeScreenShot(result.getName());


        if (VIDEO_RECORD.equals("true")) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test case " + result.getName() + " is skipped.");
        LogUtils.warn("==> Status: " + result.getStatus());

        if (VIDEO_RECORD.equals("true")) {
            CaptureHelper.stopRecord();
        }


    }
}


