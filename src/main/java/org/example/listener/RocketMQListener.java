package org.example.listener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class RocketMQListener implements MessageListenerOrderly {


    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        // 设置为暂停消费，直到当前消息被完全处理
        context.setAutoCommit(false);
        try {
            for (MessageExt msg : msgs) {
                log.info("消费消息：" + new String(msg.getBody()));
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
