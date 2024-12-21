package org.example.listener;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RocketMQListener implements MessageListenerOrderly {


    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        // 设置为暂停消费，直到当前消息被完全处理
        context.setAutoCommit(false);

        try {
            for (MessageExt msg : msgs) {
                // 打印消息内容，实际应用中应替换为具体业务逻辑
                System.out.printf("Receive message[msgId=%s] %n", msg.getMsgId());

                // 在这里执行你的业务逻辑代码

                // 模拟业务处理时间
                Thread.sleep(1000); // 仅用于演示，实际不应无故sleep

                // 如果需要暂停消费可以在这里设置，比如等待外部资源可用
                // context.setSuspendCurrentQueueTimeMillis(3000);
            }

            // 如果一切顺利，返回成功状态
            return ConsumeOrderlyStatus.SUCCESS;
        } catch (Exception e) {
            // 处理消费过程中的异常
            e.printStackTrace();
            // 返回消费失败状态，并可能需要记录日志或采取其他措施
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT; // 暂停当前队列一段时间后重试
        }
    }
}
