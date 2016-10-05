package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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
import util.TestUtil;

public class ENW007 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

	}

	@Test
	public void testcaseENW007() throws Exception {

		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			List<String> list = Arrays.asList(new String[] { "Reference Type:", "Author:", "Title:", "Journal:",
					"Volume:", "Issue:", "Abstract:", "Accession Number:", "Notes:", "URL:" });
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host);

			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL007"),
					LOGIN.getProperty("USERPASSWORD007"));
			pf.getLoginTRInstance(ob).clickLogin();

			pf.getAuthoringInstance(ob).searchArticle(CONFIG.getProperty("article"));

			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getAuthoringInstance(ob).chooseArticle();
			BrowserWaits.waitTime(4);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();

			

			String expectedNotes1 = ob.findElement(By.xpath("//span[text()='Times Cited']//preceding::span[1]"))
					.getText();
			String expectedNotes2 = ob.findElement(By.xpath("//span[text()='Cited References']//preceding::span[1]"))
					.getText();
		
			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedReferenceType", "Journal Article");
			neonValues.put("expectedURL", "https://dev-stable.1p.thomsonreuters.com");
			neonValues.put("expectedTitle",
					ob.findElement(By.xpath("//div[@class='ne-publication__header']//h2")).getText());
			neonValues.put("expectedJournal",
					ob.findElement(By.xpath("//div[@class='ne-publication__metadata ng-binding']")).getText());
			neonValues.put("expectedVolume",
					ob.findElement(By.xpath("//div[@ng-show='record.volume != null']/span[2]")).getText());
			neonValues.put("expectedIssue",
					ob.findElement(By.xpath("//div[@ng-show='record.issue != null']/span[2]")).getText());
			neonValues.put("expectedPages",
					ob.findElement(By.xpath("//div[@ng-show='record.pages != null']/span[2]")).getText());
			neonValues.put("expectedNotes", "Times Cited:" + expectedNotes1 + " Cited References:" + expectedNotes2);

			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			BrowserWaits.waitTime(2);
			test.log(LogStatus.PASS, "User navigate to End note");

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
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BrowserWaits.waitTime(4);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
			

			HashMap<String, String> endNoteDetails = new HashMap<String, String>();
			endNoteDetails.put("ReferenceType",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_XPATH.toString())).getText());
			endNoteDetails.put("ReferenceTypeValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("URL",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_XPATH.toString())).getText());
			endNoteDetails.put("URLValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Author",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_AUTHOR_XPATH.toString())).getText());
			endNoteDetails.put("Title",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_XPATH.toString())).getText());
			endNoteDetails.put("TitleValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Journal",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_JOURNAL_XPATH.toString())).getText());
			endNoteDetails.put("JournalValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_JOURNAL_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Volume",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_VOLUME_XPATH.toString())).getText());
			endNoteDetails.put("VolumeValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_VOLUME_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Issue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ISSUE_XPATH.toString())).getText());
			endNoteDetails.put("IssueValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ISSUE_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Abstract",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ABSTRACT_XPATH.toString())).getText());
			endNoteDetails.put("Accessionnumber",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ACCESSIONNUMBER_XPATH.toString())).getText());
			endNoteDetails.put("Notes",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_NOTES_XPATH.toString())).getText());
			endNoteDetails.put("NotesValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_NOTES_VALUE_XPATH.toString())).getText());

			System.out.println("Verifying label ");
			Collection<String> values = endNoteDetails.values();
			for (String listItem : list) {
				if (!values.contains(listItem)) {
					test.log(LogStatus.FAIL, "label present is incorrect " + listItem);
					Assert.assertEquals(true, false);
				}
			}

			System.out.println("Verifying Values  ");
			if (!(endNoteDetails.get("ReferenceTypeValue").contains(neonValues.get("expectedReferenceType"))
					&& endNoteDetails.get("URLValue").contains(neonValues.get("expectedURL"))
					&& neonValues.get("expectedTitle").equals(endNoteDetails.get("TitleValue"))
					&& neonValues.get("expectedJournal").equals(endNoteDetails.get("JournalValue"))
					&& neonValues.get("expectedVolume").equals(endNoteDetails.get("VolumeValue"))
					&& neonValues.get("expectedIssue").equals(endNoteDetails.get("IssueValue"))
					&& neonValues.get("expectedNotes").equals(endNoteDetails.get("NotesValue")))) {
				test.log(LogStatus.FAIL, "Values are not matching \n"+neonValues+" Endnote Values "+endNoteDetails);
				Assert.assertEquals(true, false);
			}

			
			closeBrowser();
			/*
			 * public void deleteRecord() {
			 * ob.findElement(By.xpath("//input[@id='idCheckAllRef']")).click();
			 * ob.findElement(By.xpath("//input[@id='idDeleteTrash']")).click();
			 * 
			 * }
			 */

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

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
		 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
		 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
