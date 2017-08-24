package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * Class for Perform Browser Actions
 * 
 * @author UC225218
 *
 */
public class SearchAuthorClusterPage extends TestBase {

	public SearchAuthorClusterPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	int k = 1;

	/**
	 * Common method to search for an author cluster with only LastName
	 * 
	 * @param LastName
	 * @author UC225218
	 * @throws Exception
	 * 
	 */
	public void SearchAuthorCluster(String LastName, String Country, String org, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorLastName(LastName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			BrowserWaits.waitTime(2);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
					.isEnabled()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			} else {
				throw new Exception("FIND button not clicked");
			}
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			if (ele.size() != 0) {
				selectCountryofAuthor(Country, test);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				List<WebElement> orgName = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				if (orgName.size() != 0) {
					selectOrgofAuthor(org, test);
				}
				test.log(LogStatus.INFO, "Org name selection is not required as the searched user has only one org. ");
			} else {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and land in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author cluster and land in Author search result page.");
			throw new Exception();
		}
	}

	/**
	 * Common method to search for an author cluster with LastName and FirstName
	 * 
	 * @throws @author
	 *             UC225218
	 * @param LastName
	 *            FirstName
	 * @throws Exception
	 */
	public void SearchAuthorCluster(String LastName, String FirstName, String Country, String org, ExtentTest test)
			throws Exception {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorLastName(LastName, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorFirstName(FirstName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");

			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
					.isEnabled()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			}
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			if (ele.size() != 0) {
				selectCountryofAuthor(Country, test);
				List<WebElement> orgName = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				if (orgName.size() != 0) {
					selectOrgofAuthor(org, test);
				}
				test.log(LogStatus.INFO, "Org name selection is not required as the searched user has only one org. ");
			} else {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and landed in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author and landed in Author search result page.");
			throw new Exception(e);
		}
	}

	/**
	 * Method to enter the author search name (Last name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorLastName(String LastName, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			for (int i = 0; i < LastName.length(); i++) {
				char c = LastName.charAt(i);
				String s = new StringBuilder().append(c).toString();
				pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, s);
				BrowserWaits.waitTime(0.5);
			}
			selectLastNameFromTypeahead(LastName, test);
		} catch (Exception e) {
			e.getMessage();
			test.log(LogStatus.FAIL, "Author Last name not entered");
		}
	}

	/**
	 * Method to enter the author search name (Last name) character by
	 * character. This method is special case for Alternate name typeahead
	 * search scenario.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorLastName(OnePObjectMap path, String LastName, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).getElement(path).clear();
			for (int i = 0; i < LastName.length(); i++) {
				char c = LastName.charAt(i);
				String s = new StringBuilder().append(c).toString();
				pf.getBrowserActionInstance(ob).enterFieldValue(path, s);
				BrowserWaits.waitTime(0.5);
			}
			selectLastNameFromTypeahead(LastName, test);
		} catch (Exception e) {
			e.getMessage();
			test.log(LogStatus.FAIL, "Author Last name not entered");
		}
	}

	/**
	 * Method to enter the author search name (First name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 * 
	 */
	public void enterAuthorFirstName(String FirstName, ExtentTest test) throws Exception {

		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, s);
			BrowserWaits.waitTime(0.5);
		}
		selectFirstNameFromTypeahead(FirstName, test);
		test.log(LogStatus.INFO, "Author First name entered");
	}

	/**
	 * Method to enter the author search name (First name) character by
	 * character. This method is special case for Alternate name typeahead
	 * search scenario.
	 * 
	 * @throws Exception
	 * @param Name
	 * 
	 */
	public void enterAuthorFirstName(OnePObjectMap path, String FirstName, ExtentTest test) throws Exception {

		pf.getBrowserActionInstance(ob).getElement(path).clear();
		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(path, s);
			BrowserWaits.waitTime(0.5);
		}
		selectFirstNameFromTypeahead(FirstName, test);
		test.log(LogStatus.INFO, "Author First name entered");
	}

	/**
	 * Method to select value from typeahead, This will loop for 5 times incase
	 * if typeahead suggestions are not displayed.(Configurable)
	 * 
	 * @throws Exception
	 * 
	 * @param lnTypeahead
	 *            selectionText
	 * 
	 */
	public void selectLastNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		while (k < 2) {
			List<WebElement> web = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH);
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
			List<WebElement> lastNameSuggestions = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_OPTION_XPATH);

			if (web.size() != 0) {
				for (int j = 0; j < lastNameSuggestions.size(); j++) {
					if (lastNameSuggestions.get(j).getText().equals(selectionText)) {
						lastNameSuggestions.get(j).click();
						test.log(LogStatus.INFO, "Typeahead is present for last name");
						test.log(LogStatus.INFO, "Author Last name entered");
						break;
					}
				}
				break;
			} else if (ele.size() == 0 && pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isDisplayed()) {
				test.log(LogStatus.INFO, "Author Last name entered");
				test.log(LogStatus.INFO,
						"No Typeahead displayed, Might be due to exact matching of provided last name with name in typeahead suggestion, Hence directly clicking FIND button");
				break;
			} else if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH)
					.isDisplayed()) {
				test.log(LogStatus.FAIL, "Entered name is incorrect, Please enter a valid last name ");
				break;
			} else {
				if (k == 2)
					throw new Exception("No Typeahead suggestion displayed even after 2 attempts");
				k++;
				enterAuthorLastName(selectionText, test);
			}
		}
	}

	/**
	 * Method to select value from typeahead, This will loop for 5 times incase
	 * if typeahead suggestions are not displayed.(Configurable)
	 * 
	 * @throws Exception
	 * 
	 * @param lnTypeahead
	 *            selectionText
	 * 
	 */
	public void selectFirstNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		while (k < 2) {
			List<WebElement> web = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH);
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
			List<WebElement> firstNameSuggestions = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_OPTION_XPATH);

			if (web.size() != 0) {
				for (int j = 0; j < firstNameSuggestions.size(); j++) {
					if (firstNameSuggestions.get(j).getText().equals(selectionText)) {
						firstNameSuggestions.get(j).click();
						test.log(LogStatus.INFO, "Typeahead is present for first name");
						test.log(LogStatus.INFO, "Author First name entered");
						break;
					}
				}
				break;
			} else if (ele.size() == 0 && pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isDisplayed()) {
				test.log(LogStatus.INFO, "Author Last name entered");
				test.log(LogStatus.INFO,
						"No Typeahead displayed, Might be due to exact matching of provided first name with name in typeahead suggestion, Hence directly clicking FIND button");
				break;
			} else if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH)
					.isDisplayed()) {
				test.log(LogStatus.FAIL, "Entered name is incorrect, Please enter a valid First name ");
				break;
			} else {
				if (k == 2)
					throw new Exception("No Typeahead suggestion displayed even after 2 attempts");
				k++;
				enterAuthorLastName(selectionText, test);
			}
		}
	}

	/**
	 * Method to select Country value for further filtering of author cluster.
	 * 
	 * @author UC225218
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectCountryofAuthor(String CountryName, ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString()), 3,
				"Country dropdown is not present in Author search page");
		try {
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			test.log(LogStatus.INFO,
					"Country name selected as the searched user resulted in more than 50 clusters... ");
			List<WebElement> ctry = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_OPTION_XPATH);
			if (ctry.size() != 0) {
				for (int j = 0; j < ctry.size(); j++) {
					if (ctry.get(j).getText().equals(CountryName)) {
						ctry.get(j).click();
						test.log(LogStatus.INFO, "Country name clicked");
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to select organization value for further filtering of author
	 * cluster.
	 * 
	 * @author UC225218
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectOrgofAuthor(String orgName, ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH, 5);
		try {
			test.log(LogStatus.INFO, "Selecting relavent organization... ");
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
			List<WebElement> org = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_ORG_OPTION_XPATH);
			if (org.size() != 0) {
				for (int j = 0; j < org.size(); j++) {
					if (org.get(j).getText().equals(orgName)) {
						org.get(j).click();
						test.log(LogStatus.INFO, "Org name clicked");
						break;
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
