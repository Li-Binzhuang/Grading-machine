package org.example.listener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.example.listener.pojo.MQMessage;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component
@Slf4j
public class RocketMQListener implements MessageListenerOrderly {// 顺序消费
    RedissonClient redissonClient;
    Connection conn;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                MQMessage mqMessage = objectMapper.readValue(msg.getBody(), MQMessage.class);
                // 处理消息

            }
            return ConsumeOrderlyStatus.SUCCESS;
        } catch (Exception e) {
            // 处理消费过程中的异常
            e.printStackTrace();
            // 返回消费失败状态，并可能需要记录日志或采取其他措施
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT; // 暂停当前队列一段时间后重试
        }
    }
}
