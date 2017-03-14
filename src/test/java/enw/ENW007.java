package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
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

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
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

			pf.getAuthoringInstance(ob).searchArticle("Ligand restricted conformational shuttle: A King-Altman kinetics model for nitric oxide synthase reductase catalysis and control");

			pf.getSearchResultsPageInstance(ob).clickOnArticleTab();
			pf.getAuthoringInstance(ob).chooseArticle();
			BrowserWaits.waitTime(6);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();

			

			String expectedNotes1 = ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_NOTES1_XPATH.toString()))
					.getText();
			String expectedNotes2 = ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_NOTES2_XPATH.toString()))
					.getText();
		
			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedReferenceType", "Journal Article");
			neonValues.put("expectedURL", "https://dev-stable.1p.thomsonreuters.com");
			neonValues.put("expectedTitle",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_TITLE_XPATH.toString())).getText());
			neonValues.put("expectedJournal",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_JOURNAL_XPATH.toString())).getText());
			neonValues.put("expectedVolume",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_VOLUME_XPATH.toString())).getText());
			neonValues.put("expectedIssue",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_ISSUE_XPATH.toString())).getText());
			neonValues.put("expectedPages",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PAGES_XPATH.toString())).getText());
			neonValues.put("expectedNotes", "Times Cited:" + expectedNotes1 + " Cited References:" + expectedNotes2);
			neonValues.put("expectedAuthor1",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENT_AUTHOR1_XPATH.toString())).getText());
			neonValues.put("expectedAuthor2",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENT_AUTHOR2_XPATH.toString())).getText());
		//	neonValues.put("expectedAuthor3",
			//		ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENT_AUTHOR3_XPATH.toString())).getText());

			/*pf.getHFPageInstance(ob).clickOnEndNoteLink();
			BrowserWaits.waitTime(2);
			test.log(LogStatus.PASS, "User navigate to End note");
			
			switchToNewWindow(ob);*/
			logout();
			
			ob.navigate().to(host+CONFIG.getProperty("appendENWAppUrl"));
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL007"),
					LOGIN.getProperty("USERPASSWORD007"));
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
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BrowserWaits.waitTime(6);
			//pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_RECORD_LINK_XPATH);
			//pf.getBrowserWaitsInstance(ob).waitForAllElementsToBePresent(ob, By.xpath(OnePObjectMap.ENW_RECORD_LINK_XPATH.toString()),30);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
			
			try {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_SHOWALLFILEDS_LINK_XPATH);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
			endNoteDetails.put("AuthorValue1",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_PATENT_AUTHOR1_VALUE_CSS.toString())).getText());
			endNoteDetails.put("AuthorValue2",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_PATENT_AUTHOR2_VALUE_CSS.toString())).getText());
			//endNoteDetails.put("AuthorValue3",
				//	ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_PATENT_AUTHOR3_VALUE_CSS.toString())).getText());
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
					//Assert.assertEquals(true, false);
				}
				else
				{
					test.log(LogStatus.PASS, "label present is correct " + listItem);
				}
					
			}
			

			System.out.println("Verifying Values  ");
			if (!(endNoteDetails.get("ReferenceTypeValue").contains(neonValues.get("expectedReferenceType"))
					&& endNoteDetails.get("URLValue").contains(neonValues.get("expectedURL"))
					&& neonValues.get("expectedTitle").equals(endNoteDetails.get("TitleValue"))
					&& neonValues.get("expectedJournal").equals(endNoteDetails.get("JournalValue"))
					&& neonValues.get("expectedVolume").equals(endNoteDetails.get("VolumeValue"))
					&& neonValues.get("expectedIssue").equals(endNoteDetails.get("IssueValue"))
					&& neonValues.get("expectedNotes").equals(endNoteDetails.get("NotesValue"))
					&& neonValues.get("expectedAuthor1").contains(endNoteDetails.get("AuthorValue1"))
					&& neonValues.get("expectedAuthor2").contains(endNoteDetails.get("AuthorValue2")))) {
				test.log(LogStatus.FAIL, "Values are not matching \n"+neonValues+" Endnote Values "+endNoteDetails);
				//Assert.assertEquals(true, false);
			}
			else
			{
				test.log(LogStatus.PASS, "Values are matching \n"+neonValues+" Endnote Values "+endNoteDetails);
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

	}

}
