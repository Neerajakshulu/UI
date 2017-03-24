package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
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

public class ENW008 extends TestBase {

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
	public void testcaseENW008() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			
			List<String> list = Arrays.asList(new String[] { "Reference Type:", "Inventor:", "Title:", "Assignee:",
					"Abstract:", "Patent Number:","Accession Number:", "Keywords:","Notes:", "URL:" });
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host);

			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL008"),
					LOGIN.getProperty("USERPASSWORD008"));
			pf.getLoginTRInstance(ob).clickLogin();

			pf.getAuthoringInstance(ob).searchArticle("Baseball technologies");
			BrowserWaits.waitTime(2);
			pf.getSearchResultsPageInstance(ob).clickOnPatentsTab();
			BrowserWaits.waitTime(8);
			pf.getAuthoringInstance(ob).chooseArticle();
			BrowserWaits.waitTime(4);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();

		    
			String expectedNotes1 = ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENTS_NOTES1_XPATH.toString()))
				.getText();
			String expectedNotes2 = ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENTS_NOTES2_XPATH.toString()))
				.getText();
		
			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedReferenceType", "Patent");
			neonValues.put("expectedURL", "https://dev-stable.1p.thomsonreuters.com");
			neonValues.put("expectedTitle",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_TITLE_XPATH.toString())).getText());
		   neonValues.put("expectedNotes", "Cited Patents:" + expectedNotes1 + " Cited Articles:" + expectedNotes2);
		  
		   String expectedAuthor1=ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENT_AUTHOR1_XPATH.toString())).getText();
		//   String expectedAuthor2=ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_PATENT_AUTHOR2_XPATH.toString())).getText();
			
			logout();
			BrowserWaits.waitTime(4);
		   	ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
			ob.navigate().refresh();
			pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(LOGIN.getProperty("USEREMAIL008"),(LOGIN.getProperty("USERPASSWORD008")));
			BrowserWaits.waitTime(8);

			try {
				if (ob.findElements(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).size() != 0) {
						ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).click();
					}
				test.log(LogStatus.PASS, "User navigate to End note");
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				BrowserWaits.waitTime(4);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
//			JavascriptExecutor jse = (JavascriptExecutor)ob;
//			jse.executeScript("window.scrollBy(0,250)", "");
			BrowserWaits.waitTime(10);
			HashMap<String, String> endNoteDetails = new HashMap<String, String>();
			endNoteDetails.put("ReferenceType",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_XPATH.toString())).getText());
			endNoteDetails.put("ReferenceTypeValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_REFERENCETYPE_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("URL",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_XPATH.toString())).getText());
			endNoteDetails.put("URLValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_URL_VALUE_XPATH.toString())).getText());
			//endNoteDetails.put("Author",
				//	ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_AUTHOR_XPATH.toString())).getText());
			endNoteDetails.put("Inventor",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_INVENTOR_XPATH.toString())).getText());
			endNoteDetails.put("InventorValue1",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_PATENT_AUTHOR1_VALUE_CSS.toString())).getText());
//			endNoteDetails.put("InventorValue2",
//					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_PATENT_AUTHOR2_VALUE_CSS.toString())).getText());
			endNoteDetails.put("Assignee",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ASSIGNEE_XPATH.toString())).getText());
			endNoteDetails.put("Title",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_XPATH.toString())).getText());
			endNoteDetails.put("TitleValue",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_VALUE_XPATH.toString())).getText());
			endNoteDetails.put("Abstract",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ABSTRACT_XPATH.toString())).getText());
			endNoteDetails.put("PatentNumber",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_PATENTNO_XPATH.toString())).getText());
			endNoteDetails.put("Accessionnumber",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_ACCESSIONNUMBER_XPATH.toString())).getText());
			endNoteDetails.put("Keywords",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_KEYWORDS_XPATH.toString())).getText());
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
				else
				{
					test.log(LogStatus.PASS, "label present is correct " + listItem);
				}
			}
			
		 
			if (!(endNoteDetails.get("ReferenceTypeValue").contains(neonValues.get("expectedReferenceType")))) {
				test.log(LogStatus.FAIL, "ReferenceTypeValue is not matching between Neon and endnote");
				Assert.assertEquals(true, false);
			}
			else
			{
				test.log(LogStatus.PASS, "ReferenceTypeValue is matching between Neon and endnote");
			}
			if (!(endNoteDetails.get("URLValue").contains(neonValues.get("expectedURL")))) {
						test.log(LogStatus.FAIL, "URLValue is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}
			else
			{
				test.log(LogStatus.PASS, "URLValue is matching between Neon and endnote");
			}
			if (!(neonValues.get("expectedTitle").equals(endNoteDetails.get("TitleValue")))) {
						test.log(LogStatus.FAIL, "expectedTitleValue is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}
			else
			{
				test.log(LogStatus.PASS, "expectedTitleValue is matching between Neon and endnote");
			}
			if (!(neonValues.get("expectedNotes").equals(endNoteDetails.get("NotesValue")))) {
						test.log(LogStatus.FAIL, "NotesValue is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}
			else
			{
				test.log(LogStatus.PASS, "NotesValue is matching between Neon and endnote");
			}
			if (!(expectedAuthor1.contains(endNoteDetails.get("InventorValue1")))) {
						test.log(LogStatus.FAIL, "Author1Value is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}
			else
			{
				test.log(LogStatus.PASS, "Author1Value is matching between Neon and endnote");
			}
//			if (!(expectedAuthor2.contains(endNoteDetails.get("InventorValue2")))) {
//				test.log(LogStatus.FAIL, "Author2Value is not matching between Neon and endnote");
//				Assert.assertEquals(true, false);
//			}
//			else
//			{
//				test.log(LogStatus.PASS, "Author2Value is matching between Neon and endnote");
//			}
			BrowserWaits.waitTime(3);
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
	
	public void deleteRecord() {
		  ob.findElement(By.xpath("//input[@title='Return to list']")).click();
	  ob.findElement(By.xpath("//input[@id='idCheckAllRef']")).click();
	 ob.findElement(By.xpath("//input[@id='idDeleteTrash']")).click();
	// ob.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
	 
	 Alert alert = ob.switchTo().alert();
	 System.out.println("Alert Message "+alert.getText());
	 alert.accept();
}
	private void NavigatingToENW() {
		 try {
				//pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(LOGIN.getProperty("USEREMAIL037"),(LOGIN.getProperty("USERPASSWORD037")));
				BrowserWaits.waitTime(3);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				BrowserWaits.waitTime(5);
				if ( !ob.findElement(By.xpath(".//*[@id='idCheckAllRef']")).isSelected() )
				{
					ob.findElement(By.xpath(".//*[@id='idCheckAllRef']")).click();
				}			
				BrowserWaits.waitTime(2);
				ob.findElement(By.xpath(".//*[@id='idDeleteTrash']")).click();
				HandleAlert();
				BrowserWaits.waitTime(4);
				jsClick(ob,ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())));
				BrowserWaits.waitTime(3);
				ob.findElement(By.xpath(OnePObjectMap.ENW_FB_PROFILE_FLYOUT_SIGNOUT_XPATH.toString())).click();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	private void HandleAlert() {
		Alert alert=ob.switchTo().alert();		
		String alertMessage=ob.switchTo().alert().getText();		
       System.out.println(alertMessage);			
       alert.accept();	
		
	}
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}


}
