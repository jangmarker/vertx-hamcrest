import cucumber.api.java.en.*;
import io.vertx.core.json.JsonObject;

import static de.jangmarker.vertx.hamcrest.JsonObjectHasPropertyMatcher.hasProperty;
import static org.hamcrest.MatcherAssert.assertThat;

public class HasPropertyDefinitions {
    private JsonObject jsonObject;

    @Given("I have (.+)")
    public void haveJsonObject(String json) {
        jsonObject = new JsonObject(json);
    };

    @Then("it should have the property (.+)")
    public void shouldHaveProperty(String propertyName) {
            assertThat(jsonObject, hasProperty(propertyName));
    };
}
