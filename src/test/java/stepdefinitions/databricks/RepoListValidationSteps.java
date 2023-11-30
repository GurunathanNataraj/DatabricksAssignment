package stepdefinitions.databricks;

import driver.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.databricks.RepoListPage;

import java.util.Arrays;

public class RepoListValidationSteps {

    private RepoListPage repoList = new RepoListPage(DriverFactory.getDriver());
    @Given("user is on the repository list page")
    public void user_is_on_the_repository_list_page() {
        DriverFactory.getDriver().get("http://localhost:3000/");
    }

    @Then("Page title is {string}")
    public void page_title_is(String title) {
        Assert.assertEquals(title,repoList.getPageTitle());
    }

    @Then("Databricks logo should be displayed")
    public void databricks_logo_should_be_displayed() {
        Assert.assertTrue(repoList.isLogoDisplayed());
    }

    @Then("Page Header is {string}")
    public void page_header_is(String header) {
        Assert.assertEquals(header,repoList.getPageHeader());
    }

    @Then("Search box should be displayed")
    public void search_box_should_be_displayed() {
        Assert.assertTrue(repoList.isLogoDisplayed());
    }

    @Then("Following columns should be present in the table")
    public void following_columns_should_be_present_in_the_table(DataTable dataTable) {
        Assert.assertTrue(dataTable.asList().containsAll(repoList.getTableHeaders()));
    }

    @Then("No Data Found message should be displayed")
    public void no_data_found_message_should_be_displayed() {
        Assert.assertTrue(repoList.isNoDataFoundMessageDisplayed());
    }

    @Then("Rows per page dropdown should be displayed with below values")
    public void rows_per_page_dropdown_should_be_displayed_with_below_values(DataTable dataTable) {
        repoList.clickPaginationDropDown();
        Assert.assertTrue(dataTable.asList().containsAll(repoList.getPaginationDropDownOptions()));
    }

    @Given("Enter the value {string} in the search box")
    public void enterTheValueInTheSearchBox(String searchValue) {
        repoList.enterTheValueInSearchBox(searchValue);
        repoList.clickOnSearchIcon();
        if(repoList.isAPILimitErrorMessageDisplaying())  {
            Assert.assertTrue("API Limit exceeded for the search call. please try after some time",false);
        }
    }


    @Then("Validate all the filtered results name should has the {string}")
    public void validateAllTheFilteredResultsNameShouldHasThe(String searchValue) {
        for(String name : repoList.getTheNames()) {
            Assert.assertTrue("Filtered value does not contains the search value : "+searchValue,name.contains(searchValue));
        }
    }

    @And("only {string} rows should display and all the values should be not null")
    public void onlyRowsShouldDisplayAndAllTheValuesShouldBeNotNull(String count) {
        Assert.assertEquals("displaying number of rows not matching with the pagination value", Integer.parseInt(count),repoList.getTheNumberOfRows());
        for(String value : repoList.getTheValueInAllTheCells())
        {
            Assert.assertNotEquals("One of the cell is empty","",value);
        }
    }

    @And("link values should be the combination of owner and name values")
    public void linkValuesShouldBeTheCombinationOfOwnerAndNameValues() {
        Assert.assertTrue("Mistake in the link formation. usually it is the combination of owner and repo name",repoList.validateTheLinkFormation());
    }

    String link;

    @When("Click on the random link")
    public void clickOnTheRandomLink() throws InterruptedException {
        link = repoList.clickOnTheRandomLink();
    }

    @Then("Github page should opens in the new tab")
    public void githubPageShouldOpensInTheNewTab() {
        System.out.println("Actual title : "+repoList.getTheGithubWindowTitle());
        System.out.println("Expected Title : "+"GitHub - "+link+":");
        Assert.assertTrue("Github window title mismatches",repoList.getTheGithubWindowTitle().contains("GitHub - "+link+":"));
    }

    @And("stars value should be the number")
    public void starsValueShouldBeTheNumber() {
        for(String num : repoList.getTheStars()) {
            Assert.assertTrue("Star value is not numeric",repoList.isNumeric(num));
        }
    }

    @When("hover over on the details icon")
    public void hoverOverOnTheDetailsIcon() {
        repoList.hoverOverOnTheDetailsIcon();
    }

