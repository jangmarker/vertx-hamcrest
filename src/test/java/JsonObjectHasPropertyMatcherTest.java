import de.jangmarker.vertx.hamcrest.JsonObjectHasPropertyMatcher;
import io.vertx.core.json.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static de.jangmarker.vertx.hamcrest.JsonObjectHasPropertyMatcher.hasProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class JsonObjectHasPropertyMatcherTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void hasProperty_match() {
        Matcher<JsonObject> matcher = hasProperty("a");

        assertMatch(matcher, new JsonObject().put("a", "b"));
    }

    @Test
    public void doesNotHaveProperty_fail() {
        Matcher<JsonObject> matcher = hasProperty("a");

        assertFail(matcher, new JsonObject().put("z", "b"));
    }

    @Test
    public void matchTwoUsingAnd_hasProperties_succeeds() {
        Matcher<JsonObject> matcher = allOf(hasProperty("a"), hasProperty("b"));
        assertMatch(matcher, new JsonObject().put("a", "y").put("b", "z"));
    }

    @Test
    public void matchTwoUsingAnd_hasOneProperty_fails() {
        Matcher<JsonObject> matcher = allOf(hasProperty("a") ,hasProperty("b"));
        assertFail(matcher, new JsonObject().put("a", "z"));
    }

    @Test
    public void hasDescription() {
        final Matcher<JsonObject> matcher = hasProperty("a");
        assertDescription("has property a", matcher);
    }

    private void assertDescription(String expectedDescription, Matcher<JsonObject> matcher) {
        Description description = new StringDescription();
        matcher.describeTo(description);
        assertThat(expectedDescription, is(equalTo(description.toString())));
    }

    private void assertFail(Matcher<JsonObject> matcher, JsonObject actual) {
        assertFalse(matcher.matches(actual));
    }

    private void assertMatch(Matcher<JsonObject> matcher, JsonObject actual) {
        assertTrue(matcher.matches(actual));
    }

}
