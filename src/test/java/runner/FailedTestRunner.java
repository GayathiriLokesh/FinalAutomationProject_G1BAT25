package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/failed.txt",
        glue = "steps",
        plugin = {
                "pretty",
                "html:target/failed-report.html",
                "json:target/failed.json"
        },
        monochrome = true
)

public class FailedTestRunner {
}
