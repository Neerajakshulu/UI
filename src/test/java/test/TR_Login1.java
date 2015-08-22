

package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TR_Login1 {

	public static void main(String[] args) throws Exception{
		String password="Transaction@2";
		String first_name="duster";
		String last_name="man";
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob=new ChromeDriver();
		
//		WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();
		
		//Open Guerilla Mail and get email id
		ob.get("https://www.guerrillamail.com");
		String email=ob.findElement(By.id("email-widget")).getText();
		
		//Navigate to TR login page
		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");
		Thread.sleep(8000);
		ob.findElement(By.xpath("//button[@class='btn webui-btn-primary unauth-login-btn']")).click();
		Thread.sleep(4000);
		
		//Create new TR account
		ob.findElement(By.linkText("Register now")).click();
		Thread.sleep(2000);
		ob.findElement(By.id("uid")).sendKeys(email);
		ob.findElement(By.id("givenname")).sendKeys(first_name);
		ob.findElement(By.id("sn")).sendKeys(last_name);
		ob.findElement(By.id("userpassword")).sendKeys(password);
		ob.findElement(By.id("confirmpassword")).sendKeys(password);
		ob.findElement(By.id("termsAndConditions")).click();
		ob.findElement(By.xpath("//button[contains(text(),'Register')]")).click();
		Thread.sleep(4000);
		
		//Verify that confirmation email is sent
		String text=ob.findElement(By.id("profileMessage")).getText();
		if(text.contains(email))
			System.out.println("Account activation email sent successfully");
		else
			System.out.println("Account activation email not sent");
		
		
		//Verify that account activation email has been received
		ob.get("https://www.guerrillamail.com");
		List<WebElement> email_list=ob.findElements(By.xpath("//tr[starts-with(@id,'mr_')]"));
		email_list.get(0).click();
		Thread.sleep(2000);
		String email_subject=ob.findElement(By.xpath("//*[@class='email_subject']")).getText();
		if(email_subject.contains("Please confirm your email Address"))
			System.out.println("Account activation email received successfully");
		else
			System.out.println("Account activation email not received");
		
		//Activate the account
		WebElement email_body=ob.findElement(By.xpath("//*[@class='email_body']"));
		List<WebElement> links=email_body.findElements(By.tagName("a"));
		links.get(0).click();
		Thread.sleep(4000);
		
		//Switch to 2nd window
		Set<String> myset=ob.getWindowHandles();
		Iterator<String> myIT=myset.iterator();
		ArrayList<String> al=new ArrayList<String>();
		for(int i=0;i<myset.size();i++){
			
			al.add(myIT.next());
		}
		ob.switchTo().window(al.get(1));
		
		
		//Verify that newly registered user credentials are working fine
		ob.findElement(By.id("userid")).sendKeys(email);
		ob.findElement(By.id("password")).sendKeys(password);
		ob.findElement(By.id("ajax-submit")).click();
		Thread.sleep(15000);
		if(ob.findElement(By.xpath("//span[contains(text(),'Help')]")).isDisplayed())
			System.out.println("Newly registered user credentials are working fine");
		else
			System.out.println("Newly registered user credentials are not working fine");
		
		//Verify that profile name gets displayed correctly
		String profile_name_xpath="//span[contains(text(),'"+first_name+" "+last_name+"')]";
		String profile_name=ob.findElement(By.xpath(profile_name_xpath)).getText();
		String expected_profile_name=first_name+" "+last_name;
		if(profile_name.equalsIgnoreCase(expected_profile_name))
			System.out.println("Correct profile name getting displayed");
		else
			System.out.println("Incorrect profile name getting displayed");
		System.out.println(expected_profile_name);
		System.out.println(profile_name);
		
		ob.quit();
		
	}
	
}