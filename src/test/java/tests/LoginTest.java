package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.LoginPage;
import utils.ConfigReader;
import utils.CustomLogger;

public class LoginTest {
    LoginPage login;
    WebDriver driver;
    @Parameters("browser")
    @BeforeClass(groups="smoke")
    public void openBrowser(String browser) {
        driver = DriverSetup.getDriver(browser);
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        login = new LoginPage(driver);  
    }
    
    @BeforeMethod
    public void logTestStart(Method method) {
        CustomLogger.info("====== Starting Test: " + method.getName() + " ======");
    }
    
    @Test(priority = 1,groups="smoke")
    public void EnterLoginCredential() throws InterruptedException {
    	login.enterMobile("9838297040");

    	login.clickContinue();
    	login.isContinueButtonDisplayed();

    }
    
    
    @Test(priority = 2,groups="smoke")
    public void testClosePopup() {
        login.closeLogin(); 
        System.out.println("The login popup closed");
    }

    @AfterClass(groups="smoke")
    public void destroyBrowser() {
        login.closeBrowser();
    }
}
