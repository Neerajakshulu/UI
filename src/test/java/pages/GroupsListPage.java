package pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

/**
 * This class contains all the method related to account page
 * 
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

		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_DESCRIPTION_CSS.toString()), 30);
		WebElement titleField = ob
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_ENTER_GROUP_DESCRIPTION_CSS.toString()));
		titleField.clear();
		titleField.sendKeys(desc);
	}

	public void clickOnSaveGroupButton() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_SAVE_GROUP_BUTTON_CSS.toString()),
				60);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_SAVE_GROUP_BUTTON_CSS.toString())).click();
	}

	public void createGroup(String title, String desc) {
		enterGroupTitle(title);
		enterGroupDescription(desc);
		clickOnSaveGroupButton();

	}

	public void createGroup(String title) {
		enterGroupTitle(title);
		clickOnSaveGroupButton();
	}

	public void clickOnCancelGroupButton(ExtentTest test) throws Exception {

		waitForAjax(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_CANCEL_GROUP_BUTTON_CSS.toString()), 60);
		List<WebElement> list = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUPSLIST_CANCEL_GROUP_BUTTON_CSS);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				we.click();
				test.log(LogStatus.PASS, "Cancel button is clicked Succesfully");
				return;
			}
		}
		throw new Exception("Cancel button is not displaye in group details page");
	}

	public void clickOnGroupTitle(String title) throws Exception {
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()), 30);
		List<WebElement> list = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS);
		for (WebElement we : list) {
			if (we.getText().contains(title)) {
				we.click();
				return;
			}
		}
		throw new Exception("Group name not found in Group list");
	}

	public void addCoverPhoto() throws Exception {
		clickOnAddImage();
		String imagePath = System.getProperty("user.dir") + "\\images\\" + "images3" + ".jpg";
		Runtime.getRuntime().exec("autoit_scripts/imageUpload2.exe" + " " + imagePath);
	}

	public void clickOnAddImage() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_COVER_PHOTO_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_COVER_PHOTO_CSS);
	}

	private WebElement getGroupCard(String groupTitle) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS.toString()), 60);
		List<WebElement> groupsList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS);

		String actTitle;
		for (WebElement we : groupsList) {
			actTitle = we.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()))
					.getText();
			if (actTitle.contains(groupTitle)) {
				return we;
			}
		}
		throw new Exception("Group name not found in the group list");
	}

	public boolean verifyItemsCount(int count, String grouptitle) throws Exception {
		WebElement groupRecord = getGroupCard(grouptitle);
		String itemsCount = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_ITEMS_COUNT_CSS.toString())).getText();
		itemsCount = itemsCount.substring(0, itemsCount.indexOf("Item")).trim();
		return Integer.parseInt(itemsCount) == count;
	}

	public boolean verifyMembersCount(int count, String grouptitle) throws Exception {
		WebElement groupRecord = getGroupCard(grouptitle);
		String itemsCount = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_MEMBERS_COUNT_CSS.toString())).getText();
		return Integer.parseInt(itemsCount) == count;
	}

	public boolean verifyGroupDescription(String desc, String grouptitle) throws Exception {
		WebElement groupRecord = getGroupCard(grouptitle);
		String groupDesc = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_DESCRIPTION_CSS.toString())).getText();
		return groupDesc.equals(desc);
	}

	public String getGroupOwnerDetails(String grouptitle) throws Exception {
		WebElement groupRecord = getGroupCard(grouptitle);
		System.out.println("");
		String name = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).getText();
		String role = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_ROLE_CSS.toString())).getText();
		return name.trim() + ", " + role.trim();
	}

	public void clickOnGroupOwnerName(String grouptitle) throws Exception {

		WebElement groupRecord = getGroupCard(grouptitle);
		groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).click();
	}

	public boolean verifyGroupsortIsDisplayed() throws Exception {

		int count = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_SORT_CSS.toString())).size();
		// logger.info("Count is " + count);
		if (count == 1) {
			return true;
		}
		return false;
	}

	public void verifyGroupsTabDefaultMessage(ExtentTest test) throws Exception {
		String expectedGroupMessage1 = "You do not have any groups";
		String expectedGroupMessage2 = "Get started by creating a new group. Groups can be shared with others. You can also join groups by accepting invitations sent to you by other Project Neon users.";
		String actualGroupMessage1 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPS_MESSAGE1_CSS)
				.getText();
		String actualGroupMessage2 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUPS_MESSAGE2_CSS)
				.getText();

		try {
			Assert.assertEquals(actualGroupMessage1, expectedGroupMessage1);
			Assert.assertEquals(actualGroupMessage2, expectedGroupMessage2);
			test.log(LogStatus.PASS, "Group default message is displayed correctly");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Group default message is not displayed correctly");
			test.log(LogStatus.FAIL, "Snapshot below: " + test
					.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Group_Message_mismatch")));// screenshot
			ErrorUtil.addVerificationFailure(t);
		}

	}

	public void navigateToGroupRecordPage(String grouptitle) throws Exception {
		WebElement groupcard = getGroupCard(grouptitle);
		groupcard.click();
		pf.getBrowserWaitsInstance(ob).waitUntilText("Groups", "Create new Group", "Articles", "Patents", "Posts",
				"Attached files", "Members");
	}

	public boolean checkForGroup(String groupTitle) throws Exception {
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS.toString()), 60);
		List<WebElement> groupsList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS);

		String actTitle;
		for (WebElement we : groupsList) {
			actTitle = we.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_TITLE_CSS.toString()))
					.getText();
			if (actTitle.contains(groupTitle)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkAddedUserDetails(String title, String userName) throws Exception {
		List<WebElement> userDetails = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUPSLIST_GROUP_CARD_CSS);
		String actTitle = null;
		boolean status = false;
		for (int i = 0; i < userDetails.size(); i++) {
			actTitle = userDetails.get(i).getText();
			if (actTitle.contains(title) && actTitle.contains(userName)) {
				status = true;
				break;
			}
		}
		return status;
	}

	public void ValidateSaveButtonDisabled(ExtentTest test) throws Exception {
		String ErrorMsg = "Please provide a title for your group. (Minimum 2 characters)";
		String MinLenthMsg1=null;
		String MinLenthMsg=ValidateSave(MinLenthMsg1);
		String title2 = RandomStringUtils.randomAlphanumeric(1);
		if (title2.length() == 1) {
			if (MinLenthMsg.equalsIgnoreCase(ErrorMsg)) {
				test.log(LogStatus.PASS,
						"Error message 'Please provide a title for your group. (Minimum 2 characters)'is displayed correctly");
				if (!ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_SAVE_GROUP_BUTTON_CSS.toString()))
						.isEnabled()) {
					test.log(LogStatus.PASS, "'Save' button is disabled as minimum group name requirement is not met");

				} else {
					test.log(LogStatus.FAIL, "Save button is enabled.");

				}

			} else {
				test.log(LogStatus.FAIL, "Error message is not displayed.");

			}

		}

	}

	public String ValidateSave(String TxtMsg) throws Exception {

		waitForAjax(ob);
		TxtMsg = null;

		waitForAllElementsToBePresent(ob, By.xpath(OnePObjectMap.RCC_ERROR_MSG_TEXT_XPATH.toString()), 60);

		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.RCC_ERROR_MSG_TEXT_XPATH);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				TxtMsg = we.getText();

			}
		}
		return TxtMsg;
	}
	public void ValidateDescLessThan500(ExtentTest test) throws Exception {
		int GroupLength = ob.findElement(By.xpath(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH.toString())).getText().length();
		if (GroupLength <= 500) {

			test.log(LogStatus.PASS,
					" user is able to create a new Group with Group name and description of <= 500 characters.");
		}

		else {

			test.log(LogStatus.FAIL,
					"user is able to create a new Group with Group name and description of <= 500 characters.");
		}
	}

	public void ValidateDescMoreThan500(int actualLength, int Desc500, ExtentTest test) throws Exception {
		try {
			Assert.assertNotEquals(actualLength, Desc500);
			test.log(LogStatus.PASS,
					" user is not able to create a new group with more than 500 characters in group description.");

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL,
					"user is  able to create a new group with more than 500 characters in group description.");// extent
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
					.getSimpleName()
					+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
			ErrorUtil.addVerificationFailure(t);

		}

	}

	public boolean verifyButtonIsEnabled(Object elementName) throws Exception {

		boolean ButtonStatus = pf.getBrowserActionInstance(ob).getElement(elementName).isEnabled();
		return ButtonStatus;

	}

	public boolean verifySortByOptions() throws Exception {
		String opt1 = "Most recent activity", opt2 = "Creation date", opt3 = "Group name";
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.RCC_GROUP_LIST_PAGE_SORT_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_LIST_PAGE_SORT_BUTTON_CSS);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()), 30);
		String val1 = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()))
				.get(0).getText();
		String val2 = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()))
				.get(1).getText();
		String val3 = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()))
				.get(2).getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_LIST_PAGE_SORT_BUTTON_CSS);
		if (val1.equalsIgnoreCase(opt1) && val2.equalsIgnoreCase(opt2) && val3.equalsIgnoreCase(opt3))
			return true;
		else
			return false;

	}

	public void selectSortoptions(String optionvalue) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.RCC_GROUP_LIST_PAGE_SORT_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_LIST_PAGE_SORT_BUTTON_CSS);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()), 30);
		List<WebElement> li = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_SORT_BY_MENU_CSS.toString()));
		for (WebElement we : li) {
			if (we.getText().equalsIgnoreCase(optionvalue)) {
				we.click();
				return;
			}
		}
		throw new Exception("Sortion option is not found");
	}

	public void sortByGroupName() throws Exception {
		List<String> list = new ArrayList();

		selectSortoptions("Group name");
		List<WebElement> li = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_TITLE_CSS.toString()));
		for (WebElement we : li) {
			String val = we.getText();
			System.out.println("group name" + val);
			list.add(val);

		}
		Collections.sort(list);

	}

	public boolean getCreateGroupCard() throws Exception {
		boolean status = false;
		List<WebElement> list = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_CARD_CSS);
		if (list.size() == 1) {
			ob.findElement(By.cssSelector((OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_CARD_CANCEL_BUTTON_CSS.toString())))
					.click();
			status = true;
		}
		return status;
	}

	public String getGroupOwnerDetailsFromCreateGroupCard() throws InterruptedException {
		BrowserWaits.waitTime(6);
		WebElement groupRecord = ob
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUP_CARD_CSS.toString()));
		String name = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).getText();
		logger.info("User Name : " + name);
		String role = groupRecord
				.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_ROLE_CSS.toString())).getText();
		logger.info("User role : " + role);
		groupRecord.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPSLIST_GROUP_OWNER_NAME_CSS.toString())).click();
		return name.trim() + ", " + role.trim();
	}

	public void sortByCreationDate() throws Exception {
		List<String> list = new ArrayList();

		selectSortoptions("Creation date");
		List<WebElement> li = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUPS_TILE_DATE_CSS.toString()));
		for (WebElement we : li) {
			String val = we.getText().substring(7);
			System.out.println("date value" + val);
			list.add(val);

		}
		Collections.sort(list);

	}
	public void ValidateEditDescLessThan500(ExtentTest test) throws Exception {
		int GroupLength = ob.findElement(By.xpath(OnePObjectMap.RCC_GROUP_DESCRIPTION_XPATH.toString())).getText()
				.length();
		if (GroupLength <= 500) {

			test.log(LogStatus.PASS,
					" user is able to edit group description with <= 500 characters from group details page.");
		}

		else {

			test.log(LogStatus.FAIL,
					"user is not able to edit group description with <= 500 characters from group details page.");
		}
	}
	public void ValidateEditDescMoreThan500(int actualLength, int Desc500, ExtentTest test) throws Exception {
		try {
			Assert.assertNotEquals(actualLength, Desc500);

			if (actualLength < Desc500) {
				test.log(LogStatus.PASS,
						" user is not able to edit group with more than 500 characters in group description.");
			}

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "user is  able to edit group with more than 500 characters in group description.");// extent
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
					.getSimpleName()
					+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
			ErrorUtil.addVerificationFailure(t);

		}

	}

	public void ValidateEditDescMoreThan50(int actualLength, int Desc500, ExtentTest test) throws Exception {
		try {
			Assert.assertNotEquals(actualLength, Desc500);

			if (actualLength < Desc500) {
				test.log(LogStatus.PASS,
						" user is not able to edit group with more than 50 characters in group description.");
			}

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "user is  able to edit group with more than 50 characters in group description.");// extent
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
					.getSimpleName()
					+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
			ErrorUtil.addVerificationFailure(t);

		}

	}

	public void ValidateUpdatedTitle(String updatedTitleupdatedTitle, ExtentTest test) {
		waitForAllElementsToBePresent(ob, By.xpath(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH.toString()), 60);

		String updatedTitle = ob.findElement(By.xpath(OnePObjectMap.RCC_DESC_MSG_TEXT_XPATH.toString())).getText();
		try {
			if (updatedTitle.equalsIgnoreCase(updatedTitle)) {
				test.log(LogStatus.PASS,
						" user is able to edit the group with group name of 2 character and without any description from group details page.");
			} else {
				test.log(LogStatus.FAIL,
						"user is not able to edit the group with group name of 2 character and without any description from group details page.");// extent

			}

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL,
					"user is not able to edit the group with group name of 2 character and without any description from group details page.");

		}
	}
	public void ValidateTitle(String titleUpdate, ExtentTest test) {
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_TITLE_CSS.toString()), 60);

		String updatedTitle = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_TITLE_CSS.toString())).getText();
		try {
			if (updatedTitle.equalsIgnoreCase(updatedTitle)) {
				test.log(LogStatus.PASS,
						"Save' button closed the form and  updated group details are available in view mode.");
			} else {
				test.log(LogStatus.FAIL,
						"user is not able to edit the group with group name of 2 character and without any description from group details page.");// extent

			}

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL,
					"user is not able to edit the group with group name of 2 character and without any description from group details page.");

		}

	}

	public void ValidateTitleCancel(String titleUpdate, ExtentTest test) {
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_TITLE_CSS.toString()), 60);

		String updatedTitle = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_TITLE_CSS.toString())).getText();
		try {
			if (updatedTitle.equalsIgnoreCase(updatedTitle)) {
				test.log(LogStatus.PASS,
						"'Cancel' button deletes all modified information and show the group details in view mode.");
			} else {
				test.log(LogStatus.FAIL,
						"'Cancel' button didn't deletes all modified information and show the group details in view mode.");// extent

			}

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "user is not able to click cancel button");

		}
	}
	public void sortByMostRecentActivity() throws Exception
	 {
		 List<Date> beforesort=new ArrayList<Date>();
	    List<WebElement> li= ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUPS_TILE_DATE_CSS.toString()));
	    for(WebElement  we:li)
	    {
	    	String val=we.getText().substring(7);
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMMMMMM yyyy");
	    	 Date date = formatter.parse(val);
	    	 System.out.println("Before sort value"+val);
	    	 beforesort.add(date);
	    	
	    }
	    Collections.sort(beforesort);
	    selectSortoptions("Most recent activity");
	    List<Date> aftersort=new ArrayList<Date>();

	    List<WebElement> list=ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_GROUPS_TILE_DATE_CSS.toString()));
	    for(WebElement ww:list)
	    {

	    	String val=ww.getText().substring(7);
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMMMMMM yyyy");
	    	 Date date1 = formatter.parse(val);
	    	 System.out.println("After date value"+val);
	    	 aftersort.add(date1);
	    }
	    if(beforesort.equals(aftersort))
	    {
	    	test.log(LogStatus.PASS,"Sorting is done correctly");
	    }
	
	     
	    throw new Exception("Sorted is not done properly");
	 
	 }
	
	public boolean compareList(List<Date> l1,List<Date> l2) throws Exception
	{
		if(l1.equals(l2))
			return true;
		else
			return false;
	}
	
	public void randomUpdate(int val,String title) throws Exception{
		 waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_TITLE_CSS.toString()),30);
		 List<WebElement> list=ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_LIST_PAGE_TITLE_CSS.toString()));
		 if(val<=list.size())
		 {
			 list.get(val).click();
			 pf.getGroupDetailsPage(ob).clickOnEditButton();
			 pf.getGroupDetailsPage(ob).updateGroupTitle(title);
			 pf.getGroupDetailsPage(ob).clickOnSaveButton();
		 }
		 else
			 throw new Exception("Group title is not updated randomly");
	}

	public boolean isCoverPhotoDisplayed() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_NEWLY_CREATED_GROUP_PHOTO_CSS);
		WebElement image1 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_NEWLY_CREATED_GROUP_PHOTO_CSS);
		Boolean ImagePresent = image1.isDisplayed();
		return ImagePresent; 
		
	}

	
	
}
