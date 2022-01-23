package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.ahmed.excelizer.ExcelReader;

import pages.AddressScreen;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.PaymentScreen;
import pages.ShippingScreen;
import pages.SummaryScreen;
import utilities.ExpectedDataDriven;
import utilities.Helper;

public class NewUserOrderCheckout extends TestBase {

	// Objects declaration
	// ------------------------------------------------------------------------------
	HomePage homePageObject = null;
	Helper helperObject = null;
	CreateAccountPage createAccountPageObject = null;
	MyAccountPage myAccountPageObject = null;
	SummaryScreen SummaryScreenObject = null;
	AddressScreen AddressScreenObject = null;
	ShippingScreen ShippingScreenObject = null;
	PaymentScreen PaymentScreenObject = null;

	// Excel sheet path and PreCondition
	// ------------------------------------------------------------------------------
	String testDataPath = System.getProperty("user.dir") + "/TestData/Data.xlsx";
	static String Sheet_email = "Sheet_email";
	static String Sheet_AuthData = "Sheet_AuthData";

	// Get data from the sheet in the excel file
	@DataProvider(name = "Sheet_email")
	public Object[][] Sheet_email() {

		return ExcelReader.loadTestData(testDataPath, Sheet_email);
	}

	// Get data from the sheet in the excel file
	@DataProvider(name = "Sheet_AuthData")
	public Object[][] Sheet_AuthData() {

		return ExcelReader.loadTestData(testDataPath, Sheet_AuthData);
	}
	// ------------------------------------------------------------------------------
	// Objects Initialization

	@BeforeClass
	public void initPageObject() {
		helperObject = new Helper();
		homePageObject = new HomePage(driver);
		createAccountPageObject = new CreateAccountPage(driver);
		myAccountPageObject = new MyAccountPage(driver);
		SummaryScreenObject = new SummaryScreen(driver);
		PaymentScreenObject = new PaymentScreen(driver);
		AddressScreenObject = new AddressScreen(driver);
		ShippingScreenObject = new ShippingScreen(driver);
	}

	// ------------------------------------------------------------------------------
	// Test-case to check the redirection of the desired URLs
	@Test(priority = 1, enabled = true, description = "Check the redirection of the URL")
	public void Check_Website_Redirection() {
		try {

			// Open the web-site
			OpenURL(ExpectedDataDriven.websiteURL);

			// Get the actual values from the web-site
			String ActualPageURL = helperObject.getActualPageURL();
			String ActualPageTitle = helperObject.getActualPageTitle();

			// Log the status into the test report
			reportLog("WebsiteURL", ActualPageURL, ExpectedDataDriven.websiteURL);
			reportLog("PageTitle", ActualPageTitle, ExpectedDataDriven.webSiteTitle);

			// Validation - Proper URL redirection
			Assert.assertEquals(ActualPageURL, ExpectedDataDriven.websiteURL);

			// Validation - Proper Page Title displayed
			Assert.assertEquals(ActualPageTitle, ExpectedDataDriven.webSiteTitle);

		} catch (Exception e) {
			// Write a reason in the report
			Assert.fail("Invalid URL in the File!");

		}
	}

	// Test-Case to register and proceed with the registered data
	@Test(priority = 2, enabled = false, dependsOnMethods = "Check_Website_Redirection", dataProvider = "Sheet_email")
	public void Check_AccountCreation_Successfully(String email) {

		// 1. Create a new account user
		// create a new account user with specific email from the excel sheet
		homePageObject.createNewAccountUser(email);

		// Validate authentication page opened successfully
		String actualCreateHDR_TC2 = createAccountPageObject.validatePageHeader(email);
		reportLog("CreationHeader", actualCreateHDR_TC2, ExpectedDataDriven.expCreateHDR_TC2);
		Assert.assertEquals(actualCreateHDR_TC2, ExpectedDataDriven.expCreateHDR_TC2);
	}

