package unit;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsDemo {

    private Person person = new Person("John", "Doe");

    @Test
    void standardAssertions() {
        assertEquals(2, 2);
        assertEquals(4, 4, "The optional assertion message is now the last parameter");
        assertTrue(2 == 2, () -> "Assertion messages can be lazily evaluated " +
                "to avoid constructing complex messages unnecessarily");
    }

    @Test
    void groupAssertions() {
        assertAll("person",
                () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName()));
    }

    @Test
    void dependentAssertions() {
        assertAll("properties",
                () -> {
                    String firstName = person.getFirstName();
                    assertNull(firstName);

                    assertAll("first name",
                            () -> assertTrue(firstName.startsWith("J")),
                            () -> assertTrue(firstName.endsWith("n")));
                },
                () -> {
                    String lastName = person.getLastName();
                    assertNull(lastName);

                    assertAll("last name",
                            () -> assertTrue(lastName.startsWith("D")),
                            () -> assertTrue(lastName.endsWith("e")));
                });
    }

    @Test
    void exceptionTesting() {
        Throwable excption = assertThrows(IllegalArgumentException.class,
                () -> {
                    throw new IllegalArgumentException("a message");
                });
        assertEquals("a message", excption.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        assertTimeout(Duration.ofMinutes(2), () -> {});
    }

    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(Duration.ofMinutes(2), () -> {
            return "a result";
        });
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethod() {
        String actualGreeting = assertTimeout(Duration.ofMinutes(2), AssertionsDemo::greeting);
        assertEquals("hello world", actualGreeting);
    }

    private static String greeting() {
        return "hello world";
    }

    @Test
    void timeoutExceeded() {
        assertTimeout(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            Thread.sleep(100);
        });
    }
}
