package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B66 extends TestBase {
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
						"Verify that counts of search results of all the content types should get displayed irrespective of the content type chosen for searching")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB66() throws Exception {

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
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("j");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
//			waitForElementTobeVisible(ob, By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'All')]"), 30);
			Thread.sleep(5000);
			
			String all_text=ob.findElement(By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'All')]")).getText();
//			System.out.println(all_text);
			int all_num=Integer.parseInt(all_text.substring(3,4));
			System.out.println(all_num);
			boolean cond1=all_num!=0;
			System.out.println(cond1);
			
			String articles_text=ob.findElement(By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'Articles')]")).getText();
			int articles_num=Integer.parseInt(articles_text.substring(8,9));
			System.out.println(articles_num);
			boolean cond2=articles_num!=0;
			System.out.println(cond2);
			
			String patents_text=ob.findElement(By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'Patents')]")).getText();
			int patents_num=Integer.parseInt(patents_text.substring(7,8));
			System.out.println(patents_num);
			boolean cond3=patents_num!=0;
			System.out.println(cond3);
			
			String people_text=ob.findElement(By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'People')]")).getText();
			int people_num=Integer.parseInt(people_text.substring(6,7));
			System.out.println(people_num);
			boolean cond4=people_num!=0;
			System.out.println(cond4);
			
			
			String posts_text=ob.findElement(By.xpath("//li[contains(@class,'content-type-selector ng-scope') and contains(text(),'Posts')]")).getText();
			int posts_num=Integer.parseInt(posts_text.substring(5,6));
			System.out.println(posts_num);
			boolean cond5=posts_num!=0;
			System.out.println(cond5);
			
			boolean master_cond=cond1 && cond2 && cond3 && cond4 && cond5;
			System.out.println("Master condition="+master_cond);
			
			try{
				
				Assert.assertTrue(master_cond);
				test.log(LogStatus.PASS, "Counts of search results of all the content types getting displayed irrespective of the content type chosen for searching");// extent report
			}
			
			catch(Throwable t){
				
				test.log(LogStatus.FAIL, "Counts of search results of all the content types not getting displayed");// extent report

            	ErrorUtil.addVerificationFailure(t);// testng
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_counts_of_search_results_of_all_the_content_types_not_getting_displayed")));// screenshot

				
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
