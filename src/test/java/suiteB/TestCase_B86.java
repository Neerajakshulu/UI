package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class TestCase_B86 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);

		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that following fields get displayed correctly for an patent in record view page: a)Title b)Inventors c)Assignees d)Publication Date e)Publication Number f)Times Cited count g)Cited patents count h)Cited Articles count i)Comments count j)Abstract k)IPC Codes l)DETAILS link")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB86() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
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
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			// ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			// Searching for patents
			selectSearchTypeFromDropDown("Patents");
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);

			// Navigating to record view page
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForAjax(ob);

			try {
				boolean titlePresent = ob.findElements(By.xpath("//div/h2[@class='record-heading ng-binding']"))
						.size() == 1;
				if (titlePresent) {
					test.log(LogStatus.PASS, "Title is present in patent record view page");
				} else {
					test.log(LogStatus.INFO, "Title is present multiple times in patent record view page");
				}
			} catch (NoSuchElementException e) {
				status = 2;
				test.log(LogStatus.FAIL, "Title is not displayed present in patent record view page");
			}

			// Checking the content types
			List<WebElement> cotentTypeList = ob
					.findElements(By.xpath("//div[@class='full-record-content']/h5[@class='inline']"));
			List<String> expectedList = Arrays
					.asList(new String[] { "Assignee", "Inventor", "Publication date", "Publication number" });
			List<String> actualList = new ArrayList<String>();
			String tagType;
			for (WebElement cotentType : cotentTypeList) {
				tagType = cotentType.getText();
				actualList.add(tagType.substring(0, tagType.length() - 1));
			}

			if (expectedList.equals(actualList)) {
				test.log(LogStatus.PASS,
						"Assignee,Inventor,Publication date,Publication number tags are displayed properly");
			} else {
				test.log(LogStatus.FAIL,
						"Assignee,Inventor,Publication date,Publication number tags are not displayed properly");// extent
																													// report
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_assignee_inventor_publicationDate_publicationNumber_tags_are_not_displayed_properly")));// screenshot
			}

			// Checking the doc info types
			expectedList = Arrays.asList(new String[] { "Times Cited", "Cited Patents", "Cited Articles", "Comments" });
			actualList.clear();
			List<WebElement> docInfoTypeList = ob
					.findElements(By.xpath("//div[@class='doc-info']/h6[@class='inline']"));

			for (WebElement docInfoType : docInfoTypeList) {

				actualList.add(docInfoType.getText());
			}

			if (expectedList.equals(actualList)) {
				test.log(LogStatus.PASS,
						"Times Cited,Cited Patents,Cited Articles,Comments doc info types are displayed properly");
			} else {
				test.log(LogStatus.FAIL,
						"Times Cited,Cited Patents,Cited Articles,Comments doc info types are not displayed properly");// extent
																														// report
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_TimesCited_CitedPatents_CitedArticles_Comments_doc_info_types_are_not_displayed_properly")));// screenshot
			}

			try {
				List<WebElement> abstractTag = ob.findElements(By.xpath("//div/h3[contains(text(),'Abstract')]"));
				List<WebElement> ipcTag = ob.findElements(By.xpath("//div/h3[contains(text(),'IPC')]"));
				List<WebElement> detailsLink = ob.findElements(By.linkText("Details"));
				if (abstractTag.size() != 0) {

					test.log(LogStatus.PASS, "Abstract tag is present in the record view page");
				} else {
					test.log(LogStatus.PASS, "Abstract tag is not displayed in the record view page");
				}

				if (ipcTag.size() != 0) {

					test.log(LogStatus.PASS, "IPC tag is present in the record view page");
				} else {
					test.log(LogStatus.PASS, "IPC tag is not displayed in the record view page");
				}

				if (detailsLink.size() != 0) {

					test.log(LogStatus.PASS, "Details link is present in the record view page");
				} else {
					test.log(LogStatus.PASS, "Deatils link is not displayed in the record view page");
				}
			} catch (NoSuchElementException e) {

				status = 2;
				test.log(LogStatus.FAIL, "Abstract or IPC or Details lisnk is not displayed");// extent
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
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

//		if (status == 1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
//		else if (status == 2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
//					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}
