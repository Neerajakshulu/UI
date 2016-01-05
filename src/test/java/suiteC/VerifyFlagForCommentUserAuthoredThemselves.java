package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class VerifyFlagForCommentUserAuthoredThemselves extends TestBase {

	private static final String PROFILE_NAME = "amneet singh";
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var, "Verify that flag button is not displyed for comments a user authored themselves")
				.assignCategory("Suite C");

	}

	@Test
	public void testFlagForCommentUserAuthoredThemselves() throws Exception {
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
			Thread.sleep(8000);
			login();
			Thread.sleep(15000);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 40);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("searchResults_links")), 40);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 40);
			jsClick(ob,ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))));
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("test");
			jsClick(ob,ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			System.out.println(commentsList.size());
			String commentText;
			for (WebElement we : commentsList) {
				commentText = we.getText();
				if (commentText.contains(PROFILE_NAME)) {

					try {
						if (we.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
								.isDisplayed()) {
							test.log(LogStatus.FAIL, "Flag is displayed for comment user authored themselves");
							status = 2;
							test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
									this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot

						}

					} catch (Exception e) {

						test.log(LogStatus.PASS, "Flag is not displayed for comments user authored themselves");
					}
					break;
				}

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
