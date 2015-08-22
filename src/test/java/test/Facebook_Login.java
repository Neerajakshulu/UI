package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Facebook_Login {

	public static void main(String[] args) throws Exception{
		
		String email="amneetsinghasr@gmail.com";
		String password="Iliveinasr123";
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob=new ChromeDriver();
		
//		WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();
		
		//Navigate to FB login page
		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");
		Thread.sleep(8000);
		ob.findElement(By.xpath("//button[@class='unauth-login-btn fb-btn']")).click();
		Thread.sleep(4000);
		
		
		//Verify that existing FB credentials are working fine
		ob.findElement(By.name("email")).sendKeys(email);
		ob.findElement(By.name("pass")).sendKeys(password);
		ob.findElement(By.id("loginbutton")).click();
		Thread.sleep(15000);
		if(ob.findElement(By.xpath("//span[contains(text(),'Help')]")).isDisplayed())
			System.out.println("Existing FB user credentials are working fine");
		else
			System.out.println("Existing FB user credentials are not working fine");
		
		//Verify that profile name gets displayed correctly
		String profile_name_xpath="//span[contains(text(),'Amneet Singh')]";
		if(ob.findElement(By.xpath(profile_name_xpath)).isDisplayed())
			System.out.println("Correct profile name getting displayed");
		else
			System.out.println("Incorrect profile name getting displayed");
				
		ob.quit();
		
		
	}
}
