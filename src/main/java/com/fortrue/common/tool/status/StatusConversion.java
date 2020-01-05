package com.fortrue.common.tool.status;

@FunctionalInterface
public interface StatusConversion {

    Status convert(Status sourceStatus, Status.Event event);

}
