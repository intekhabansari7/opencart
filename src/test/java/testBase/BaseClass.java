package testBase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public ResourceBundle rb; //to read config.properties
	
	public static WebDriver driver;
	
	public Logger logger; // for logging
	
	@BeforeClass(groups= {"Master","Sanity","Regression"})  //Step8 groups added
	@Parameters("browser")   //getting browser parameter from testng.xml
	public void setup(String br) {
		
		rb = ResourceBundle.getBundle("config");  //Load config.properties
		
		logger = LogManager.getLogger(this.getClass());
		
    //    ChromeOptions options = new ChromeOptions();
		
	//	options.addArguments("--remote-allow-origins=*");
		
	//	options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"}); //Remove "chrome is being controlled by automated test software"
		
		//launch right browser based on parameter
		
		if(br.equals("chrome")) {
			
		driver = new ChromeDriver();
		
		}
		 
		else if(br.equals("edge")) {
			
			driver = new EdgeDriver();
		}
		
		else {
			
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(rb.getString("appURL"));   // get url from config.properties file
		
		driver.manage().window().maximize();
		
	}
	
	@AfterClass(groups= {"Master","Sanity","Regression"})  //Step8 groups added
	 void tearDown() {
		
		driver.quit();
	}
	
	public String randomString() {
		
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		
		return (generatedString);
		
	}
	
	public String randomNumber() {
		
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		
		return (generatedString2);
	}
	
	public String randomAlphaNumeric() {
		
		String st = RandomStringUtils.randomAlphabetic(4);
		
		String num = RandomStringUtils.randomNumeric(3);
		
		return (st +"@" +num);
	}

	public String captureScreen(String tname) {
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String destination = System.getProperty("user.dir") + "\\screenshots\\ "+ tname + "_" + timeStamp + ".png";
		
		try {
			
			FileUtils.copyFile(source, new File(destination));
		}
		
		catch(Exception e) {
			
			e.getMessage();
		}
		
		return destination;
		
	}
}
