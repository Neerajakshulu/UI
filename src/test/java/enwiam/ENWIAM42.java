package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM42 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(),
				rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseh3() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->"
				+ this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case "
					+ this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		
		try {
			String statuCode = deleteUserAccounts(LOGIN
					.getProperty("sru_fbusername02"));
			logger.info("status code-->"+statuCode);
			if(!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				throw new Exception("Delete API Call failed");
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts ");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);

			pf.getLoginTRInstance(ob).loginWithFBCredentials(
					LOGIN.getProperty("sru_fbusername02"),
					LOGIN.getProperty("sru_fbpwd02"));
			test.log(LogStatus.PASS,
					"user has logged in with social account which has Matching Steam");
			pf.getHFPageInstance(ob).clickOnEndNoteLink();

			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.MATCHING_STEAM_MODALTITLE_CSS
							.toString()), 90);
			
			WebElement text_on_modal = ob.findElement(By
					.cssSelector(OnePObjectMap.MATCHING_STEAM_MODALTITLE_CSS
							.toString()));

			String expected_text_onmodal = "Did you know?";
			String actual_text_onmodal = text_on_modal.getText();

			// verifying that did you know modal is displaying or not.

			try {
				Assert.assertEquals(actual_text_onmodal, expected_text_onmodal);
				test.log(
						LogStatus.PASS,
						"User is able to see 'Did you know? ...' Modal when user has email same as existing steam acount ");
				waitForElementTobeVisible(
						ob,
						By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS
								.toString()), 30);
				
				//verifying user is able to exit the linking modal by clicking close button on the modal
				try {
					ob.findElement(
							By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS
									.toString())).click();

					if (!checkElementPresence("ul_name")) {

						test.log(LogStatus.FAIL,
								"User is to not taken back to the Neon Home page ");// extent
																					// reports
						status = 2;// excel
						
						closeBrowser();

					}

					test.log(LogStatus.PASS,
							"User is to taken back to the Neon Home page when clicked on close button on modal ");

				} catch (Throwable t) {
					t.printStackTrace();
					test.log(LogStatus.FAIL,
							"User is not able to close the linking modal");
					ErrorUtil.addVerificationFailure(t);

				}

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"User is not able to see 'Did you know? ...' Modal");
				ErrorUtil.addVerificationFailure(t);

			}
			BrowserWaits.waitTime(2);
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			
			//verifying user is able to exit the linking modal through clicking anywhere on the page
			try {
				
						
				Dimension dimesions=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH).getSize();
				logger.info("Width : "+dimesions.width);
				logger.info("Height : "+dimesions.height);
				int x=dimesions.width;
				int y=dimesions.height;
				pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH); 
				Actions builder = new Actions(ob);  
				builder.moveToElement(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH), x+150, y).build().perform();
				builder.click().build().perform();
				
				if (!checkElementPresence("ul_name")) {

					test.log(LogStatus.FAIL,
							"User is not able to exit the linking modal ");// extent
																				// reports
					status = 2;// excel
					
					closeBrowser();

				}

				test.log(LogStatus.PASS,
						"User is to taken back to the Neon Home page when clicked outside the modal ");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"User is not able to click outside the linking modal");
				ErrorUtil.addVerificationFailure(t);

			}

			BrowserWaits.waitTime(2);
			logout();
			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
			// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this
									.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution ends--->");

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}