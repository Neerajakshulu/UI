package pages;

import java.util.ArrayList;
	import java.util.HashSet;
	import java.util.List;
	import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;

	import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

	import base.TestBase;
	import util.BrowserAction;
	import util.BrowserWaits;
	import util.OnePObjectMap;

	public class DashboardPage extends TestBase {

	                PageFactory pf;
	                BrowserWaits browserWait;
	                BrowserAction browserAction;

	                public DashboardPage(WebDriver ob) {
	                                this.ob = ob;
	                                pf = new PageFactory();
	                                browserWait = new BrowserWaits(ob);
	                                browserAction = new BrowserAction(ob);

	                }

	                public void SearchTermEnter(String searchType, String searchTerm) throws Exception {
	                                boolean switched = false;
	                                if (searchType.equalsIgnoreCase("company")) {
	                                                if (!ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS.toString() + ">hr"))
	                                                                                .getAttribute("class").contains("__active")) {
	                                                                browserAction.jsClick(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS);
	                                                                switched = ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_COMPANY_LINK_CSS.toString() + ">hr"))
	                                                                                                .getAttribute("class").contains("__active");
	                                                } else
	                                                                switched = true;
	                                } else {
	                                                if (!ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS.toString() + ">hr"))
	                                                                                .getAttribute("class").contains("__active")) {
	                                                                browserAction.jsClick(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS);
	                                                                switched = ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_TECHNOLOGY_LINK_CSS.toString() + ">hr"))
	                                                                                                .getAttribute("class").contains("__active");
	                                                } else
	                                                                switched = true;
	                                }
	                                if (!switched)
	                                                throw new Exception("Desired Seach Type Not selected");
	                                browserAction.clickAndClear(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS);
	                                browserAction.enterFieldValue(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS, searchTerm);
	                                browserWait.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SUGGESTION_COINTAINER_CSS);
	                }

	                // 1:2:2&&1:1:2:2
	                public List<String> addCompanyTerms(String hierarchy) throws Exception {
	                	List<String> comapanyName=new ArrayList<>();
	                                String Parent = "li[index='$Parent']";// >ul>li:nth-of-type(2)>div
	                                String Child = ">ul>li:nth-of-type($Child)";
	                                String[] Company = hierarchy.split("&&");
	                                for (int x = 0; x < Company.length; x++) {
	                                                String[] Index = Company[x].split(":");
	                                                String Xpath = "";
	                                                if (Index.length > 0) {
	                                                                Xpath = Parent.replace("$Parent", "" + (Integer.valueOf(Index[0]) - 1));
	                                                                for (int i = 1; i < Index.length; i++) {
	                                                                                Xpath = Xpath + Child.replace("$Child", Index[i]);
	                                                                }
	                                                                Xpath = Xpath + ">div";
	                                                }
	                                                comapanyName.add(ob.findElement(By.cssSelector(Xpath)).getText());
	                                                //BrowserWaits.waitTime(5);
	                                                browserAction.jsClick(ob.findElement(By.cssSelector(Xpath+">button")));
	                                }
	                                return comapanyName;

	                }

	                public void addTechnologyTerms(String Term, int count, Boolean freetext, Boolean shollall) throws Exception {
	                                String suggestiontext = OnePObjectMap.NEON_IPA_TECH_SUG_TEXT_VAR_CSS.toString();
	                                String hits = OnePObjectMap.NEON_IPA_TECH_SUG_HITS_VAR_CSS.toString();
	                                String add = OnePObjectMap.NEON_IPA_TECH_SUG_ADD_VAR_CSS.toString();
	                                int added = 0;

	                                if (!Term.isEmpty() || (count > 0 && count < 8)) {
	                                                for (int i = 1; i < 8; i++) {

	                                                                String row = suggestiontext.replace("$index", String.valueOf(i));
	                                                                String button = add.replace("$index", String.valueOf(i));

	                                                                if (!Term.isEmpty()) {
	                                                                                if (ob.findElement(By.cssSelector(row)).getText().trim().equalsIgnoreCase(Term.trim())) {
	                                                                                                ob.findElement(By.cssSelector(button)).click();
	                                                                                                break;
	                                                                                }
	                                                                } else {
	                                                                                ob.findElement(By.cssSelector(button)).click();
	                                                                                added++;
	                                                                                if (added == count)
	                                                                                                break;
	                                                                }

	                                                }
	                                } else if (freetext) {
	                                                String row = suggestiontext.replace("$index", String.valueOf(0));
	                                                String button = add.replace("$index", String.valueOf(0));
	                                                ob.findElement(By.cssSelector(button)).click();
	                                }

	                                if (shollall)
	                                                browserAction.jsClick(OnePObjectMap.NEON_IPA_SHOW_ALL_LINK_XPATH);

	                }

	public void exploreSearch() throws Exception {
		browserAction.jsClick(OnePObjectMap.NEON_IPA_EXPLORE_BUTTON_CSS);

		browserWait.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_PATENTS_FOUND_CNT_CSS);
		try {
			ob.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_DASH_PATENTS_FOUND_CNT_CSS.toString()));
		} catch (Exception e) {
			throw new Exception("Explore not working");
		}
	}

	public boolean checkForTextInSearchTermList(String text) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERMS_LABEL_CSS.toString()), 60);
		List<WebElement> labelsList = ob.findElements(By.cssSelector(
				OnePObjectMap.NEON_IPA_SEARCH_TERMS_CSS.toString() + " " + OnePObjectMap.NEON_IPA_SEARCH_TERMS_LABEL_CSS.toString()));
		if (text.contains("("))
			text = text.substring(0, text.indexOf("(")).trim();
		System.out.println(text);
		for (WebElement we : labelsList) {

			if (we.getText().trim().equalsIgnoreCase(text)) {
				return true;
			}
		}
		return false;
	}

	public void selectSearchTermFromSuggestion(int index) {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.NEON_IPA_TECH_SUG_DROPDOWN_CSS.toString()), 60);
		ob.findElement(By.cssSelector(
				OnePObjectMap.NEON_IPA_TECH_SUG_ADD_VAR_CSS.toString().replace("$index", String.valueOf(index))))
				.click();
	}

	public void clickOnNewSearchLinkInHeader() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_TECH_NEW_SEARCH_HEADER_LINK_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_TECH_NEW_SEARCH_HEADER_LINK_CSS);
	}

	public WebElement getSearchItemElement(String serachTerm) throws Exception {
		if (serachTerm.contains("("))
			serachTerm = serachTerm.substring(0, serachTerm.indexOf("(")).trim();
		System.out.println(serachTerm);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERMS_CSS.toString()), 60);
		List<WebElement> labelsList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.NEON_IPA_SEARCH_TERMS_CSS);
		String text = "";
		for (WebElement we : labelsList) {
			text = we.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERMS_LABEL_CSS.toString())).getText();

			if (text.trim().equalsIgnoreCase(serachTerm)) {
				return we;
			}
		}
		throw new Exception("Search term not found in list");
	}

	public int getselectedSynonymsCount(String searchTerm) throws Exception {
		WebElement term = getSearchItemElement(searchTerm);

		List<WebElement> synms = term
				.findElements(By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERM_PRESELECTED_SYNMS_CSS.toString()));

		return synms.size();
	}

	// pass 0 to select all synonyms
	public List<String> selectSearchTermSynms(String searchTerm, int count) throws Exception {
		List<String> synmsList = new ArrayList<>();
		WebElement term = getSearchItemElement(searchTerm);
		if (count == 0)
			count = term.findElements(By.cssSelector("li")).size();

		List<WebElement> list = term.findElements(By.cssSelector("li"));
		WebElement checkBox;
		for (int i = 0; i <= count - 1; i++) {
			synmsList.add(list.get(i).getText());
			checkBox = list.get(i).findElement(By.cssSelector("input[type='checkbox']"));
			if (!checkBox.isSelected())
				checkBox.click();
		}
		return synmsList;
	}
	
	public List<String> validateSelectedSynonyms(String searchTerm,List<String> synms) throws Exception{
		List<String> synmsList=new ArrayList<>();
		WebElement term=getSearchItemElement(searchTerm);
		List<WebElement> list=term.findElements(By.cssSelector("li"));
		
		for (String str : synms) {
			for (WebElement we : list) {
				if (we.getText().equals(str)) {
					if (!we.findElement(By.cssSelector("input[type='checkbox']")).isSelected())
					{
						synmsList.add(str);
						break;
					}
					
				}

			}
		}
		return synmsList;
	}
	
	public void clickOnSearchTermDropDown(String searchTerm) throws Exception{
		WebElement term=getSearchItemElement(searchTerm);
		term.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERM_DROP_DOWN_ICON_CSS.toString())).click();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SEARCH_TERM_SYNMS_LIST_CSS);
	}
	
	public boolean checkIfSearchTermDropDownIsDispalyed(String searchTerm) throws Exception{
		WebElement term=getSearchItemElement(searchTerm);
		try{
		return term.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERM_DROP_DOWN_ICON_CSS.toString())).isDisplayed();
		}catch(Exception e){
			return false;
		}
	}
	
	public void removeSearchTerm(String searchTerm) throws Exception{
		WebElement term=getSearchItemElement(searchTerm);
		term.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SEARCH_TERMS_REMOVE_CSS.toString())).click();
	}
	
	public void deselectSearchTermSynms(String searchTerm,List<String> synms) throws Exception{
		WebElement term=getSearchItemElement(searchTerm);
		List<WebElement> list=term.findElements(By.cssSelector("li"));
		
		for (String str : synms) {
			for (WebElement we : list) {
				if (we.getText().equals(str)) {
					if (we.findElement(By.cssSelector("input[type='checkbox']")).isSelected()){
						we.findElement(By.cssSelector("input[type='checkbox']")).click();
						break;
					}					
				}
			}
		}
	}
	
	public void clickOnShowAllLinkInTypeAhead() throws Exception{
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_TECH_SHOW_ALL_LINK_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_TECH_SHOW_ALL_LINK_CSS);
	}
	
	public int validatePatentsCountForCompanyInTypeAhead() throws Exception {
		int count=0;
		boolean found=false;
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.NEON_IPA_COMPANY_TYPE_AHAED_LABEL_CSS.toString()), 60);
		
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.NEON_IPA_COMPANY_TYPE_AHAED_LABEL_CSS);
		Pattern pattern = Pattern.compile("\\([,\\d]+\\)");
		Matcher matcher;
		for(int i=0;i<list.size()-1;i++){
				
			matcher = pattern.matcher(list.get(i).getText());

			while (matcher.find()) {
				found=true;
				break;			
			}
			
			if(!found)count++;
		}
		return count;	
	}
	
	public void clickOnPatentFoundIcon() throws Exception {

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_ICON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_ICON_CSS);

	}
	
	public List<String> getAllPatentRecords() throws Exception {
		List<String> recordList = new ArrayList<>();
		loadAllPatentRecords();
		List<WebElement> patentList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.NEON_IPA_RECORD_LIST_PAGE_PATENT_TITLE_CSS);

		for (WebElement we : patentList) {

			recordList.add(we.getText().trim());
		}

		return recordList;
	}
	
	public void loadAllPatentRecords() throws InterruptedException{
		scrollAndWait();
		while(isSpinnerDisplayed()){
			
			scrollAndWait();
		}
		
	}
	
	private void scrollAndWait() throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor) ob;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(1000);
		
	}
	
	private boolean isSpinnerDisplayed() {
		try {
			return pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS)
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public int getPatentCount() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_COUNT_CSS);
		String count = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_COUNT_CSS).getText();
		count = count.replaceAll(",", "");
		return Integer.parseInt(count);

	}
}
