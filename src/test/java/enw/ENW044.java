package enw;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
public class ENW044 extends TestBase {
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
	public void testcaseENW044() throws Exception {
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
			ob.get(host);
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USEREMAIL044"),
					LOGIN.getProperty("USERPASSWORD044"));
			pf.getLoginTRInstance(ob).clickLogin();
			pf.getAuthoringInstance(ob).searchArticle(" Neon Testing3");
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_results_post_title_css")), 60);
			// Navigating to record view page
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_post_title_css"))).click();
			BrowserWaits.waitTime(3);
			pf.getpostRVPageInstance(ob).clickSendToEndnoteRecordViewPage();
			HashMap<String, String> neonValues = new HashMap<String, String>();
			neonValues.put("expectedName",
			ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_POST_NAME_XPATH.toString())).getText());
			neonValues.put("expectedAbstract",
					ob.findElement(By.xpath(OnePObjectMap.NEON_RECORDVIEW_POST_ABSTRACT_XPATH.toString())).getText());
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			BrowserWaits.waitTime(2);
			test.log(LogStatus.PASS, "User navigate to End note");
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
				String text = ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()))
						.getText();
				if (text.equalsIgnoreCase("Continue")) {
					ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_UNFILEDFOLDER_LINK_XPATH);
				sortReferences();
				BrowserWaits.waitTime(5);
				List<WebElement> list = ob.findElements(By.xpath(".//*[@title='Go to reference']"));
				 for(int i=0;i<list.size();){
					 if(list.get(i).getText().equals(neonValues.get("expectedName")))
						 System.out.println("List items in ENW:"+list.get(i).getText());
					 	System.out.println("List items from Neon:"+neonValues.get("expectedName"));
						 list.get(i).click();
					 break;
				 }
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			BrowserWaits.waitTime(4);
	 		pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RECORD_LINK_XPATH);
			HashMap<String, String> endNoteDetails = new HashMap<String, String>();
						endNoteDetails.put("TitleofEntry",
					ob.findElement(By.xpath(OnePObjectMap.ENW_RECORD_TITLE_ENTRY_XPATH.toString())).getText());
						endNoteDetails.put("Abstract",
								ob.findElement(By.cssSelector(OnePObjectMap.ENW_RECORD_ABSTRACT_VALUE_CSS.toString())).getText());
						BrowserWaits.waitTime(4);		
			if (!(endNoteDetails.get("TitleofEntry").equals(neonValues.get("expectedName")))) {
				test.log(LogStatus.FAIL, "Abstract content is not matching between Neon and endnote");
				Assert.assertEquals(true, false);
			}else{
			test.log(LogStatus.PASS, "Post Name is matching and both are same after exporting the post data");
			}
			if (!(endNoteDetails.get("Abstract").equals(neonValues.get("expectedAbstract")))) {
						test.log(LogStatus.FAIL, "Assignee value is not matching between Neon and endnote");
						Assert.assertEquals(true, false);
					}
			else{
			test.log(LogStatus.PASS, "Abstract content is matching between Neon and endnote and both are same after exporting the data");
					}
		 //deleteRecord();
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
	 private void sortReferences() throws InterruptedException {
			 if (ob.findElements(By.xpath("//div[@class='sortBy']")).size() > 0) {
				Select SortItems = new Select(ob.findElement(By.xpath("//*[@id='sort_By']")));
				SortItems.selectByVisibleText("Year -- newest to oldest");
				} else {
				test.log(LogStatus.FAIL, "Sorting not done .");
			}
		
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
