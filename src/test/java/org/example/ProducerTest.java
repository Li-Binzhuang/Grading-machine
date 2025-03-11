package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.example.listener.VO.CodeType;
import org.example.listener.pojo.MQMessage;
import org.junit.Test;

@Slf4j
public class ProducerTest {
    @Test
    public void testMessage() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ProblemConsumer");
        producer.setNamesrvAddr("192.168.31.221:9876");
        producer.start();
        MQMessage python =new MQMessage();
        python.setUserId(1);
        python.setCode("123");
        python.setCodeType(CodeType.PYTHON);
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
