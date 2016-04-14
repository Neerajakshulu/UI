package test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchDropdown {

	public static void main(String[] args) throws Exception {

		String email = "amneet.singh@thomsonreuters.com";
		String password = "Transaction@2";
		String search_query = "b";

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob = new ChromeDriver();

		// WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();

		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");

		// login using TR credentials
		ob.findElement(By.xpath("//button[@class='btn webui-btn-primary unauth-login-btn']")).click();
		ob.findElement(By.id("userid")).sendKeys(email);
		ob.findElement(By.id("password")).sendKeys(password);
		ob.findElement(By.id("ajax-submit")).click();

		// Type into the search box and get search results
		ob.findElement(By.xpath("//input[@type='text']")).sendKeys(search_query);

		// Verify that the search drop down gets displayed
		if (ob.findElement(By.xpath("//ul[starts-with(@id,'typeahead')]")).isDisplayed())
			System.out.println("Search drop down getting displayed");
		else
			System.out.println("Search drop down not getting displayed");

		// Verify that 10 options are contained in search drop down
		List<WebElement> options = ob.findElements(By.xpath("//a[@class='ng-scope ng-binding']"));
		if (options.size() == 10)
			System.out.println("10 options getting displayed in search drop down");
		else
			System.out.println("10 options not getting displayed in search drop down");

		ob.quit();
	}
}
