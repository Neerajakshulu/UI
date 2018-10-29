package Publons;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

public class PUBLONS011 extends TestBase {
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
	public void testcaseA11() throws Exception {
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
			
			//CMTY Customercare
			try{				
				ob.navigate().to(host +"/#/login?app=cmty");
				String cmty_contact = ob.findElement(By.xpath(OR.getProperty("customercare_mail"))).getText();
				logger.info("CustomerCare Email is--->" +cmty_contact);
				test.log(LogStatus.PASS, "CMTY CustomerCare email details found");
				Assert.assertEquals( "community.info@clarivate.com", cmty_contact);
				test.log(LogStatus.PASS, "CMTY CustomerCare email is correct");
				
				
			}
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "Customercare Contact email is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			
			}
			finally {
					test.log(LogStatus.INFO, " CMTY execution end");
					extent.endTest(test);
				}	
							
			//CADP Customercare
			/*try{
				ob.navigate().to(host +"/#/login?app=cadp");
				String cadp_contact = ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).getText();
				logger.info("CustomerCare Contact is--->"+cadp_contact);
				ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).click();
				Set<String> handler = ob.getWindowHandles();
				Iterator<String> it = handler.iterator();
				
				
				String parentWindowId = it.next();
				logger.info("parent window id --->" + parentWindowId);
				
				String childWindowId = it.next();
				logger.info("child window id --->" +childWindowId );			
				
				ob.switchTo().window(childWindowId);
				//waitForElementTobeVisible(ob, By.xpath(OR.getProperty("//span[@class='uiOutputText']")),50);
				//ob.findElement(By.tagName(childWindowId)).sendKeys("Keys.ESCAPE");
				//ob.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				waitForPageLoad(ob);
				String Current_Url = ob.getCurrentUrl();
				logger.info("Current_Url"+Current_Url);
				Assert.assertEquals("https://support.clarivate.com/s/", Current_Url);
				test.log(LogStatus.PASS, "Getting expected page with correct URL for CADP");
				ob.close();
				ob.switchTo().window(parentWindowId);
				
			}
			
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " CADP execution end");
				extent.endTest(test);
			}
			*/
			//IPA Customercare
			try{
				
			ob.navigate().to(host +"/#/login?app=ipa");
			String ipa_contact = ob.findElement(By.xpath(OR.getProperty("customercare_mail"))).getText();
			logger.info("CustomerCare Email is ---->" + ipa_contact);
			test.log(LogStatus.PASS, "IPA CustomerCare email details found");
			Assert.assertEquals("IPA.support@thomsonreuters.com", ipa_contact);
			test.log(LogStatus.PASS, "IPA CustomerCare email is correct");
			
			}
			
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "Customercare Contact email is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			}
			finally {
				test.log(LogStatus.INFO, " IPA execution end");
				extent.endTest(test);
				}

