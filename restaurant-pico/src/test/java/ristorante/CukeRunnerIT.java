package ristorante;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(/* tags = "@ToRun", */
		plugin = { "summary", "progress", "json:target/cucumber-report/cucumber.json" })
public class CukeRunnerIT {

}
