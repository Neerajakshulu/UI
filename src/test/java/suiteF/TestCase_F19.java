package suiteF;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class TestCase_F19 extends TestBase {
	static int status = 1;
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent.startTest(var, "Verify that follower of the article is able to start conversation from home page when some one commented on the article he is following.")
				.assignCategory("Suite F");

	}


	@Test
	public void testcaseF19() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try{
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn3 = generateRandomName(8);
			ln3 = generateRandomName(10);
			System.out.println(fn3 + " " + ln3);
			user3 = createNewUser(fn3, ln3);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("brain");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")),30);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			String document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			String document_url = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getAttribute("href");
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			test.log(LogStatus.INFO, " user watching an article");
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("selectWatchListInBucket")), 30);
			ob.findElement(By.xpath(OR.getProperty("selectWatchListInBucket"))).click();
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("closeWatchListBucketDisplay")), 30);
			ob.findElement(By.xpath(OR.getProperty("closeWatchListBucketDisplay"))).click();
			Thread.sleep(2000);
			LoginTR.logOutApp();

			//Login with someother user and comment on the article in watchlist of the above user
			LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			Thread.sleep(2000);
			ob.navigate().to(document_url);
			Thread.sleep(4000);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("My Favourite Game");
			Thread.sleep(5000);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			test.log(LogStatus.INFO, "another user adding the comment for an article");
			Thread.sleep(2000);
			LoginTR.logOutApp();


			//Login with first user and check if notification is present
			LoginTR.enterTRCredentials(user3, CONFIG.getProperty("defaultPassword"));
			LoginTR.clickLogin();
			Thread.sleep(5000);

			String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
			System.out.println(text);
			try {
				Assert.assertTrue(text.contains("New comments") && text.contains("TODAY")
						&& text.contains(document_title) && text.contains(fn1 + " " + ln1) && text.contains("My Favourite Game"));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
				try{
					test.log(LogStatus.PASS, "User is commenting from home page");
					ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("TestCase19_comment");
					Thread.sleep(2000);
					jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
					Thread.sleep(5000);

					String updatedText = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
					System.out.println(updatedText);
					Assert.assertTrue(updatedText.contains("TestCase19_comment"));
					test.log(LogStatus.PASS, "User is able to comment from home page");
				}catch(Throwable t){
					test.log(LogStatus.FAIL, "User is not able to comment from homepage");// extent
					// reports
					test.log(LogStatus.INFO, "Error--->" + t);
					ErrorUtil.addVerificationFailure(t);
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_something_went_wrong_while_commenting")));// screenshot
					closeBrowser();

				}
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
				closeBrowser();

			}
			closeBrowser();
		}catch(Throwable t){
			test.log(LogStatus.FAIL, "Something happened");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_Title selected is not same in search text box")));// screenshot
			closeBrowser();
		}
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");

	}
}
