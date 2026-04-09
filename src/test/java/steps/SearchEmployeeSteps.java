package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;

import java.util.Map;

public class SearchEmployeeSteps extends CommonMethods {

    Map<String, String> data;

    @When("user navigates to employee search page")
    public void user_navigates_to_employee_search_page() {
        click(dashboardPage.pimMenu);
        click(searchEmployeePage.employeeListTab);
    }

    @And("user searches employee by full name from search employee excel row {int}")
    public void user_searches_employee_by_full_name_from_search_employee_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.SEARCH_EMPLOYEE_SHEET,
                rowNum
        );
        String empName = data.get("EmployeeName").trim();
        sendText(empName, searchEmployeePage.employeeNameField);
        //selectEmployeeFromVisibleSuggestions(empName);
        click(searchEmployeePage.searchButton);
        waitForSearchResultsToLoad();
    }
    @And("user searches unmatched employee name from search employee excel row {int}")
    public void user_searches_unmatched_employee_name_from_search_employee_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.SEARCH_EMPLOYEE_SHEET,
                rowNum
        );

        sendText(data.get("EmployeeName").trim(), searchEmployeePage.employeeNameField);
        click(searchEmployeePage.searchButton);
    }

    @And("user searches employee by partial name from search employee excel row {int}")
    public void user_searches_employee_by_partial_name_from_search_employee_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.SEARCH_EMPLOYEE_SHEET,
                rowNum
        );

        sendText(data.get("EmployeeName"), searchEmployeePage.employeeNameField);
        click(searchEmployeePage.searchButton);
        waitForSearchResultsToLoad();
    }

    @And("user searches employee by ID from search employee excel row {int}")
    public void user_searches_employee_by_id_from_search_employee_excel_row(Integer rowNum) {
        data = ExcelReader.readRow(
                Constants.EXCEL_FILE_PATH,
                Constants.SEARCH_EMPLOYEE_SHEET,
                rowNum
        );

        sendText(data.get("EmployeeId"), searchEmployeePage.employeeIdField);
        click(searchEmployeePage.searchButton);
        waitForSearchResultsToLoad();
    }

    @Then("matching employee records are displayed")
    public void matching_employee_records_are_displayed() {
        waitForSearchResultsToLoad();

        Assert.assertTrue(
                "No employee records found",
                !searchEmployeePage.resultRows.isEmpty()
        );
    }

    @Then("no records found message is displayed")
    public void no_records_found_message_is_displayed() {
        Assert.assertTrue(isElementDisplayed(searchEmployeePage.noRecordsFoundMessage));
        Assert.assertEquals("No Records Found", getText(searchEmployeePage.noRecordsFoundMessage));
    }
}