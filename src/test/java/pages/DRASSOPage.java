package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.TestBase;
import util.OnePObjectMap;

public class DRASSOPage extends TestBase {

	public DRASSOPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	

	public void loginDRAApp(String email, String pass) throws Exception {
		//pf.getBrowserWaitsInstance(ob).waitUntilText("PLATFORM-IAM");
		ob.findElement(By.name(OnePObjectMap.DRA_SSO_LOGIN_PAGE_USERNAME_NAME.toString())).sendKeys(email);
		ob.findElement(By.name(OnePObjectMap.DRA_SSO_LOGIN_PAGE_PASSWORD_NAME.toString())).sendKeys(pass);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_SSO_LOGIN_PAGE_LOGIN_BUTTON_CSS);
		
	}
	
}