	// Test-Case to register and proceed with the registered data
	@Test(priority = 3, enabled = false, dependsOnMethods = "Check_AccountCreation_Successfully", dataProvider = "Sheet_AuthData")
	public void Check_Register_Successfully(String gender, String firstname, String lastname, String password,
			String address, String city, String state, String country, String aliasAddress, String birthDay,
			String birthMonth, String birthYear, String secondFN, String secondLN, String code, String mobile) {
		// 2. Authenticate as new user
		// Fill user's data to authenticate as a new user
		createAccountPageObject.AuthenticateUser(gender, firstname, lastname, password, address, city, state, country,
				aliasAddress, birthDay, birthMonth, birthYear, secondFN, secondLN, code, mobile);

		// Validate My Account screen is displayed
		String actualAuthDesc_TC3 = myAccountPageObject.validateMyAccountNavigation();
		Assert.assertEquals(actualAuthDesc_TC3, ExpectedDataDriven.expAuthDes_TC3);

	}

	@Test(priority = 4, enabled = false, dependsOnMethods = "Check_Website_Redirection")
	public void Check_Product_Selection() {

		// 3. Select “Blouses” Sub-category in “Women” Category
		// 4. Select resulted product
		myAccountPageObject.selectSubCategory();

		// Validate selected product is added successfully
		String actualProdTitle_TC4 = myAccountPageObject.select_and_addProductToCart().trim();

		Assert.assertEquals(actualProdTitle_TC4, ExpectedDataDriven.expProdTitle_TC4);

		// Validate Summary screen navigation & Follow checkout procedure
		myAccountPageObject.checkout();
		String actualCartCount_TC4 = SummaryScreenObject.validateShoppingSummaryViewed();
		Assert.assertEquals(actualCartCount_TC4, ExpectedDataDriven.expCartCount_TC4);

	}

	@Test(priority = 5, enabled = false, dependsOnMethods = "Check_Product_Selection", dataProvider = "Sheet_AuthData")
	public void Order_Checkout(String gender, String firstname, String lastname, String password, String address,
			String city, String state, String country, String aliasAddress, String birthDay, String birthMonth,
			String birthYear, String secondFN, String secondLN, String code, String mobile) {

		// 5. Follow checkout procedure
		// 6. Confirm order by selecting bank wire option

		// SUMMARY STEP - Proceed to the next step "Address"
		SummaryScreenObject.proceedToCheckOut_summary();
		// Validate the address step and check the entered alias address is
		// displayed
		String ActualAddress = AddressScreenObject.validateAliasAddress();
		Assert.assertTrue(ActualAddress.contains(aliasAddress));

		// ADDRESS STEP - Proceed to the next step "Shipping"
		AddressScreenObject.proceedToCheckOut_Address();
		// Validate the shipping screen is displayed
		String actualShp_TC5 = ShippingScreenObject.validateShipping();
		Assert.assertEquals(actualShp_TC5, ExpectedDataDriven.expShp_TC5 + aliasAddress);

		// SHIPPING STEP - Accept Shipping terms then proceed
		ShippingScreenObject.acceptShippingTerms_proceed();
		// Validate Payment screen is displayed to choose a payment method
		String actualPay_TC5 = PaymentScreenObject.validatePaymentScreen();
		Assert.assertEquals(actualPay_TC5, ExpectedDataDriven.expPay_TC5);

	}

	@Test(priority = 6, enabled = false, dependsOnMethods = "Order_Checkout")
	public void Order_Payment() {

		// 6. Confirm order by selecting bank wire option
		// PAYMENT STEP - Choose "BankWire" payment method
		PaymentScreenObject.selectPaymentMethod();
		// Validate the selected payment method is selected
		String actualConf_TC6 = PaymentScreenObject.validateConfirmationPaymentScreen();
		Assert.assertEquals(actualConf_TC6, ExpectedDataDriven.expConf_TC6);

		// PAYMENT CONFIRMATION STEP - Click on confirm order
		PaymentScreenObject.confirmOrder();
		// Validate the title of the completion screen is displayed
		String actualComp_TC6 = PaymentScreenObject.validateOrderCompletion();
		Assert.assertEquals(actualComp_TC6, ExpectedDataDriven.expectedComp_TC6);

	}
}
