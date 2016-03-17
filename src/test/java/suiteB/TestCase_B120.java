package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.annotation.ThreadSafe;
import org.openqa.selenium.By;
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

public class TestCase_B120 extends TestBase {
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
		test = extent
				.startTest(var,
						"Verify that following fields get displayed correctly for an article in ALL search results page:a)Titleb)Authorsc)Publication named)Publication date)Times cited countf)Comments count")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB110() throws Exception {

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
//			ob.navigate().to(host);
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			String search_term="Fostering synergy between cell biology and systems biology";
		
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_term);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			
//			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 30);
			Thread.sleep(15000);
			String title=ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
//			System.out.println(search_term);
//			System.out.println(title);
			
			if(!compareStrings(search_term,title)){
				
				test.log(LogStatus.FAIL, "Title not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_title_not_getting_displayed_correctly")));// screenshot
			}
			
			
			String author=ob.findElement(By.xpath("//span[@ng-hide='vm.record.author.length == 0']")).getText();
//			System.out.println(author);
			String expected_author="By: Eddy, James A. ; Funk, Cory C. ; Price, Nathan D.";
			
			
			if(!compareStrings(expected_author,author)){
				
				test.log(LogStatus.FAIL, "Author field not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_author_field_not_getting_displayed_correctly")));// screenshot
			}
			
			String pub_name=ob.findElement(By.xpath("//span[@class='ng-binding' and contains(text(),'BIOLOGY')]")).getText();
//			System.out.println(pub_name);
			String expected_pub_name="TRENDS IN CELL BIOLOGY";
			
			if(!compareStrings(expected_pub_name,pub_name)){
				
				test.log(LogStatus.FAIL, "Publication name not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_publication_name_not_getting_displayed_correctly")));// screenshot
			}
			
			String pub_date=ob.findElement(By.xpath("//span[@ng-hide='!vm.record.date']")).getText();
//			System.out.println(pub_date);
			String expected_pub_date="Published: AUG 2015";
			
			if(!compareStrings(expected_pub_date,pub_date)){
				
				test.log(LogStatus.FAIL, "Publication date not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_publication_date_not_getting_displayed_correctly")));// screenshot
			}
			
			String times_cited=ob.findElement(By.xpath("//*[@class='h6 doc-info']")).getText();
//			System.out.println(times_cited);
			String expected_times_cited="0 Times Cited";
			

			if(!compareStrings(expected_times_cited,times_cited)){
				
				test.log(LogStatus.FAIL, "Times Cited information not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_times_cited_information_not_getting_displayed_correctly")));// screenshot
			}
			
			String comments=ob.findElement(By.xpath("//*[@class='h6 doc-info ng-scope']")).getText();
//			System.out.println(comments);
			String expected_comments="807 Comments";
			
			if(!compareStrings(expected_comments,comments)){
				
				test.log(LogStatus.FAIL, "Comments information not getting displayed correctly for an article in ALL search results page");// extent
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_comments_information_not_getting_displayed_correctly")));// screenshot
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

		if (status == 1)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}
