package enwiam;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

//OPQA-2399 = Verify that,the user should not be able to exit the STeAM account linking process 
//through clicking anywhere on the page.

//OPQA-2382= "Verify that,user should go back to ENW login page after clicking the [X] button on top right corner(close symbol) 
//in ""Endnote Online lets you sign in with LinkedIn "" overlay (Yes,I have an account) button"

//OPQA-2374 = "Verify that,user should go back to ENW login page after clicking the [X] button on top right corner(close symbol)
//in ""Endnote Online lets you sign in with Facebook "" overlay (Yes,I have an account) button"

//OPQA-2373 || OPQA-2375 || OPQA-2377 || OPQA-2379 || OPQA-2381 || OPQA-2383 || OPQA-2385 || OPQA-2404 || OPQA-2405
//Sign-in with social with-out a linked steam account and link with non-matching email.


public class ENWIAM0002 extends TestBase {

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 30;
	PageFactory pf = new PageFactory();

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

	}

	@Test
	public void testLogin() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		// Deleting the links for aravind.attur@thomsonreuters.com

		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("fbUserName21"));
			//Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			if (!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				// test.log(LogStatus.FAIL, "Delete accounts api call failed");
				throw new Exception("Delete API Call failed");
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		// Deleting the links for enwyogi3@yahoo.com
		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName21"));
			//Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			if (!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				// test.log(LogStatus.FAIL, "Delete accounts api call failed");
				throw new Exception("Delete API Call failed");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			
			ob.navigate().to(host+CONFIG.getProperty("appendENWAppUrl"));
			pf.getENWReferencePageInstance(ob).loginWithENWFBCredentials(LOGIN.getProperty("fbUserName21"), LOGIN.getProperty("fbPassword21"));
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NO_LETS_CONTINUE_BUTTON_XPATH);

			Dimension dimesions = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.NO_LETS_CONTINUE_BUTTON_XPATH).getSize();
			logger.info("Width : " + dimesions.width);
			logger.info("Height : " + dimesions.height);
			int x = dimesions.width;
			int y = dimesions.height;

			Actions builder = new Actions(ob);
			builder.moveToElement(
					pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NO_LETS_CONTINUE_BUTTON_XPATH), x + 150, y)
					.build().perform();
			builder.click().build().perform();
			test.log(LogStatus.PASS, "Linking modal has been disappered");
			/*new Actions(ob).moveByOffset(200, 200).click().build().perform();
			ob.findElement(By.cssSelector("i[class='fa fa-close']")).click();
			Thread.sleep(3000);
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString())).click();*/

			pf.getENWReferencePageInstance(ob).yesAccount(LOGIN.getProperty("UserName21"), LOGIN.getProperty("Password21"));
			
			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_AGREE_CSS);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_AGREE_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
			} catch (Exception e) {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
			}
			/*try {
				ob.findElement(By.className("button-form btn-common")).click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			try {
				ob.findElement(By.className("btn-common")).click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			pf.getENWReferencePageInstance(ob).clickAccount();

			validateLinkedAccounts(2, "Facebook");

			pf.getENWReferencePageInstance(ob).logout();


		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Unexpected error");// extent
			ErrorUtil.addVerificationFailure(t);
		}


		try {
			/*
			 * openBrowser(); maximizeWindow(); clearCookies();
			 */
			//loginToLn();
			closeBrowser();
			pf.clearAllPageObjects();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Unexpected error");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}


	private void validateLinkedAccounts(int accountCount,
			String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Neon", LOGIN.getProperty("UserName21")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("fbUserName21")));
			test.log(LogStatus.PASS, "The account are matching");
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			System.out.println(accountCount);
			test.log(LogStatus.PASS,
					"Linked accounts are available in accounts page : Neon and " + linkName + " accounts");

		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL,
					"Linked accounts are not available in accounts page : Neon and " + linkName + " accounts");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
		}
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
