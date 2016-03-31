package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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

public class TestCase_B18 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		extent = ExtentManager.getReporter(filePath);
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent
				.startTest(var,
						"Verify that RESET button in the left navigation pane in search results page is working correctly")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB18() throws Exception {
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
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();
			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			login();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 20);
			ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")).click();
			waitForElementTobeVisible(ob, By.xpath("//li[contains(@class,'content-type-selector') and contains(text(),'Articles')]"), 20);
			ob.findElement(By.xpath("//li[contains(@class,'content-type-selector') and contains(text(),'Articles')]")).click();
			waitForAllElementsToBePresent(ob,
					By.cssSelector(OR.getProperty("tr_search_results_refine_expand_css")), 40);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_refine_expand_css"))).click();
					
			int checkboxesSelected = 0;
			List<WebElement> checkboxList; 
			for (int i= 0;i<2 ; i++) {
				checkboxList	= ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_all_refine_checkboxes_css")));
				if (checkboxList.get(i).isDisplayed() && !checkboxList.get(i).isSelected())
					jsClick(ob,checkboxList.get(i));
				waitForAjax(ob);

			}
			
			checkboxList	= ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_all_refine_checkboxes_css")));
			for (WebElement element : checkboxList) {
				if (element.isSelected())
					checkboxesSelected++;

			}
			Assert.assertTrue(checkboxesSelected != 0, "No filters is selected");
			WebElement resetButton = ob
					.findElement(By.cssSelector(OR.getProperty("tr_search_results_reset_button_css")));
			jsClick(ob, resetButton);
			waitForAjax(ob);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_refine_expand_css"))).click();
			checkboxList = ob
					.findElements(By.cssSelector(OR.getProperty("tr_search_results_all_refine_checkboxes_css")));

			checkboxesSelected = 0;
			for (WebElement element : checkboxList) {
				if (element.isSelected())
					checkboxesSelected++;

			}

			try {
				Assert.assertTrue(checkboxesSelected == 0);
				test.log(LogStatus.PASS, "Reset button for search results should work fine");
			} catch (Throwable t) {
				test.log(LogStatus.PASS, "Reset button for search results not working as expected");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "reset_ is_not_ working_ for_ search_ results")));// screenshot
			}
			logout();
			closeBrowser();
			
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.PASS, "Something went wrong");// extent reports
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
