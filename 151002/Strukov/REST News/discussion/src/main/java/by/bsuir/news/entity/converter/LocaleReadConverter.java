package by.bsuir.news.entity.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Component
@ReadingConverter
public class LocaleReadConverter implements Converter<String, Locale> {

    @Override
    public Locale convert(String s) {
        return StringUtils.parseLocaleString(s);
    }
}
