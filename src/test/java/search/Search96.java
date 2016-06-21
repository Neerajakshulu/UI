package search;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Search96 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);

		String var = xlRead2(returnExcelPath('B'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(
						var,
						"Verify that following fields get displayed correctly for a post in ALL search results page: a)Title b)Creation date and time c)Author d)Author details e)Likes count f)Comments count")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB84() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Search");
		boolean testRunmode = TestUtil.isTestCaseRunnable(searchxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			 ob.navigate().to(host);
			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			// login using TR credentials
			login();
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 180);

			String postToSearch = "Post for Testing P9mW5A";

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postToSearch);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			
			String postTitle = ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_post_title_css")))
					.getText();
			String postAuthor = ob.findElement(By.cssSelector("div[class*='ne-profile-object-name'] a"))
					.getText();
			String postCreationDate = ob.findElement(By.cssSelector("div[class='wui-descriptor wui-descriptor--uppercase']")).getText();
			String profileMetaData = ob.findElement(By.cssSelector("div[class*='wui-descriptor wui-descriptor__profile']")).getText();
			String statsXpath="div[class='wui-card__footer-right ng-scope'] span";
			String postLikeCount = ob
					.findElements(By.cssSelector(statsXpath)).get(4)
					.getText();
			String postLikeLabel = ob
					.findElements(By.cssSelector(statsXpath)).get(5)
					.getText();

			String postCommentCount = ob
					.findElements(By.cssSelector(statsXpath)).get(2)
					.getText();
			String postCommentLabel = ob
					.findElements(By.cssSelector(statsXpath)).get(3)
					.getText();

			boolean isPostTitleAvailable = StringUtils.containsIgnoreCase(postTitle, postToSearch);
			boolean isPostedByAuthor = postAuthor.isEmpty();
			boolean ispostCreationDateAndTimeAvailable = StringUtils.containsIgnoreCase(postCreationDate, "2016");
					//&& (StringUtils.containsIgnoreCase(postCreationDate, "AM") || StringUtils.containsIgnoreCase(
						//	postCreationDate, "PM"));
			boolean ispostAuthorMetadataAvailable = profileMetaData.isEmpty();
			boolean isPostLikeCountAvailable = Integer.parseInt(postLikeCount) >= 0
					&& postLikeLabel.equalsIgnoreCase("Likes");
			boolean isPostCommentCountAvailable = Integer.parseInt(postCommentCount) >= 0
					&& postCommentLabel.equalsIgnoreCase("Comments");

			if (!(isPostTitleAvailable && (!isPostedByAuthor) && ispostCreationDateAndTimeAvailable
					&& (!ispostAuthorMetadataAvailable) && isPostLikeCountAvailable && isPostCommentCountAvailable)) {
				throw new Exception("Post all fiedls are not getting displayed in search results ALL page");
			}

			closeBrowser();

		}

		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_patent_metadata_failed")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		// if (status == 1)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "PASS");
		// else if (status == 2)
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "FAIL");
		// else
		// TestUtil.reportDataSetResult(searchxls, "Test Cases",
		// TestUtil.getRowNum(searchxls, this.getClass().getSimpleName()), "SKIP");

	}

}
