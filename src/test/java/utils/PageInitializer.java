package utils;
import pages.*;

public class PageInitializer {

    public static LoginPage loginPage;
    public static DashboardPage dashboardPage;
    public static AddEmployeePage addEmployeePage;
    public static SearchEmployeePage searchEmployeePage;
    public static CreateLoginDetailsPage createLoginDetailsPage;
    public static PersonalDetailsPage personalDetailsPage;
    public static ContactDetailsPage contactDetailsPage;
    public static DependentsPage dependentsPage;
    public static ProfilePicturePage profilePicturePage;
    public static SystemUsersPage systemUsersPage;

    public static void initializePageObjects() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        addEmployeePage = new AddEmployeePage();
        searchEmployeePage = new SearchEmployeePage();
        createLoginDetailsPage = new CreateLoginDetailsPage();
        personalDetailsPage = new PersonalDetailsPage();
        contactDetailsPage = new ContactDetailsPage();
        dependentsPage = new DependentsPage();
        profilePicturePage = new ProfilePicturePage();
        systemUsersPage = new SystemUsersPage();
    }
}
