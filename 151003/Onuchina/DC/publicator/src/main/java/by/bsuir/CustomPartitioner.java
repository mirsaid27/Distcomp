package by.bsuir;

import by.bsuir.dto.NoteRequestTo2;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (key == null || ((NoteRequestTo2) key).getStoryId() == null) {
            return 0;
        }
        Long storyId = ((NoteRequestTo2) key).getStoryId();
        return Math.abs(storyId.hashCode()) % cluster.partitionCountForTopic(topic);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}