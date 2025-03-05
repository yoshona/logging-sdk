package net.yoshona.monitor.appender;

import ch.qos.logback.core.UnsynchronizedAppenderBase;

/** author kiku create 2/16/2025 description */
@SuppressWarnings("all")
public abstract class LogAppenderConfig<E> extends UnsynchronizedAppenderBase<E> {
  // 系统名称
  protected String applicationName;
  // 只采集指定范围的日志
  protected String groupId;
  // 日志分离符号
  protected String logSplitter = " ";

  // 日志发送服务类型 redis/rabbitmq
  protected String type;
  // redis/rabbitmq URI
  protected String uri;
  // 用户名
  protected String username;
  // 密码
  protected String password;
  // redis pub_sub topic rabbitmq queue
  protected String topic;

  protected String routingKey;

  /** 日志发送依赖配置 如Redisson 为用户名密码等 */
  public static class PubConfiguration {
    private final String type;
    private final String topic;
    private final String username;
    private final String password;
    private final String routingKey;
    private final String uri;

    public String getType() {
      return type;
    }

    public String getUri() {
      return uri;
    }

    public String getTopic() {
      return topic;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }

    public PubConfiguration(
        String type,
        String topic,
        String username,
        String password,
        String uri,
        String routingKey) {
      this.type = type;
      this.topic = topic;
      this.username = username;
      this.password = password;
      this.routingKey = routingKey;
      this.uri = uri;
      if (uri == null || topic == null) {
        throw new IllegalArgumentException("address and topic cannot be null");
      }
      if ("rabbitmq".equals(type) && routingKey == null) {
        throw new IllegalArgumentException("routingKey cannot be null");
      }
    }

    public String getRoutingKey() {
      return routingKey;
    }
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getLogSplitter() {
    return logSplitter;
  }

  public void setLogSplitter(String logSplitter) {
    this.logSplitter = logSplitter;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getRoutingKey() {
    return routingKey;
  }

  public void setRoutingKey(String routingKey) {
    this.routingKey = routingKey;
  }
}
