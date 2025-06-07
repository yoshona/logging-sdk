package net.yoshona.monitor.pub;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import net.yoshona.monitor.appender.LogAppender;
import net.yoshona.monitor.pub.impl.RabbitMQPubServiceImpl;
import net.yoshona.monitor.pub.impl.RedisPubServiceImpl;

/** author kiku create 2/11/2025 description */
public class DefaultPubServiceFactory {

  private static final AtomicReference<PubService> pubService = new AtomicReference<>();

  public static PubService getPubService(LogAppender.PubConfiguration configuration) {
    return pubService.updateAndGet(
        currentService -> {
          if (null == currentService || !currentService.isAlive()) {
            return doGetPubService(configuration);
          }
          return currentService;
        });
  }

  private static PubService doGetPubService(LogAppender.PubConfiguration configuration) {
    if ("redis".equals(configuration.getType())) {
      return new RedisPubServiceImpl(configuration);

    } else if ("rabbitmq".equals(configuration.getType())) {
      try {
        return new RabbitMQPubServiceImpl(configuration);
      } catch (IOException
          | TimeoutException
          | URISyntaxException
          | NoSuchAlgorithmException
          | KeyManagementException e) {
        throw new RuntimeException(e);
      }
    }
    return new RedisPubServiceImpl(configuration);
  }
}
