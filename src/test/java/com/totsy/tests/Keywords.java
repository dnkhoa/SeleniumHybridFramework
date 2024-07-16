package com.totsy.tests;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static com.totsy.tests.DriverScript.*;
import static com.totsy.tests.GetOSName.OsUtils.isWindows;
import static com.totsy.tests.GetOSName.OsUtils.isMac;

@SuppressWarnings("ALL")
public class Keywords {

    public WebDriver driver;

    public String openBrowser(String object,String data){
    	File file;

        APP_LOGS.debug("Opening browser");
        if(data.equals("Mozilla"))
            driver=new FirefoxDriver();
        else if(data.equals("IE")){
            file = new File("IEDriver/IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            driver=new InternetExplorerDriver();
        }
        else if(data.equals("Chrome")){
            file = new File("ChromeDriver/chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            driver=new ChromeDriver();
        }

        long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return Constants.KEYWORD_PASS;

    }

    public String navigate(String object,String data){
        APP_LOGS.debug("Navigating to URL");
        try{
        	driver.get(data);
        	if(driver instanceof InternetExplorerDriver)
        		driver.navigate().to("javascript:document.getElementById('overridelink').click()");
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Not able to navigate";
        }
        return Constants.KEYWORD_PASS;
    }

    public String clickLink(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }

        return Constants.KEYWORD_PASS;
    }

    public String clickLink_linkText(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        driver.findElement(By.linkText(OR.getProperty(object))).click();

        return Constants.KEYWORD_PASS;
    }

    public  String verifyLinkText(String object,String data){
        APP_LOGS.debug("Verifying link Text");
        try{
            String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
            String expected=data;

            if(actual.equals(expected))
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL+" -- Link text not verified";

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

        }

    }


    public  String clickButton(String object,String data){
        APP_LOGS.debug("Clicking on Button");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
        }


        return Constants.KEYWORD_PASS;
    }

    public  String verifyButtonText(String object,String data){
        APP_LOGS.debug("Verifying the button text");
        try{
            String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
            String expected=data;

            if(actual.equals(expected))
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
        }

    }

    public  String selectList(String object, String data){
        APP_LOGS.debug("Selecting from list");
        try{
            if(!data.equals(Constants.RANDOM_VALUE)){
                driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
            }else{
                // logic to find a random value in list
                WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object)));
                List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
                Random num = new Random();
                int index=num.nextInt(droplist_cotents.size());
                String selectedVal=droplist_cotents.get(index).getText();

                driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(selectedVal);
            }
        }catch(Exception e){
            return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();

        }

        return Constants.KEYWORD_PASS;
    }

    public String verifyAllListElements(String object, String data){
        APP_LOGS.debug("Verifying the selection of the list");
        try{
            WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object)));
            List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));

            // extract the expected values from OR. properties
            String temp=data;
            String allElements[]=temp.split(",");
            // check if size of array == size if list
            if(allElements.length != droplist_cotents.size())
                return Constants.KEYWORD_FAIL +"- size of lists do not match";

            for(int i=0;i<droplist_cotents.size();i++){
                if(!allElements[i].equals(droplist_cotents.get(i).getText())){
                    return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
                }
            }
        }catch(Exception e){
            return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();

        }
        return Constants.KEYWORD_PASS;
    }

    public  String verifyListSelection(String object,String data){
        APP_LOGS.debug("Verifying all the list elements");
        try{
            String expectedVal=data;
            //System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
            WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object)));
            List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
            String actualVal=null;
            for(int i=0;i<droplist_cotents.size();i++){
                String selected_status=droplist_cotents.get(i).getAttribute("selected");
                if(selected_status!=null)
                    actualVal = droplist_cotents.get(i).getText();
            }

            if(!actualVal.equals(expectedVal))
                return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

        }catch(Exception e){
            return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();

        }
        return Constants.KEYWORD_PASS;

    }
    
    public  String selectRadio(String object, String data){
        APP_LOGS.debug("Selecting a radio button");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL +"- Not able to find radio button";
        }

        return Constants.KEYWORD_PASS;
    }
    

    public  String verifyRadioSelected(String object, String data){
        APP_LOGS.debug("Verify Radio Selected");
        try{
            String temp[]=object.split(Constants.DATA_SPLIT);
            String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
            if(checked==null)
                return Constants.KEYWORD_FAIL+"- Radio not selected";


        }catch(Exception e){
            return Constants.KEYWORD_FAIL +"- Not able to find radio button";

        }

        return Constants.KEYWORD_PASS;

    }

    public  String checkCheckBox(String object,String data){
        APP_LOGS.debug("Checking checkbox");
        try{
            // true or null
            String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
            if(checked==null)// checkbox is unchecked
                driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbox";
        }
        return Constants.KEYWORD_PASS;

    }
    
    public String unCheckCheckBox(String object,String data){
        APP_LOGS.debug("Unchecking checkBox");
        try{
            String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
            if(checked!=null)
                driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbox";
        }
        return Constants.KEYWORD_PASS;

    }

    public  String verifyCheckBoxSelected(String object,String data){
        APP_LOGS.debug("Verifying checkbox selected");
        try{
            String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
            if(checked!=null)
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL + " - Not selected";

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbox";

        }
    }
    
    public String verifyText(String object, String data){
        APP_LOGS.debug("Verifying the text");
        try{
            String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
            String expected=data;

            if(actual.equals(expected))
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
        }

    }
    
    public  String writeInInput(String object,String data){
        APP_LOGS.debug("Writing in text box");

        try{
        	driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.chord(Keys.CONTROL, "a"), data);
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

        }
        return Constants.KEYWORD_PASS;

    }

    public  String verifyTextinInput(String object,String data){
        APP_LOGS.debug("Verifying the text in input box");
        try{
            String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
            String expected=data;

            if(actual.equals(expected)){
                return Constants.KEYWORD_PASS;
            }else{
                return Constants.KEYWORD_FAIL+" Not matching ";
            }

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

        }
    }

    public  String clickImage(String object, String data){
        APP_LOGS.debug("Clicking the image");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    public  String verifyTitle(String object, String data){
        APP_LOGS.debug("Verifying title");
        try{
            String actualTitle= driver.getTitle();
            String expectedTitle=data;
            if(actualTitle.equals(expectedTitle))
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Error in retrieving title";
        }
    }

    public String exist(String object,String data){
        APP_LOGS.debug("Checking existance of element");
        try{
            driver.findElement(By.xpath(OR.getProperty(object)));
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Object doest not exist";
        }


        return Constants.KEYWORD_PASS;
    }

    public  String click(String object,String data){
        APP_LOGS.debug("Clicking on any element");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Not able to click";
        }
        return Constants.KEYWORD_PASS;
    }

    public  String synchronize(String object,String data){
        APP_LOGS.debug("Waiting for page to load");
        ((JavascriptExecutor) driver).executeScript(
                "function pageloadingtime()"+
                        "{"+
                        "return 'Page has completely loaded'"+
                        "}"+
                        "return (window.onload=pageloadingtime());");
        return Constants.KEYWORD_PASS;
    }

    public  String waitForElementVisibility(String object,String data){
        APP_LOGS.debug("Waiting for an element to be visible");
        int start=0;
        int time=(int)Double.parseDouble(data);
        try{
            while(time == start){
                if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
                    Thread.sleep(1000L);
                    start++;
                }else{
                    break;
                }
            }
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    public  String closeBrowser(String object, String data){
        APP_LOGS.debug("Closing the browser");
        try{
            driver.close();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    public  String deleteAllCookies(String object, String data){
        APP_LOGS.debug("Deleting all the Browser cookies");
        try{
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable delete all the cookies from Browser"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    public  String quitBrowser(String object, String data){
        APP_LOGS.debug("Closing the browser");
        try{
            driver.quit();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    public String pause(String object, String data) throws NumberFormatException, InterruptedException{
        long time = (long)Double.parseDouble(object);
        Thread.sleep(time*1000L);
        return Constants.KEYWORD_PASS;
    }


    /************************APPLICATION SPECIFIC KEYWORDS********************************/
    
    public String selectItemFromList(String object, String data){
    	APP_LOGS.debug("Select item from List");
    	try{
        	WebElement trElement = driver.findElement(By.xpath(OR.getProperty(object)+ "/tbody/tr/td[text()='" + data + "']"));
        	trElement.click();
    	}catch(Exception e){
    		return Constants.KEYWORD_FAIL+" -- Not able to select item";
    	}
    	return Constants.KEYWORD_PASS;
    }
    
    public String setTime(String object, String data){
    	String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    	 try {
    		 Date date = simpleDateFormat.parse(data);
             Runtime.getRuntime().exec("cmd /c date " + simpleDateFormat.format(date));
//             final Process timeProcess = Runtime.getRuntime().exec("cmd /c time "+ "00:00:00");
         } catch (Exception exception) {
             return Constants.KEYWORD_FAIL+" -- Not able to set system time";
         }
    	 return Constants.KEYWORD_PASS;
    }
    
    public String importWarehouse(String object, String data){
    	APP_LOGS.debug("Import Warehouse");
    	try{
        	List<WebElement> trLeftCollection = driver.findElements(By.xpath(OR.getProperty("ProductStockListPopup_trLeftCollection")));
        	List<WebElement> trRightCollection = driver.findElements(By.xpath(OR.getProperty("ProductStockListPopup_trRightCollection")));
        	String [] subData = data.split(Constants.DATA_SPLIT);
        	int position = 0;
        	for (int i = 0; i < subData.length; i++){
        	   	for(WebElement trElement : trLeftCollection){
            		String condition = trElement.findElement(By.xpath("td[2]")).getText();
            		if(condition.equals(subData[i].split(Constants.DATA_SPLIT_TYPE)[0])){
            			List<WebElement> webElementList = trRightCollection.get(position).findElements(By.xpath("td"));
            			String [] stockValue = subData[i].split(Constants.DATA_SPLIT_TYPE)[1].split(Constants.DATA_SPLIT_TYPE_SUB);
            			webElementList.get(2).findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), stockValue[0]);
            			webElementList.get(4).findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), stockValue[1]);
            			webElementList.get(6).findElement(By.tagName("input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), stockValue[2]);
            			position++;
            		}
            	}
        	}
    	}catch(Exception e){
    		return Constants.KEYWORD_FAIL+" -- Not able to import Warehouse";
    	}
    	return Constants.KEYWORD_PASS;
    }
    
    public String getXpath(String object, String data){
    	
    	JavascriptExecutor js;
    	if (driver instanceof JavascriptExecutor) {
    	    js = (JavascriptExecutor)driver;
    	    try{
    	    js.executeScript(
    	    				"var elements = document.querySelectorAll('input,select,a,span,td,div');" + 
    	    				"var myList = new Array();" +
    	    				"for(var i = 0; i < elements.length; i++){" +
    	    					"var xpath = '';" +
    	    					"if (elements[i] && elements[i].id)" +
    	    					"{" +
    	    						"if (elements[i].type != 'hidden')" +
    	    						 	"xpath = '//*[@id=\'' + elements[i].id + '\']';" +
    	    					"}" +
    	    					"else " +
    	    						 "continue;" +
    	    				"}" +
    	    				"else if(elements[i] && checkElement(elements[i]) && elements[i].type != 'hidden'){" +
    	    					"xpath = getElementTreeXPath(elements[i]);" +
    	    				"}" +
    	    				"else" +
    	    					"continue;" +
    	    				
    	    				"var temp = elements[i].id.split('_');" +
    	    				"var col1 = '';" +
    	    				"if(elements[i].id){" +
    	    				"if (temp.length == 1)" +
    	    					"col1 ='" + data + "' +'_' + temp[0];" +
    	    				"else" +
    	    					"col1 ='" + data + "' + '_' + ((temp[temp.length - 2] == 'ContentPlaceHolderBody' || temp[temp.length - 2] == 'ctl00') ? temp[temp.length - 1] : temp[temp.length - 2] + '_' + temp[temp.length - 1]);" +
    	    				"}" +
    	    				"else" +
    	    					"col1 = name + '_GROUP';" +
    	    				"var col2 = col1 + ' ' + '=' + ' ' + xpath;" +
    	    				"var record = new Data(col1,col2);" +
    	    				"myList.push(record);" +
    	    				"}" +
    	    					"var csvData = new Array();" +
    	    			        "myList.forEach(function (item, index, array) {" +
    	    			        "csvData.push('\"' + item.col1 + '\",\"' + item.col2 + '\"');" +
    	    			        "});" +
    	    					
    	    					"var buffer = csvData.join('\\n');" +
    	    			        "var uri = 'data:text/csv;charset=utf8,' + encodeURIComponent(buffer);" +
    	    			        "var fileName ='" + data + "'+ '.csv';" +

    	    			        "var link = document.createElement('a');" +
    	    			        "if (link.download !== undefined) {" +
    	    			            "link.setAttribute('href', uri);" +
    	    			            "link.setAttribute('download', fileName);" +
    	    			        "}" +
    	    			        "else if (navigator.msSaveBlob) {"+
    	    			            "link.addEventListener('click', function (event) {" +
    	    			                "var blob = new Blob([buffer], {" +
    	    			                    "'type': 'text/csv;charset=utf-8;'" +
    	    			                "});" +
    	    			                "navigator.msSaveBlob(blob, fileName);" +
    	    			            "}, false);" +
    	    			        "}" +
    	    			        "else {" +
    	    			            "link.setAttribute('href', 'http://www.example.com/export');" +
    	    			        "}" +
    	    			        "link.innerHTML = 'Export to CSV';" +
    	    			        "var node = document.getElementById(\"header\")" +
    	    			        "if (node != null)" +
    	    			        	"node.appendChild(link);" +
    	    			        "else" +
    	    			        	"document.body.appendChild(link);" +

    	    					  "function Data(col1, col2){" +
    	    			            "this.col1 = col1;" +
    	    			            "this.col2  = col2;" +
    	    			         "}" +
    	    			            
    	    			         "function getElementTreeXPath(element) {" +
    	    				      "var paths = [];" +

    	    				      // Use nodeName (instead of localName) so namespace prefix is included (if any).
    	    				      "for (; element && element.nodeType == 1; element = element.parentNode)  {" +
    	    				          "var index = 0;" +
    	    				          // EXTRA TEST FOR ELEMENT.ID
    	    				          "if (element && element.id) {" +
    	    				              "paths.splice(0, 0, '/*[@id=\'' + element.id + '\']');" +
    	    				              "break;" +
    	    				          "}" +

    	    				          "for (var sibling = element.previousSibling; sibling; sibling = sibling.previousSibling) {" +
    	    				              // Ignore document type declaration.
    	    				              "if (sibling.nodeType == Node.DOCUMENT_TYPE_NODE)" +
    	    				                "continue;" +

    	    				              "if (sibling.nodeName == element.nodeName)" +
    	    				                  "++index;" +
    	    				          "}" +

    	    				          "var tagName = element.nodeName.toLowerCase();" +
    	    				          "var pathIndex = (index ? \"[\" + (index+1) + \"]\" : \"\");" +
    	    				          "paths.splice(0, 0, tagName + pathIndex);" +
    	    				      "}" +

    	    				      "return paths.length ? \"/\" + paths.join(\"/\") : null;" +
    	    				      
								"function checkElement(element){" +
	    	    					"for(var i=0; i<element.childNodes.length;i++){" +
	    	    						"var child = element.childNodes[i];" +
	    	    						"if (child.nodeType == 1 && (child.getAttribute(\"type\") == \"radio\" || child.getAttribute(\"type\") == \"checkbox\" ))" +
	    	    								"return 1;" +
	    	    					"}" +
	    	    					"return 0;" +
    	    				  	"}"
    	    );
    	    driver.findElement(By.linkText("Export to CSV")).click();
    	    }catch(Exception e){
        		return Constants.KEYWORD_FAIL+" -- Not able to get Xpath";
        	}
    	}
    	return Constants.KEYWORD_PASS;
    }
    
    public String navigateToPage(String object,String data){
        APP_LOGS.debug("Navigating to URL");
        try{
        	driver.get(data);
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" -- Not able to navigate";
        }
        return Constants.KEYWORD_PASS;
    }
    
    public  String selectRadioList(String object, String data){
        APP_LOGS.debug("Selecting a radio button");
        try{
        	if(data.isEmpty())
        		return "The value of Radio is not changed";
        	
        	List<WebElement> elementList = driver.findElement(By.xpath(OR.getProperty(object))).findElements(By.tagName("input"));
        	String[] subString = data.split(Constants.DATA_SPLIT_TYPE_SUB);
        	elementList.get(Integer.parseInt(subString[0])).click();
        	Thread.sleep(1000);
        	if(subString.length > 1){
        		elementList.get(Integer.parseInt(subString[1])).click();
        	}
        	}catch(Exception e){
            return Constants.KEYWORD_FAIL +"- Not able to find radio button";
        }
        return Constants.KEYWORD_PASS;
    }
    
    public  String inputSpecificKeyword(String object,String data){
        APP_LOGS.debug("inputSpecificKeyword");
        try{
        	if(data.isEmpty()) 
        		return "All field are not changed";
        	String[] temp = data.split(Constants.DATA_SPLIT);
        	String type = temp[0].split(Constants.DATA_SPLIT_TYPE)[0];
        	if (type.equals("rb"))
        		selectRadioList(object, temp[0].split(Constants.DATA_SPLIT_TYPE)[1]);
        	else if (type.equals("cb"))
        		checkCheckBoxList(object, temp[0].split(Constants.DATA_SPLIT_TYPE)[1]);
        	input(object, temp[1]);
        }catch(Exception e){
        	return Constants.KEYWORD_FAIL + e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }
       
    public String unCheckAllCheckBox(String object,String data){
        APP_LOGS.debug("Unchecking allCheckBox");
        try{
        	List<WebElement> elementList = driver.findElement(By.xpath(OR.getProperty(object))).findElements(By.tagName("input"));
        	for(int i=0; i < elementList.size(); i++){
        		if(elementList.get(i).getAttribute("type")=="checkbox" && elementList.get(i).getAttribute("checked")!= null)
        			elementList.get(i).click();
        	}
//            String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
//            if(checked!=null)
//                driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbox";
        }
        return Constants.KEYWORD_PASS;
    }
    
    public  String checkCheckBoxList(String object,String data){
        APP_LOGS.debug("Checking checkbox");
        try{
        	if(data.isEmpty())
        		return "The value of check box are not changed";
        	unCheckAllCheckBox(object,"");
        	String values[] = data.split(Constants.DATA_SPLIT_TYPE_SUB);
        	for(int i=0; i < values.length; i++){
        		List<WebElement> elementList = driver.findElement(By.xpath(OR.getProperty(object))).findElements(By.tagName("input"));
        		WebElement checkbox = elementList.get(Integer.parseInt(values[i]));
        		if(checkbox.getAttribute("checked")==null)// checkbox is unchecked
        			checkbox.click();
//        		Thread.sleep(100);
        	}
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" - Could not find checkbox";
        }
        return Constants.KEYWORD_PASS;
    }
    
    public  String input(String object,String data){
        APP_LOGS.debug("input");
        try{
        	if(data.isEmpty())
        		return "The value of field are not changed";
        	WebElement objectElement = driver.findElement(By.xpath(OR.getProperty(object)));
        	List<WebElement> elementList = objectElement.findElements(By.tagName("input"));
        	if (!elementList.isEmpty())
        		for(WebElement element:elementList)
        			if(element.getAttribute("type")=="text")
        				element.clear();
        	List<WebElement> selectControlElementList = objectElement.findElements(By.tagName("select"));
        	elementList.addAll(selectControlElementList);
        	String[] temp = data.split(Constants.DATA_SPLIT_TYPE_SUB);
        	for(int i=0; i< temp.length; i++){
        		elementList.get(Integer.parseInt(temp[i].split(":")[0])).sendKeys(temp[i].split(":")[1]);
        	}
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

        }
        return Constants.KEYWORD_PASS;
    }

    public  String enter(String object, String data){
        APP_LOGS.debug("Going back one page");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.ENTER);
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to go back, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    public  String tab(String object, String data){
        APP_LOGS.debug("Going back one page");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.TAB);
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to go back, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    public String windowHandler(String object, String data){
        APP_LOGS.debug("Handling multiple windows");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        try{
            String mainWindowHandle=driver.getWindowHandle();
            driver.findElement(By.xpath(OR.getProperty(object))).click();
            Set<String> window = driver.getWindowHandles();
            Iterator<String> iterator= window.iterator();

            while(iterator.hasNext())
            {
                String popupHandle=iterator.next().toString();
                if(!popupHandle.contains(mainWindowHandle))
                {
                    driver.switchTo().window(popupHandle);
                    driver.navigate().to("javascript:document.getElementById('overridelink').click()");	
                }
            }

            //Back to main window
            //driver.switchTo().window(mainWindowHandle);

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to handle windows, See log4j report for more info"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }
    
    public String switchToPopup(String object, String data){
    	return Constants.KEYWORD_PASS;
    }

    public  String click_JS(String object,String data){
        APP_LOGS.debug("Clicking on any element using JavaScript");
        try {
            ((JavascriptExecutor)driver).executeScript("document.getElementById('"+object+"').click()");
        } catch (Exception e) {
            return Constants.KEYWORD_FAIL+"Unable to click, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    public  String writeInInput_JS(String object,String data){
        APP_LOGS.debug("Writing in text box using JavaScript");
        try {
            ((JavascriptExecutor)driver).executeScript("document.getElementById('"+object+"').value='"+data+"'");
        } catch (Exception e) {
            return Constants.KEYWORD_FAIL+"Unable to write, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    // << Go back one page
    public  String goBack(String object, String data){
        APP_LOGS.debug("Going back one page");
        try{
            driver.navigate().back();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to go back, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    // >> Go to forward one page
    public  String goForward(String object, String data){
        APP_LOGS.debug("Going back one page");
        try{
            driver.navigate().forward();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to go back, Check if its open"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    public  String chrodKeys(String object,String data){
        APP_LOGS.debug("Selecting in text box");

        try{
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Keys.chord(data));
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Unable to select "+e.getMessage();

        }
        return Constants.KEYWORD_PASS;

    }

    public  String clearInputText(String object,String data){
        APP_LOGS.debug("Clearing input text box");

        try{
            driver.findElement(By.xpath(OR.getProperty(object))).clear();
        }catch(Exception e){
            return Constants.KEYWORD_FAIL+" Unable to clear input text "+e.getMessage();

        }
        return Constants.KEYWORD_PASS;

    }

    public  String mouseHover(String object, String data){
        APP_LOGS.debug("Mouse Hovering to the element");
        try{

            Thread.sleep(3000L);
            WebElement menu = driver.findElement(By.xpath(OR.getProperty(object)));
            Actions builder = new Actions(driver);
            builder.moveToElement(menu).build().perform();

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to mouse hover"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    public  String doubleClick(String object, String data){
        APP_LOGS.debug("Mouse Hovering to the element");
        try{

            Thread.sleep(3000L);
            WebElement menu = driver.findElement(By.xpath(OR.getProperty(object)));
            Actions builder = new Actions(driver);
            builder.doubleClick(menu).build().perform();

        }catch(Exception e){
            return Constants.KEYWORD_FAIL+"Unable to mouse hover"+e.getMessage();
        }
        return Constants.KEYWORD_PASS;

    }

    // not a keyword

	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
        // take screen shots
		String[] name = filename.split(Constants.DATA_SPLIT_NAME);
        if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
            // capturescreen

            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+name[0]+"//"+name[1]+"//"+name[2]+"//"+name[3]+".jpg"));

        }else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
            // capture screenshot
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
        }
    }
}
