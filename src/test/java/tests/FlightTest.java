package tests;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.FlightPage;
import utils.ConfigReader;
import utils.CustomLogger;

public class FlightTest {
	
	FlightPage flight;
	WebDriver driver;
	@Parameters("browser")
	@BeforeClass(groups="smoke")
    public void openBrowser(String browser) {
        driver = DriverSetup.getDriver(browser);
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        flight = new FlightPage(driver);  
        flight.closeLogin();
    }
	 @BeforeMethod
	    public void logTestStart(Method method) {
	        CustomLogger.info("====== Starting Test: " + method.getName() + " ======");
	    }
	    
	@Test(priority = 1)
	public void ads() {
		flight.closeAds();
		System.out.println("the ads closed");
	}
	
	@Test(priority=2,groups="smoke")
	public void OneWayTrip() throws InterruptedException {
		flight.selectFromLocation("Kolkata");
		flight.selectToLocation("Delhi");
		flight.selectDepartureDate("Wed Sep 17 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	@Test(priority = 3)
	public void RoundTrip() throws InterruptedException {
		flight.clickCheckBox();
		flight.selectFromLocation("delhi");
		flight.selectToLocation("Kolkata");
		flight.selectDepartureDate("Wed Sep 17 2025");
		flight.selectReturnDate("Sat Sep 27 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	
	@AfterClass(groups="smoke")
    public void tearDown() throws InterruptedException {

        if (driver != null) {
            driver.quit();
        }
    }
}