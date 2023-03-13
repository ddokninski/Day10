package org.example;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestDataProvider extends BaseTest {

    public static Stream<Arguments> testDataProviderForSiiPage() {
        return Stream.of(
                Arguments.of("https://sii.pl/", "Dziękujemy za wypełnienie formularza.")
        );
    }
}
