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
public class GroupsListPage extends TestBase {

	PageFactory pf;

	public GroupsListPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}


	public void enterGroupTitle(String title) {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_TILTLE_CSS.toString()),
				30);
		WebElement titleField = ob
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_TILTLE_CSS.toString()));
		titleField.clear();
		titleField.sendKeys(title);
	}

	public void enterGroupDescription(String desc) {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_DESCRIPTION_CSS.toString()),
				30);
		WebElement titleField = ob
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_DESCRIPTION_CSS.toString()));
		titleField.clear();
		titleField.sendKeys(desc);
	}
	
	public void clickOnSaveGroupButton(){

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_SAVE_GROUP_BUTTON_CSS.toString()),
					30);
			ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_SAVE_GROUP_BUTTON_CSS.toString())).click();
		}
	
	public void createGroup(String title,String desc){
		enterGroupTitle(title);
		enterGroupDescription(desc);
		clickOnSaveGroupButton();
	}
	
	public void createGroup(String title){
		enterGroupTitle(title);
		clickOnSaveGroupButton();
	}

	public void clickOnCancelGroupButton(){

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_CANCEL_GROUP_BUTTON_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_CANCEL_GROUP_BUTTON_CSS.toString())).click();
	}
	
	public void clickOnGroupTitle(String title){

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()),
				30);
		ob.findElement(By.linkText(title)).click();
	}
	
	public void addCoverPhoto(){
	
		
		
	}
	
	
	private WebElement getGroupCard(String groupTitle) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS.toString()),
				60);
		List<WebElement> invitationList = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPDETAILS_RECORD_CARD_CSS.toString()));
		String actTitle;
		for (WebElement we : invitationList) {
			actTitle = we
					.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPINVITATIONS_RECORD_CARD_TITLE_CSS.toString()))
					.getText();
			if (actTitle.equalsIgnoreCase(groupTitle)) {
				return we;
			}
		}
		throw new Exception("Group name not found in the group list");
	}
	
	
	
	public boolean verifyItemsCount(int count, String grouptitle) throws Exception{
		WebElement groupRecord=getGroupCard(grouptitle);
		String itemsCount=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_ITEMS_COUNT_CSS.toString())).getText();
		return Integer.parseInt(itemsCount)==count;
	}
	
	public boolean verifyMembersCount(int count, String grouptitle) throws Exception{
		WebElement groupRecord=getGroupCard(grouptitle);
		String itemsCount=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_MEMBERS_COUNT_CSS.toString())).getText();
		return Integer.parseInt(itemsCount)==count;
	}
	
	public boolean verifyGroupDescription(String desc, String grouptitle) throws Exception{
		WebElement groupRecord=getGroupCard(grouptitle);
		String groupDesc=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_DESCRIPTION_CSS.toString())).getText();
		return groupDesc.equals(desc);
	}
	
	public String getGroupOwnerDetails(String grouptitle) throws Exception{
		WebElement groupRecord=getGroupCard(grouptitle);
		
		String name=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).getText();
		String role=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_ROLE_CSS.toString())).getText();
		return name.trim()+","+role.trim();
	}
	
	public void clickOnGroupOwnerName(String grouptitle) throws Exception{
		
		WebElement groupRecord=getGroupCard(grouptitle);
		groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).click();
	}
	
}
