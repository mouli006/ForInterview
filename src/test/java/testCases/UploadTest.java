package testCases;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ScreenRecorderUtil;
import Utilities.TakeScreenshot;

public class UploadTest {

	public static void main(String[] args) throws Exception {
		//Start screen recorder
		try
		{
		ScreenRecorderUtil.startRecord("Upload Testing");
		
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.manage().window().maximize();

		driver.get("https://demo.dealsdray.com/");

		driver.findElement(By.xpath("//div/input[@name='username']")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("//div/input[@name='password']")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("//div/button[@type='submit']")).click();

		driver.findElement(By.xpath("//div/span[text()='Order']")).click();

		driver.findElement(By.xpath("//button[@type='button']/span[text()='Orders']")).click();

		driver.findElement(By.xpath("//button[text()='Add Bulk Orders']")).click();
		
		String filePath=System.getProperty("user.dir")+"\\test-data\\demo-data.xlsx";
		
		WebElement fileInput=driver.findElement(By.xpath("//div/div[contains(@class,'MuiInputBase-root')]"));
		
		fileInput.click();
		
		//Using Robot class to Upload a file
		Robot robot = new Robot();
		
		StringSelection filePathSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePathSelection, null);
		
        robot.delay(1000); // Add delay to handle the file dialog opening
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter to confirm the file upload
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Add a delay if necessary to ensure the upload is complete
        Thread.sleep(3000);
        
        driver.findElement(By.xpath("//button[text()='Import']")).click();
        
        driver.findElement(By.xpath("//button[text()='Validate Data']")).click();
        
        //handle alert
        Alert myalert = wait.until(ExpectedConditions.alertIsPresent());
        
        myalert.accept();
        
        //take the screenshot
        TakeScreenshot.takeScreenshotAs(driver);
        
        //validate the output:
        List<WebElement> errors=driver.findElements(By.xpath("//tbody/tr/td/p"));
        
        int duplicateOrder=0;
        
        int notNewOrder=0;
        
        int duplicateIMEINumber=0;
        
        int trackingIdError=0;
        
        for(int i=0; i<errors.size(); i++)
        {
        	String errorName=errors.get(i).getText();
        	
        	if(errorName.equalsIgnoreCase("Not a new order"))
        	{
        		notNewOrder++;
        	}
        	
        	else if(errorName.equalsIgnoreCase("Order Id Is Duplicate"))
        	{
        		duplicateOrder++;
        	}
        	
        	else if(errorName.equalsIgnoreCase("IMEI Number Is Duplicate"))
        	{
        		duplicateIMEINumber++;
        	}
        	
        	else if(errorName.equalsIgnoreCase("Tracking Id Must Be 12 Digits"))
        	{
        		trackingIdError++;
        	}
        	
        	
        }
        
        //Validation
        System.out.println("no.of.duplicateOrders is: "+duplicateOrder);
        
        System.out.println("no.of.Not a New orders is: "+notNewOrder);
        
        System.out.println("no.of.duplicate IMEI number is: "+duplicateOrder);
        
        System.out.println("no.of.tracking ID error is: "+trackingIdError);
        
        driver.quit();
        
        ScreenRecorderUtil.stopRecord();
		}
		
		catch (Exception e)
		{
			System.out.println("Test failed: "+e.getMessage());
		}
        
        
        
        
	}
	
	

}
