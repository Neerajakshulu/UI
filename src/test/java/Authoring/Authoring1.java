package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Authoring1 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	static int time = 15;
	PageFactory pf = new PageFactory();

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('C'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var,
						"Verify that user Is able to comment on any article and validate the comment count increment")
				.assignCategory("Authoring");
		runmodes = TestUtil.getDataSetRunmodes(authoringxls, this.getClass().getSimpleName());
		System.out.println("Run modes-->" + runmodes.length);
	}

	@Test
	public void testLoginTRAccount() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Authoring");
		boolean testRunmode = TestUtil.isTestCaseRunnable(authoringxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + count + "--->");

		// selenium code
		try {
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(System.getProperty("host"));
			// ob.get(CONFIG.getProperty("testSiteName"));
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Error: Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_login_not_done")));// screenshot
			closeBrowser();

		}
	}

	@Test(dependsOnMethods = "testLoginTRAccount", dataProvider = "getTestData")
	public void performAuthoringCommentOperations(String username, String password, String article,
			String completeArticle, String addComments) throws Exception {
		try {
			//waitForTRHomePage();
			loginAs("USERNAME15", "PASSWORD15");
			pf.getAuthoringInstance(ob).searchArticle(article);
			pf.getAuthoringInstance(ob).chooseArticle(completeArticle);
			int count = pf.getAuthoringInstance(ob).getCommentCount();
			pf.getAuthoringInstance(ob).enterArticleComment(addComments);
			pf.getAuthoringInstance(ob).clickAddCommentButton();
			pf.getAuthoringInstance(ob).validateCommentAdd(test, count);
			pf.getAuthoringInstance(ob).validateViewComment(addComments);
			pf.getAuthoringInstance(ob).updateComment("comment updated");
			validateUpdatedComment("comment updated");
			closeBrowser();
		} catch (Throwable e) {
			test.log(LogStatus.FAIL, "Error: Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_login_not_done")));// screenshot
			closeBrowser();
		}
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(authoringxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(authoringxls, "Test Cases"
		 * , TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()),
		 * "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases",
		 * TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(authoringxls, "Test Cases"
		 * , TestUtil.getRowNum(authoringxls,this.getClass().getSimpleName()),
		 * "SKIP");
		 */
		// closeBrowser();
	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(authoringxls, this.getClass().getSimpleName());
	}

	public void validateUpdatedComment(String updatedComments) throws Exception {
		scrollingToElementofAPage();
		String commentText = ob.findElements(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS.toString())).get(0)
				.getText();
		System.out.println("Commentary Text-->" + commentText);
		if (!(commentText.contains(updatedComments) && commentText.contains("EDITED"))) {
			// TestBase.test.log(LogStatus.INFO, "Snapshot below: " +
			// TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered
			// comment not added")));
			status = 2;
			throw new Exception("Updated " + updatedComments + " not present");
		}
	}

}
