package de.jangmarker.vertx.hamcrest;

import io.vertx.core.json.JsonObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class JsonObjectHasPropertyMatcher extends BaseMatcher<JsonObject> {
    private final String expectedProperty;

    private JsonObjectHasPropertyMatcher(String expectedProperty) {
        this.expectedProperty = expectedProperty;
    }

    public static JsonObjectHasPropertyMatcher hasProperty(String property) {
        return new JsonObjectHasPropertyMatcher(property);
    }

    public boolean matches(Object o) {
        return ((JsonObject) o).containsKey(expectedProperty);
    }

    public void describeTo(Description description) {
        description.appendText("has property ").appendText(expectedProperty);
    }
}
