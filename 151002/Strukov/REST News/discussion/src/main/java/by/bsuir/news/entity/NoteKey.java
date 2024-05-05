package by.bsuir.news.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.Locale;

@Getter
@Setter
@EqualsAndHashCode
@PrimaryKeyClass
@AllArgsConstructor
public class NoteKey {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private Locale country;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private Long id;
    @PrimaryKeyColumn(name = "news_id", type = PrimaryKeyType.CLUSTERED,ordinal = 0)
    private Long newsId;
}
