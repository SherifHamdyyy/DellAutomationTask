package pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.DataEnums.elementSelectedBy;

public class PageBase {

	protected static WebDriver driver2;
	protected static WebDriverWait wait = null;
	protected static Actions actions = null;
	protected static JavascriptExecutor js = null;
	protected Select elementSelection = null;
	protected static Robot robot = null;

	// create constructor
	public PageBase(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		actions = new Actions(driver);
		js = (JavascriptExecutor) driver;
		driver2 = driver;
	}

	// Method to Click Buttons
	protected void clickButton(WebElement button) {
		button.click();
	}

	protected void implicitlyWait(int seconds) {
		driver2.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	protected void moveTo_new(WebElement browse) {
		Actions actionsz = new Actions(driver2);
		actionsz.moveToElement(browse).perform();

	}

	protected void moveTo_newClick(WebElement browse) {
		Actions actionsz = new Actions(driver2);
		actionsz.moveToElement(browse).click().build().perform();

	}

	protected void doubleClick(WebElement browse) {
		actions.doubleClick(browse).build().perform();
		// actions.click(browse).build().perform();

	}

	protected void escapeBtn() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
		} catch (Exception e) {
			printValueOf("Execption in escape method_PagePase(Robot) " + e);
		}

	}

	protected void upload(String FilePath) {

		try {

			Robot robot = new Robot();

			robot.setAutoDelay(2000);

			StringSelection selection = new StringSelection(FilePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

			robot.setAutoDelay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);

			robot.setAutoDelay(1000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			printValueOf("Execption in upload method_PagePase(Robot) " + e);
		}

	}

	// Method to send text into a field
	protected void setText(WebElement textElement, String value) {

		waitForElement(textElement);
		textElement.clear();
		textElement.sendKeys(value);
	}

	// Method to scroll to an element by position
	protected static void scrollByPosition(WebElement element) {

		// get position
		int x = 0;
		int y = element.getLocation().getY();

		// scroll to x y
		js.executeScript("window.scrollBy(" + x + ", " + y + ")");
	}

	// Method to scroll to an element
	protected void scrollByactions(WebElement element) {

		// Get the current browser name to perform it's desired scrolling
		// function
		Capabilities cap = ((RemoteWebDriver) driver2).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();

		if (browserName.contains("chrome")) {
			actions.moveToElement(element).perform();
		} else {
			// in-case of fire-fox scroll with the below function
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	// Method to open new tab
	protected void openNewTab(String url) {
		actions.sendKeys(Keys.CONTROL + "t");
		js.executeScript("window.open('" + url + "','_blank');");

	}

	// Method to select an item from a drop-list by value/visibleText/index
	protected void selectElement(WebElement selectWebElement, elementSelectedBy selection, String option) {
		try {
			// Method to select from Drop down
			elementSelection = new Select(selectWebElement);
			
			switch(selection) {
		      case visibleText:
					elementSelection.selectByVisibleText(option);
		        break;
		      case value:
					elementSelection.selectByValue(option);
		        break;
		      case index:
		    	  int indexValue = Integer.parseInt(option);
					elementSelection.selectByIndex(indexValue);
		        
		        default:
					System.out.println("Different selection for the elementSelectBy enum");

		    }

			/*if (selection.equalsIgnoreCase("value")) {
				elementSelection.selectByValue(option);
			} else if (selection.equalsIgnoreCase("visibletext")) {
				elementSelection.selectByVisibleText(option);
			} else {
				int index = Integer.parseInt(option);
				elementSelection.selectByIndex(index);
			}
			*/
		} catch (Exception e) {
			System.out.println("Can't Select elements from Drop list!");
		}
	}
	// Method to select an item from a drop-list by visibleText only
	protected void selectElementByVisibleText(WebElement selectWebElement,String option) {
		try {
			// Method to select from Drop down
			elementSelection = new Select(selectWebElement);
			elementSelection.selectByVisibleText(option);
			} 
		 catch (Exception e) {
			System.out.println("Can't Select elements from Drop list!");
		}
	}
	
	// Method to select an item from a drop-list by value
	protected void selectElementByValue(WebElement selectWebElement,String option) {
		try {
			// Method to select from Drop down
			elementSelection = new Select(selectWebElement);
			elementSelection.selectByValue(option);
			} 
		 catch (Exception e) {
			System.out.println("Can't Select elements from Drop list!");
		}
	}
	

	// Method to wait for an element until visibility
	protected void waitForElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver2, 20);
			wait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected int countElementsByTagName(WebElement element, String tagName) {
		return element.findElements(By.tagName("div")).size();
	}

	protected WebElement getFirstElementOfList() {
		WebElement parentDiv = driver2.findElement(By.className("tab-content"));
		WebElement ul = parentDiv.findElement(By.tagName("ul"));
		List<WebElement> lis = ul.findElements(By.tagName("li"));
		return lis.get(0);

	}

	protected WebElement getSecondElementOfList() {
		WebElement parentDiv = driver2.findElement(By.className("tab-content"));
		WebElement ul = parentDiv.findElement(By.tagName("ul"));
		List<WebElement> lis = ul.findElements(By.tagName("li"));
		return lis.get(1);

	}

	// Method to print the value as text
	protected void printValueOf(String text) {
		System.out.println(text);
	}

	// Method to get text of an element
	protected String getTextOf(WebElement element) {
		return (element).getText();
	}

	// Method to check the element enabling
	protected boolean isEnabledElement(WebElement element) {
		return (element).isEnabled();
	}

	// Method to check the element display
	protected boolean isDisplayedElement(WebElement element) {
		return (element).isDisplayed();
	}

	// Calling Method to check the element is Enabled & Displayed With Text
	protected boolean checkEnabledDisplayedWzText(String expectedvalue, WebElement desiredElement,
			boolean enabled_trueFalse) {
		boolean flag = false;
		try {

			waitForElement(desiredElement);

			if (isDisplayedElement(desiredElement) == true && isEnabledElement(desiredElement) == enabled_trueFalse
					&& getTextOf(desiredElement).contentEquals(expectedvalue)) {
				flag = true;
				printValueOf(" PASS, " + expectedvalue + " is displayed properly");
			} else {
				flag = false;
				printValueOf(" FAIL , " + expectedvalue + " is not displayed properly, [Actual]: "
						+ getTextOf(desiredElement) + " [Expected]:" + expectedvalue);
			}
		} catch (Exception e) {
			flag = false;
			printValueOf(" FAIL , " + expectedvalue + " is not displayed properly!" + e);
		}
		return flag;
	}

	// Calling Method to check the element is Displayed With Text
	protected boolean checkDisplayedWzText(String expectedvalue, WebElement desiredElement) {
		boolean flag = false;
		try {

			waitForElement(desiredElement);

			if (isDisplayedElement(desiredElement) == true && getTextOf(desiredElement).contentEquals(expectedvalue)) {
				flag = true;
				printValueOf(" PASS, " + expectedvalue + " is displayed properly");
			} else {
				flag = false;
				printValueOf(" FAIL , " + expectedvalue + " is not displayed properly, [Actual]: "
						+ getTextOf(desiredElement) + " [Expected]:" + expectedvalue);
			}
		} catch (Exception e) {
			flag = false;
			printValueOf(" FAIL , " + expectedvalue + " is not displayed properly!" + e);
		}
		return flag;
	}

	// Calling Method to check the element is Displayed only
	protected boolean checkDisplayed(String expectedvalue, WebElement desiredElement) {
		boolean flag = false;
		try {

			waitForElement(desiredElement);

			if (isDisplayedElement(desiredElement) == true) {
				flag = true;
				printValueOf(" PASS, " + expectedvalue + " is displayed properly");
			} else {
				flag = false;
				printValueOf(" FAIL , " + expectedvalue + " is not displayed properly, [Actual]: "
						+ getTextOf(desiredElement) + " [Expected]:" + expectedvalue);
			}
		} catch (Exception e) {
			flag = false;
			printValueOf(" FAIL , " + expectedvalue + " is not displayed properly!" + e);
		}
		return flag;
	}

	// Calling Method to check the element is Enabled & Displayed
	protected boolean checkEnabledDisplayed(String expectedvalue, WebElement desiredElement) {
		boolean flag = false;
		try {

			waitForElement(desiredElement);

			if (isDisplayedElement(desiredElement) == true && isEnabledElement(desiredElement) == true) {
				flag = true;
				printValueOf(" PASS, " + expectedvalue + " is displayed properly");
			} else {
				flag = false;
				printValueOf(" FAIL , " + expectedvalue + " is not displayed properly, [Actual]: "
						+ getTextOf(desiredElement) + " [Expected]:" + expectedvalue);
			}
		} catch (Exception e) {
			flag = false;
			printValueOf(" FAIL , " + expectedvalue + " is not displayed properly!" + e);
		}
		return flag;
	}

	// Method to generate a random email
	protected String generateRandomEmail() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(90000);
		String email = "username" + randomInt + "@test.com";
		return email;
	}

	protected int generateRandomPassword() {
		/*
		 * String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; String character =
		 * "!@#$%^&*-_=+|;:,<.>/?"; String pwd = RandomStringUtils.random( 15,
		 * upper+character); return pwd;
		 */
		Random ran = new Random();
		int x = ran.nextInt(6) + 5;
		return x;
	}
	

}
