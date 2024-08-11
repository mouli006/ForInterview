package testCases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Utilities.ScreenRecorderUtil;
import Utilities.TakeScreenshot;

public class ResolutionTest {
	public static void main(String[] args) throws Exception {
		//starting screen record
		ScreenRecorderUtil.startRecord("UI testing");
		
		// Define the base URL to fetch links from
		String baseUrl = "https://www.getcalley.com/page-sitemap.xml";

		// Desktop and Mobile Resolutions
		int[][] desktopResolutions = { { 1920, 1080 }, { 1366, 768 }, { 1536, 864 } };

		int[][] mobileResolutions = { { 360, 640 }, { 414, 896 }, { 375, 667 } };
		
		//to run the two browsers at a same time
		/*
		 * // Open and close Chrome browser WebDriver chromeDriver =
		 * setupChromeDriver(); processBrowser(chromeDriver, baseUrl,
		 * desktopResolutions, mobileResolutions, "Chrome"); chromeDriver.quit();
		 * 
		 * // Open and close Firefox browser WebDriver firefoxDriver =
		 * setupFirefoxDriver(); processBrowser(firefoxDriver, baseUrl,
		 * desktopResolutions, mobileResolutions, "Firefox"); firefoxDriver.quit();
		 */

		boolean testPassed = true;

		// Open and close Chrome browser
		WebDriver chromeDriver = null;
		try 
		{
			chromeDriver = setupChromeDriver();
			processBrowser(chromeDriver, baseUrl, desktopResolutions, mobileResolutions, "Chrome");
		} 
		catch (Exception e) 
		{
			System.out.println("Test failed on Chrome: " + e.getMessage());
			testPassed = false;
		} 
		finally 
		{
			if (chromeDriver != null) 
			{
				chromeDriver.quit();
			}
		}

		// Open and close Firefox browser
		WebDriver firefoxDriver = null;
		try 
		{
			firefoxDriver = setupFirefoxDriver();
			processBrowser(firefoxDriver, baseUrl, desktopResolutions, mobileResolutions, "Firefox");
		} 
		catch (Exception e) 
		{
			System.out.println("Test failed on Firefox: " + e.getMessage());
			testPassed = false;
		} 
		finally 
		{
			if (firefoxDriver != null) 
			{
				firefoxDriver.quit();
			}
		}

		// Output test result
		if (testPassed) {
			System.out.println("Test Passed");
		} else {
			System.out.println("Test Failed");
		}
		
		//Stopping the screen recorder
		ScreenRecorderUtil.stopRecord();
		

	}


	public static WebDriver setupChromeDriver() {
		WebDriver chromeDriver = new ChromeDriver();
		return chromeDriver;
	}

	public static WebDriver setupFirefoxDriver() {
		WebDriver firefoxDriver = new FirefoxDriver();
		return firefoxDriver;
	}

	public static void processBrowser(WebDriver driver, String baseUrl, int[][] desktopResolutions,
			int[][] mobileResolutions, String browserName) throws IOException {
		// Extract URLs from the base URL
		GetFirstFiveURL urlExtractor = new GetFirstFiveURL();
		List<String> urls = urlExtractor.extractFirstFiveUrls(driver, baseUrl);

		System.out.println("**************************");

		// Capture screenshots for each URL and resolution
		for (String url : urls) {
			driver.get(url);
			TakeScreenshot.captureScreenshots(driver, url, desktopResolutions, "desktop", browserName);
			TakeScreenshot.captureScreenshots(driver, url, mobileResolutions, "mobile", browserName);
		}
	}

}


