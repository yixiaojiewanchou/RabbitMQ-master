package MQ1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式
 * ①1个生产者，多个消费者
 * ②每个消费者都有自己的一个队列
 * ③生产者没有将消息直接发送到队列，而是发送到交换机
 * ④每个队列都要绑定到交换机
 * ⑤生产者发送的熊爱惜，经过交换机，到达队列，实现一个消息被多个小费制获取的目的
 *
 */
public class Send {
    private final static String EXCHANGE_NAME="test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = MQ1.ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //消息内容
        String message="hello world";
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        System.out.println("[x] Sent:"+message);

        channel.close();
        connection.close();


    }
}
