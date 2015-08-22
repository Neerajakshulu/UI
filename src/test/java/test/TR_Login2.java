package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TR_Login2 {

	public static void main(String[] args) throws Exception{
		
		String email="1q6nt5+83hxvkofjc2w0@sharklasers.com";
		String password="Transaction@2";
		
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
//		WebDriver ob=new ChromeDriver();
		
		WebDriver ob=new FirefoxDriver();
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
		if(ob.findElement(By.xpath("//span[contains(text(),'Help')]")).isDisplayed())
			System.out.println("Existing TR user credentials are working fine");
		else
			System.out.println("Existing TR user credentials are not working fine");
		
		//Verify that profile name gets displayed correctly
		String profile_name_xpath="//span[contains(text(),'duster man')]";
		if(ob.findElement(By.xpath(profile_name_xpath)).isDisplayed())
			System.out.println("Correct profile name getting displayed");
		else
			System.out.println("Incorrect profile name getting displayed");
		
		ob.quit();

				
				
	}
}
