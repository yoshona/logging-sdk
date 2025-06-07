package net.yoshona.monitor.pub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import net.yoshona.monitor.model.LoggingMessage;

/** author kiku create 1/24/2025 description */
public interface PubService {

  void publish(LoggingMessage loggingMessage) throws IOException;

  void close() throws IOException, TimeoutException;


  boolean isAlive();
}
