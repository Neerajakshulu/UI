package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.OnePObjectMap;

/**
 * This class contains all the method related to account page
 * 
 * @author uc205521
 *
 */
public class GroupsPage extends TestBase {

	PageFactory pf;

	public GroupsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();//
	}

	public void clickOnGroupsLink() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_GROUPS_LINK__CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_GROUPS_LINK__CSS);
		waitForAjax(ob);
	}
	
	public void clickOnGroupsTab() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_GROUPTAB_HEADER_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_GROUPTAB_HEADER_CSS);
		
	}
	
	public void clickOnCreateNewGroupButton() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_CREATE_NEW_GROUP_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUP_CREATE_NEW_GROUP_BUTTON_CSS);
		
	}

	public int getGroupsCount() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_GROUPS_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUP_GROUPS_COUNT_CSS);
		int groupsCount = Integer.parseInt(count.getText());
		return groupsCount;
	}

	public int getInvitationsCount() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_INVITATIONS_COUNT_CSS);
		WebElement count = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.RCC_GROUP_INVITATIONS_COUNT_CSS);
		int invitationsCount = Integer.parseInt(count.getText());
		return invitationsCount;
	}
	
	

}
