package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test1 {

	public static void main(String[] args) throws Exception{
		
		WebDriver ob=new FirefoxDriver();
		ob.get("https://staging.1p.thomsonreuters.com/ui/demo/index.html#/");
		ob.manage().window().maximize();
		System.out.println(ob.getTitle());
		ob.quit();
		
	}
}
