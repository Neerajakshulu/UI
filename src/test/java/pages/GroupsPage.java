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
		pf = new PageFactory();
	}

	public void clickOnGroupsTab() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_GROUPTAB_HEADER_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_GROUPTAB_HEADER_CSS.toString())).click();
	}
	
	public void clickOnCreateNewGroupButton() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_CREATE_NEW_GROUP_BUTTON_CSS.toString()),
				30);
		ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_CREATE_NEW_GROUP_BUTTON_CSS.toString())).click();
	}

	public int getGroupsCount() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_GROUPS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_GROUPS_COUNT_CSS.toString()));
		int groupsCount = Integer.parseInt(count.getText());
		return groupsCount;
	}

	public int getInvitationsCount() {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUP_INVITATIONS_COUNT_CSS.toString()), 30);
		WebElement count = ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUP_INVITATIONS_COUNT_CSS.toString()));
		int invitationsCount = Integer.parseInt(count.getText());
		return invitationsCount;
	}
	
	

}
