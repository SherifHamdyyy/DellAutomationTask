package tests;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import utilities.ExpectedDataDriven;
import utilities.Helper;

public class TestBase {
	public static WebDriver driver;
	public static ExtentReports extentreport;
	public static ExtentTest logger;
	static String FailureReason_main = "";

	// Locate report file
	String ReportfolderPath = System.getProperty("user.dir") + "/TestReport/Report" + Helper.getCurrentTime() + ".html";

	// Get into the report the desired configuration - Path,Operating System,Web
	// Browser,Programming Language
	@BeforeSuite
	protected void Start() {
		/// Generate extent report within the folder path
		extentreport = new ExtentReports(ReportfolderPath, true);
		extentreport.addSystemInfo("OS: ", ExpectedDataDriven.OS);
		extentreport.addSystemInfo("Browser: ", ExpectedDataDriven.browserName);
		extentreport.addSystemInfo("Language: ", "JAVA");
	}

	// End of the report log
	@AfterSuite
	protected void End() {
		extentreport.flush();
	}

	// Open the driver with the desired capabilities according to the test-data
	// configuration
	@BeforeClass()
	protected void startDriver() throws InterruptedException {

		if (ExpectedDataDriven.OS.equalsIgnoreCase("windows")) {

			if (ExpectedDataDriven.browserName.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				driver = new ChromeDriver();

			}

			else if (ExpectedDataDriven.browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}

		} else if (ExpectedDataDriven.OS.equalsIgnoreCase("mac")) {

			if (ExpectedDataDriven.browserName.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver_mac");
				driver = new ChromeDriver();

			}

			else if (ExpectedDataDriven.browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/geckodriver_mac");
				driver = new FirefoxDriver();
			}

		} else if (ExpectedDataDriven.OS.equalsIgnoreCase("linux")) {

			if (ExpectedDataDriven.browserName.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/drivers/chromedriver_linux");
				driver = new ChromeDriver();

			}

			else if (ExpectedDataDriven.browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/drivers/geckodriver_linux");
				driver = new FirefoxDriver();
			}
		}
	}

	// Close the driver
	@AfterClass
	protected void exitDriver() {

		driver.quit();

	}

	// Take Screen-shot when test case fails and add it to the screen-shot
	// folder
	// and log extent report status
	@AfterMethod
	protected static void screenshotOnFailure(ITestResult result) throws InterruptedException {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				try {
					System.out.println("Failed! Taking Screenshot....");
					Helper.captureFAIL_Screenshot(driver, result.getName().concat(" [Failed]"));
					logger.log(LogStatus.FAIL, "Test Fail : " + FailureReason_main);

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Failed to add screenshot in the Report");
				}

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				logger.log(LogStatus.PASS, "Test Pass ");
			} else {
				logger.log(LogStatus.SKIP, "Test Skipped");

			}

		} catch (Exception e) {
			System.out.println("Exception in screenShotOn Fail Method !");
		}
	}

	// Get the test-case name before each execution for the report
	@BeforeMethod
	protected static void setLogs(Method method) {
		logger = extentreport.startTest(method.getName());

	}

	protected void OpenURL(String url_txt) {
		driver.manage().window().setSize(new Dimension(1024, 768));
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.navigate().to(url_txt);
		// Helper.sleepThreeSeconds();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}
	protected void reportLog(String description,String actual,String expected)
	{
		String FailureReason = "";
		// In-case of failure, Write into the report
				FailureReason = " Actual"+ description+": [ " + actual + " ] & Expected: [ " + expected+ " ]";
				FailureReason_main = FailureReason;
	}

}
