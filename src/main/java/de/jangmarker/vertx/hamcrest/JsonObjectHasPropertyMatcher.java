package de.jangmarker.vertx.hamcrest;

import io.vertx.core.json.JsonObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.anything;

public class JsonObjectHasPropertyMatcher extends BaseMatcher<JsonObject> {
    private final String expectedProperty;
    private boolean matchContent = true;
    private Matcher<?> contentMatcher;

    private JsonObjectHasPropertyMatcher(String expectedProperty, Matcher<?> contentMatcher) {
        this.expectedProperty = expectedProperty;
        this.contentMatcher = contentMatcher;
    }

    private JsonObjectHasPropertyMatcher(String expectedProperty) {
        this(expectedProperty, anything());
        this.matchContent = false;
    }

    public static JsonObjectHasPropertyMatcher hasProperty(String property) {
        return new JsonObjectHasPropertyMatcher(property);
    }

    public static JsonObjectHasPropertyMatcher hasProperty(String property, Matcher<?> contentMatcher) {
        return new JsonObjectHasPropertyMatcher(property, contentMatcher);
    }

    public boolean matches(Object o) {
        return ((JsonObject) o).containsKey(expectedProperty)
            && contentMatcher.matches(((JsonObject) o).getValue(expectedProperty));
    }

    public void describeTo(Description description) {
        description.appendText("has property ").appendText(expectedProperty);
        if (this.matchContent) {
            description.appendText(" with content ");
            contentMatcher.describeTo(description);
        }
    }
}
