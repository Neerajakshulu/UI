package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserWaits;
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
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS);
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

	public void switchToInvitationTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_INVITATIONLINK_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_INVITATIONLINK_CSS);
		waitForAjax(ob);
	}

	public void switchToGroupTab() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPLINK_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPLINK_CSS);
		waitForAjax(ob);
	}

	public int countGroupsTabOverlay() throws InterruptedException {
		int i = 0;
		BrowserWaits.waitTime(2);
		List<WebElement> element = ob
				.findElements(By.cssSelector(OnePObjectMap.RCC_COUNT_GROUP_OVERLAY_CSS.toString()));
		if (element.size() == 1) {
			String countGroupsTabOverlay = element.get(0).getText();
			int countGroupsTabOverlay1 = Integer.parseInt(countGroupsTabOverlay);
			i = countGroupsTabOverlay1;
		}
		return i;
	}

	public String defaultselectedTab() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.RCC_DEFAULT_SELECTED_INVITATION_TAB_CSS);
		WebElement element = ob
				.findElement(By.cssSelector(OnePObjectMap.RCC_DEFAULT_SELECTED_INVITATION_TAB_TEXT_CSS.toString()));
		String str = element.getText();
		return str;
	}

	public void acceptInvitation() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPINVITATIONS_ACCEPT_INVITAION_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPINVITATIONS_ACCEPT_INVITAION_BUTTON_CSS);
	}

	public void clickOnGroupName(String group_title) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUP_TITLE_CSS);
		List<WebElement> element = ob.findElements(By.cssSelector(OnePObjectMap.RCC_GROUP_TITLE_CSS.toString()));
		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).getText().equals(group_title)) {
				element.get(i).click();
			}
		}

	}
}
