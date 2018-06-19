package br.com.poll.test.step.definitions;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import com.jayway.restassured.specification.RequestSpecification;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class PollCreationStepDefs {

	private com.jayway.restassured.response.Response pollCreationResult;
	
	RequestSpecification pollRequest;
	
	@Dado("^um usuario autenticado com id \"([^\"]*)\" username \"([^\"]*)\" password \"([^\"]*)\"$")
	public void um_usuario_autenticado_com_id_username_password(String arg1, String arg2, String arg3)	throws Throwable {
		pollCreationResult = given()
		.contentType("application/json")
		.header("ownerId", arg1)
		.header("username", arg2)
		.header("password", arg3)
		.body("{\"description\": \"Como você classifica o App Cielo?\", \"finalDate\": \"2018-07-20T23:50:29.000+0000\" }")
		.when().post("http://localhost:8080/poll");
	}

	@Quando("^confirmar criacao de enquete$")
	public void confirmar_criacao_de_enquete() throws Throwable {
		pollCreationResult.then().statusCode(200);
	}

	@Entao("^a enquete deve ser criada$")
	public void a_enquete_deve_ser_criada() throws Throwable {
		pollCreationResult.then().statusCode(200).body("$", notNullValue());	

	}

}
