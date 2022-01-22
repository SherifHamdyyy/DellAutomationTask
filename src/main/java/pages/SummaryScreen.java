package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SummaryScreen extends PageBase {

	// Driver linkage
	public SummaryScreen(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in the screen

	@FindBy(xpath = "//*[@class='heading-counter']")
	private WebElement shoppoingCartLabelCount;

	@FindBy(xpath = "//*[@id='summary_products_quantity']")
	private WebElement shoppoingCartLabelCounter;

	@FindBy(xpath = "//*[@class='cart_ref']")
	private WebElement modelName;

	@FindBy(xpath = "//*[@id='total_price']")
	private WebElement totalPrice;

	@FindBy(xpath = "//*[@id='HOOK_SHOPPING_CART']/..//a[contains(@title, 'Proceed to checkout')]")
	private WebElement proceedToCheckOut_summary;

	// Function to wait and get header text of summary section
	public String validateShoppingSummaryViewed() {
		waitForElement(shoppoingCartLabelCount);
		String shoppingLabelCount = getTextOf(shoppoingCartLabelCount);
		return shoppingLabelCount;
	}

	// Function to wait and click on proceed button
	public void proceedToCheckOut_summary() {
		waitForElement(proceedToCheckOut_summary);
		clickButton(proceedToCheckOut_summary);
	}

}
