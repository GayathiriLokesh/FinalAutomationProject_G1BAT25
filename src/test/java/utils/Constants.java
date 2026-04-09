package utils;

public class Constants {
    public static final  String CONFIG_FILE_PATH=
            System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
    public static final String EXCEL_FILE_PATH =
            System.getProperty("user.dir") + "/src/test/resources/testdata/hrmsData.xlsx";

    public static final String LOGIN_SHEET = "Login";
    public static final String ADD_EMPLOYEE_SHEET = "AddEmployee";
    public static final String CREATE_LOGIN_SHEET = "ESSUser";
    public static final String SEARCH_EMPLOYEE_SHEET = "SearchEmployee";
    public static final String PERSONAL_DETAILS_SHEET = "PersonalDetails";
    public static final String CONTACT_DETAILS_SHEET = "ContactDetails";
    public static final String PROFILE_PICTURE_SHEET = "ProfilePicture";
    public static final String DEPENDENTS_SHEET = "Dependents";

    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 20;

    public static final String SCREENSHOT_FILEPATH =
            System.getProperty("user.dir") + "/screenshots/";

}
