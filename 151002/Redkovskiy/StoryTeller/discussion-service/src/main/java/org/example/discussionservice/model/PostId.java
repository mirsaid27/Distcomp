package org.example.discussionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostId {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String country;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Long id;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private Long storyId;

}
