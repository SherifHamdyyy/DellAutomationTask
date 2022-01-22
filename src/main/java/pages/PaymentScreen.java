package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentScreen extends PageBase {

	// Driver linkage
	public PaymentScreen(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in the screen

	@FindBy(xpath = "//*[@class='page-heading']")
	private WebElement paymentHeader;

	@FindBy(xpath = "//*[@class='bankwire']")
	private WebElement bankWire;

	@FindBy(xpath = "//*[@class='page-subheading']")
	private WebElement bankWireHeader;

	@FindBy(xpath = "//*[@id='cart_navigation']/button")
	private WebElement confirmOrderBTN;

	@FindBy(xpath = "//*[@class='cheque-indent']")
	private WebElement orderCompleteLabel;

	// Function to get text of the payment section
	public String validatePaymentScreen() {
		waitForElement(paymentHeader);
		String title = getTextOf(paymentHeader);
		return title;
	}

	// Function to wait and click on bankwire option to select payment method
	public void selectPaymentMethod() {
		waitForElement(bankWire);
		clickButton(bankWire);
	}

	// Function to get text of the payment method section
	public String validateConfirmationPaymentScreen() {
		waitForElement(bankWireHeader);
		String title = getTextOf(bankWireHeader);
		return title;
	}

	// Function to wait and click on confirm order button
	public void confirmOrder() {
		waitForElement(confirmOrderBTN);
		clickButton(confirmOrderBTN);
	}

	// Function to wait and get text of the payment confirmation section
	public String validateOrderCompletion() {
		waitForElement(orderCompleteLabel);
		String title = getTextOf(orderCompleteLabel);
		return title;
	}
}