    @Then("Get Details text should be displayed")
    public void getDetailsTextShouldBeDisplayed() {
        Assert.assertTrue("Tooltip message is not displayed",repoList.isToolTipMsgDisplayed());
    }

    @Then("Validate the repo details header.it should contains the combination of name and owner of the repository")
    public void validateTheRepoDetailsHeaderItShouldContainsTheCombinationOfNameAndOwnerOfTheRepository() {
        repoList.waitTillLoaderToBeDisappeared();
        Assert.assertEquals("Details dialog header is mismatching","Repo Details - "+repoList.getLink(),repoList.getDetailsDialogHeader());
    }

    @And("Last {int} committers label should be displayed with values")
    public void lastCommittersLabelShouldBeDisplayedWithValues(int count) {
        int actualCount  = Arrays.stream(repoList.getLastCommitters().split(",")).toList().size();
        Assert.assertEquals("Expected no of committers are there",count,actualCount);
    }

    @And("Recent Forked User label should be displayed with value")
    public void recentForkedUserLabelShouldBeDisplayedWithValue() {
        Assert.assertTrue("Last Forked User value is Empty",!repoList.getForkedUser().equals(""));
    }

    @And("Recent Forked User Bio label should be displayed")
    public void recentForkedUserBioLabelShouldBeDisplayed() {
        Assert.assertTrue("Recent Forked user bio is not displayed",repoList.isForkedUserBioDisplayed());
    }

    @When("Click on the close icon")
    public void clickOnTheCloseIcon() {
        repoList.clickOnCloseIcon();
    }

    @Then("Repo details dialog should not be displayed")
    public void repoDetailsDialogShouldNotBeDisplayed() {
        Assert.assertEquals("Repo Dialog Header should display",0,repoList.isRepoDetailsDialogDisplayed());
    }

    @When("Click on the details icon")
    public void clickOnTheDetailsIcon() {
        repoList.clickOnTheDetailsIcon();
    }

    @And("Click on the OK button")
    public void clickOnTheOKButton() throws InterruptedException {
        repoList.clickOnOkButton();
    }

    @Given("By default pagination value should be {string};")
    public void byDefaultPaginationValueShouldBe(String expectedValue) {
        repoList.waitTillLoaderToBeDisappeared();
        Assert.assertEquals("Default Pagination value in wrong",expectedValue,repoList.getThePaginationValue());
    }

    @Then("{string} data rows should be displayed")
    public void dataRowsShouldBeDisplayed(String expectedValue) {
        repoList.waitTillLoaderToBeDisappeared();
        Assert.assertEquals("Pagination value is not equals to the no. of rows displaying",Integer.parseInt(expectedValue),repoList.getTheNumberOfRows());
    }

    @When("Select the value of {string}")
    public void selectTheValueOf(String paginationValue) {
        repoList.selectPaginationValue(paginationValue);
    }

    @And("Pagination {string} button should be {string}")
    public void paginationButtonShouldBe(String type, String state) {
        repoList.waitTillLoaderToBeDisappeared();
        Assert.assertTrue(repoList.getStateOfPaginationButton(type,state));
    }

    @When("Click on the pagination Next button")
    public void clickOnThePaginationNextButton() {
        repoList.clickOnNextPaginationButton();
    }

    @Then("Validate all the filtered results displaying")
    public void validate_all_the_filtered_results_displaying() {
        int expectedCountOfFilteredResults = repoList.getTheTotalFilteredResultCount();
        int actualCount=0;
        while(expectedCountOfFilteredResults>actualCount) {
            actualCount+=repoList.getTheNumberOfRows();
            if(repoList.getStateOfPaginationButton("next","enabled"))  {
                repoList.clickOnNextPaginationButton();
            }
            repoList.waitTillLoaderToBeDisappeared();
        }
        Assert.assertEquals("Number of rows in the pagination section and filtered results section are mismatching",expectedCountOfFilteredResults,actualCount);
    }

    @Then("At the last page Pagination {string} button should be {string}")
    public void at_the_last_page_pagination_button_should_be(String type, String state) {
        Assert.assertTrue("In the last page, Next button is enabled",repoList.getStateOfPaginationButton(type,state));
    }
}
