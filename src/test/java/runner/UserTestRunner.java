 package runner;

 import io.cucumber.junit.Cucumber;
 import io.cucumber.junit.CucumberOptions;
 import org.junit.runner.RunWith;


 @RunWith(Cucumber.class)
 @CucumberOptions(

         features = "src/test/resources/features/user",
         glue = "steps",

         plugin = {
                 "pretty",
                 "html:target/user-report.html",
                 "json:target/user.json",
                 "rerun:target/failed.txt"
         },

         monochrome = true,
         dryRun = false,
         tags = "@regression"
 )
 public class UserTestRunner {
 }

