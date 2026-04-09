
package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CommonMethods;

public class Hooks extends CommonMethods {
    public static final Logger logger = LogManager.getLogger(Hooks.class);
    @Before
    public void start(Scenario scenario){
        logger.info("========== START SCENARIO: {} ==========", scenario.getName());
        openBrowserAndLaunchApplication();
        initializePageObjects();
    }
    @After
    public void end(Scenario scenario){
        byte[] pic;
        if(scenario.isFailed()){
            logger.error("SCENARIO FAILED: {}", scenario.getName());
            pic=takeScreenshot("Failed/"+scenario.getName());
        }else{
            logger.info("SCENARIO PASSED: {}", scenario.getName());
            pic=takeScreenshot("Passed/"+scenario.getName());
        }
        scenario.attach(pic,"image/png",scenario.getName());
        closeBrowser();
        logger.info("========== END SCENARIO ==========");
    }

}
