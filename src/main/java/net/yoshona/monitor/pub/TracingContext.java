package net.yoshona.monitor.pub;

import com.alibaba.ttl.TransmittableThreadLocal;

/** author kiku create 2/16/2025 description */
@SuppressWarnings("unused")
public class TracingContext {
  private static final TransmittableThreadLocal<Long> traceInfo = new TransmittableThreadLocal<>();

  public static void setTraceId(long traceId) {
    traceInfo.set(traceId);
  }

  public static long getTraceId() {
    return traceInfo.get();
  }
}
