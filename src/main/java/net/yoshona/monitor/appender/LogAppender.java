package net.yoshona.monitor.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import net.yoshona.monitor.model.LoggingMessage;
import net.yoshona.monitor.pub.DefaultPubServiceFactory;
import net.yoshona.monitor.pub.PubService;
import net.yoshona.monitor.pub.TracingContext;

/** author kiku create 1/24/2025 description */
@SuppressWarnings("unused")
public class LogAppender<E> extends LogAppenderConfig<E> {
  // 日志发送服务
  protected PubService pubService;

  @Override
  public void start() {
    super.start();
    doCreatePubService();
  }

  @Override
  public void stop() {
    super.stop();
    try {
      pubService.close();
    } catch (IOException | TimeoutException e) {
      addError("close encounter problems", e);
    }
  }

  @Override
  protected void append(E eventObject) {
    if (eventObject instanceof ILoggingEvent event) {
      String className = "unknown";
      String methodName = "unknown";
      StackTraceElement[] callerData = event.getCallerData();
      if (callerData != null && callerData.length > 0) {
        className = callerData[0].getClassName();
        methodName = callerData[0].getMethodName();
      }
      if (className.isBlank() || !className.startsWith(groupId)) {
        return;
      }

      LoggingMessage loggingMessage =
          new LoggingMessage(
              applicationName,
              className,
              methodName,
              TracingContext.getTraceId(),
              Arrays.asList(event.getFormattedMessage().split(logSplitter)));
      try {
        pubService.publish(loggingMessage);
      } catch (Exception e) {
        addError("Error publishing log message: " + e.getMessage());
      }
    }
  }

  private void doCreatePubService() {
    PubConfiguration configuration =
        new PubConfiguration(type, topic, username, password, uri, routingKey);
    pubService = DefaultPubServiceFactory.getPubService(configuration);
  }
}
