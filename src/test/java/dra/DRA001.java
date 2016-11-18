package dra;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class DRA001 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;

	/**
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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("DRA");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseh1() throws Exception {
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

			// Verify that DRA Landing page, displays application branding and
			// logo
			try {

				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_BRANDING_NAME_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LOGO_CSS);
				WebElement brand_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_BRANDING_NAME_CSS);
				WebElement dra_element = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_LOGO_CSS);

				String branding_name = brand_element.getText();
				String dra_logo = dra_element.getText();

				if (brand_element.isDisplayed() && dra_element.isDisplayed()) {
					Assert.assertEquals(dra_logo, "Drug Research Advisor");
					Assert.assertEquals(branding_name, "TARGET DRUGGABILITY");
					test.log(LogStatus.PASS, "DRA Landing page displays branding and marketing copy");
				}

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "DRA Landing page doesn't displays branding and marketing copy");
				ErrorUtil.addVerificationFailure(t);
			}

			// Verifying that DRA Landing page, displays privacy statement and
			// terms of use links
			try {

				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsDisplayed(OnePObjectMap.DRA_LANDINGPAGE_PRIVACY_LINK_CSS);
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsDisplayed(OnePObjectMap.DRA_LANDINGPAGE_TERMS_LINK_CSS);

				WebElement tl_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_LANDINGPAGE_TERMS_LINK_CSS);
				WebElement pl_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_LANDINGPAGE_PRIVACY_LINK_CSS);

				if (tl_element.isDisplayed() && pl_element.isDisplayed()) {
					test.log(LogStatus.PASS, "DRA Landing page displays terms of use and privacy links");
				}

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "DRA Landing page doesn't displays terms of use and privacy links");
				ErrorUtil.addVerificationFailure(t);
			}

			// verifying that DRA Landing page, displays the message and email
			// id
			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LANDINGPAGE_HELP_MESSAGE_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LANDINGPAGE_SUPPORT_MAILID_CSS);
				WebElement helpmsg = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_LANDINGPAGE_HELP_MESSAGE_CSS);
				WebElement supportmailid = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_LANDINGPAGE_SUPPORT_MAILID_CSS);
				String actual_help_text = helpmsg.getText();
				String actual_mail_id=supportmailid.getText();
				
				if (actual_help_text.contains("Need help signing in? Please contact") && actual_mail_id.contains("DRA_support@thomsonreuters.com")){
					test.log(LogStatus.PASS, "DRA Landing page  displays the message and email");
				}
				
				
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "DRA Landing page displays the incorrect message and email");
				ErrorUtil.addVerificationFailure(t);
			}
		

			// Verifying DRA marketing copy are displayed.
			try {
				boolean find_icon = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.DRA_LANDINGPAGE_EXPLORE_ICON_CSS.toString()));
				Assert.assertEquals(find_icon, true);
				test.log(LogStatus.PASS, "Explore icon is displayed on DRA landing page");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Explore icon is not displayed on DRA landing page");
				ErrorUtil.addVerificationFailure(t);
			}

			try {
				boolean organize_icon = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.DRA_LANDINGPAGE_IDENTIFY_ICON_CSS.toString()));
				Assert.assertEquals(organize_icon, true);
				test.log(LogStatus.PASS, "Identify icon is displayed on DRA landing page");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Identify icon is not displayed on DRA landing page");
				ErrorUtil.addVerificationFailure(t);
			}

			try {
				boolean create_icon = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.DRA_LANDINGPAGE_VALIDATE_ICON_CSS.toString()));
				Assert.assertEquals(create_icon, true);
				test.log(LogStatus.PASS, "Validate icon is displayed on DRA landing page");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Validate icon is not displayed on DRA landing page");
				ErrorUtil.addVerificationFailure(t);
			}

			try {
				boolean connect_icon = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.DRA_LANDINGPAGE_RANK_ICON_CSS.toString()));
				Assert.assertEquals(connect_icon, true);
				test.log(LogStatus.PASS, "Rank icon is displayed on DRA landing page");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Rank icon is not displayed on DRA landing page");
				ErrorUtil.addVerificationFailure(t);
			}
			
			// Verifying the Learn more link is displaying
			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LANDINGPAGE_LEARNMORE_LINK_CSS);
				WebElement lm_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.DRA_LANDINGPAGE_LEARNMORE_LINK_CSS);
				if(lm_element.isDisplayed())
				{
					lm_element.click();
					BrowserWaits.waitTime(4);
					
					Set<String> myset=ob.getWindowHandles();
					Iterator<String> myIT=myset.iterator();
					ArrayList<String> al=new ArrayList<String>();
					for(int i=0; i< myset.size() ;i++)
					{
						al.add(myIT.next());
					}
					
					ob.switchTo().window(al.get(1));
					String actual_URL = ob.getCurrentUrl();
					String expected_URL = "http://ip-science.interest.thomsonreuters.com/TargetDruggabilityEAP";
					Assert.assertTrue(actual_URL.contains(expected_URL));
					test.log(LogStatus.PASS,
							"user is taken to the TargetDruggability page");
				}
				

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to click and not taken to the TargetDruggability page");
				ErrorUtil.addVerificationFailure(t);
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

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}
