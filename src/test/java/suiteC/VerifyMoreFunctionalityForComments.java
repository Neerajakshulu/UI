package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class VerifyMoreFunctionalityForComments extends TestBase{
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that default comments displayed for an article is 10 and valildate more functionality")
				.assignCategory("Suite C");

	}

	@Test
	public void testMoreFumctionalityForComments() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));
			//Thread.sleep(8000);
			login();
			//Thread.sleep(15000);
			selectAnArticle();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 80);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			try {
				
				Assert.assertTrue(commentsList.size()==10);
				test.log(LogStatus.PASS, "10 comments are displayed by default");
				
				
			} catch (Throwable t) {
				
				test.log(LogStatus.FAIL, "10 comments are not displayed by default");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Cancel_Flag_validation_for_comments_failed")));// screenshot
			}
			
			
			try {
				
				Assert.assertTrue(ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_more_css"))).isDisplayed());
				test.log(LogStatus.PASS, "More button is displayed for comments more than 10");
				
				
			} catch (Throwable t) {
				
				test.log(LogStatus.FAIL, "More button is displayed for comments more than 10");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "more_button_validation_for_comments_failed")));// screenshot
			}

			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")), 40);

			WebElement more = ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")));
			Point point = more.getLocation();
			int y = point.getY() + 100;
			String script = "scroll(0," + y + ");";
			((JavascriptExecutor) ob).executeScript(script);
			jsClick(ob,more);
			waitForAjax(ob);
			
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
	
			try {
				
				Assert.assertTrue(commentsList.size()>10);
				test.log(LogStatus.PASS, "More functionality is working fine");
				
				
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "More functionality is not working fine");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "more_button_validation_for_comments_failed")));// screenshot
			}

		
			LoginTR.logOutApp();
			closeBrowser();
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
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

	private void selectAnArticle() throws InterruptedException {
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 80);
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
		ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
	//	Thread.sleep(4000);
		waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 80);
		List<WebElement> itemList;
		itemList = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
		

		while (true) {
			itemList = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int commentsCount, itr = 1;
			String strCmntCt;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < itemList.size(); i++) {
				strCmntCt = itemList.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css")))
						.getText();
				commentsCount = Integer.parseInt(strCmntCt);
				if (commentsCount >10) {
					jsClick(ob,itemList.get(i).findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));
							
					isFound = true;
					break;
				}

			}

			if (isFound)
				break;
			itr++;
			((JavascriptExecutor)ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
		}
		//Thread.sleep(5000);
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");

	}

}
