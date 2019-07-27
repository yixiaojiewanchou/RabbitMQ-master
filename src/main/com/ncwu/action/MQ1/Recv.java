package MQ1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者队列
 * 消费者1 看做前台系统
 */
public class Recv {
    private final static String QUEUE_NAME="test_queue_work1";
    private final static String EXCHAGE_NAME="test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = MQ1.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHAGE_NAME,"");

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
