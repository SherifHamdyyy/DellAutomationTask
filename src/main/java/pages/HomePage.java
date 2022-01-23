package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {

	// Driver linkage
	public HomePage(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in HomePage screen
	@FindBy(xpath = "//*[@class='login']")
	private WebElement signin;

	@FindBy(xpath = "//*[@id='email_create']")
	private WebElement emailAddressInputField;

	@FindBy(xpath = "//*[@id='SubmitCreate']")
	private WebElement emailAddressCreateAccBtn;

	// Method to sign in and create a new random email address
	public void createNewAccountUser(String email) {
		try {
			waitForElement(signin);
			clickButton(signin);
			waitForElement(emailAddressInputField);
			if (email.isEmpty()) {
				setText(emailAddressInputField, generateRandomEmail());
			} else {
				setText(emailAddressInputField, email);
			}
			clickButton(emailAddressCreateAccBtn);
			
		} catch (Exception e) {
			printValueOf("Something written wrong while type email!");
		}
		
	}

}
