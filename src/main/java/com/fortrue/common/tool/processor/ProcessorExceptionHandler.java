package com.fortrue.common.tool.processor;

@FunctionalInterface
public interface ProcessorExceptionHandler<T, R> {

    void exceptionExecution(Processor<T, R> processor, Throwable throwable) throws Throwable;

}
