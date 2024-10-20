package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Assert;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class VisureLoginSteps {

    private static WiniumDriver driver;
    private String appPath = "C:\\Program Files (x86)\\Visure Solutions, Inc\\Visure Requirements ALM 8 Client\\VisureRequirements.exe";
    private String driverURL = "http://localhost:9999";

    @When("Launch the Visure ALM Client")
    public void launchVisureALMClient() throws MalformedURLException, InterruptedException {
        DesktopOptions option = new DesktopOptions();
        option.setApplicationPath(appPath);
        URL winiumURL = new URL(driverURL);
        driver = new WiniumDriver(winiumURL, option);

        // Allow time for the application to launch
        Thread.sleep(4000);

        // Bring the application window to the foreground using Robot
        try {
            Robot robot = new Robot();
            for (int i = 0; i < 3; i++) {  // Retry mechanism
                robot.keyPress(KeyEvent.VK_ALT);
                robot.keyPress(KeyEvent.VK_TAB);
                Thread.sleep(500);  // Small delay to ensure window focus
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_ALT);
            }
            System.out.println("Application window is brought to the foreground using Robot.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to bring the application window to the foreground.");
        }

        // Assert the login page is displayed
        WebElement loginLabel = driver.findElement(By.id("5708"));  // Use AutomationId or Name
        Assert.assertTrue("Login label is not displayed.", loginLabel.isDisplayed());
        String actualText = loginLabel.getAttribute("Name"); // Retrieve text using "Name" attribute
        Assert.assertEquals("Log in to VisureDB", actualText);


    }

    @When("I check if the username {string} is already there")
    public void check_if_username_exists(String username) {
        WebElement userField = driver.findElement(By.name("User"));
        String currentUsername = userField.getAttribute("Name"); // Get the current value in the field

        if (!currentUsername.equals(username)) {
            userField.clear();
            userField.sendKeys(username);
            System.out.println("Entered username: " + username);
        } else {
            System.out.println("Username is already entered.");
        }
    }

    @When("I enter a valid password {string}")
    public void i_enter_a_valid_password(String password) {
        WebElement passwordField = driver.findElement(By.name("Password"));
        passwordField.clear(); // Ensure field is cleared before entering new password
        passwordField.sendKeys(password);
        System.out.println("Entered password.");
    }

    @Then("Validate all the labels and elements")
    public void validate_all_labels_and_elements() {
        // Validate the labels on the login window
        WebElement userLabel = driver.findElement(By.name("User"));
        WebElement passwordLabel = driver.findElement(By.name("Password"));
        WebElement projectLabel = driver.findElement(By.name("Project"));
        WebElement userGroupLabel = driver.findElement(By.name("User Group"));

        Assert.assertTrue(userLabel.isDisplayed());
        Assert.assertTrue(passwordLabel.isDisplayed());
        Assert.assertTrue(projectLabel.isDisplayed());
        Assert.assertTrue(userGroupLabel.isDisplayed());

        System.out.println("All labels are visible.");

        // Validate the buttons
        WebElement loginButton = driver.findElement(By.name("Login"));
        WebElement cancelButton = driver.findElement(By.name("Cancel"));
        WebElement ssoButton = driver.findElement(By.name("Log In with Single Sign-On (SSO)"));

        Assert.assertTrue(cancelButton.isDisplayed());
        Assert.assertTrue(ssoButton.isDisplayed());

        // Check if the login button is disabled by default
        Assert.assertFalse(loginButton.isEnabled());
        System.out.println("Login button is disabled by default.");

        // Validate the Visure logo
        WebElement visureLogo = driver.findElement(By.className("Static")); // Adjust className or locator as necessary
        Assert.assertTrue(visureLogo.isDisplayed());
        System.out.println("Visure logo is visible.");
    }

    @When("I select {string} project")
    public void i_select_the_project(String projectName) {
        try {
            Thread.sleep(2000);
            // Locate the project dropdown and click to expand
            //5604

            WebElement projectDropDown = driver.findElement(By.id("5604"));
            projectDropDown.click();

            // Allow some time for the dropdown to expand and options to appear
            Thread.sleep(1000);

            // Now attempt to locate the desired project option
            WebElement projectOption = null;
            try {
                // Try to locate the project option by its name
                projectOption = driver.findElement(By.name(projectName));
            } catch (Exception e) {
                System.out.println("Project option not visible yet. Attempting alternative navigation.");
            }

            if (projectOption != null) {
                projectOption.click();
            } else {
                // Use keyboard navigation as an alternative approach
                projectDropDown.sendKeys(Keys.ARROW_DOWN);
                Thread.sleep(500); // Adjust sleep time if necessary

                for (int i = 0; i < 5; i++) { // Adjust number of attempts based on the position of the project
                    projectDropDown.sendKeys(Keys.ARROW_DOWN);
                    Thread.sleep(500); // Wait a bit between each arrow down
                }

                projectDropDown.sendKeys(Keys.ENTER); // Select the highlighted option
            }

            System.out.println("Selected project: " + projectName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to select the project: " + projectName);
        }
    }

    @When("I click the {string} button")
    public void i_click_the_button(String buttonName) {
        WebElement button = driver.findElement(By.name(buttonName));
        button.click();
        System.out.println("Clicked button: " + buttonName);
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        WebElement dashboard = driver.findElement(By.name("Dashboard"));  // Replace with actual dashboard element
        Assert.assertTrue("Login Failed.", dashboard.isDisplayed());
        System.out.println("Login Successful.");
    }
}
