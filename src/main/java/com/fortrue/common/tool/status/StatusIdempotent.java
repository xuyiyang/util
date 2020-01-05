package com.fortrue.common.tool.status;

@FunctionalInterface
public interface StatusIdempotent {

    boolean isIdempotent(Status source, Status.Event event);

}
