package test;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import request.RequestFactoryForPet;
import utils.ConfigRead;
import utils.ExtentReportUtils;

import java.util.Properties;

public class BaseTest {
    String configFilename;
    Properties configProperties;
    String currentDir;
    RequestFactoryForPet requestFactoryForPet;
    ExtentReportUtils extentReport;
    String htmlReportFilename;

    @BeforeSuite
    public void preSetup() throws Exception {
        currentDir = System.getProperty("user.dir");
        configFilename = currentDir + "/src/test/resources/config/config.properties";
        configProperties = ConfigRead.readConfigProperties(configFilename);
        htmlReportFilename = currentDir + "/src/test/resources/reports/testReport.html";
        extentReport = new ExtentReportUtils(htmlReportFilename);
    }

    @BeforeClass
    public void setup() throws Exception {
        extentReport.createTest("Setup: Update all config");
        RestAssured.baseURI = configProperties.getProperty("baseUrl");
        extentReport.addLog(Status.INFO, "Base URL - " + RestAssured.baseURI);

        String port = configProperties.getProperty("portNumber");
        if(!port.equals("NA")){
            try{
                RestAssured.port = Integer.parseInt(port);
                extentReport.addLog(Status.INFO, "Base port - " + RestAssured.port);
            } catch (Exception e){
                throw new Exception("Invalid port number");
            }
        }
        requestFactoryForPet = new RequestFactoryForPet();
    }

    @AfterMethod
    public void postTestCheck(ITestResult result){
        if(result.isSuccess()){
            extentReport.addLog(Status.PASS, "All test step passed");
        } else if (result.isNotRunning()) {
            extentReport.addLog(Status.SKIP, "One or more test step skipped");
        } else {
            extentReport.addLog(Status.FAIL, "One or more test step failed");
        }
    }

    @AfterClass
    public void cleanUp(){
        RestAssured.reset();
        extentReport.closeReports();
    }
}
