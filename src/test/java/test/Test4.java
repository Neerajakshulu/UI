package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test4 {

	
	public static void main(String[] args) throws Exception{
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\Thomson_Reuters\\drivers\\chromedriver.exe");
		WebDriver ob=new ChromeDriver();
		
		
		
//		WebDriver ob=new FirefoxDriver();
		ob.manage().window().maximize();
		ob.get("https://staging.1p.thomsonreuters.com/ui/demo/index.html#/");
		ob.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ob.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		
		
		ob.findElement(By.xpath("//span[contains(text(),'Help')]")).click();
		Thread.sleep(2000);
		
		if(ob.findElement(By.xpath("//*[@class='modal-content']")).isDisplayed())
			System.out.println("Pass");
		else
			System.out.println("Fail");
		
		ob.findElement(By.xpath("//button[@class='close-btn-bottom']")).click();
		Thread.sleep(2000);
		
		//Verify that HELP pop up disappears
		
		ob.quit();
		
		
		
		
	}
}
