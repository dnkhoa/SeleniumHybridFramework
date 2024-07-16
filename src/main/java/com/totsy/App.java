package com.totsy;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException
    {
    	keywords = new Keywords();
    	FileInputStream fs = new FileInputStream("src/test/java/com/totsy/config/config.properties");
    	CONFIG= new Properties();
    	CONFIG.load(fs);
    	
    	fs = new FileInputStream("src/test/java/com/totsy/config/or.properties");
    	OR= new Properties();
    	OR.load(fs);
    	
    	APP_LOGS = Logger.getLogger("devpinoyLogger");
    	keywords.openBrowser("","Mozilla");
    	keywords.navigate("", "https://localhost/V5/Web/w2.Commerce.Manager/Default.aspx");
    	keywords.writeInInput("login_userId", "x");
    	keywords.writeInInput("login_password", "x");
    	keywords.clickImage("login_login", "");
    	keywords.clickLink("//*[@id=\"aspnetForm\"]/table/tbody/tr/td/table[1]/tbody/tr/td[3]/dl/dd[3]/span[4]/a", "");
    	keywords.clickButton("//*[@id=\"ctl00_ContentPlaceHolderBody_btnInsertTop\"]", "");
    }
}
