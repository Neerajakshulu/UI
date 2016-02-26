package suiteF;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class TestCase_F15 extends TestBase {
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
			test = extent.startTest(var, "Verify that users should be able to select from a list of suggested topics and check selected topic is presented in users type ahead")
					.assignCategory("Suite F");

		}
		
		@Test
		public void testcaseF15() throws Exception {
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
				ob.navigate().to(host);
				//Logging in with User2
				LoginTR.enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
				LoginTR.clickLogin();
				Thread.sleep(8000);
				jsClick(ob,ob.findElement(By.xpath(OR.getProperty("trending_now_menu_links").replaceAll("FILTER_TYPE","Topics"))));
				Thread.sleep(6000);
				WebElement element=ob.findElement(By.xpath(OR.getProperty("trending_now_topics_link")));
				String specialCharacterRemovedoutput=element.getText().replaceAll("[^\\dA-Za-z ]","");
				String expectedTitle=specialCharacterRemovedoutput.replaceAll("( )+"," ");
				element.click();
				Thread.sleep(6000);
				String searchText=ob.findElement(By.xpath("//input[@type='text']")).getAttribute("value");
				System.out.println(searchText);

				try {
					Assert.assertTrue(searchText.equals(expectedTitle));
					test.log(LogStatus.PASS, "User receiving notification with correct content");
				} catch (Throwable t) {

					test.log(LogStatus.FAIL, "Title selected is not same in search text box");// extent
					// reports
					test.log(LogStatus.INFO, "Error--->" + t);
					ErrorUtil.addVerificationFailure(t);
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_Title selected is not same in search text box")));// screenshot
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
