package Utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot {
	
	public static void takeScreenshotAs(WebDriver driver) throws IOException {
        // Get the current date and time for the folder name
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        
        // Define the directory structure
        String directoryPath = System.getProperty("user.dir") + "\\uploadTestScreenshot\\" + timestamp;
        
        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Capture the screenshot and save it to the directory
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(Paths.get(directoryPath, "screenshot.png").toString()));
    }
	
	public static void captureScreenshots(WebDriver driver, String url, int[][] resolutions, String deviceType,
			String browserName) throws IOException {
		String pageTitle = driver.getTitle();
		for (int[] resolution : resolutions) {
			String resolutionString = resolution[0] + "x" + resolution[1];
			driver.manage().window().setSize(new Dimension(resolution[0], resolution[1]));

			// Capture screenshot
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Prepare the directory
			String directoryPath = "screenshots/" + browserName + "/" + deviceType + "/" + resolutionString + "_"
					+ pageTitle + "/";
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Save screenshot with timestamp
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			File destinationFile = new File(directoryPath + "Screenshot-" + timestamp + ".png");
			ImageIO.write(ImageIO.read(screenshot), "png", destinationFile);

			System.out.println("Screenshot saved at: " + destinationFile.getAbsolutePath());
		}

}

}
