package util;

import org.openqa.selenium.WebDriver;

import base.TestBase;
import pages.PageFactory;

public class Utility extends TestBase{
	public Utility(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}
	
	public void deleteGroup(String groupTitle) throws Exception{
		
		pf.getGroupsPage(ob).clickOnGroupsTab();
		pf.getGroupsPage(ob).switchToGroupTab();
		pf.getGroupsListPage(ob).clickOnGroupTitle(groupTitle);
		pf.getGroupDetailsPage(ob).clickOnDeleteButton();
		pf.getGroupDetailsPage(ob).clickOnDeleteButtonInConfirmationMoadl();
	}
}
