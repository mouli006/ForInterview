package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class GetFirstFiveURL {

	public List<String> extractFirstFiveUrls(WebDriver driver, String baseUrl) {
		driver.get(baseUrl);

		// Extract the first five links
		List<WebElement> links = driver.findElements(By.xpath("//tbody//a"));
		List<String> urls = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			WebElement link = links.get(i);
			String linkUrl = link.getAttribute("href");

			// Skip if href is null or empty
			if (linkUrl != null && !linkUrl.isEmpty()) {
				urls.add(linkUrl);

			}
		}

		return urls;
	}
}
