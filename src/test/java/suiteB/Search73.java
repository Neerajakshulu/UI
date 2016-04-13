package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Search73 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);

	String var=xlRead2(returnExcelPath('B'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var,
						"Verify that profile page of a person gets displayed when user clicks on any PEOPLE option in the search type ahead while ALL option is selected in the search drop down")
				.assignCategory("Search suite");

	}

	@Test
	public void testcaseB73() throws Exception {

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
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("john");
			BrowserWaits.waitTime(2);
			
			WebElement myE2=ob.findElement(By.xpath(OR.getProperty("peopleTile")));
			String text2=myE2.getText();
			
			String[] arr2=text2.split("\n");
			
			ArrayList<String> al2=new ArrayList<String>();
			for(int i=1;i<arr2.length;i++){
				
				al2.add(arr2[i]);
			}
			
			String expected_text=al2.get(1);
			System.out.println(expected_text);
			
			for(int i=1;i<=15;i++){
				
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ARROW_DOWN);
			}
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(Keys.ENTER);
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.tagName("h2"), 120);
			
			String actual_text=ob.findElement(By.tagName("h2")).getText();
			System.out.println(actual_text);
			
			if(!compareStrings(expected_text,actual_text)){
				
				
				test.log(LogStatus.FAIL, "Profile page of a person does not get displayed when user clicks on any PEOPLE option in the search type ahead while ALL option is selected in the search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_profile_page_of_a_person_not_getting_displayed")));// screenshot

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
	
	public int getHeadingCount(){
		
		String heading_text=ob.findElement(By.tagName("h1")).getText();
		String heading_temp=heading_text.substring(16);
		int heading_num=convertStringToInt(heading_temp);
		return heading_num;
		
	}
	
	

}
