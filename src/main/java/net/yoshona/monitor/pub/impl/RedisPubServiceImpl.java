package net.yoshona.monitor.pub.impl;

import net.yoshona.monitor.appender.LogAppender;
import net.yoshona.monitor.model.LoggingMessage;
import net.yoshona.monitor.pub.PubService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/** author kiku create 1/24/2025 description */
public class RedisPubServiceImpl implements PubService {
  private final String PUB_TOPIC;
  private final RedissonClient redisson;

  public RedisPubServiceImpl(LogAppender.PubConfiguration configuration) {
    PUB_TOPIC = configuration.getTopic();
    Config config = new Config();
    config
        .useSingleServer()
        .setAddress(configuration.getUri())
        .setUsername(configuration.getUsername())
        .setPassword(configuration.getPassword());
    redisson = Redisson.create(config);
  }

  @Override
  public void publish(LoggingMessage loggingMessage) {
    redisson.getTopic(PUB_TOPIC).publish(loggingMessage);
  }

  @Override
  public void close() {
    redisson.shutdown();
  }

  @Override
  public boolean isAlive() {
    return !redisson.isShutdown();
  }
}
