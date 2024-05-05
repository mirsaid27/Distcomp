package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Name Surname
 * 
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetStickerId implements Serializable {
    @Column(name = "tweets_id", nullable = false)
    private Long tweetId;
    @Column(name = "label_id", nullable = false)
    private Long labelId;
}
