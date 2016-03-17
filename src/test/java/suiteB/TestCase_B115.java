package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class TestCase_B115 extends TestBase {
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
						"Verify that search drop down content type is retained when user navigates back to PEOPLE search results page from profile page")
						.assignCategory("Suite B");

	}

	@Test
	public void testcaseB115() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		List<String> profileOrderBeforeNavigation=new ArrayList<String>();
		List<String> profileOrderAfterNavigation=new ArrayList<String>();
		
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
			Thread.sleep(3000);

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 50);
			// Searching for people
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("John");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_tab_xpath")),8);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_tab_xpath"))).click();
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath")),30);
			//checking for Default sort option
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath"))).click();
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_sortBy_selection").replaceAll("Filter","2")), 30);
			
			//Filter=1: Relevance and Filter=2:Registration Date
			Thread.sleep(2000);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_selection").replaceAll("Filter","2"))).click();
			Thread.sleep(3000);
			test.log(LogStatus.PASS,"Selected Registration Date as sort option");
			
			List<WebElement> webElementOrderBeforeNavigation=ob.findElements(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")));
			Iterator<WebElement> iterator=webElementOrderBeforeNavigation.iterator();
			while(iterator.hasNext()){
				profileOrderBeforeNavigation.add(iterator.next().getText());
			}
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")),30);
			
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath"))).click();
			waitForElementTobeVisible(ob, By.xpath("//h2[contains(text(),'Interests')]"),15);
			test.log(LogStatus.PASS,"Record view page is opened");
			ob.navigate().back();
			//checking for Sort option
			waitForElementTobeVisible(ob,By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath")) ,30);
			String sortOptionSelected=ob.findElement(By.xpath(OR.getProperty("tr_search_people_sortBy_dropdown_xpath"))).getText();
			System.out.println(sortOptionSelected);
			Thread.sleep(6000);
			
			List<WebElement> webElementOrderAfterNavigation=ob.findElements(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")));
			Iterator<WebElement> itr=webElementOrderAfterNavigation.iterator();
			while(itr.hasNext()){
				profileOrderAfterNavigation.add(itr.next().getText());
			}
			
			try{
				Assert.assertTrue(sortOptionSelected.equals("Registration Date") && profileOrderBeforeNavigation.equals(profileOrderAfterNavigation));
				test.log(LogStatus.PASS, "Sort option selected is retained after navigating back to people search results page");
			}catch(Throwable t){
				test.log(LogStatus.FAIL, "Sort option is not retained");// extent
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
