package com.revature.revspace.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"feature"},
		glue = {"com.example.gluecode"}
		)
public class FollowerTestRunner {

}
