package main.com.ncwu.action.MQ2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者队列
 * 消费者2 看做搜索系统
 */
public class Recv2 {
    private final static String QUEUE_NAME="test_queue_direct_2";
    private final static String EXCHAGE_NAME="test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = MQ2.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"update");
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"delete");
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"insert");

        channel.basicQos(1);
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message=new String(delivery.getBody());
            System.out.println("[Recv] received:"+message);
            Thread.sleep(10);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }

    }
}
