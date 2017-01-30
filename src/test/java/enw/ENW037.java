package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

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

public class ENW037 extends TestBase {

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
	public void testcaseENW037() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			NavigatingToENW();
			ob.get(host);
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL037"),
					LOGIN.getProperty("USERPASSWORD037"));
			pf.getLoginTRInstance(ob).clickLogin();
			pf.getAuthoringInstance(ob).searchArticle("Portable calendaring device having perceptual agent managing calendar entries");
			pf.getSearchResultsPageInstance(ob).clickOnPatentsTab();
			BrowserWaits.waitTime(8);
			pf.getAuthoringInstance(ob).chooseArticle();
			BrowserWaits.waitTime(4);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();
			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedAbstract",
					ob.findElement(By.cssSelector(OnePObjectMap.NEON_RECORDVIEW_PATENT_ABSTRACT_CSS.toString())).getText());
			neonValues.put("expectedAssignee",
			ob.findElement(By.cssSelector(OnePObjectMap.NEON_RECORDVIEW_PATENT_ASSIGNEE_CSS.toString())).getText());
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			BrowserWaits.waitTime(8);
			test.log(LogStatus.PASS, "User navigate to End note");
			try {
				if (ob.findElements(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).size() != 0) {
						ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString())).click();
					}
						pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				BrowserWaits.waitTime(4);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
				BrowserWaits.waitTime(4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HashMap<String, String> endNoteDetails = new HashMap<String, String>();
			endNoteDetails.put("AbstractValue",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_ABSTRACT_VALUE_CSS.toString())).getText());
			endNoteDetails.put("AssigneeValue",
					ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_ASSIGNEE_VALUE_CSS.toString())).getText());
			if (!(endNoteDetails.get("AbstractValue").equals(neonValues.get("expectedAbstract")))) {
				test.log(LogStatus.FAIL, "Record is not exported and the Abstract content is not matching between Neon and endnote");
				Assert.assertEquals(true, false);
			}else{
				test.log(LogStatus.FAIL, "Record is exported and the Abstract content is matching between Neon and endnote");
			}
			if (!(endNoteDetails.get("AssigneeValue").equals(neonValues.get("expectedAssignee")))) {
						test.log(LogStatus.FAIL, "Record is not exported , The Assignee value is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}else{
						test.log(LogStatus.FAIL, "After exporting the record, The Assignee value is matching between Neon and endnote");
					}
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
		 ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
		 try {
			pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin1(LOGIN.getProperty("USEREMAIL037"),(LOGIN.getProperty("USERPASSWORD037")));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	private void HandleAlert() {
		Alert alert=ob.switchTo().alert();		
		String alertMessage=ob.switchTo().alert().getText();		
        System.out.println(alertMessage);			
        alert.accept();	
	}
	public void deleteRecord() {
		  ob.findElement(By.xpath("//input[@title='Return to list']")).click();
	  ob.findElement(By.xpath("//input[@id='idCheckAllRef']")).click();
	 ob.findElement(By.xpath("//input[@id='idDeleteTrash']")).click();
	 ob.findElement(By.xpath("//*[contains(text(),'OK')]")).click();
	 
	 Alert alert = ob.switchTo().alert();
	 System.out.println("Alert Message "+alert.getText());
	 alert.accept();
  }
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
}
}
