package util;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			extent = new ExtentReports(filePath, true);
			extent.loadConfig(new File("src/test/resources/extent.xml"));
			if(System.getProperty("host").contains("stable")) {
				extent.addSystemInfo("Environment", "Dev-Stable");
			} else if (System.getProperty("host").contains("snapshot")){
				extent.addSystemInfo("Environment", "Dev-Snapshot");
			} else {
				extent.addSystemInfo("Environment", "Production");
			}
		}

		return extent;
	}

	public synchronized static ExtentReports getReporter() {
		return extent;
	}
}