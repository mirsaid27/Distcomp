package by.bsuir.discussion.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@WritingConverter
public class LocaleWriteConverter implements Converter<Locale, String> {
    private static final String DEFAULT = Locale.getDefault().getCountry();

    @Override
    public String convert(Locale locale) {
        final String country = locale.getCountry();
        return country.isBlank() ? DEFAULT : country;
    }
}