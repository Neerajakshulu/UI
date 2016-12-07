package pages;

import org.openqa.selenium.WebDriver;

import util.OnePObjectMap;
import base.TestBase;

public class IPApage extends TestBase {

	PageFactory pf;

	public IPApage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginToIPA(String username,
			String password) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_USERNAME_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_USERNAME_CSS, username);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_PASSWORD_CSS, password);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_SIGNIN_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_IPA_SIGNIN_CSS);

	}

}
