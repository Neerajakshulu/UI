package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Linkedin_Login {

	public static void main(String[] args) throws Exception {

		String email = "amneetsingh100@gmail.com";
		String password = "transaction@2";

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob = new ChromeDriver();

		// WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();

		// Navigate to LI login page
		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");
		ob.findElement(By.xpath("//button[@class='unauth-login-btn li-btn']")).click();

		// Verify that exiusting LI user credentials are working fine
		ob.findElement(By.name("session_key")).sendKeys(email);
		ob.findElement(By.name("session_password")).sendKeys(password);
		ob.findElement(By.name("authorize")).click();
		if (ob.findElement(By.xpath("//span[contains(text(),'Help')]")).isDisplayed())
			System.out.println("Existing LI user credentials are working fine");
		else
			System.out.println("Existing LI user credentials are not working fine");

		// Verify that profile name gets displayed correctly
		String profile_name_xpath = "//span[contains(text(),'amneet singh')]";
		if (ob.findElement(By.xpath(profile_name_xpath)).isDisplayed())
			System.out.println("Correct profile name getting displayed");
		else
			System.out.println("Incorrect profile name getting displayed");

		ob.quit();

	}
}
