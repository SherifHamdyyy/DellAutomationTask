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

	// ------------------------------------------------------------------------------
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

	// static String FailureReason_main = "";
	// ------------------------------------------------------------------------------
	/*
	 * String gender = "Mrs"; String firstname = "Tester FN"; String lastname =
	 * "Tester LN"; String email = "sherif.121212@test.com"; String password =
	 * "1234567"; String birthDay = "17"; String birthMonth = "5"; String
	 * birthYear = "1993"; String secondFN = "FirstName address"; String
	 * secondLN = "LastName address"; String address = "Any address here";
	 * String city = "NewCity"; String state = "Alabama"; String code = "11111";
	 * String country = "United States"; String mobile = "01001111111"; String
	 * aliasAddress = "Alias address Here";
	 */
	// variable for the expected product add title txt
	// String expCreateHDR_TC2 = "CREATE AN ACCOUNT";
	/*
	 * String expAuthDes_TC3 =
	 * "Welcome to your account. Here you can manage all of your personal information and orders."
	 * ; String expProdTitle_TC4 =
	 * "Product successfully added to your shopping cart"; String
	 * expCartCount_TC4 = "Your shopping cart contains: 1 Product"; String
	 * expShp_TC5 = "Choose a shipping option for this address: "; String
	 * expPay_TC5 = "PLEASE CHOOSE YOUR PAYMENT METHOD"; String expConf_TC6 =
	 * "BANK-WIRE PAYMENT."; String expectedComp_TC6 =
	 * "You order on My Store is complete.";
	 */// ------------------------------------------------------------------------------

	// Excel sheet path and PreCondition
	// ------------------------------------------------------------------------------
	String testDataPath = System.getProperty("user.dir") + "/TestData/Data.xlsx";
	// static String sheetName = "Sheet4";
	static String Sheet_email = "Sheet_email";
	static String Sheet_AuthData = "Sheet_AuthData";
	String demoEmail = null;

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
	// ------------------------------------------------------------------------------

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
	@Test(priority = 1, enabled = true, description = "Check the redirection of the URL", dataProvider = "Sheet_email")
	public void Check_Website_Redirection(String email) {
		// String FailureReason = "";
		try {

			// Get the actual values from the web-site
			OpenURL(ExpectedDataDriven.websiteURL);

			String ActualPageURL = helperObject.getActualPageURL(ExpectedDataDriven.websiteURL);
			String ActualPageTitle = helperObject.getActualPageTitle(ExpectedDataDriven.webSiteTitle);

			// In-case of failure, Write into the report
			// FailureReason = " Actual WebSite: " + ActualPageURL + " &
			// Expected WebSite: " + WebURL;
			// FailureReason_main = FailureReason;

			reportLog("WebsiteURL",ActualPageURL, ExpectedDataDriven.websiteURL);
			reportLog("PageTitle",ActualPageTitle, ExpectedDataDriven.webSiteTitle);

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
	@Test(priority = 2, enabled = false, dependsOnMethods = "Check_Website_Redirection", dataProvider = "Sheet_url_email")
	public void Check_AccountCreation_Successfully(String URL, String email) {
		// String FailureReason = "";

		// 1. Create a new account user
		// create a new account user with specific email from the excel sheet
		homePageObject.createNewAccountUser(demoEmail);

		// Validate authentication page opened successfully
		String actualCreateHDR_TC2 = createAccountPageObject.validatePageHeader(demoEmail);
		// In-case of failure, Write into the report
		// FailureReason = " Actual: " + actualCreateHDR_TC2 + " & Expected
		// WebSite: " + expCreateHDR_TC2;
		// FailureReason_main = FailureReason;
		reportLog("CreationHeader",actualCreateHDR_TC2, ExpectedDataDriven.expCreateHDR_TC2);
		Assert.assertEquals(actualCreateHDR_TC2, ExpectedDataDriven.expCreateHDR_TC2);
	}

	// Test-Case to register and proceed with the registered data
	@Test(priority = 3, enabled = false, dependsOnMethods = "Check_AccountCreation_Successfully", dataProvider = "Sheet_AuthData")
	public void Check_Register_Successfully(String gender, String firstname, String lastname, String demoemail,
			String password, String address, String city, String state, String country, String aliasAddress,
			String birthDay, String birthMonth, String birthYear, String secondFN, String secondLN, String code,
			String mobile) {
		this.demoEmail = demoemail;
		// String FailureReason = "";
		// 2. Authenticate as new user
		// Fill user's data to authenticate as a new user
		createAccountPageObject.AuthenticateUser(gender, firstname, lastname, password, birthDay, birthMonth, birthYear,
				secondFN, secondLN, address, city, state, code, country, mobile, aliasAddress);

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
	public void Order_Checkout(String gender, String firstname, String lastname, String demoemail, String password,
			String birthDay, String birthMonth, String birthYear, String secondFN, String secondLN, String address,
			String city, String state, String code, String country, String mobile, String aliasAddress) {

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
