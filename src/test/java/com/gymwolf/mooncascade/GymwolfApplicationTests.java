package com.gymwolf.mooncascade;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GymwolfApplicationTests {

	static {
		Configuration.browser = "chrome";
		Configuration.headless = false;
	}

	private static final String WEB_URL = "https://www.gymwolf.com/staging/";
	private static String email;
	private final String password = "tere";

	private static String getEmail() {
		String uuid = UUID.randomUUID().toString();
		return "elinda.tragel+" + uuid + "@gmail.com";
	}

	@BeforeAll
	static void beforeAll() {
		email = getEmail();
	}

	@Test
	@Order(1)
	@DisplayName("User tries to register without email")
	void registerWithoutEmail() {

		open(WEB_URL);

		$$(tagName("a"))
				.find(text("Registreeru"))
				.click();

		$$(tagName("button"))
				.find(text("Registreeru"))
				.click();

		SelenideElement selenideElement = $(className("alert-danger"));

		selenideElement
				.should(exist);

		$(byText("Vigane e-maili aadress"))
				.shouldBe(visible);
	}

	@Test
	@Order(2)
	@DisplayName("User registers successfully and logs out")
	void registerSuccessfully() {

		open(WEB_URL);

		$$(tagName("a"))
				.find(text("Registreeru"))
				.click();

		$$(tagName("input"))
				.find(Condition.name("signup_email"))
				.setValue(email);

		$$(tagName("button"))
				.find(text("Registreeru"))
				.click();

		$$(tagName("input"))
				.find(Condition.id("gwp"))
				.setValue(password);

		$$(tagName("input"))
				.find(Condition.id("gwp2"))
				.setValue(password);

		$$(tagName("input"))
				.find(value("Salvesta"))
				.click();

		SelenideElement selenideElement = $(className("alert-success"));

		selenideElement
				.should(exist);

		$(byText("Andmed uuendatud"))
				.shouldBe(visible);

		logout();
	}

	@Test
	@Order(3)
	@DisplayName("User tries to register with the same email again")
	void registerWithSameEmail() {

		open(WEB_URL);

		$$(tagName("a"))
				.find(text("Registreeru"))
				.click();

		$$(tagName("input"))
				.find(Condition.name("signup_email"))
				.setValue(email);

		$$(tagName("button"))
				.find(text("Registreeru"))
				.click();

		SelenideElement selenideElement = $(className("alert-danger"));

		selenideElement
				.should(exist);

		$(byText("See e-maili aadress on juba kasutusel, palun vali mõni teine."))
				.shouldBe(visible);
	}

	@Test
	@Order(4)
	@DisplayName("User logs in, edits account details, and logs out")
	void loginAndEditDetails() {

		login();

		$(className("menu-name-label"))
				.click();

		$$(tagName("a"))
				.find(text("Muuda profiili"))
				.click();

		$$(tagName("input"))
				.find(Condition.id("gwn"))
				.setValue("Test User");

		$$(tagName("input"))
				.find(Condition.id("gender_female"))
				.click();

		$$(tagName("select"))
				.find(Condition.name("Date_Month"))
				.click();

		$$(tagName("option"))
				.find(text("August"))
				.click();

		$$(tagName("select"))
				.find(Condition.name("Date_Day"))
				.click();

		$$(tagName("option"))
				.find(text("13"))
				.click();

		$$(tagName("select"))
				.find(Condition.name("Date_Year"))
				.click();

		$$(tagName("option"))
				.find(text("1994"))
				.click();

		$$(tagName("input"))
				.find(Condition.id("gwp"))
				.setValue(password);

		$$(tagName("input"))
				.find(Condition.id("gwp2"))
				.setValue(password);

		$$(tagName("input"))
				.find(Condition.id("mygym"))
				.setValue("TestGym");

		$$(tagName("textarea"))
				.find(Condition.id("user-description"))
				.setValue("Testing testing 123");

		$$(tagName("input"))
				.find(value("Salvesta"))
				.click();

		SelenideElement selenideElement = $(className("alert-success"));

		selenideElement
				.should(exist);

		$(byText("Andmed uuendatud"))
				.shouldBe(visible);

		logout();
	}

	private void login() {

		open(WEB_URL);

		$$(tagName("a"))
				.find(text("Logi sisse"))
				.click();

		$$(tagName("input"))
				.find(Condition.name("email"))
				.setValue(email);

		$$(tagName("input"))
				.find(Condition.name("password"))
				.setValue(password)
				.pressEnter();
	}

	private void logout() {

		$(className("menu-name-label"))
				.click();

		$$(tagName("a"))
				.find(text("Logi Välja"))
				.click();
	}
}
