package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Test2 {

	public static void main(String[] args) throws Exception{
		
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\Thomson_Reuters\\drivers\\chromedriver.exe");
//		System.setProperty("webdriver.ie.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\Thomson_Reuters\\drivers\\IEDriverServer.exe");
//		WebDriver ob=new InternetExplorerDriver();
		
		
		WebDriver ob=new FirefoxDriver();
		ob.manage().window().maximize();
		ob.manage().deleteAllCookies();
		ob.get("https://staging.1p.thomsonreuters.com/ui/demo/index.html#/");
		ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		ob.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
		ob.findElement(By.xpath("//a[@class='linkedin']")).click();
		
		ob.findElement(By.name("session_key")).sendKeys("amneetsingh100@gmail.com");
		ob.findElement(By.name("session_password")).sendKeys("transaction@2");
		ob.findElement(By.name("authorize")).click();
		
		String name=ob.findElement(By.xpath("//img[@class='profile-image']")).getAttribute("alt");
		System.out.println(name);
		
		if(name.equals("amneet singh"))
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		ob.quit();
	}
}
