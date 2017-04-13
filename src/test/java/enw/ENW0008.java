package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class ENW0008 extends TestBase {

	static int status = 1;

	// Verify that the Reference type","Title",URL in ENDnote from Neon for
	// Posts
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

	}

	@Test
	public void testcaseENW0008() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			List<String> list = Arrays.asList(new String[] { "Reference Type:", "Author:", "Title of Entry:",
					"Abstract:", "Last Update Date:", "Accession Number:", "URL:" });
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host);

			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL0008"),
					LOGIN.getProperty("USERPASSWORD0008"));
			pf.getLoginTRInstance(ob).clickLogin();

			pf.getAuthoringInstance(ob).searchArticle("Post Test 3 From API by Neon User3");

			// pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULT_PAGE_POSTS_CSS.toString())).click();
			pf.getSearchResultsPageInstance(ob).clickOnFirstPostTitle();
			BrowserWaits.waitTime(4);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();

			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedReferenceType", "Blog");
			neonValues.put("expectedURL", "https://dev-stable.1p.thomsonreuters.com");
			neonValues.put("expectedTitle",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_TITLE_XPATH.toString())).getText());
			neonValues.put("expectedAuthor",
					ob.findElement(By.cssSelector(OnePObjectMap.NEON_RECORDVIEW_POSTAUTHOR_CSS.toString())).getText());
			logout();
			ob.navigate().to(host + CONFIG.getProperty("appendENWAppUrl"));
			ob.navigate().refresh();
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL0008"),
					LOGIN.getProperty("USERPASSWORD0008"));
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);

			try {
				String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
						.getText();
				if (text.equalsIgnoreCase("Continue")) {
					ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				test.log(LogStatus.PASS, "User navigate to End note");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				BrowserWaits.waitTime(7);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
				BrowserWaits.waitTime(4);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// BrowserWaits.waitTime(7);
			// pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
			// BrowserWaits.waitTime(4);
			HashMap<String, String> endNoteDetails = new HashMap<String, String>();
			endNoteDetails.put("ReferenceType",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_XPATH.toString())).getText());
			endNoteDetails.put("ReferenceTypeValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_VALUE_XPATH.toString())).getText());

			endNoteDetails.put("Author",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_AUTHOR_XPATH.toString())).getText());
			endNoteDetails.put("AuthorValue",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_AUTHOR_VALUE_CSS.toString())).getText());

			endNoteDetails.put("Title",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_XPATH.toString())).getText());
			endNoteDetails.put("TitleValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_VALUE_XPATH.toString())).getText());

			endNoteDetails.put("Abstract",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ABSTRACT_XPATH.toString())).getText());
			endNoteDetails.put("LastUpdatedNo",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_LASTUPDATEDNO_XPATH.toString())).getText());

			endNoteDetails.put("Accessionnumber",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ACCESSIONNUMBER_XPATH.toString())).getText());
			endNoteDetails.put("URL",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_XPATH.toString())).getText());
			endNoteDetails.put("URLValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_VALUE_XPATH.toString())).getText());

			System.out.println("Verifying label ");
			Collection<String> values = endNoteDetails.values();
			for (String listItem : list) {
				if (!values.contains(listItem)) {
					test.log(LogStatus.FAIL, "label present is incorrect " + listItem);
					// Assert.assertEquals(true, false);
				} else {
					test.log(LogStatus.PASS, "label present is correct " + listItem);
				}
			}

			System.out.println("Verifying Values  ");
			Assert.assertEquals(endNoteDetails.get("ReferenceTypeValue"), neonValues.get("expectedReferenceType"));
			Assert.assertEquals(endNoteDetails.get("TitleValue"), neonValues.get("expectedTitle"));
			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("window.scrollBy(0,250)", "");
			if ((endNoteDetails.get("URLValue").contains(neonValues.get("expectedURL")))) {
				test.log(LogStatus.PASS, "Values are matching \n" + neonValues + " Endnote Values " + endNoteDetails);
			}

			else {

				test.log(LogStatus.FAIL,
						"Values are not matching \n" + neonValues + " Endnote Values " + endNoteDetails);

			}
			NavigatingToENW();
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

	private void NavigatingToENW() {
		try {
			// pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(LOGIN.getProperty("USEREMAIL037"),(LOGIN.getProperty("USERPASSWORD037")));
			BrowserWaits.waitTime(3);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
			BrowserWaits.waitTime(5);
			if (!ob.findElement(By.xpath(OnePObjectMap.ENW_ALLRECORDS_CHECKBOX_XPATH.toString())).isSelected()) {
				ob.findElement(By.xpath(OnePObjectMap.ENW_ALLRECORDS_CHECKBOX_XPATH.toString())).click();
			}
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OnePObjectMap.ENW_ALLRECORDS_DELETE_XPATH.toString())).click();
			HandleAlert();
			BrowserWaits.waitTime(4);
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())));
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OnePObjectMap.ENW_FB_PROFILE_FLYOUT_SIGNOUT_XPATH.toString())).click();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void HandleAlert() {
		Alert alert = ob.switchTo().alert();
		String alertMessage = ob.switchTo().alert().getText();
		System.out.println(alertMessage);
		alert.accept();

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}
