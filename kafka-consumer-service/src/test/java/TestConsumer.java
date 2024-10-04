import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class TestConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        // kafka地址,列表格式为host1:port1,host2:port2,...，无需添加所有的集群地址，kafka会根据提供的地址发现其他的地址(建议多提供几个，以防提供的服务器关闭) 必须设置
        props.put("bootstrap.servers", "localhost:9092");
        // key序列化方式 必须设置
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value序列化方式 必须设置
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put("group.id", "consumer_riemann_test");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 可消费多个topic,组成一个list
        String topic = "riemann_kafka_test";
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
