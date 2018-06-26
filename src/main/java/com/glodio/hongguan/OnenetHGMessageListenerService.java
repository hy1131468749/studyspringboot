package com.glodio.hongguan;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*
 *订阅获取设备数据-监听类 
 * 
 **/
public class OnenetHGMessageListenerService implements MessageListener {

	@Override
	public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
            	//System.out.println(((TextMessage) message).getText());
                OnenetHGDeviceDataQueue.getInstance().put(((TextMessage) message).getText());
            }
            catch (JMSException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }

	}

}
