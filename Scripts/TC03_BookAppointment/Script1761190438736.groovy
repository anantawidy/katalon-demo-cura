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

WebUI.callTestCase(findTestCase('TC01_Login'), [('username') : 'John Doe', ('password') : 'ThisIsNotAPassword', ('expected_result') : ''], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.selectOptionByLabel(findTestObject('Appointment/Page_CURA Healthcare Service/select_TokyoCura'), facility, false)

if (readmission.toBoolean()) {
    WebUI.check(findTestObject('Appointment/Page_CURA Healthcare Service/check_Readmission'))
} else {
    WebUI.uncheck(findTestObject('Appointment/Page_CURA Healthcare Service/check_Readmission'))
}

if (healthcare_program == 'Medicare') {
    WebUI.click(findTestObject('Appointment/Page_CURA Healthcare Service/input_Medicare_programs'))
} else if (healthcare_program == 'Medicaid') {
    WebUI.click(findTestObject('Appointment/Page_CURA Healthcare Service/input_Medicaid_programs'))
} else if (healthcare_program == 'None') {
    WebUI.click(findTestObject('Appointment/Page_CURA Healthcare Service/input_None_programs'))
}

if (visit_date != '') {
    TestObject dateField = findTestObject('Appointment/Page_CURA Healthcare Service/input_VisitDate')
    WebUI.click(dateField)
    WebUI.delay(1)
    WebUI.executeJavaScript("arguments[0].removeAttribute('readonly')", Arrays.asList(WebUI.findWebElement(dateField)))
    WebUI.setText(dateField, visit_date)
}

if (comment != '') {
    WebUI.setText(findTestObject('Appointment/Page_CURA Healthcare Service/textarea_Comment'), comment)
}

WebUI.click(findTestObject('Appointment/Page_CURA Healthcare Service/button_Book Appointment'))

WebUI.waitForPageLoad(10)

if (expected_result.toLowerCase().contains('success')) {
    WebUI.verifyElementClickable(findTestObject('Appointment/Page_CURA Healthcare Service/a_Go to Homepage'), FailureHandling.STOP_ON_FAILURE)
} 
else if (expected_result.toLowerCase().contains('error')) {
    // Cek native HTML5 validation message untuk field visit_date
    TestObject dateField = findTestObject('Appointment/Page_CURA Healthcare Service/input_VisitDate')
	WebUI.delay(1)
	String validationMsg = WebUI.getAttribute(dateField, "validationMessage")
	WebUI.comment("Validation message: " + validationMsg)
	
	WebUI.verifyMatch(validationMsg, "Please fill out this field.", false)
}