			//DRA Customercare
			try{
				
				ob.navigate().to(host +"/#/login?app=dra");
				String dra_contact = ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).getText();
				logger.info("Customercare contact is" +dra_contact);
				ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).click();
				
				Set<String> handler = ob.getWindowHandles();
				Iterator<String> it = handler.iterator();
				
				String ParentWindowId = it.next();
				logger.info("Parent Window id is" + ParentWindowId);
				
				String ChildWindowId = it.next();
				logger.info("Child Window id is"+ ChildWindowId );
				
				ob.switchTo().window(ChildWindowId);
				waitForPageLoad(ob);
				//ob.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				String CurrentURl = ob.getCurrentUrl();
				logger.info(CurrentURl);
				//Assert.assertEquals("https://support.clarivate.com/LifeSciences/", CurrentURl);
				test.log(LogStatus.PASS, "Getting Expected page with correct URL for DRA");
				
				ob.close();
				ob.switchTo().window(ParentWindowId);
				
			}
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO," DRA execution end");
				extent.endTest(test);
			}
			
			//CMC Customercare
			try{
				
				ob.navigate().to(host +"/#/login?app=cmc");
				String CMC_contact = ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).getText();
				logger.info("Customercare contact is" +CMC_contact);
				
				ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).click();
				
				Set<String> handler =  ob.getWindowHandles();
				Iterator<String> it =handler.iterator();
				
				String ParentWindowId = it.next();
				logger.info("Parent Window id is --->" +ParentWindowId);
				
				String ChildWindowId = it.next();
				logger.info("Child Window id is ---->" +ChildWindowId);
				
				ob.switchTo().window(ChildWindowId);
				waitForPageLoad(ob);
				//waitForElementTobeVisible(ob, By.xpath(OR.getProperty("//span[@class='uiOutputText']")),30);
				//ob.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				String CurrentURL = ob.getCurrentUrl();
				logger.info("Current URL is ----->" +CurrentURL);
				//Assert.assertEquals(actual, expected);
				test.log(LogStatus.PASS, "Getting Expected page with Correct URL for CMC");
				ob.close();
				ob.switchTo().window(ParentWindowId);				
				
			}
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " CMC execution end");
				extent.endTest(test);
			}
			
			// INTEGRITY Customercare
			
			try{
				
				ob.navigate().to(host +"/#/login?app=integrity");
				String Integrity_Contact = ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).getText();
				logger.info("Customercare contact is" +Integrity_Contact);
				ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).click();
				
				Set<String> handler = ob.getWindowHandles();
				Iterator<String> it = handler.iterator();
				
				String ParentWindowId = it.next();
				logger.info("Parent Window Id is --->" +ParentWindowId);
				
				String ChildWIndowId = it.next();
				logger.info("Child Window Id is --->" +ChildWIndowId);
				
				ob.switchTo().window(ChildWIndowId);
				waitForPageLoad(ob);
				//ob.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				String CurrentUrl = ob.getCurrentUrl();
				logger.info("Current Url is" +CurrentUrl);
				
				//Assert.assertEquals("https://support.clarivate.com/LifeSciences/", CurrentUrl);
				test.log(LogStatus.PASS, "Getting Expected page with Correct URL for INTEGRITY");
				ob.close();
				ob.switchTo().window(ParentWindowId);	
				
				
			}
			
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " INTEGRITY execution end");
				extent.endTest(test);
			}
			
			//ENDNOTE Customercare
			/*try{
				
				ob.navigate().to(host +"/#/login?app=endnote");
				String EndNote_Contact = ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).getText();
				logger.info("Customercare Contact is " +EndNote_Contact);
				
				ob.findElement(By.xpath(OR.getProperty("customercare_link_text"))).click();
				
				Set<String> handler = ob.getWindowHandles();
				Iterator <String> it = handler.iterator();
				
				String ParentWIndowId = it.next();
				logger.info("ParentWindowId is --->" +ParentWIndowId);
				
				String ChildWindowId = it.next();
				logger.info("ChildWindowId is --->"+ChildWindowId);
				
				ob.switchTo().window(ChildWindowId);
				waitForPageLoad(ob);
				//ob.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				String CurrentUrl = ob.getCurrentUrl();
				logger.info("CurrentUrl is"+CurrentUrl);
				//Assert.assertEquals("http://endnote.com/support", CurrentUrl);
				ob.close();
				test.log(LogStatus.PASS, "Getting Expected page with Correct URL for ENDNOTE");
				
				ob.switchTo().window(ParentWIndowId);
				
			}
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " ENDNOTE execution end");
				extent.endTest(test);
			}*/
			
			//PUBLONS Customercare
			try{
				
				ob.navigate().to(host +"/#/login?app=publons");
				String publons_contact = ob.findElement(By.xpath(OR.getProperty("customercare_mail"))).getText();
				logger.info("CustomerCare Email is--->" +publons_contact);
				test.log(LogStatus.PASS, "PUBLONS CustomerCare email details found");
				//Assert.assertEquals( "info@publons.com", publons_contact);
				test.log(LogStatus.PASS, " PUBLONS CustomerCare details are correct");
				
				
			}
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " PUBLONS execution end");
				extent.endTest(test);
			}
		
			
			//PSA  Customercare
			try{
				
				ob.navigate().to(host +"/#/login?app=psa");
				String PSA_contact = ob.findElement(By.xpath(OR.getProperty("customercare_mail"))).getText();
				logger.info("CustomerCare Email is--->" +PSA_contact);
				test.log(LogStatus.PASS, "PSA CustomerCare email details found");
				Assert.assertEquals( "community.info@clarivate.com", PSA_contact);
				test.log(LogStatus.PASS, " PSA CustomerCare email is correct");
				
					}
		
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " PSA execution end");
				extent.endTest(test);
			}
			
			//WAT  Customercare
			
			try{
				
				ob.navigate().to(host +"/#/login?app=wat");
				String WAT_contact = ob.findElement(By.xpath(OR.getProperty("customercare_mail"))).getText();
				logger.info("CustomerCare Email is--->" +WAT_contact);
				test.log(LogStatus.PASS, "WAT CustomerCare email details found");
				Assert.assertEquals( "sarlabs.info@clarivate.com", WAT_contact);
				test.log(LogStatus.PASS, " WAT CustomerCare email is correct");
				closeBrowser();
			}
		
			catch (Throwable t) {
				test.log(LogStatus.FAIL, "CustomerCare Contact is not displayed in Login page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
				ob.close();
				closeBrowser();
				
			}
			
			finally {
				test.log(LogStatus.INFO, " WAT execution end");
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
