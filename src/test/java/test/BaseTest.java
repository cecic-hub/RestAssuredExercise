package test;

import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import request.RequestFactory;
import utils.ConfigRead;

import java.io.StringReader;
import java.util.Properties;

public class BaseTest {
    String configFilename;
    Properties configProperties;
    RequestFactory requestFactory;
    String currentDir;

    @BeforeSuite
    public void preSetup() throws Exception {
        currentDir = System.getProperty("user.dir");
        configFilename = currentDir + "/src/test/resources/config/config.properties";
        configProperties = ConfigRead.readConfigProperties(configFilename);
    }

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = configProperties.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(configProperties.getProperty("portNumber"));
        requestFactory = new RequestFactory();
    }

    @AfterClass
    public void cleanUp(){
        RestAssured.reset();
    }

}
