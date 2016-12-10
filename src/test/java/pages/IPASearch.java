package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

public class IPASearch extends TestBase {

	PageFactory pf;

	public IPASearch(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}
	
	public boolean loginToIPA(String username,String password) throws Exception{
	       WebElement Element=null;
	       pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_USERNAME_CSS);
	       pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_USERNAME_CSS, username);
	       pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_PASSWORD_CSS, password);
	       pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_SIGNIN_CSS);
	       pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_IPA_SIGNIN_CSS);
	       try{
	              Element=      ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_IPA_LINK_CSS.toString()));
	       }catch(Exception ex){
	       }
	       return Element!=null;
	       
	}
	
	/**
	 * Following block verifying the Following items
	 *  - User must have a text search box unique to Technology
	 *  - User must be able to search for one or more technology terms
	 *  - User must view a instructional text 
	 * @author uc209280
	 *
	 */
	public void validateTechnologySearch(ExtentTest test) throws Exception {
		ob.navigate().refresh();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS);
		WebElement searchTextbox = ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS.toString()));
		String searchWatermark = searchTextbox.getAttribute("placeholder");
		
		if(searchTextbox.isDisplayed() && searchTextbox.isEnabled()){
			test.log(LogStatus.PASS, "Search text box found in technology page");
		}else{
			test.log(LogStatus.FAIL, "Search text box not found in technology page");
			throw new Exception("Search text box not found in technology page");
			
		}
		
		if(searchWatermark.equals("Enter a technology you're interested in (eg. laser eye surgery)")){
			test.log(LogStatus.PASS, "Water mark is found in the search text box");
			
		}else{
			test.log(LogStatus.FAIL, "Search text box not found in technology page");
			throw new Exception("Search text box not found in technology page");			
		}
		
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS, "carbon");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_SEARCHSUGGESTIONS_XPATH);
		List<WebElement> searchTerms = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.NEON_IPA_TECH_SEARCHSUGGESTIONS_XPATH);
		
		for(WebElement searchTerm:searchTerms){
			searchTerm.click();
			BrowserWaits.waitTime(2);
		}
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_CLIPBOARD_CSS);
		
		List<WebElement> addedTerms = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.NEON_IPA_TECH_CLIPBOARD_CSS);
			
		if(searchTerms.size() == addedTerms.size()){
			test.log(LogStatus.PASS, "Search terms added to clipboard successfully");
		}else{
			test.log(LogStatus.FAIL, "Incorrect number of Search terms are added to clipboard. " + searchTerms.size() + " terms should be added but added " + addedTerms.size());
			logFailureDetails(test, "Incorrect number of Search terms are added to clipboard", "Incorrect number of Search Terms");
		}
		
			
	}
	
	public void validateCompanySearch(ExtentTest test) throws Exception {
		ob.navigate().refresh();
		IPALandingPage();
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_COMPANYSEARCH_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS);
		WebElement searchTextbox = ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS.toString()));
		String searchWatermark = searchTextbox.getAttribute("placeholder");
		
		if(searchTextbox.isDisplayed() && searchTextbox.isEnabled()){
			test.log(LogStatus.PASS, "NEON-574 - Search text box found for company search");
		}else{
			test.log(LogStatus.FAIL, "NEON-574 - Search text box not found for company search");
			throw new Exception("NEON-574 - Search text box not found for company search");
			
		}
		
		if(searchWatermark.equals(OnePObjectMap.NEON_IPA_COMPANYWATERMARK_CSS.toString())){
			test.log(LogStatus.PASS, "NEON-574 - Water mark is found in the search text box --> " + OnePObjectMap.NEON_IPA_COMPANYWATERMARK_CSS.toString());
			
		}else{
			test.log(LogStatus.FAIL, "NEON-574 - Search text box not found in technology page");
			throw new Exception("NEON-574 - Search text box not found in technology page");			
		}
		
		
		List<String> termsAdded = new ArrayList<String>();
		
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS, "carbon");
		termsAdded = addCompanyTerms("1&&2:3");
		
		List<WebElement> clipboardTerms = ob.findElements(By.cssSelector(OnePObjectMap.NEON_IPA_COMPANYCLIPBOARD_CSS.toString()));
		
		if(clipboardTerms.size() == termsAdded.size() ){
			for(int i=0; i<clipboardTerms.size(); i++){
				if(termsAdded.get(i).equals(clipboardTerms.get(i).getText())){
					test.log(LogStatus.PASS, "NEON-574 - One or more terms added successfully to clipboard");
				}else{
					test.log(LogStatus.FAIL, "NEON-574 - Terms are not matching");
					System.out.println();
					throw new Exception("NEON-574 - Terms are not matching");
				}
			}
			
		}else{
			test.log(LogStatus.FAIL, "NEON-574 - Wrong number of terms added to clipboard. Expected " +  termsAdded.size() + " Actual " + clipboardTerms.size());
			throw new Exception("NEON-574 - Wrong number of terms added to clipboard");	
		}
			
	}
	
	public void validateNewSearchLandingPage(ExtentTest test) throws Exception {
		
		//Initializing the test
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_NEWSEARCH_XPATH);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_TECH_NEWSEARCH_XPATH);
		
		performTechnologySearch();
		BrowserWaits.waitTime(2);
		returntoNewSearchLandingPage();
		
		WebElement selection = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_IPA_TECHHIGHLIGHT_XPATH);
		
		if(!(selection == null)){
			test.log(LogStatus.PASS, "NEON-400 - Technology Search is highlighted");
		}else{
			test.log(LogStatus.FAIL, "NEON-400 - Technology Search is not highlighted");
			throw new Exception("NEON-400 - Technology Search is not highlighted");	
		}
		
		performCompanySearch();
		//pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECHPORTFOLIO_CSS);
		returntoNewSearchLandingPage();
		selection = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_IPA_COMPANYHIGHLIGHT_XPATH);
			
		if(!(selection == null)){
			test.log(LogStatus.PASS, "NEON-400 - Company Search is highlighted");
		}else{
			test.log(LogStatus.FAIL, "NEON-400 - Company Search is not highlighted");
			throw new Exception("NEON-400 - sCompany Search is not highlighted");	
		}
	}
	
	public void validateIPAnalyticsLandingPage(ExtentTest test) throws Exception {
	
		performTechnologySearch();
		BrowserWaits.waitTime(2);
		IPALandingPage();
		
		WebElement selection = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_IPA_TECHHIGHLIGHT_XPATH);
		
		if(!(selection == null)){
			test.log(LogStatus.PASS, "NEON-438 - Technology Search is highlighted");
		}else{
			test.log(LogStatus.FAIL, "NEON-438 - Technology Search is not highlighted");
			throw new Exception("NEON-438 - Technology Search is not highlighted");	
		}
		
		performCompanySearch();
		BrowserWaits.waitTime(5);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_TECHPORTFOLIO_CSS);
		IPALandingPage();
		//BrowserWaits.waitTime(4);
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECHHIGHLIGHT_XPATH);
		selection = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_IPA_TECHHIGHLIGHT_XPATH);
		
		if(!(selection == null)){
			test.log(LogStatus.PASS, "NEON-438 - Technology Search is highlighted");
		}else{
			test.log(LogStatus.FAIL, "NEON-438 - Technology Search is not highlighted");
			throw new Exception("NEON-438 - Technology Search is not highlighted");	
		}
	}
	
	
	
	public void performTechnologySearch() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECHNOLOGYSEARCH_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_TECHNOLOGYSEARCH_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS, "carbon");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_EXPLOREBUTTON_XPATH);
		
	}
	
	public void performCompanySearch() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_COMPANYSEARCH_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_COMPANYSEARCH_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_TECH_SEARCHBOX_CSS, "Thomson");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_EXPLOREBUTTON_XPATH);
		
	}
	
	public void returntoNewSearchLandingPage() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_TECH_NEWSEARCH_XPATH);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_TECH_NEWSEARCH_XPATH);
		
	}
	
	
	public void IPALandingPage() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_HEADER_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_HEADER_CSS);
		
	}
	
    public List<String> addCompanyTerms(String hierarchy) throws Exception {

        String Parent = "li[index='$Parent']";// >ul>li:nth-of-type(2)>div
        String Child = ">ul>li:nth-of-type($Child)";
        String[] Company = hierarchy.split("&&");
        List<String> termsAdded = new ArrayList<String>();
        for (int x = 0; x < Company.length; x++) {
                        String[] Index = Company[x].split(":");
                        String Xpath = "";
                        String Xpath1="";
                        if (Index.length > 0) {
                                        Xpath = Parent.replace("$Parent", "" + (Integer.valueOf(Index[0]) - 1));
                                        for (int i = 1; i < Index.length; i++) {
                                                        Xpath = Xpath + Child.replace("$Child", Index[i]);
                                        }
                                        Xpath1 = Xpath + ">div";
                                        Xpath = Xpath + ">div>button";
                        }                        
                        pf.getBrowserActionInstance(ob).jsClick(ob.findElement(By.cssSelector(Xpath)));
                        String termAdded = ob.findElement(By.cssSelector(Xpath1)).getText();
                        termsAdded.add(termAdded.substring(0, termAdded.indexOf("(")-1));
                        
        }
        return termsAdded;
    }
    
    	
}
