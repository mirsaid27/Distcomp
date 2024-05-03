package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Name Surname
 * 
 */
@Entity
@Table(name = "tweet_sticker")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetSticker {
    @EmbeddedId
    private TweetStickerId id;

    @ManyToOne
    @MapsId("tweetId")
    @JoinColumn(name = "tweet_id", referencedColumnName = "id")
    private Tweet tweet;

    @ManyToOne
    @MapsId("stickerId")
    @JoinColumn(name = "sticker_id", referencedColumnName = "id")
    private Sticker sticker;
}
