package pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.jna.platform.win32.Sspi.TimeStamp;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * This class contains all the method related to account page
 * 
 * @author uc205521
 *
 */
public class GroupInvitationPage extends TestBase {

	PageFactory pf;

	public GroupInvitationPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	private WebElement getRecordCard(String recordTitle) throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_INVITATION_CARD_CSS.toString()), 60);
		List<WebElement> invitationList = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_INVITATION_CARD_CSS.toString()));
		String actTitle;
		for (WebElement we : invitationList) {
			actTitle = we
					.findElement(
							By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_INVITATION_CARD_TITLE_CSS.toString()))
					.getText();
			if (actTitle.equalsIgnoreCase(recordTitle)) {
				return we;
			}
		}
		throw new Exception("Group record not found in the group list");
	}

	public void acceptInvitation(String groupTitle) throws Exception {

		WebElement groupCard = getRecordCard(groupTitle);
		groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_ACCEPT_INVITAION_BUTTON_CSS.toString()))
				.click();

	}

	public void declineInvitation(String groupTitle) throws Exception {

		WebElement groupCard = getRecordCard(groupTitle);
		groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_DECLINE_INVITAION_BUTTON_CSS.toString()))
				.click();

	}

	public void clickOnGroupDetailsLinkOfInvitation(String groupTitle) throws Exception {
		WebElement groupCard = getRecordCard(groupTitle);
		groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_GROUP_DETAILS_BUTTON_CSS.toString()))
				.click();
	}

	public String getGroupOwnerProfileDetails(String groupTitle) throws Exception {

		WebElement groupCard = getRecordCard(groupTitle);
		String profileName = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_PROFILE_NAME_CSS.toString())).getText();
		String profileDetails = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_PROFILE_DETAILS_CSS.toString()))
				.getText();
		return profileName + profileDetails;
	}

	public boolean verifyingInvitations(String groupTitle) throws Exception {
		boolean istitle = false;
		WebElement groupCard = getRecordCard(groupTitle);
		String Title = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_INVITATION_CARD_TITLE_CSS.toString()))
				.getText();
		if (Title.equals(groupTitle)) {
			istitle = true;
		}
		return istitle;

	}

	public boolean verifyCustomMessage(String groupTitle, String custommsg) throws Exception {
		WebElement groupCard = getRecordCard(groupTitle);
		String cumsg = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITATION_CUSTOM_MESSAGE_CSS.toString()))
				.getText();
		if (cumsg.equals(custommsg))
			return true;
		else
			return false;
	}

	public void validateFollowOrUnfollow(String groupTitle, ExtentTest test) throws Exception {
		waitForPageLoad(ob);
		waitForAjax(ob);
		WebElement groupCard = getRecordCard(groupTitle);
		String attribute = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_FOLLOW_OWNER_CSS.toString()))
				.getAttribute("data-tooltip");

		if (attribute.equalsIgnoreCase("Follow this person")) {
			groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_FOLLOW_OWNER_CSS.toString()))
					.click();

			BrowserWaits.waitTime(10);
			attribute = groupCard
					.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_FOLLOW_OWNER_CSS.toString()))
					.getAttribute("data-tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Unfollow this person"));
			test.log(LogStatus.PASS, "Follow functionality is working fine in view post record page");

		} else {
			groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_FOLLOW_OWNER_CSS.toString()))
					.click();
			BrowserWaits.waitTime(10);
			attribute = groupCard
					.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_FOLLOW_OWNER_CSS.toString()))
					.getAttribute("data-tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Follow this person"));
			test.log(LogStatus.PASS, "UnFollow functionality is working fine in view post record page");

		}

	}

	public boolean VerifyGroupDetailsLinkOfInvitation(String groupTitle, String groupDisc) throws Exception {
		WebElement groupCard = getRecordCard(groupTitle);
		groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_GROUP_DETAILS_BUTTON_CSS.toString()))
				.click();

		String groupDetailsDisc = groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_INIVATIONS_DETAILS_DISC_CSS.toString())).getText();
		if (groupDisc.equals(groupDetailsDisc))
			return true;
		else
			return false;

	}

	public boolean VerifytheDateandTimeofIvitation(String grouptitle) throws Exception {
		WebElement groupcard = getRecordCard(grouptitle);

		String Timecard = groupcard
				.findElement(By.xpath(OnePObjectMap.RCC_GROUP_INVITATIONS_DETAILS_TIMESTAMP_XPATH.toString()))
				.getText();

		Calendar cal = Calendar.getInstance();
		String OriginaltimeStamp = new SimpleDateFormat("dd MMMMMMMMM yyyy").format(cal.getTime());
		if (Timecard.contains(OriginaltimeStamp.toUpperCase()) && (Timecard.contains("PM") || Timecard.contains("AM")))
			return true;
		else
			return false;

	}

}
