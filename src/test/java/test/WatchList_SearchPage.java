package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WatchList_SearchPage {

	public static void main(String[] args) throws Exception{
		
		String email="amneet.singh@thomsonreuters.com";
		String password="Transaction@2";
		String search_query="biology";
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob=new ChromeDriver();
		
//		WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();
		
		//Navigate to TR login page
		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");
		Thread.sleep(8000);
		ob.findElement(By.xpath("//button[@class='btn webui-btn-primary unauth-login-btn']")).click();
		Thread.sleep(4000);
		
		
		//Verify that existing user credentials are working fine
		ob.findElement(By.id("userid")).sendKeys(email);
		ob.findElement(By.id("password")).sendKeys(password);
		ob.findElement(By.id("ajax-submit")).click();
		Thread.sleep(15000);
		
		//Search anything
		ob.findElement(By.xpath("//input[@type='text']")).sendKeys(search_query);
		ob.findElement(By.xpath("//button[@class='projectne-search-btn']")).click();
		Thread.sleep(4000);
		
		
		
		
	}
}
