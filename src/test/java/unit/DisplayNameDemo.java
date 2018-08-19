package unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A special test case")
public class DisplayNameDemo {

    @Test
    @DisplayName("Custom test name containing spaces")
    void testWithDisplayNameConcationingSpaces() {

    }

    @Test
    @DisplayName("╯°□°）╯")
    void testWithDisplayNameContainingSpecialCharacters() {

    }

    @Test
    @DisplayName("\uD83D\uDE31")
    void TestWithDisplayNameContainingEmoji() {

    }
}
