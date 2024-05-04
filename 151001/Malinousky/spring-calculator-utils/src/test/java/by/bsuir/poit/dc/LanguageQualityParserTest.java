package by.bsuir.poit.dc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Name Surname
 * 
 */
class LanguageQualityParserTest {

    @Test
    void parse() {
	var parser = LanguageQualityParser.withDefaults();
	String headers = "en-US, en;q=0.9, fr-FR;q=0.8, fr;q=0.7";
	List<String> list = parser.parse(headers, LanguageQualityParser.OrderType.PREFERABLE).orElseThrow();
	Assertions.assertThatList(list).isEqualTo(List.of("en-US", "en", "fr-FR", "fr"));
    }
}
