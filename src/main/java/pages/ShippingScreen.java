package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShippingScreen extends PageBase {

	// Driver linkage
	public ShippingScreen(WebDriver driver) {
		super(driver);
	}
	// Detecting Elements from the UI in the screen

	@FindBy(xpath = "//*[@name='processCarrier']")
	private WebElement proceedToCheckOut_shipping;

	@FindBy(xpath = "//*[@class='delivery_options_address']/*[1]")
	private WebElement shippingHeaderLabel;

	@FindBy(xpath = "//*[@id='cgv']")
	private WebElement shippingCheckBox;

	// Function to wait and click on proceed button
	public void proceedToCheckOut_shipping() {
		waitForElement(proceedToCheckOut_shipping);
		clickButton(proceedToCheckOut_shipping);
	}

	// Function to get text of the shipping header
	public String validateShipping() {
		waitForElement(shippingHeaderLabel);
		String label = getTextOf(shippingHeaderLabel);
		return label;

	}

	// Function to accept shipping terms box, and proceed to the next steps
	public void acceptShippingTerms_proceed() {
		clickButton(shippingCheckBox);
		proceedToCheckOut_shipping();
	}
}
