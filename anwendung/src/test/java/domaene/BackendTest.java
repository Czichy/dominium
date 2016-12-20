package domaene;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(monochrome = true,
        strict = false,
        plugin = {"pretty:target/cucumber/backend.txt", "html:target/site/cucumber-backend-pretty",
                "json:target/cucumber/backend.json", "junit:target/cucumber/backend.xml"},

        // tags = {"~@ignore", "@backend"}
        tags = {"~@ignore"})
@RunWith(Cucumber.class)
public class BackendTest
{
}