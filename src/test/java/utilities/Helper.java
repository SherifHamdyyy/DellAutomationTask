package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import tests.TestBase;

public class Helper extends TestBase {

	// Method to take screenshot when Test cases Fail
	public static void captureFAIL_Screenshot(WebDriver driver, String screenshotname) {
		// Save the capture screen shot into the destination created folder with
		// the
		// name of the test case and date
		Path dest = Paths.get("./Fail_Screenshots",
				screenshotname + new SimpleDateFormat(" yyyy-MM-dd-HH-mm").format(new Date()) + ".png");

		try {
			Files.createDirectories(dest.getParent());
			FileOutputStream out = new FileOutputStream(dest.toString());
			out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			out.close();
		} catch (IOException e) {
			System.out.print("Exception while taking screen shot" + e.getMessage());
		}
	}

	// Get current time/date for the reports
	public static String getCurrentTime() {
		DateFormat format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		// DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		return format.format(date);
	}

	public static void sleepThreeSeconds() throws InterruptedException {
		Thread.sleep(3000);
		// driver.wait(3000);
	}

	// Calling Method to get the actual page title
	public String getActualPageTitle() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		String ActualPageTitle = driver.getTitle();
		//System.out.println("ActualPageTitle: [ " + ActualPageTitle + " ] & Expected: [ " + ExpectedLoginTabTitle + " ]");

		return ActualPageTitle;
	}


	// Calling Method to get the actual page URL
	public String getActualPageURL() {
		String ActualPageURL = driver.getCurrentUrl();
		//System.out.println("ActualURL: [ " + ActualPageURL + " ] & Expected: [ " + ExpectedURL + " ]");
		return ActualPageURL;
	}
}
