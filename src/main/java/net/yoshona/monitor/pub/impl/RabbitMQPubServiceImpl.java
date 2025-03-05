package net.yoshona.monitor.pub.impl;

import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import net.yoshona.monitor.appender.LogAppender;
import net.yoshona.monitor.model.LoggingMessage;
import net.yoshona.monitor.pub.PubService;

/** author kiku create 2/16/2025 description */
public class RabbitMQPubServiceImpl implements PubService {
  private final Channel channel;
  private final String exchange;
  private final String routingKey;
  private final Connection connection;

  @Override
  public void publish(LoggingMessage loggingMessage) throws IOException {
    channel.basicPublish(exchange, routingKey, null, JSON.toJSONBytes(loggingMessage));
  }

  @Override
  public void close() throws IOException, TimeoutException {
    channel.close();
    connection.close();
  }

  public RabbitMQPubServiceImpl(LogAppender.PubConfiguration configuration)
      throws IOException,
          TimeoutException,
          URISyntaxException,
          NoSuchAlgorithmException,
          KeyManagementException {
    ConnectionFactory cf = new ConnectionFactory();
    cf.setUri(configuration.getUri());
    this.connection = cf.newConnection();
    this.channel = connection.createChannel();
    this.exchange = configuration.getTopic();
    this.routingKey = configuration.getRoutingKey();
  }
}
