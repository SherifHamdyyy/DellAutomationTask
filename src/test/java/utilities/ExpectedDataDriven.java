package utilities;

public class ExpectedDataDriven {
	// Get data from the property manager class and get the values from the
	// TestData - PropertyManager
	public static String websiteURL = PropertyManager.getInstance().getExpectedURL();
	public static String webSiteTitle = PropertyManager.getInstance().getExpectedWebsiteTitle();
	public static String browserName = PropertyManager.getInstance().getExpectedBrowserName();
	public static String OS = PropertyManager.getInstance().getExpectedOS();
	// TestData - ExpectedProperties
	public static String expCreateHDR_TC2 = ExpectedProperties.getInstance().getexpCreateHDR_TC2();
	public static String expAuthDes_TC3 = ExpectedProperties.getInstance().getexpAuthDes_TC3();
	public static String expProdTitle_TC4 = ExpectedProperties.getInstance().getexpProdTitle_TC4();
	public static String expCartCount_TC4 = ExpectedProperties.getInstance().getexpCartCount_TC4();
	public static String expShp_TC5 = ExpectedProperties.getInstance().getexpShp_TC5();
	public static String expPay_TC5 = ExpectedProperties.getInstance().getexpPay_TC5();
	public static String expConf_TC6 = ExpectedProperties.getInstance().getexpConf_TC6();
	public static String expectedComp_TC6 = ExpectedProperties.getInstance().getexpectedComp_TC6();

}
