package Profile;

import static com.jayway.restassured.RestAssured.given;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import base.TestBase;

public class OnboardingModalsTest extends TestBase {
	
	private static final String COLON = ":";
	private static final String HTTP = "http://";
	private static final String EUREKA_APP_NAME = "name";
	private static final String EUREKA_HOST_NAME = "hostName";
	private static final String EUREKA_IP_ADDRESS = "ipAddr";
	private static final String EUREKA_HOST_PORT = "port";
	private static final String EUREKA_VIP_ADDRESS = "vipAddress";
	private static final String EUREKA_DC_NAME = "Amazon";
	protected Map<String, String> appHosts = new HashMap<String, String>();	
	
	/**
	 * Read host names across all applications for the given environment and load them to map.
	 * 
	 * @param env environment for which the tests connect
	 * @throws Exception
	 */
	protected void getAllAppHostsForGivenEnv(String eurekaURL,
			String env,
			String local) throws Exception {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		String appName = null;
		String hostName = null;
		String port = null;

		URL url = new URL(eurekaURL);
		URLConnection conn = url.openConnection();

		XMLEventReader eventReader = inputFactory.createXMLEventReader(conn.getInputStream());

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			// reach the start of an item
			if (event.isStartElement()) {

				StartElement startElement = event.asStartElement();

				// Get app name
				if (startElement.getName().getLocalPart().equals(EUREKA_APP_NAME)) {
					event = eventReader.nextEvent();
					if (!event.asCharacters().getData().equalsIgnoreCase(EUREKA_DC_NAME))
						appName = event.asCharacters().getData();
				}

				// Get IP Address
				if (startElement.getName().getLocalPart().equals(EUREKA_HOST_NAME) && local.equalsIgnoreCase("Y")) {
					event = eventReader.nextEvent();
					hostName = event.asCharacters().getData();
				}

				if (startElement.getName().getLocalPart().equals(EUREKA_IP_ADDRESS) && local.equalsIgnoreCase("N")) {
					event = eventReader.nextEvent();
					hostName = event.asCharacters().getData();
				}

				// Get port
				if (startElement.getName().getLocalPart().equals(EUREKA_HOST_PORT)) {
					event = eventReader.nextEvent();
					port = event.asCharacters().getData();
				}

				// Get vip address
				if (startElement.getName().getLocalPart().equals(EUREKA_VIP_ADDRESS)) {
					event = eventReader.nextEvent();
					if (event.asCharacters().getData().endsWith(env))
						appHosts.put(appName, HTTP + hostName + COLON + port);
				}

			}
		}
	}

	
	/**
	 * Method for set onboarding status is false to get onboarding modals
	 * @param truid
	 * @throws Exception, When onboarding status is not getting changed
	 */
	@BeforeClass
	@Parameters("truid")
	public void setOnboardingStatus(String truid) throws Exception {
		String local = null;
		if (StringUtils.isNotBlank(System.getenv("SELENIUM_BROWSER"))) {
			local = "N";
		}else{
			local = "Y";
		}
		getAllAppHostsForGivenEnv("http://eureka.us-west-2.dev.oneplatform.build:8080/v2/apps", "1p.stable.dev", local);
		
		RequestSpecification reqSpec = given();
		Response resp;
		Map<String, String> hm= new HashMap<String, String>();
		hm.put("X-1P-User",truid);
		hm.put("Content-Type","application/json");
		reqSpec.headers(hm);
		reqSpec.body("{\"truid\":\""+truid+"\",\"onboarded\":\"false\"}");
		logger.info("host name-->"+appHosts.get("1PPROFILE"));
		resp = reqSpec.when().put(appHosts.get("1PPROFILE")+"/users/user/"+truid+"/status/onboarded");
		String jsonResponse = resp.asString();
		logger.info("response-->"+jsonResponse);
		JsonPath jsonPath = new JsonPath(jsonResponse);
		String onboarding=jsonPath.getString("onboarded");
		logger.info("onboarding status-->"+onboarding);
	}
}
