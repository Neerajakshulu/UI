package customercare;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class Customercare020 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;

	/** 
	 * Verify that If the user is logged-in into the application, The system should prefill the webform with the name,
	 *  contact information, organization, and country information obtained from the user's STeAM account
	 * Method for displaying JIRA ID's for test case in specified path of Extent
	 * Reports
	 * 
	 * @throws Exception
	 *             , When Something unexpected
	 */

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription())
				.assignCategory("customercare");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseDRA73() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");
		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));

			pf.getLoginTRInstance(ob).enterTRCredentials("7ttabm+arteo85hji878@sharklasers.com",
					"@Salma123");
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
			pf.getDraPageInstance(ob).clickOnNotNowButton();
			pf.getDraPageInstance(ob).clickOnProfileImageDRA();
			BrowserWaits.waitTime(5);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_PROFILEDATA_CSS);

			String expectedName = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_PROFILE_FNAME_CSS)
					.getAttribute("value") + " "
					+ pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_PROFILE_LNAME_CSS)
							.getAttribute("value");
			String expectedOrg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_PROFILE_ORG_CSS)
					.getAttribute("value");
			// String expectedcountry =
			// pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_PROFILE_COUNTRY_CSS).getAttribute("value");
			String expectedemail = "7ttabm+arteo85hji878@sharklasers.com";
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.IPA_ACCOUNTSETTINGS_CLOSEBUTTON_CSS);
			BrowserWaits.waitTime(4);
			pf.getDraPageInstance(ob).clickOnProfileImageDRA();
			BrowserWaits.waitTime(5);
			pf.getDraPageInstance(ob).clickOnHelpDRA();

			Set<String> myset = ob.getWindowHandles();
			Iterator<String> myIT = myset.iterator();
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < myset.size(); i++) {
				al.add(myIT.next());
			}
			ob.switchTo().window(al.get(1));
			pf.getDraPageInstance(ob).clickOnSupportLinkHelp();
			BrowserWaits.waitTime(5);
			// pf.getDraPageInstance(ob).validateFeedbackPageDRA(test);
			validateFeedbackDRA();
			String actualName = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_NAME_CSS).getAttribute("value");
			String actualOrg = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_ORG_CSS).getAttribute("value");
			String actualemail = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_EMAIL_CSS).getAttribute("value");
			
			try {
				Assert.assertEquals(expectedName, actualName);
				Assert.assertEquals(expectedOrg, actualOrg);
				Assert.assertEquals(expectedemail, actualemail);
				test.log(LogStatus.PASS, " Support Request form is prepopulating with Name,Org,Email and country");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Support Request form is Not prepopulating with Name,Org,Email and country");
			}
			
			new Actions(ob).moveToElement(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CUSTOMER_CARE_CLEAR_CSS)).build().perform();
			
			String bckcolor1=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CUSTOMER_CARE_CLEAR_CSS).getCssValue("background-color");
			System.out.println(bckcolor1);
            try {
				
				Assert.assertTrue(bckcolor1.contains("rgba(139, 197, 197"));
				
				test.log(LogStatus.PASS, "Color of clear button is changed to blue when mouse over");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Color of clear button is not changed to blue when mouse over");
			}
          pf.getBrowserActionInstance(ob).click(OnePObjectMap.CUSTOMER_CARE_CLEAR_CSS);
			String actualName1 = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_NAME_CSS).getAttribute("value");
			String actualOrg1 = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_ORG_CSS).getAttribute("value");
			String actualemail1 = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.CUSTOMER_CARE_SUPPORTREQUEST_EMAIL_CSS).getAttribute("value");
			
			try {
				
				Assert.assertTrue(actualName1.isEmpty());
				Assert.assertTrue(actualOrg1.isEmpty());
				Assert.assertTrue(actualemail1.isEmpty());
				test.log(LogStatus.PASS, "Name,Org,Email and country fields are cleared");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Name,Org,Email and country fields are not cleared");
			}
			BrowserWaits.waitTime(2);
			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
			// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");

	}

	private void validateFeedbackDRA() throws InterruptedException {
		String expected_URL = "http://dev-stable.1p.thomsonreuters.com/#/customer-care?app=dra";
		String newWindow = switchToNewWindow(ob);
		if (newWindow != null) {
			if (ob.getCurrentUrl().contains(expected_URL)) {
				Assert.assertTrue(ob.getCurrentUrl().contains(expected_URL));
				logger.info("Privacy-statement is opened in the new browser and Content Available");
				test.log(LogStatus.PASS,
						" user is taken to the customer care page in the seperate browser when User clicks on support link");
			}
		} else {
			test.log(LogStatus.FAIL, "New browser is not displayed and content is not matching");
			Assert.assertEquals(true, false);
		}
		BrowserWaits.waitTime(5);
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}
