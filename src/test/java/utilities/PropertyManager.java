package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	

	private static PropertyManager instance;
    private static final Object lock = new Object();
    private static String propertyFilePath = System.getProperty("user.dir") + "/TestData/Configuation.properties";
    private static String url;
    private static String os;
    private static String browser;
    private static String WebsiteTitle;
    //private static String  LoginURL;
 
    //Create a Singleton instance. We need only one instance of Property Manager.
    public static PropertyManager getInstance () {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                instance.loadData();
            }
        }
        return instance;
    }
 
    //Get all configuration data and assign to related fields.
    private void loadData() {
        //Declare a properties object
        Properties prop = new Properties();
 
        //Read configuration.properties file
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }
     
        //Get properties from configuration.properties
        url = prop.getProperty("URL");
        os = prop.getProperty("OSName");
        browser = prop.getProperty("browserName");
        WebsiteTitle = prop.getProperty("WebsiteTitle");
    }
 
    public String getExpectedURL () {
      return url;
    }
 
    public String getExpectedOS () {
        return os;
    }
 
    public String getExpectedBrowserName () {
        return browser;
    }
    
    public String getExpectedWebsiteTitle() {
        return WebsiteTitle;
    }
}
