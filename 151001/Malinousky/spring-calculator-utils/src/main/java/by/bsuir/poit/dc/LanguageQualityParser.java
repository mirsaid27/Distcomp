package by.bsuir.poit.dc;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Name Surname
 * 
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LanguageQualityParser {
    private final float defaultQuality;

    public enum OrderType {
	PREFERABLE
    }

    public Optional<List<String>> parse(@NotNull String language, @NotNull OrderType type) {
	assert language != null && type != null;
	Matcher matcher = LANG_PATTERN.matcher(language);
	List<String> languages = new ArrayList<>();
	while (matcher.find()) {
	    String lang = matcher.group(LANG_GROUP_NAME);
	    languages.add(lang);
	}
	if (language.isEmpty()) {
	    return Optional.empty();
	}
	return Optional.of(languages);
    }

    public static final String LANG_GROUP_NAME = "lang";
    private static final Pattern LANG_PATTERN = Pattern.compile("(?<lang>(?!q=)[a-zA-Z-]+)");

    public static LanguageQualityParser withDefaults() {
	final float DEFaULT_QUALITY = 1.0f;
	return new LanguageQualityParser(DEFaULT_QUALITY);
    }
}
