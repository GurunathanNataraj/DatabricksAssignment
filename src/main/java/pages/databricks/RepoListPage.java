package pages.databricks;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RepoListPage {

    WebDriver driver;
    WebDriverWait wait;

    public RepoListPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By pageHeader = By.xpath("//div[contains(@class,'MuiTypography-root')]");
    private By databricksLogo = By.xpath("//img[@src='db_icon_orange.png']");
    private By searchBox = By.xpath("//input[@placeholder='Search']");
    private By tableHeaders = By.xpath("//th[contains(@class,'MuiTableCell-head')]");
    private By noDataFoundMsg = By.xpath("//h6[text()='No Data Found']");
    private By paginationDropdown = By.xpath("//p[text()='Rows per page:']/parent::div/div[@variant='standard']");
    private By paginationOptions = By.xpath("//li[contains(@class,'MuiTablePagination-menuItem')]");
    private String paginationOption = "//li[contains(@class,'MuiTablePagination-menuItem') and text()='Replace_Me']";
    private By loadingIcon  = By.xpath("//div[@data-testid='bars-loading']");
    private By namesInTable = By.xpath("//table/tbody/tr/td[1]");
    private By ownersInTable = By.xpath("//table/tbody/tr/td[2]");
    private By startsInTable = By.xpath("//table/tbody/tr/td[3]");
    private By linkedInTable = By.xpath("//table/tbody/tr/td[4]/a");
    private By rowsInTable = By.xpath("//table/tbody/tr");
    private By infoIcon = By.xpath("//span[contains(@class,'MuiIconButton')]");
    private By tooltipMsg = By.xpath("//div[contains(@class,'tooltip') and text()='Get Details']");
    private By detailsDialogHeader = By.xpath("//h2[@id='customized-dialog-title']");
    private By committersInfo = By.xpath("//p[contains(., 'Last 3 committers')]/following-sibling::p");
    private By recentForkedUserInfo = By.xpath("//p[contains(., 'Recent Forked User')]/following-sibling::p");
    private By recenForkedUserBioLabel = By.xpath("//p[contains(., 'Recent Forked User Bio:')]");
    private By okButton = By.xpath("//button[text()='Ok']");
    private By closeButton = By.xpath("//button[@aria-label='close']");
    private By searchIcon = By.xpath("//button[@aria-label='search']");
    private By currentPaginationValue = By.xpath("//div[contains(@class,'MuiTablePagination') and @role='button']");
    private By nextButton = By.xpath("//button[@title='Go to next page']");
    private By previousButton = By.xpath("//button[@title='Go to previous page']");
    private By paginationLabel = By.xpath("//p[contains(@class,'MuiTablePagination-displayedRows')]");
    private By errorMsgAfterSearch = By.xpath("//div[@class='swal2-popup swal2-toast swal2-icon-error swal2-show']");

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageHeader() {
        return driver.findElement(pageHeader).getText();
    }

    public Boolean isLogoDisplayed() {
        return driver.findElement(databricksLogo).isDisplayed();
    }

    public Boolean isSearchBoxDisplayed() {
         return driver.findElement(searchBox).isDisplayed();
    }

    public List<String> getTableHeaders() {
        return driver.findElements(tableHeaders).stream()
                .map(s -> s.getText()).collect(Collectors.toList());
    }

    public Boolean isNoDataFoundMessageDisplayed() {
        return driver.findElement(noDataFoundMsg).isDisplayed();
    }

    public Boolean isPaginationDropdownDisplayed() {
        return driver.findElement(paginationDropdown).isDisplayed();
    }

    public List<String> getPaginationDropDownOptions() {
        return driver.findElements(paginationOptions).stream()
                .map(s -> s.getText()).collect(Collectors.toList());
    }

    public void clickPaginationDropDown() {
        driver.findElement(paginationDropdown).click();
    }

    public void enterTheValueInSearchBox(String value) {
        driver.findElement(searchBox).sendKeys(value);
    }

    public List<String> getTheNames() {
        return driver.findElements(namesInTable).stream()
                .map(s -> s.getText())
                .collect(Collectors.toList());
    }

    public int getTheNumberOfRows() {
        return driver.findElements(rowsInTable).size();
    }

    public List<String> getTheValueInAllTheCells() {
        List<String> list = new ArrayList<>();
        for(int i=1;i<=driver.findElements(rowsInTable).size();i++) {
            for(int j=1;j<=4;j++) {
                list.add(driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td["+j+"]")).getText());
            }
        }
        return list;
    }

    public Boolean validateTheLinkFormation() {
        Boolean flag = true;
        List<WebElement> name = driver.findElements(namesInTable);
        List<WebElement> owner = driver.findElements(ownersInTable);
        List<WebElement> link = driver.findElements(linkedInTable);
        for(int i=0;i<driver.findElements(rowsInTable).size();i++) {
            if(!((owner.get(i).getText()+"/"+name.get(i).getText()).equals(link.get(i).getText()))) {
               return false;
            }
        }
        return true;
    }

    public String clickOnTheRandomLink() throws InterruptedException {
        String link;
        Random random = new Random();
        int value = random.nextInt(3)+1;
        List<WebElement> links = driver.findElements(linkedInTable);
        link = links.get(value).getText();
        links.get(value).click();
//        JavascriptExecutor executor = (JavascriptExecutor)driver;
//        executor.executeScript("arguments[0].click();", links.get(value));
//        System.out.println("Random value : "+value);
        Thread.sleep(5000);
        return link;
    }

    public String getTheGithubWindowTitle() {
        String title;
        String parentWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for(String window : windows) {
            if(!parentWindow.equals(window)) {
                driver.switchTo().window(window);
                break;
            }
        }
        title =driver.getTitle();
        driver.switchTo().window(parentWindow);
        return title;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public List<String> getTheStars() {
        return driver.findElements(startsInTable).stream()
                .map(s->s.getText()).collect(Collectors.toList());
    }

    public void hoverOverOnTheDetailsIcon() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(infoIcon)).build().perform();
    }

    public Boolean isToolTipMsgDisplayed() {
        return driver.findElement(tooltipMsg).isDisplayed();
    }

    public String getName(){
        return driver.findElement(namesInTable).getText();
    }

    public String getOwner() {
        return driver.findElement(ownersInTable).getText();
    }

    public String getLink() {
        return driver.findElement(linkedInTable).getText();
    }

    public void clickOnTheDetailsIcon() {
        driver.findElement(infoIcon).click();
    }

    public String getDetailsDialogHeader() {
        return driver.findElement(detailsDialogHeader).getText();
    }

    public String getLastCommitters() {
        return driver.findElement(committersInfo).getText();
    }

    public String getForkedUser() {
        return driver.findElement(recentForkedUserInfo).getText();
    }

    public Boolean isForkedUserBioDisplayed() {
        return driver.findElement(recenForkedUserBioLabel).isDisplayed();
    }

    public void clickOnCloseIcon() {
        driver.findElement(closeButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(detailsDialogHeader));
    }

    public int isRepoDetailsDialogDisplayed() {
        return driver.findElements(detailsDialogHeader).size();
    }

    public void clickOnOkButton() throws InterruptedException {
        driver.findElement(okButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(detailsDialogHeader));
        Thread.sleep(2000);
        }

    public void clickOnSearchIcon() {
        driver.findElement(searchIcon).click();
    }

    public String getThePaginationValue() {
        return driver.findElement(currentPaginationValue).getText();
    }

    public void selectPaginationValue(String value) {
        driver.findElement(currentPaginationValue).click();
        driver.findElement(By.xpath(paginationOption.replace("Replace_Me",value))).click();
    }

    public void clickOnNextPaginationButton() {
        driver.findElement(nextButton).click();
    }

    public Boolean getStateOfPaginationButton(String type,String state) {
        if(type.equalsIgnoreCase("previous")) {
            if(state.equalsIgnoreCase("enabled")) {
                return driver.findElement(previousButton).isEnabled();
            } else if(state.equalsIgnoreCase("disabled")) {
                return !driver.findElement(previousButton).isEnabled();
            } else {
                System.out.println("Please pass the correct type");
                return false;
            }
        } else if (type.equalsIgnoreCase("next")) {
            if(state.equalsIgnoreCase("enabled")) {
                return driver.findElement(nextButton).isEnabled();
            } else if(state.equalsIgnoreCase("disabled")) {
                return !driver.findElement(nextButton).isEnabled();
            } else {
                System.out.println("Please pass the correct type");
                return false;
            }
        } else {
            System.out.println("Please pass the correct type");
            return false;
        }
    }

    public void waitTillLoaderToBeDisappeared() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
    }

    public int getTheTotalFilteredResultCount() {
        waitTillLoaderToBeDisappeared();
        return Integer.parseInt(driver.findElement(paginationLabel)
                .getText()
                .split(" of ")[1]);
    }

    public Boolean isAPILimitErrorMessageDisplaying() {
        return driver.findElements(errorMsgAfterSearch).size()>0 ? true: false;

    }

}
