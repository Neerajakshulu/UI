package test;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MustNotOccur {

	public static void main(String[] args) throws Exception{
		
		String email="amneet.singh@thomsonreuters.com";
		String password="Transaction@2";
		String search_query="cat -dog goat";
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\UC201214\\Desktop\\project\\workspace\\OneP-ui-automation\\drivers\\chromedriver.exe");
		WebDriver ob=new ChromeDriver();
		
//		WebDriver ob=new FirefoxDriver();
		ob.manage().deleteAllCookies();
		ob.manage().window().maximize();
		
		ob.get("https://dev-stable.1p.thomsonreuters.com/ui/demo/#/login");
		
		//login using TR credentials
		ob.findElement(By.xpath("//button[@class='btn webui-btn-primary unauth-login-btn']")).click();
		ob.findElement(By.id("userid")).sendKeys(email);
		ob.findElement(By.id("password")).sendKeys(password);
		ob.findElement(By.id("ajax-submit")).click();
		
		//Type into the search box and get search results
		ob.findElement(By.xpath("//input[@type='text']")).sendKeys(search_query);
		ob.findElement(By.xpath("//button[@class='projectne-search-btn']")).click();
		
		//Put the urls of all the search results documents in a list and test whether documents contain searched keywork or not
		List<WebElement> searchResults=ob.findElements(By.xpath("//a[@class='searchTitle ng-binding']"));
		ArrayList<String> urls=new ArrayList<String>();
		for(int i=0;i<searchResults.size();i++){
			
			urls.add(searchResults.get(i).getAttribute("href"));
		}
		boolean condition1;
		String pageText;
		ArrayList<Integer> error_list=new ArrayList<Integer>();
		int count=0;
		for(int i=0;i<urls.size();i++){
			
			ob.navigate().to(urls.get(i));
			ob.findElement(By.xpath("//a[contains(text(),'Details')]")).click();
			pageText=ob.getPageSource().toLowerCase();
			condition1=!(pageText.contains("dog"));
			System.out.println(condition1);
			if(condition1){
				
				count++;
			}
			else
			{
				
				error_list.add(i+1);
			}
			
			
			
		}
		
		
		
		if(count==urls.size())
			System.out.println("MUST NOT OCCUR rule working correctly");
		else
		{
			System.out.println("MUST NOT OCCUR rule not working correctly");
			System.out.println("Issues are in the following documents");
			for(int i=0;i<error_list.size();i++){
				
				System.out.println(error_list.get(i));
			}
		}
		ob.quit();
		
	}
}
