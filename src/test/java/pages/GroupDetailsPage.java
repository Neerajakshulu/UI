package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

/**
 * This class contains all the method related to account page
 * 
 * @author uc205521
 *
 */
public class GroupDetailsPage extends TestBase {

	PageFactory pf;

	public GroupDetailsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public int getArticlesCounts() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_COUNT_CSS);
		int articlesCount = Integer.parseInt(count.getText());
		return articlesCount;
	}

	public int getPatentsCounts() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_COUNT_CSS);
		int patentCount = Integer.parseInt(count.getText());
		return patentCount;
	}

	public int getPostsCounts() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_POSTS_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPDETAILS_POSTS_COUNT_CSS);
		int postsCount = Integer.parseInt(count.getText());
		return postsCount;
	}

	public int getAttachedFilesCounts() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPDETAILS_ATTACHED_FILES_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(
				OnePObjectMap.RCC_GROUPDETAILS_ATTACHED_FILES_COUNT_CSS);
		int attachedFilesCount = Integer.parseInt(count.getText());
		return attachedFilesCount;
	}

	public int getMembersCounts() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_COUNT_CSS);
		int membersCount = Integer.parseInt(count.getText());
		return membersCount;
	}

	public void clickOnDeleteButton() throws Exception {
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_DELETE_GROUP_BUTTON_CSS.toString()), 60);
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.RCC_GROUPSDETAILS_DELETE_GROUP_BUTTON_CSS);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				we.click();
				return;
			}
		}
		throw new Exception("Delete button is not displaye in group details page");
	}

	public void clickOnEditButton() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPSDETAILS_EDIT_GROUP_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPSDETAILS_EDIT_GROUP_BUTTON_CSS);

	}

	public void clickOnAttachFileButton() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPSDETAILS_LINK_ITEMS_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPSDETAILS_LINK_ITEMS_BUTTON_CSS);
	}

	public void clickOnInviteOthersButton() throws Exception {
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_INVITE_OTHERS_BUTTON_CSS.toString()), 60);
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.RCC_GROUPSDETAILS_INVITE_OTHERS_BUTTON_CSS);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				we.click();
				return;
			}
		}
		throw new Exception("Invite Others button is not displaye in group details page");

	}

	public void clickArticlesTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_CSS);
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(
				OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS);
	}

	public void clickPatentstab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_TAB_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_TAB_CSS);
		waitForAjax(ob);
	}

	public void clickPostsTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_POSTS_TAB_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_POSTS_TAB_CSS);
		waitForAjax(ob);
	}

	public void clickAttachedFilesTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_TAB_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_TAB_CSS);
		waitForAjax(ob);
	}

	public void clickMembersTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_TAB_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_TAB_CSS);
		waitForAjax(ob);
	}

	private WebElement getRecordCard(String recordTitle) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS.toString()), 60);
		List<WebElement> invitationList = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS
				.toString()));
		String actTitle;
		for (WebElement we : invitationList) {
			actTitle = we.findElement(
					By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_RECORD_CARD_TITLE_CSS.toString())).getText();
			if (actTitle.equalsIgnoreCase(recordTitle)) {
				return we;
			}
		}
		throw new Exception("Record not found in the group list");
	}

	public String getGroupTitle() {
		waitForElementTobePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_GROUP_TITLE_CSS.toString()), 30);
		List<WebElement> list = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_GROUP_TITLE_CSS
				.toString()));
		String title = "";
		for (WebElement we : list) {

			if (we.isDisplayed()) {
				title = we.getText();
				break;
			}
		}

		return title;

	}

	public void inviteMembers(List<String> membersList) {

	}

	public boolean inviteMembers(String membersName) throws InterruptedException {
		boolean isFound = false;
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_CSS.toString())).click();
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_CSS.toString()))
				.sendKeys(membersName);

		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_OPTIONS_CSS.toString()), 30);
		BrowserWaits.waitTime(2);
		List<WebElement> optionsList = ob.findElements(By
				.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_OPTIONS_CSS.toString()));
		for (WebElement we : optionsList) {

			if (we.getText().equals(membersName)) {
				we.click();
				isFound = true;
				break;
			}
		}
		clickOnSendInvitation();
		return isFound;
	}

	public void clickOnSendInvitation() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBERS_SEND_BUTTON_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBERS_SEND_BUTTON_CSS.toString()))
				.click();
	}

	public void clickOnCancelInvitation() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBERS_CANCEL_BUTTON_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBERS_CANCEL_BUTTON_CSS.toString()))
				.click();
	}

	public void cancelPendingInvitations(String username) throws Exception {
		waitForAjax(ob);
		WebElement groupCard = getPendingRecords(username);
		groupCard.findElement(
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PENDING_MEMBERS_CANCEL_INVITATION_BUTTON_CSS.toString()))
				.click();

	}

	private WebElement getPendingRecords(String username) throws Exception {

		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PENDING_MEMBERS_CARD_CSS.toString()), 60);
		List<WebElement> pendinglist = ob.findElements(By
				.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PENDING_MEMBERS_CARD_CSS.toString()));
		String username1 = null;
		for (WebElement we : pendinglist) {
			username1 = we.findElement(
					By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PENDING_MEMBERS_NAMES_CSS.toString())).getText();
			if (username.equalsIgnoreCase(username1)) {
				return we;
			}
		}
		throw new Exception("Record not found in the group list");
	}

	public void clickOnDeleteButtonInConfirmationMoadl() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPDETAILS_DELETE_CONFIMATION_DELETE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPDETAILS_DELETE_CONFIMATION_DELETE_BUTTON_CSS);

	}

	public void typeCustomMessage(String Message) {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_CUSTOM_MESSAGE_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_CUSTOM_MESSAGE_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_CUSTOM_MESSAGE_CSS.toString())).sendKeys(
				Message);

	}

	public void validateArtcileInGroupDetailsPage(String groupTitle) throws Exception {
		clickArticlesTab();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(
				OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_DETAILS_CSS);
		String groupArticleTitle = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_DETAILS_CSS).getText();
		logger.info("Article Title in Group Details page-->" + groupArticleTitle);
		if (!groupTitle.equalsIgnoreCase(groupArticleTitle)) {
			throw new Exception("Added to Group Article not present in Group Details Article tab");
		}
	}

	public String getPendingInvitationMessage() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUP_DETAILS_PAGE_PENDING_INVITATION_MESSAGE_CSS);

		String otherMemberJoinGroup = ob.findElement(
				By.cssSelector(OnePObjectMap.RCC_GROUP_DETAILS_PAGE_PENDING_INVITATION_MESSAGE_CSS.toString()))
				.getText();
		return otherMemberJoinGroup;

	}

	public void clickOnCancelButtonINCancelInviTationModal() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS);

	}

	public void clickOnCloseButtonINCancelInviTationModal() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob)
				.click(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS);
	}

	public void clickOnSubmitButtonINCancelInviTationModal() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS);

	}

	public boolean verifyingCustomMsgOnCancelModal() throws Exception {
		String expectedmsg = "Are you sure you want to Cancel this invitation?";
		String msgheader = "Cancel invitation";
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CSS);
		String msg = ob.findElement(
				By.cssSelector(OnePObjectMap.RCC_GROUP_DETAILS_PAGE_CUSTOM_MSG_OF_CANCEL_MODAL_CSS.toString()))
				.getText();
		String headertext = ob.findElement(
				By.cssSelector(OnePObjectMap.RCC_GROUP_DETAILS_PAGE_HEADER_MSG_OF_CANCEL_MODAL_CSS.toString()))
				.getText();
		if (expectedmsg.equalsIgnoreCase(msg) && msgheader.equalsIgnoreCase(headertext)) {
			return true;
		}
		throw new Exception("Record not found in the group list");

	}
	public void validateGroupMembersInGroupDetailPage() throws Exception
	{   
		  clickMembersTab();
	
		
	}

}
