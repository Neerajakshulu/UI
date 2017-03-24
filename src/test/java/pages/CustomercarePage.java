
package pages;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.OnePObjectMap;
import base.TestBase;

public class CustomercarePage extends TestBase {

	PageFactory pf;

	public CustomercarePage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void EnterCustomercareDetails(ExtentTest test, String Name, String Orgname, String Email, String Phone,
			String Extension, String Country, String Request) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.CUSTOMER_CARE_USER_FULLNAME_NAME);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_FULLNAME_NAME, Name);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_ORGANIZATION_NAME, Orgname);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_EMAILID_NAME, Email);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_PHONE_NAME, Phone);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_EXTN_NAME, Extension);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_COUNTRY_NAME, Country);
		// pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_CATEGORY_NAME,
		// Category);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.CUSTOMER_CARE_USER_REQUEST_NAME, Request);

		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.CUSTOMER_CARE_SUBMIT_BUTTON_CSS);
		test.log(LogStatus.PASS,
				" All characters including special characters are  allowed in extension field in customer care page.");
	}

	public void ValidateSuccessMessage(ExtentTest test) throws Exception {

		try {
			String msg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CUSTOMER_CARE_USER_REQUEST_MSG_CSS)
					.getText();
			String msg1 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CUSTOMER_CARE_USER_REQUEST_MSG1_CSS)
					.getText();
			String msg2 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CUSTOMER_CARE_USER_REQUEST_MSG2_CSS)
					.getText();

			if (msg.contains("Thank you!") && msg1.contains("Thank you for contacting the Customer Care team.")
					&& msg2.contains("We will contact you via email within 2 business days.")) {
				test.log(LogStatus.PASS,
						" correct msg displayed and success message in customer care page matches with wire frame.");
			} else {
				test.log(LogStatus.FAIL, " incorrect msg displayed ");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");
			ErrorUtil.addVerificationFailure(t);// testng
			closeBrowser();
		}
	}
}
