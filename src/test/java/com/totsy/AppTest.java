package com.totsy;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.totsy.tests.Keywords;
import static com.totsy.tests.DriverScript.*;
/**
 * Hello world!
 *
 */
public class AppTest
{
    public static void main( String[] args )throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchMethodException, SecurityException
    {
    	FileInputStream fs = new FileInputStream("src/test/java/com/totsy/config/config.properties");
    	CONFIG= new Properties();
    	CONFIG.load(fs);
    	
    	fs = new FileInputStream("src/test/java/com/totsy/config/or.properties");
    	OR= new Properties();
    	OR.load(fs);
    	
    	APP_LOGS = Logger.getLogger("devpinoyLogger");
    	
    	keywords = new Keywords();
    	keywords.openBrowser("","IE");
    	keywords.navigate("", "https://localhost/V5/Web/w2.Commerce.Manager/Default.aspx");
    	keywords.writeInInput("Login_tbUserId", "x");
    	keywords.writeInInput("Login_tbPassword", "x");
    	keywords.clickImage("Login_btnLogin", "");
    	keywords.clickLink("Menu_lnkProductList", "");
    	keywords.selectItemFromList("ProductList_tableProductList", "吉野杉のトレイ　大【包装】【のし】");
    	keywords.windowHandler("ProductConfirm_lnkProductStockList", "");
    	keywords.clickButton("ProductStockListPopup_btnEditTop", "");
    	keywords.importWarehouse("","-:1,2,3");
    	keywords.clickButton("ProductStockListPopup_btnStockUpdateTop", "");
    	keywords.clickButton("ProductStockListPopup_btRedirectEditTop", "");
    	keywords.clickButton("ProductStockListPopup_btnClose", "");
    }
}
