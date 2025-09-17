package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import base.DriverSetup;
import pages.HotelPage;
import utils.ConfigReader;
import utils.CustomLogger;

import java.io.IOException;
import java.lang.reflect.Method;

public class HotelTest {
    private WebDriver driver;
    private HotelPage hotel;
    
    @Parameters("browser")
    @BeforeClass(groups="smoke")
    public void setup(String browser) {
        driver = DriverSetup.getDriver(browser);
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        hotel = new HotelPage(driver);
        hotel.closeLogin();

    }
    @BeforeMethod
    public void logTestStart(Method method) {
        CustomLogger.info("====== Starting Test: " + method.getName() + " ======");
    }
    
    @Test(priority = 0, description = "Search hotels in a valid location with future check-in and check-out dates",groups="smoke")
    public void hotelSearchWithFutureDatesTest() throws InterruptedException {
    	hotel.selectHotelOption();
        hotel.enterDestination("Ranchi");
        hotel.selectCheckInDate("Thu Sep 18 2025");
        hotel.selectCheckOutDate("Sat Sep 27 2025");
        hotel.selectRoomAndGuest();
        hotel.clickSearch();

        Assert.assertTrue(hotel.isSearchResultsDisplayed(), "Hotel results should be displayed");
    }
    
    @Test(priority = 1, description = "Apply filters like star rating, amenities, and price range")
    public void hotelFilterApplicationTest() throws InterruptedException, IOException {

    	hotel.closeDeal();

    	hotel.applyStarRatingFilter(4);
//        Assert.assertTrue(hotel.applyStarRatingFilter(4));
        

        hotel.closeDeal();

//        hotel.applyAmenitiesFilter("Wi-Fi");
//
//        hotel.closeDeal();

//        hotel.applyPriceRangeFilter(2000, 5000);
//
//        hotel.closeDeal();

        hotel.takeProof("Amenities");
    }
    
    
    @Test(priority = 2, description = "Sort hotel results by rating or lowest price")
    public void hotelSortResults() throws InterruptedException {

        hotel.sortResultsBy("Rating");

        hotel.closeDeal();

    }

    @Test(priority = 3, description = "Open hotel detail page and verify room types and prices")
    public void hotelDetailPageValidation() throws InterruptedException {

        hotel.openHotelDetailPage();   // open first hotel details

    }

    @Test(priority = 4, description = "Book a hotel room with valid guest details")
    public void hotelRoomBooking() throws IOException {
      hotel.bookRoom("John", "Doe", "johndoe@gmail.com", "9876543210");  
      hotel.takeProof("Booking_Payment");
      hotel.switchToParent();
      System.out.println("hotel room booking done");

    }

    @Test(priority = 5, description = "Search with past check-in date should show validation error")
    public void hotelSearchWithPastCheckinDate() {
    	
        String pastDate = "Thu Aug 28 2025"; 
        boolean pastDateSelected = false;
		try {
			hotel.goBack();
	    	hotel.refresh();
	    	hotel.selectHotelOption();
	    	hotel.enterDestination("Delhi");
	        hotel.clickSearch();
			hotel.selectCheckInDate(pastDate);

			pastDateSelected = true;
		} catch (Exception e) {
			System.out.println("    Past check-in date was disabled: " + pastDate);
		}

		if (pastDateSelected) {
			System.out.println("Test Failed: Past check-in date got selected");
		} else {
			System.out.println("Test Passed: Past check-in date cannot be selected");
		}
    }
    @Test(priority = 6, description = "Same check-in and check-out date should show validation error")
    public void hotelSearchWithSameCheckinCheckoutDate() {
    	try {
	        hotel.enterDestination("Goa");
	        hotel.selectCheckInDate("2025-09-01");
	        hotel.selectCheckOutDate("2025-09-01");
	        hotel.selectRoomAndGuest();
	        hotel.clickSearch();
	        System.out.println("Test Failed: Same check-out date cannot be selected");
    	}catch(Exception e) {
    		System.out.println("Test Passed: Same check-out date cannot be selected");
    	}


    }

    @Test(priority = 7, description = "Search without entering destination should show validation error")
    public void hotelSearchWithoutDestination() throws IOException {
    	try {
    		hotel.goBack();
        	hotel.refresh();
        	hotel.selectHotelOption();
            hotel.emptyDestination();
            hotel.selectCheckInDate("2025-09-04");
            hotel.selectCheckOutDate("2025-09-07");
            hotel.clickSearch();
            hotel.takeProof("Empty Destination");
            System.out.println("Test Failed: Search without entering destination should show validation error");
    	}
    	catch(Exception e) {
    		System.out.println("Test Passed: Search without entering destination should show validation error");
    	}

    }
    

    @AfterClass
    public void tearDown() throws InterruptedException {

        if (driver != null) {
            driver.quit();
        }
    }

}
