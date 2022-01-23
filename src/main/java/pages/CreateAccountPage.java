package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.DataEnums.elementSelectedBy;

public class CreateAccountPage extends HomePage {

	// Driver linkage
	public CreateAccountPage(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in the screen
	@FindBy(xpath = "//*[@id='account-creation_form']")
	private WebElement creationForm;

	@FindBy(xpath = "//*[@class='page-heading']")
	private WebElement createAccountLabel;

	@FindBy(xpath = "//*[@id='id_gender1']")
	private WebElement mrRadioBtn;

	@FindBy(xpath = "//*[@id='id_gender2']")
	private WebElement mrsRadioBtn;

	@FindBy(xpath = "//*[@id='days']")
	private WebElement birthDay;
	@FindBy(xpath = "//*[@id='months']")
	private WebElement birthMonth;
	@FindBy(xpath = "//*[@id='years']")
	private WebElement birthYear;
	// -----
	@FindBy(xpath = "//*[@id='customer_firstname']")
	private WebElement firstnameField;

	@FindBy(xpath = "//*[@id='customer_lastname']")
	private WebElement lastnameField;

	@FindBy(xpath = "//*[@id='email']")
	private WebElement emailField;

	@FindBy(xpath = "//*[@id='passwd']")
	private WebElement passField;

	@FindBy(xpath = "//*[@id='firstname']")
	private WebElement secondfirstnameField;

	@FindBy(xpath = "//*[@id='lastname']")
	private WebElement secondlastnameField;

	@FindBy(xpath = "//*[@id='address1']")
	private WebElement addressField;
	// --------
	@FindBy(xpath = "//*[@id='city']")
	private WebElement cityField;

	@FindBy(xpath = "//*[@id='id_state']")
	private WebElement stateField;

	@FindBy(xpath = "//*[@id='postcode']")
	private WebElement zipCodeField;

	@FindBy(xpath = "//*[@id='id_country']")
	private WebElement countryField;

	@FindBy(xpath = "//*[@id='phone_mobile']")
	private WebElement mobileField;

	@FindBy(xpath = "//*[@id='alias']")
	private WebElement aliasAddressField;

	@FindBy(xpath = "//*[@id='submitAccount']")
	private WebElement registerBTN;

	// function to validate page header and email existence,
	// incase email already exist,try to create a new random email
	public String validatePageHeader(String email) {
		waitForElement(creationForm);
		String actualHeader = null;

		try {
			if (createAccountLabel.getText().equals("AUTHENTICATION")) {
				createNewAccountUser(email);
			}

			actualHeader = getTextOf(createAccountLabel);

		} catch (Exception e) {
			printValueOf("Something went wrong while creating a new account/Authentication page");
		}
		return actualHeader;
	}

	// function to fill data to register user's data
	public void AuthenticateUser(String gender, String firstname, String lastname, String password, String address,
			String city, String state, String country, String aliasAddress, String birthDay, String birthMonth,
			String birthYear, String secondFN, String secondLN, String code, String mobile) {

		selectGender(gender);
		setText(firstnameField, firstname);
		setText(lastnameField, lastname);
		clickButton(emailField);
		setText(passField, password);
		selectBirthDate(birthDay, birthMonth, birthYear);
		setText(secondfirstnameField, secondFN);
		setText(secondlastnameField, secondLN);
		setText(addressField, address);
		setText(cityField, city);
		selectElement(stateField, elementSelectedBy.visibleText, state);
		setText(zipCodeField, code);
		selectElement(countryField, elementSelectedBy.visibleText, country);
		setText(mobileField, mobile);
		setText(aliasAddressField, aliasAddress);
		clickButton(registerBTN);
	}

	// Function below used for AuthenticateUser function to select Date of birth
	private void selectBirthDate(String Day, String Month, String Year) {
		try {
			selectElement(birthDay, elementSelectedBy.value, Day);
			selectElement(birthMonth, elementSelectedBy.value, Month);
			selectElement(birthYear, elementSelectedBy.value, Year);
		} catch (Exception e) {
			printValueOf("Please enter value number,Ex: 5 for May");
		}
	}

	// Function below used for AuthenticateUser function to select Gender
	private void selectGender(String gender) {
		try {

			if (gender.equalsIgnoreCase("mrs")) {
				clickButton(mrsRadioBtn);
			} else {
				clickButton(mrRadioBtn);
			}
		} catch (Exception e) {
			printValueOf("Please enter Mr or Mrs !");
		}
	}
}
