package Publons;

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
import util.ExtentManager;

public class PUBLONS017 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("PUBLONS");
	}

	@Test
	public void testcaseA17() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		// String userName="neon.user2@tr.com";
		// String pass="Neon@123";

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			//CMTY app
			try{
				
			ob.navigate().to(host+"/#/login?app=cmty");
			pf.getPubPage(ob).Reg_email_propopulated();
			pf.getPubPage(ob).checkPrepopulatedText_Register("fhyvvi+bxgo5pgubqr8g@sharklasers.com");
			test.log(LogStatus.PASS, "Correct email id Populated in Registration page"); 
			}
			catch (Throwable t) {
	    		
				test.log(LogStatus.FAIL, "Email id  is not displayed in cmty Registration page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				closeBrowser();
			
			}
			    finally {
					test.log(LogStatus.INFO, " Email id is populated in CMTY Registration page ");
					extent.endTest(test);
				}	
			//EndNote
			try{
				ob.navigate().to(host+"/#/login?app=endnote");
				pf.getPubPage(ob).Reg_email_propopulated();
				pf.getPubPage(ob).checkPrepopulatedText_Register("fhyvvi+bxgo5pgubqr8g@sharklasers.com");
				test.log(LogStatus.PASS, "Correct email id Populated in Registration page"); 
				
			}
                catch (Throwable t) {
	    		
				test.log(LogStatus.FAIL, "Email id  is not displayed in EndNote Registration page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				
			
			}
			    finally {
					test.log(LogStatus.INFO, " Email id is populated in EndNote Registration page ");
					extent.endTest(test);
				}	
			//Publons 
			try{
				ob.navigate().to(host+"/#/login?app=publons");
				pf.getPubPage(ob).Reg_email_propopulated();
				pf.getPubPage(ob).checkPrepopulatedText_Register("fhyvvi+bxgo5pgubqr8g@sharklasers.com");
				test.log(LogStatus.PASS, "Correct email id Populated in Publons Registration page"); 
				closeBrowser();
			}
                catch (Throwable t) {
	    		
				test.log(LogStatus.FAIL, "Email id  is not displayed in Publons Registration page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				
			
			}
			    finally {
					test.log(LogStatus.INFO, " Email id is populated in Publons Registration page ");
					extent.endTest(test);
				}	
		} catch (Throwable t) {
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
	}
}
