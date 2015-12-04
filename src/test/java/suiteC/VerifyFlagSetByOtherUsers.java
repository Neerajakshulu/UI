package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class VerifyFlagSetByOtherUsers extends TestBase {
	private static final String PASSWORD = "Welcome123";
	private static final String USER_NAME = "kavya.revanna@thomsonreuters.com";
	private static final String PROFILE_NAME = "amneet singh";
	private static final String PASSWORD1 = "Welcome01";
	private static final String USER_NAME1 = "chinna.putha@thomsonreuters.com";

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() {

		test = extent.startTest(this.getClass().getSimpleName(), "Verification of flag for comment set by other users")
				.assignCategory("Suite C");

	}

	@Test
	public void testFlagSetByOtherUsers() throws Exception {
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
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 40);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 40);
			String articleTitle = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(2000);
			String comment = "testFlag";
			Authoring.enterArticleComment(comment);
			Authoring.clickAddCommentButton();
//			ob.navigate().refresh();
//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 40);
//			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).clear();
//			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))));
//			Thread.sleep(4000);
//			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys(comment);
			//sjsClick(ob,ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(6000);
			LoginTR.logOutApp();
			ob.navigate().to(host);
			loginAsOther(USER_NAME, PASSWORD);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 60);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(articleTitle);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);

			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			System.out.println(commentsList.size());
			String commentText;
			for (WebElement we : commentsList) {
				commentText = we.getText();
				if (commentText.contains(PROFILE_NAME) && commentText.contains(comment)) {
					jsClick(ob,we.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));
					waitForElementTobeVisible(ob,
							By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);
					ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css")))
							.click();
					ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css")))
							.click();
					break;
				}
			}

			LoginTR.logOutApp();
			ob.navigate().to(host);
			loginAsOther(USER_NAME1, PASSWORD1);

			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 80);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(articleTitle);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 60);

			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			boolean flag=false;
			for (WebElement we : commentsList) {
				commentText = we.getText();
				if (commentText.contains(PROFILE_NAME) && commentText.contains(comment)) {

					try {

						if (we.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
								.isDisplayed()) {
							test.log(LogStatus.PASS, "Flag set by other user is not visible to the current user");
							flag=true;
						} else {
							throw new Exception("Flag Validation Failed");
						}

					} catch (Throwable t) {
						test.log(LogStatus.FAIL, "Flag set by other user is visible to the current user");
						test.log(LogStatus.INFO, "Error--->" + t);
						ErrorUtil.addVerificationFailure(t);
						status = 2;
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
								this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot

					}
					if(!flag){
						test.log(LogStatus.FAIL, "Cooment is available");
						test.log(LogStatus.INFO, "Error--->");
						status = 2;
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
								this.getClass().getSimpleName() + "Flag_validation_for_comments_failed")));// screenshot
					}
					break;
				}
			}
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

		LoginTR.logOutApp();
		closeBrowser();
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

	private void loginAsOther(String username, String pwd) throws Exception {
		ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
		Thread.sleep(4000);
		waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")), 30);
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(username);
		ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(pwd);
		ob.findElement(By.id(OR.getProperty("login_button"))).click();

	}

}
