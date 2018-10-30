package pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

public class PUBLONSPage extends TestBase {

	public PUBLONSPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginPublonsAccont(String email, String pass) {
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).sendKeys(email);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString())).sendKeys(pass);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS.toString())));
	}

	// ===================================================================================
	/**
	 * Method for Login shared platform application using FB valid login
	 * credentials
	 * 
	 * @param username
	 * @param pwd
	 * @throws InterruptedException
	 * @throws Exception
	 *             , When user is not able to login using FB
	 */
	public void loginWithFBCredentialsWithOutLinking(String username, String pwd) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_NAME);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_NAME, username);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_FB_PASSWORD_TEXT_BOX_NAME, pwd);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_FB_LOGIN_BUTTON_NAME);

	}

	// ===================================================================================
	/**
	 * Method for Login shared platform application using Gmail valid login
	 * credentials
	 * 
	 * @param username
	 * @param pwd
	 * @throws InterruptedException
	 * @throws Exception
	 *             , When user is not able to login using FB
	 */
	public void loginWithGmailCredentialsWithOutLinking(String username, String pwd) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_BUTTON_CSS);

		fluentwaitforElement(ob, By.name(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_EMAIL_TEXT_NAME.toString()), 60);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_EMAIL_TEXT_NAME,
				username);

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_NEXT_BUTTON_ID);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_NEXT_BUTTON_ID);

		fluentwaitforElement(ob, By.name(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_PASSWORD_TEXT_NAME.toString()), 60);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_PASSWORD_TEXT_NAME, pwd);

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_SIGNIN_BUTTON_NAME);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_SIGNIN_BUTTON_NAME);

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_ACCESS_BUTTON_ID);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_ACCESS_BUTTON_ID);

	}

	public void checkBlockedUserErrorMessage() {
		pf.getBrowserWaitsInstance(ob).waitUntilText("Your account has been locked.");
		String errorMessage1 = ob
				.findElement(By.xpath(OnePObjectMap.DRA_SSO_SUSPEND_PAGE_ERROR_MESSAGE_XPATH.toString())).getText();
		String errorMessage2 = ob
				.findElement(By.xpath(OnePObjectMap.PUBLONS_BLOCKED_PAGE_ERROR_MESSAGE_AFTER30MIN_XPATH.toString()))
				.getText();
		Assert.assertEquals(errorMessage1, "Your account has been locked.");
		Assert.assertEquals(errorMessage2, "Please try again in 30 minutes.");

	}

	public void clickRegisterLink() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);
	}

	public void emailTextBox() throws Exception {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS.toString()),
				30);
		ob.findElement(By.name(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS.toString())).click();

		/*
		 * pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(
		 * OnePObjectMap .PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS);
		 * pf.getBrowserActionInstance
		 * (ob).jsClick(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS);
		 */

	}

	public void clickPasswordTextBox() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_LINK_ACCOUNTS_PASSWORD_XPATH);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_LINK_ACCOUNTS_PASSWORD_XPATH);

	}

	/*
	 * public void checkAlreadyUsedMail(String message) throws
	 * InterruptedException {
	 * pf.getBrowserWaitsInstance(ob).waitForElementTobeVisible
	 * (ob,By.xpath(OR.getProperty("signup_email_texbox")),30);
	 * ob.findElement(By
	 * .xpath(OR.getProperty("signup_email_texbox"))).sendKeys(
	 * "trloginid@gmail.com" ); //List<WebElement> email_list =
	 * ob.findElements(By.name(OR.getProperty("signup_email_texbox")));
	 * //logger.info(email_list.size()); //WebElement myE = email_list.get(1);
	 * pf.getBrowserWaitsInstance(ob).waitUntilText(
	 * "Your email address is already registered. Please sign in."); String
	 * error_message =
	 * ob.findElement(By.xpath(OR.getProperty("reg_error_email"))).getText();
	 * String expectederror_message = message +
	 * "Your email address is already registered. Please sign in.";
	 * Assert.assertEquals(error_message, expectederror_message);
	 * 
	 * }
	 */

	public void checkPrepopulatedText(String email) throws Exception {
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("loginpage_email_textbox")), 30);
		JavascriptExecutor js = (JavascriptExecutor) ob;
		String populatText = (String) (js.executeScript("return arguments[0].value",
				ob.findElement(By.xpath(OR.getProperty("loginpage_email_textbox".toString())))));
		logger.info("PopulatText--->" + populatText);

		/*
		 * String textValues = (String)(js.executeScript(
		 * "return angular.element(arguments[0]).scope().vm.email",
		 * ob.findElement
		 * (By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS
		 * .toString())))); System.out.println("PopulatText--->"+textValues);
		 */
		logger.info("email--->" + email);
		Assert.assertEquals(email, populatText);
		// pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_LANDING_PAGE_LOGGIN_BANNER_CSS);
	}

	public void moveToAccountSettingPage() throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.PUBLONS_LOGIN_PAGE_ACCOUNT_PROFILE_IMAGE_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_LOGIN_PAGE_ACCOUNT_PROFILE_IMAGE_CSS.toString())).click();
		waitForElementTobeClickable(ob, By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_BUTTON_IN_PROFILE_PAGE.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_BUTTON_IN_PROFILE_PAGE.toString())).click();
	}

	public void clickOnReruiredTab(String tabvalue) throws Exception {

	}

	ArrayList<String> al = new ArrayList<String>();

	public void windowHandle() {

		Set<String> myset = ob.getWindowHandles();
		Iterator<String> myIT = myset.iterator();
		for (int i = 0; i < myset.size(); i++) {

			al.add(myIT.next());
		}

		ob.switchTo().window(al.get(1));

	}

	public void navigateMainWindow() {

		ob.switchTo().window(al.get(0));
	}

	public void clickTab(String string) {
		List<WebElement> list1 = ob
				.findElements(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_SETTING_PAGE_LIST_OF_LINKS_CSS.toString()));

		for (WebElement we : list1)

		{
			if (we.getText().equals(string)) {
				we.click();
				break;
			}

		}

	}

	public boolean userVerification() throws Exception {
		try {
			// BrowserWaits.waitTime(3);
			ob.get("https://www.guerrillamail.com");
			waitUntilText("Please verify your", "Project Neon Account");
			// BrowserWaits.waitTime(22);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("email_list")), 30);
			List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
			WebElement myE = email_list.get(0);
			JavascriptExecutor executor = (JavascriptExecutor) ob;
			executor.executeScript("arguments[0].click();", myE);
			// BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("email_body")), 30);
			WebElement email_body = ob.findElement(By.xpath(OR.getProperty("email_body")));
			List<WebElement> links = email_body.findElements(By.tagName("a"));

			ob.get(links.get(0).getAttribute("href"));
			waitUntilText("Account sign in & security");

		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Snapshot below: " + test
					.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_user_not_registered")));// screenshot
			closeBrowser();
			return false;
		}
		return true;
	}

	public void checkPrepopulatedText_login(String email) throws Exception {
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("loginpage_email_textbox")), 30);
		JavascriptExecutor js = (JavascriptExecutor) ob;
		String populatText = (String) (js.executeScript("return arguments[0].value",
				ob.findElement(By.xpath(OR.getProperty("loginpage_email_textbox".toString())))));
		logger.info("PopulatText--->" + populatText);
		logger.info("email--->" + email);
		Assert.assertEquals(email, populatText);
	}

	public void switchToWindow(int index) {

		String WindowId = null;
		Set<String> handler = ob.getWindowHandles();
		Iterator<String> It = handler.iterator();

		for (int i = 1; i <= index; i++) {
			WindowId = It.next();
		}
		ob.switchTo().window(WindowId);
	}

	public void checkPrepopulatedText_Register(String Email) throws Exception {
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("regpage_email_textbox")), 30);
		JavascriptExecutor js = (JavascriptExecutor) ob;
		String PopulatText = (String) (js.executeScript("return arguments[0].value",
				ob.findElement(By.xpath(OR.getProperty("regpage_email_textbox".toString())))));
		logger.info("PopulatText--->" + PopulatText);
		logger.info("email--->" + Email);
		Assert.assertEquals(Email, PopulatText);
	}

	public void Reg_email_propopulated() {
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_username_css")))
				.sendKeys("fhyvvi+bxgo5pgubqr8g@sharklasers.com");
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_password_css"))).sendKeys("test1ng");
		ob.findElement(By.xpath(OR.getProperty("Signin_button"))).click();
		waitUntilText("Invalid email/password. Please try again.");
		String email_error = ob.findElement(By.xpath(OR.getProperty("Signin_email_error"))).getText();
		logger.info("Invalid email error --->" + email_error);
		Assert.assertEquals("Invalid email/password. Please try again.", email_error);
		ob.findElement(By.xpath(OR.getProperty("reg_button"))).click();

	}
	public void linkAccount(String steamPassword) throws Exception{
		waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH.toString()), 60);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_PASSWORD_TEXT_XPATH, steamPassword);
		waitForElementTobePresent(ob, By.xpath(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString()), 60);
		//jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH.toString())));
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_ACCOUNT_LINKING_MODEL_SIGNIN_BUTTON_XPATH);
		moveToAccountSettingPage();
		windowHandle();
		clickTab("Connected accounts");
		Thread.sleep(3000);
	}
	public void verifyLinkedAccounts(String emailid, int accountCount,ExtentTest test) throws Exception {
		try {
			waitForAllElementsToBePresent(ob, By.cssSelector("span[class='ng-binding ng-scope']"), 60);
			List<WebElement> list = ob.findElements(By.cssSelector("span[class='ng-binding ng-scope']"));
			Assert.assertEquals(accountCount, list.size(),"Account count is not matching in connected tab.");
			for(WebElement el1:list){
				String str1=el1.getText();
				Assert.assertEquals(str1, emailid);
					}
			ob.close();
			test.log(LogStatus.PASS,"Linked accounts are available in connected page : Facebook and STeAM accounts");
			
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,
					"Linked accounts are not available in connected page : Facebook and STeAM accounts");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
		}
	}

}
