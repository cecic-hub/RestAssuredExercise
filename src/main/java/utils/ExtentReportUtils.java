package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtils {
    ExtentSparkReporter spark;
    ExtentReports extent;
    ExtentTest test;

    public ExtentReportUtils(String filename){
        spark = new ExtentSparkReporter(filename);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public void createTest(String testName){
        test = extent.createTest(testName);
    }

    public void addLog(Status status, String comments){
        test.log(status, comments);
    }

    public void closeReports(){
        extent.flush();
    }
}
