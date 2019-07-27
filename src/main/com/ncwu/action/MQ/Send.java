package MQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者模式（自动确认 公平分配）
 * 一个生产者，多个消费者
 *
 * 能者多劳的work模式（消息确认改为手动确认）
 *  开启这行 表示使用手动确认模式
 *  channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
 *  监听队列，false表示手动返回完成状态，true表示自动
 *  channel.basicConsume(QUEUE_NAME, false, consumer);
 */
public class Send {
    private final static String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws Exception {
        //获取到连接以及mq通道
        Connection connection = MQ.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        //durable=True即可做的队列持久化queueDeclare（）的第二个参数就是
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        for (int i=0;i<100;i++){
            //消息内容
            String message=""+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("[X] sent:"+message);

            Thread.sleep(i*10);
        }
        channel.close();
        connection.close();
    }
}
