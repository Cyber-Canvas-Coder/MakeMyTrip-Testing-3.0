package base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {
	private static WebDriver driver;
	public static WebDriver getDriver(String browser) {
		if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver();
			FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
	        driver = new FirefoxDriver(options);
		}else if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
	        driver = new ChromeDriver(options);
		}
        
        driver.manage().window().maximize();
		return driver;
	}
}
