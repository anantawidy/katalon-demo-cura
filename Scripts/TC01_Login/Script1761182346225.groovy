import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('https://katalon-demo-cura.herokuapp.com/')

WebUI.maximizeWindow()

WebUI.click(findTestObject('Login/Page_CURA Healthcare Service/a_CURA Healthcare_menu-toggle'))

WebUI.click(findTestObject('Login/Page_CURA Healthcare Service/a_Login'))

WebUI.setText(findTestObject('Login/Page_CURA Healthcare Service/input_Username'), username)

WebUI.setText(findTestObject('Login/Page_CURA Healthcare Service/input_Password'), password)

WebUI.click(findTestObject('Login/Page_CURA Healthcare Service/button_Login'))

String expected = expected_result.toLowerCase()

if ((expected_result != null) && (expected_result != '')) {
    if (expected.contains('success')) {
        WebUI.verifyElementPresent(findTestObject('Appointment/Page_CURA Healthcare Service/button_Book Appointment'), 5)
    } else if (expected.contains('')) {
        WebUI.verifyElementPresent(findTestObject('Login/Page_CURA Healthcare Service/div_LoginFailed'), 5)

        WebUI.verifyElementText(findTestObject('Login/Page_CURA Healthcare Service/div_LoginFailed'), 'Login failed! Please ensure the username and password are valid.')
    }
}

