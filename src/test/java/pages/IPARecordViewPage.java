package pages;

import org.openqa.selenium.WebDriver;

import util.OnePObjectMap;
import base.TestBase;


public class IPARecordViewPage extends TestBase{
	
	public IPARecordViewPage(WebDriver ob){
		this.ob=ob;
		pf=new PageFactory();
	}
	
	public String getTitle() throws Exception{
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.IPA_RECORD_VIEW_PAGE_PATENT_TITLE_CSS);
		String title=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.IPA_RECORD_VIEW_PAGE_PATENT_TITLE_CSS).getText();
		return title;
		
	}
	

}
