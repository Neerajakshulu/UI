package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
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
	
	public void clickOnGroupTitle(String title) throws Exception{
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()),
				30);
		List<WebElement> list=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS);
		for(WebElement we: list){
			if(we.getText().contains(title)){
				we.click();
				return;
			}
		}
		 throw new Exception("Group name not found in Group list");
	}
	
	public void addCoverPhoto(){
	
		
		
	}
	
	
	private WebElement getGroupCard(String groupTitle) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS.toString()),
				60);
		List<WebElement> invitationList = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS.toString()));
		String actTitle;
		for (WebElement we : invitationList) {
			actTitle = we
					.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()))
					.getText();
			if (actTitle.contains(groupTitle)) {
				return we;
			}
		}
		throw new Exception("Group name not found in the group list");
	}
	
	
	
	public boolean verifyItemsCount(int count, String grouptitle) throws Exception{
		WebElement groupRecord=getGroupCard(grouptitle);
		String itemsCount=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_ITEMS_COUNT_CSS.toString())).getText();
		itemsCount=itemsCount.substring(0, itemsCount.indexOf("Item")).trim();
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
		System.out.println("");
		String name=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).getText();
		String role=groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_ROLE_CSS.toString())).getText();
		return name.trim()+", "+role.trim();
	}
	
	public void clickOnGroupOwnerName(String grouptitle) throws Exception{
		
		WebElement groupRecord=getGroupCard(grouptitle);
		groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).click();
	}
	
  public boolean verifyGroupsortIsDisplayed() throws Exception{
	  
	  
			int count = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_SORT_CSS.toString())).size();
			//logger.info("Count is " + count);
			if(count==1)
			{
				return true;
			}
			return false;
		}
  
  public void verifyGroupsTabDefaultMessage(ExtentTest test) throws Exception{
	  String expectedGroupMessage1="You do not have any groups";
	  String expectedGroupMessage2="Get started by creating a new group. Groups can be shared with others. You can also join groups by accepting invitations sent to you by other Project Neon users.";
	  String actualGroupMessage1=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPS_MESSAGE1_CSS).getText();
	  String actualGroupMessage2=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPS_MESSAGE2_CSS).getText();
	  
	  try
	  {
		  Assert.assertEquals(actualGroupMessage1, expectedGroupMessage1);
		  Assert.assertEquals(actualGroupMessage2, expectedGroupMessage2);
	  test.log(LogStatus.PASS, "Group default message is displayed correctly");
	}catch(Throwable t){
		test.log(LogStatus.FAIL, "Group default message is not displayed correctly");
		test.log(
				LogStatus.FAIL,
				"Snapshot below: "
						+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "_Group_Message_mismatch")));// screenshot
		ErrorUtil.addVerificationFailure(t);
	}
		
	}
	
}
