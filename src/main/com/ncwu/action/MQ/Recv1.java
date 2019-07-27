package MQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Recv1 {
    private final static String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws Exception {
        Connection connection = MQ.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //设置通道同一时刻最大发送信息数量
        channel.basicQos(1);

        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME,true,consumer);

        //获取消息
        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message=new String(delivery.getBody());
            System.out.println("[y] Received:"+message);

            //休眠
            Thread.sleep(10);

        }

    }
}
