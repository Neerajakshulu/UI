package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument.Config;
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
import util.ExtentManager;
import util.TestUtil;

public class Search122 extends TestBase {
	static int status=1;

	//	Following is the list of status:
	//		1--->PASS
	//		2--->FAIL
	//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		extent = ExtentManager.getReporter(filePath);
		String var=xlRead2(returnExcelPath('B'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that left navigation pane content type is retained when user navigates back to PEOPLE search results page from record view page").assignCategory("Search suite");

	}

	@Test
	public void testcaseB122() throws Exception{

		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteBxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;

		if(!master_condition){

			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports

		}

		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{

			openBrowser();
			clearCookies();
			maximizeWindow();

			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);

			//login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			// Searching for people
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("John");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")),30);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")),50);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath"))).click();

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")),30);

			ob.findElement(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath"))).click();
			waitForElementTobeVisible(ob, By.xpath("//h2[contains(text(),'Interests')]"),15);
			test.log(LogStatus.PASS,"Record view page is opened");
			ob.navigate().back();


			//System.out.println("list2-->"+al2);

			try{
				WebElement peopleTab=ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath")));

				Assert.assertTrue(peopleTab.getAttribute("class").contains("active"));
				test.log(LogStatus.PASS, "Left navigation panel content type is retained in people search result page after page navigation");
			}
			catch(Throwable t){
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Left navigation panel content type is not retained in people search result page after page navigation");//extent reports
				test.log(LogStatus.INFO, "Error--->"+t);
				ErrorUtil.addVerificationFailure(t);
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_documents_getting_displayed")));//screenshot	

			}

			closeBrowser();
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends--->");
	}


	@AfterTest
	public void reportTestResult(){
		extent.endTest(test);

//		if(status==1)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
//		else if(status==2)
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");
//		else
//			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "SKIP");

	}

}
