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
public class GroupDetailsPage extends TestBase {

	PageFactory pf;

	public GroupDetailsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}


	public int getArticlesCounts(){
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_COUNT_CSS.toString()), 30);
			WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_COUNT_CSS.toString()));
			int articlesCount = Integer.parseInt(count.getText());
			return articlesCount;
	}

	public int getPatentsCounts(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_COUNT_CSS.toString()));
		int patentCount = Integer.parseInt(count.getText());
		return patentCount;
}
	
	public int getPostsCounts(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_POSTS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_POSTS_COUNT_CSS.toString()));
		int postsCount = Integer.parseInt(count.getText());
		return postsCount;
}
	
	public int getLinkedItemsCounts(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_COUNT_CSS.toString()));
		int linkedItemsCount = Integer.parseInt(count.getText());
		return linkedItemsCount;
}
	
	
	public int getMembersCounts(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_COUNT_CSS.toString()));
		int membersCount = Integer.parseInt(count.getText());
		return membersCount;
}
	
	
	public void clickOnDeleteButton(){
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_DELETE_GROUP_BUTTON_CSS.toString()),
					30);
			ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_DELETE_GROUP_BUTTON_CSS.toString())).click();
		}
	
	public void clickOnEditButton(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_EDIT_GROUP_BUTTON_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_EDIT_GROUP_BUTTON_CSS.toString())).click();
	}
	
	public void clickOnLinkItemsButton(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_LINK_ITEMS_BUTTON_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_LINK_ITEMS_BUTTON_CSS.toString())).click();
	}
	
	public void clickOnInviteOthersButton(){
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_INVITE_OTHERS_BUTTON_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSDETAILS_INVITE_OTHERS_BUTTON_CSS.toString())).click();
	}
	
	
	public void clickArticlesTab() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_ARTICLES_TAB_CSS.toString())).click();;
		waitForAjax(ob);
	}

	public void clickPatentstab() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_TAB_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_PATENTS_TAB_CSS.toString())).click();
		waitForAjax(ob);
	}

	public void clickPostsTab() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_POSTS_TAB_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_POSTS_TAB_CSS.toString())).click();
		waitForAjax(ob);
	}

	public void clickLinkedItemsTab() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_TAB_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_LINKED_ITEMS_TAB_CSS.toString())).click();
		waitForAjax(ob);
	}

	public void clickMembersTab() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_TAB_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_MEMBERS_TAB_CSS.toString())).click();
		waitForAjax(ob);
	}
	
	private WebElement getRecordCard(String recordTitle) throws Exception{
		
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS.toString()), 60);
		List<WebElement> invitationList = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS.toString()));
		String actTitle;
		for (WebElement we : invitationList) {
			actTitle = we
					.findElement(
							By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_RECORD_CARD_TITLE_CSS.toString()))
					.getText();
			if (actTitle.equalsIgnoreCase(recordTitle)) {
				return we;
			}
		}
		throw new Exception("Record not found in the group list");
	}
	
	public String getGroupTitle() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_GROUP_TITLE_CSS.toString()), 30);
		String title = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_GROUP_TITLE_CSS.toString()))
				.getText();
		return title;

	}
	
	public void inviteMembers(List<String> membersList){
		
		
	}
	
	public boolean inviteMembers(String membersName) {
		boolean isFound=false;
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_CSS.toString()))
				.sendKeys(membersName);

		List<WebElement> optionsList = ob.findElements(
				By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_INVITE_MEMBER_TYPE_AHEAD_OPTIONS_CSS.toString()));
		for (WebElement we : optionsList) {

			if (we.getText().equals(membersName)) {
				we.click();
				isFound=true;
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
	
	
}
