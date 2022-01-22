package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddressScreen extends PageBase {

	// Driver linkage
	public AddressScreen(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in the screen
	@FindBy(xpath = "//*[@name='processAddress']")
	private WebElement proceedToCheckOut_Address;

	@FindBy(xpath = "//*[@id='id_address_delivery']")
	private WebElement AliasAddress;

	// function to wait and click on proceed to checkout button
	public void proceedToCheckOut_Address() {
		waitForElement(proceedToCheckOut_Address);
		clickButton(proceedToCheckOut_Address);
	}

	// function to validate alias address placed
	public String validateAliasAddress() {
		waitForElement(AliasAddress);
		String result = getTextOf(AliasAddress);
		return result;
	}
}
