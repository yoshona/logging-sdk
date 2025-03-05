package net.yoshona.monitor.model;

import java.util.List;

/** author kiku create 1/24/2025 description */
public record LoggingMessage(
    String applicationName,
    String className,
    String methodName,
    Long requestId,
    List<String> logs) {}
