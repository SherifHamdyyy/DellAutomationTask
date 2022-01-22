package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExpectedProperties {

	private static ExpectedProperties instance;
	private static final Object lock = new Object();
	private static String propertyFilePath = System.getProperty("user.dir") + "/TestData/ExpectedValues.properties";
	private static String expCreateHDR_TC2;
	private static String expAuthDes_TC3;
	private static String expProdTitle_TC4;
	private static String expCartCount_TC4;
	private static String expShp_TC5;
	private static String expPay_TC5;
	private static String expConf_TC6;
	private static String expectedComp_TC6;

	// Create a Singleton instance. We need only one instance of Property
	// Manager.
	public static ExpectedProperties getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new ExpectedProperties();
				instance.loadData();
			}
		}
		return instance;
	}

	// Get all configuration data and assign to related fields.
	private void loadData() {
		// Declare a properties object
		Properties prop = new Properties();

		// Read configuration.properties file
		try {
			prop.load(new FileInputStream(propertyFilePath));
		} catch (IOException e) {
			System.out.println("Configuration properties file cannot be found");
		}

		// Get properties from configuration.properties
		expCreateHDR_TC2 = prop.getProperty("expCreateHDR_TC2");
		expAuthDes_TC3 = prop.getProperty("expAuthDes_TC3");
		expProdTitle_TC4 = prop.getProperty("expProdTitle_TC4");
		expCartCount_TC4 = prop.getProperty("expCartCount_TC4");
		expShp_TC5 = prop.getProperty("expShp_TC5");
		expPay_TC5 = prop.getProperty("expPay_TC5");
		expConf_TC6 = prop.getProperty("expConf_TC6");
		expectedComp_TC6 = prop.getProperty("expectedComp_TC6");

	}

	public String getexpCreateHDR_TC2() {
		return expCreateHDR_TC2;
	}

	public String getexpAuthDes_TC3() {
		return expAuthDes_TC3;
	}

	public String getexpProdTitle_TC4() {
		return expProdTitle_TC4;
	}

	public String getexpCartCount_TC4() {
		return expCartCount_TC4;
	}

	public String getexpShp_TC5() {
		return expShp_TC5;
	}

	public String getexpPay_TC5() {
		return expPay_TC5;
	}

	public String getexpConf_TC6() {
		return expConf_TC6;
	}

	public String getexpectedComp_TC6() {
		return expectedComp_TC6;
	}

}
