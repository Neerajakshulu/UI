package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Google {

	public static void main(String[] args) throws Exception {

		WebDriver ob = new FirefoxDriver();
		ob.manage().window().maximize();
		ob.get("http://www.google.co.in");

		ob.findElement(By.id("lst-ib")).sendKeys("chemistry");
		ob.findElement(By.name("btnG")).click();

		String text = ob.findElement(By.id("lst-ib")).getAttribute("value");

		if (text.equals("chemistry"))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		ob.quit();
	}

}
