package iam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IAM021 extends TestBase {

	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('A'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var, "Verify View additional email preferences link is working").assignCategory("IAM");

	}

	@Test
	public void testcaseA21() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "IAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(iamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		/*
		 * fn1 = generateRandomName(8); ln1 = generateRandomName(10); String email=createNewUserAndLogin(fn1,ln1);
		 */
		openBrowser();
		try {
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			
			//
			//BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
			//
			//BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_link"))).click();
			//waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_email_preference_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("account_email_preference_link"))).click();

			Set<String> myset = ob.getWindowHandles();
			Iterator<String> myIT = myset.iterator();
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < myset.size(); i++) {

				al.add(myIT.next());
			}
			ob.switchTo().window(al.get(1));

			test.log(LogStatus.INFO, "Preference link is present and clicked");
			new PageFactory().getBrowserWaitsInstance(ob).waitUntilText("ACCESS YOUR PREFERENCE CENTER");
			test.log(LogStatus.INFO, "Preference page is opened successfully");
			ob.close();
			ob.switchTo().window(al.get(0));
			ob.navigate().back();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 30);
			logout();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 10);
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Preference page not opened");// extent reports
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "unable_to_open_preference_page")));// screenshot
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);
		}
		closeBrowser();
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
