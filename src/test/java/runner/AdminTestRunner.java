 package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(

        features = "src/test/resources/features/admin",
        glue = "steps",

        plugin = {
                "pretty",
                "html:target/admin-report.html",
                "json:target/admin.json",
                "rerun:target/failed.txt"
        },

        monochrome = true,
        dryRun = false,
        tags = "@regression"
)
public class AdminTestRunner {
}

