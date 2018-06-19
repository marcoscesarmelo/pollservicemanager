package br.com.poll.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		glue = {"stepDefinition", "br.com.poll.test.step.definitions"},
		features = {"src/test/java/features"})
public class PollApplicationTests {}
