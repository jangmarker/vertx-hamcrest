import io.vertx.core.json.JsonObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static de.jangmarker.vertx.hamcrest.JsonObjectHasPropertyMatcher.hasProperty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class JsonObjectHasPropertyMatcherTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public static class hasOneProperty {
        @Test
        public void matchesName() {
            Matcher<JsonObject> matcher = hasProperty("a");

            assertMatch(matcher, new JsonObject().put("a", "b"));
        }

        @Test
        public void matchTwoUsingAnd_fails() {
            Matcher<JsonObject> matcher = allOf(hasProperty("a") ,hasProperty("b"));
            assertFail(matcher, new JsonObject().put("a", "z"));
        }

        @Test
        public void hasDescription() {
            final Matcher<JsonObject> matcher = hasProperty("a");
            assertDescription("has property a", matcher);
        }
    }

    public static class hasStringProperty {
        @Test
        public void matchesContentUsingMatcher() {
            Matcher<String> content = is("b");
            Matcher<JsonObject> matcher = hasProperty("a", content);

            assertMatch(matcher, new JsonObject().put("a", "b"));
        }

        public static class wrongContent {
            @Test
            public void failsUsingMatcher() {
                Matcher<String> content = is("c");
                Matcher<JsonObject> matcher = hasProperty("a", content);

                assertFail(matcher, new JsonObject().put("a", "b"));
            }

            @Test
            public void descriptionHasContentMismatch() {
                assertDescription("has property a with content is \"life\"", hasProperty("a", is("life")));
            }
        }
    }

    public static class hasNoProperty {
        @Test
        public void checkForProperty_fails() {
            Matcher<JsonObject> matcher = hasProperty("a");

            assertFail(matcher, new JsonObject().put("z", "b"));
        }
    }

    public static class hasTwoProperties {
        @Test
        public void matchBoth_succeeds() {
            Matcher<JsonObject> matcher = allOf(hasProperty("a"), hasProperty("b"));
            assertMatch(matcher, new JsonObject().put("a", "y").put("b", "z"));
        }
    }

    private static void assertDescription(String expectedDescription, Matcher<JsonObject> matcher) {
        Description description = new StringDescription();
        matcher.describeTo(description);
        assertThat(description.toString(), is(equalTo(expectedDescription)));
    }

    private static void assertFail(Matcher<JsonObject> matcher, JsonObject actual) {
        assertFalse(matcher.matches(actual));
    }

    private static void assertMatch(Matcher<JsonObject> matcher, JsonObject actual) {
        assertTrue(matcher.matches(actual));
    }

}
