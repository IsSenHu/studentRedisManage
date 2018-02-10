package ecjtu.husen.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

import java.io.UnsupportedEncodingException;

/**
 * @author 11785
 */
public class RabbitmqService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(new String(message.getBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
