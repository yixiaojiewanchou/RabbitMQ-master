package MQ2;

import MQ1.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模式（实现定向发送接收）
 *
 */
public class Send {
    private final static String EXCHANGE_NAME="test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //消息内容
        String message="hello world";
        channel.basicPublish(EXCHANGE_NAME,"delete",null,message.getBytes());
        System.out.println("[x] Sent:"+message);

        channel.close();
        connection.close();
    }
}
