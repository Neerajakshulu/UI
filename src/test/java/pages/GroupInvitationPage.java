package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.OnePObjectMap;

/**
 * This class contains all the method related to account page
 * @author uc205521
 *
 */
public class GroupInvitationPage extends TestBase {

	PageFactory pf;

	public GroupInvitationPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}


	private  WebElement getRecordCard(String recordTitle) throws Exception {
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
	
	public void clickOnGroupDetailsLinkOfInvitation(String groupTitle) throws Exception{
		WebElement groupCard = getRecordCard(groupTitle);
		groupCard
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_GROUP_DETAILS_BUTTON_CSS.toString()))
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
	 
	public boolean verifyingInvitations(String groupTitle) throws Exception{
		boolean istitle=false;
		WebElement groupCard = getRecordCard(groupTitle);
		String Title=groupCard.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_INVITATION_CARD_TITLE_CSS.toString())).getText();
		if(Title.equals(groupTitle))
		{
		   istitle=true;
		}
		return istitle;
		
	
	}
	
}
