package by.bsuir.discussion.config;

import by.bsuir.discussion.model.converter.LocaleReadConverter;
import by.bsuir.discussion.model.converter.LocaleWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;

@Configuration
public class CassandraConfig {
    @Bean
    public CassandraCustomConversions cassandraCustomConversions(LocaleWriteConverter localWriteConverter,
                                                                 LocaleReadConverter localeReadConverter) {
        return CassandraCustomConversions.create(config -> {
            config.registerConverter(localeReadConverter);
            config.registerConverter(localWriteConverter);
        });
    }
}