package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.Msg;
import org.apache.rocketmq.client.consumer.MQConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.example.Config.RocketMQConfig;
import org.example.pojo.MQMessage;
import org.junit.Test;

import java.io.Serializable;

@Slf4j
public class ProducerTest {
    @Test
    public void testMessage() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ProblemConsumer");
        producer.setNamesrvAddr("192.168.31.221:9876");
        producer.start();
        MQMessage python =new MQMessage();
        python.setId(1);
        python.setCode("123");
        python.setCodeType("python");
        python.setProblemId(1);

        // 使用Jackson序列化
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonBytes = objectMapper.writeValueAsBytes(python);

        Message message = new Message("Problem",jsonBytes);
        log.info(python.toString());
        producer.send(message);
        producer.shutdown();
        log.info("消息发送成功");
    }
}
