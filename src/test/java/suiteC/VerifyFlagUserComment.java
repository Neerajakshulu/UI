package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class VerifyFlagUserComment extends TestBase {

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
		test = extent.startTest(var, "Verify that user is able to flag and unflag the comments")
				.assignCategory("Suite C");

	}

	@Test
	public void testFlagInUserComments() throws Exception {
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
			List<WebElement> itemList;
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
					if (commentsCount != 0) {
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
			boolean isPresent = true;
			WebElement more;
			while (isPresent) {
				try {
					waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")), 40);

					more = ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")));
					Point point = more.getLocation();
					int y = point.getY() + 100;
					String script = "scroll(0," + y + ");";
					((JavascriptExecutor) ob).executeScript(script);
					jsClick(ob,more);
					waitForAjax(ob);
				} catch (Exception e) {
					e.printStackTrace();
					isPresent = false;
				}
			}
			Thread.sleep(5000);
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			System.out.println(commentsList.size());
			String commentText;
			int commentsCount = 0;
			WebElement flagWe;
			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();
				if (!commentText.contains(PROFILE_NAME) && !commentText.contains("Comment deleted")) {
					flagWe = commentsList.get(i)
							.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")));
					if (flagWe.getAttribute("class").contains("flag-inactive")) {
						jsClick(ob,flagWe);
						commentsCount = i;
						break;
					}
				}

			}
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")),
					40);
			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));
			// By.xpath(OR.getProperty("tr_authoring_comments_flag_xpath")),
			// 40);
			Thread.sleep(5000);

			try {
				Thread.sleep(6000);
				// commentsList=ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
				boolean IsFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-active");
				Assert.assertTrue(IsFlagged);
				test.log(LogStatus.PASS, "User comment is flagged");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User comment is not flagged");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot

			}
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));

			jsClick(ob,commentsList.get(commentsCount)
					.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));

			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")),
					40);

			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));

			jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));

			try {
				Thread.sleep(6000);
				boolean IsFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-inactive");
				Assert.assertTrue(IsFlagged);
				test.log(LogStatus.PASS, "User comment is unflagged");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User comment is not unflagged");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot

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
