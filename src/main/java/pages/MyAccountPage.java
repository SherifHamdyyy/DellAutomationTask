package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends PageBase {

	// Driver linkage
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	// Detecting Elements from the UI in the screen
	@FindBy(xpath = "//*[@class='info-account']")
	private WebElement myAccountLabel;

	@FindBy(xpath = "//*[@class='sf-with-ul' and contains(text(),'Women')]")
	private WebElement women;

	@FindBy(xpath = "//a[text()='Blouses']")
	private WebElement blouses;

	@FindBy(xpath = "//*[@class='product_img_link']")
	private WebElement product;

	@FindBy(xpath = "//*[@class='exclusive']")
	private WebElement addToCart;

	@FindBy(xpath = "//*[@class='button-container']/span/../*[2]")
	private WebElement proceedBTN;

	@FindBy(xpath = "//*[@class='icon-ok']/..")
	private WebElement productMSG;

	// Function to validate my account by getting the header label
	public String validateMyAccountNavigation() {
		String actualLabel = null;
		try {
			waitForElement(myAccountLabel);
			actualLabel = getTextOf(myAccountLabel);
		} catch (Exception e) {
			printValueOf("Something went wrong After filling creating data for a new account/ My Account page");
		}
		return actualLabel;
	}

	// Function to select a sub category item
	public void selectSubCategory() {
		waitForElement(women);
		actions.moveToElement(women);
		actions.moveToElement(blouses);
		actions.click().build().perform();
	}

	// Function to select a product and it to the cart
	public String select_and_addProductToCart() {
		waitForElement(product);
		clickButton(product);
		waitForElement(addToCart);
		clickButton(addToCart);
		waitForElement(productMSG);
		actions.moveToElement(productMSG).build().perform();
		String message = getTextOf(productMSG);
		return message;
	}

	// Function to wait and click on proceed button to checkout
	public void checkout() {
		waitForElement(proceedBTN);
		clickButton(proceedBTN);
	}
}
