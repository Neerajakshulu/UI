package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.TestBase;
import util.OnePObjectMap;

public class IAMPage extends TestBase{

	public IAMPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void sendEamilToTextBox(String email) throws Exception {
		waitForElementTobePresent(ob, By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_SEND_EMAIL_CSS.toString()),
				60);
		ob.findElement(By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_SEND_EMAIL_CSS.toString())).sendKeys(email);
	}

	
}
