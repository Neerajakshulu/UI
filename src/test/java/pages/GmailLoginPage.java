package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.OnePObjectMap;

public class GmailLoginPage extends TestBase {
	
	PageFactory pf;
	
	public GmailLoginPage(WebDriver ob) {
            this.ob=ob;
            pf=new PageFactory();
            
	}

	public void gmailLogin(String username,String password) throws Exception {
		
		ob.get("https://accounts.google.com/ServiceLogin#identifier");
		//ob.findElement(By.xpath(OnePObjectMap.RCC_GMAIL_LOGIN_USERNAME.toString())).clear();
        ob.findElement(By.id(OnePObjectMap.RCC_GMAIL_LOGIN_USERNAME_XPATH.toString())).sendKeys(username);
        ob.findElement(By.xpath(OnePObjectMap.RCC_GMAIL_LOGIN_NEXT_BUTTON_XPATH.toString())).click();
        ob.findElement(By.xpath(OnePObjectMap.RCC_GMAIL_LOGIN_PASSWORD_XPATH.toString())).sendKeys(password);
        ob.findElement(By.xpath(OnePObjectMap.RCC_GMAIL_LOGIN_SUBMIT_XPATH.toString())).sendKeys(Keys.ENTER);	
	}
	
	public void clickonMail()
	{
		ob.findElement(By.xpath(OnePObjectMap.RCC_GMAIL_CLICK_EMAIL_XPATH.toString())).click();
		ob.findElement(By.linkText("View invitation")).click();
    }
	
	public void MailWindowHandle()
	{
		String PARENT_WINDOW = ob.getWindowHandle();
		waitForNumberOfWindowsToEqual(ob, 2);
		Set<String> child_window_handles = ob.getWindowHandles();
		for (String child_window_handle : child_window_handles) {
			if (!child_window_handle.equals(PARENT_WINDOW)) {
				ob.switchTo().window(child_window_handle);
	}
			} }
		
		public void mailProtectclick()
		{
			ob.findElement(By.xpath(OnePObjectMap.RCC_CLICK_ON_GMAIL_SECURITY_LINK_XPATH.toString())).click();
		}
		
	
	
	
}