package com.fortrue.common.tool.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 批量处理器，可自定义异常处理机制
 * 可用于oracle查询in后面的item超过1000的时候
 * @param <T>
 * @param <R>
 */
public class Processor<T, R> {

    private static final int DEFAULT_BATCH_SIZE = 1000;

    private final ProcessorExceptionHandler<T, R> ignorePolicy = (b, t) -> System.out.println(t.toString());

    private final List<T> source;
    private final int batchSize;
    private final ProcessorExceptionHandler<T, R> processorExceptionHandler;
    private int index;

    public Processor(List<T> source, int batchSize, ProcessorExceptionHandler<T, R> processorExceptionHandler) {

        if(source == null || source.isEmpty()){
            throw new IllegalArgumentException("source can not be empty!");
        }

        if(batchSize <= 0){
            batchSize = DEFAULT_BATCH_SIZE;
        }

        if(processorExceptionHandler == null){
            processorExceptionHandler = ignorePolicy;
        }

        this.source = source;
        this.batchSize = batchSize;
        this.processorExceptionHandler = processorExceptionHandler;
        this.index = 0;
    }

    /**
     * 批量处理
     * @param function
     * @throws Throwable
     */
    public List<R> processBatch(Function<List<T>, List<R>> function) throws Throwable {

        if(batchSize == 1){
            throw new IllegalAccessException("一个一个处理应该调用processOne");
        }

        List<R> resultList = new ArrayList<>();
        int size = source.size();

        for ( ; index < size; index += batchSize) {
            try {
                List<T> subList = source.subList(index, Math.min(size, index + batchSize));
                List<R> subResultList = function.apply(subList);
                resultList.addAll(subResultList);
            } catch (Throwable throwable){
                processorExceptionHandler.exceptionExecution(this, throwable);
            }
        }

        return resultList;

    }


    /**
     * 一个一个处理
     * @param function
     * @throws Throwable
     */
    public List<R> processOne(Function<T, R> function) throws Throwable {

        if(batchSize > 1){
            throw new IllegalAccessException("批量处理应该调用processBatch");
        }

        List<R> resultList = new ArrayList<>();

        for (T t : source) {
            try {
                R result = function.apply(t);
                resultList.add(result);
            } catch (Throwable throwable){
                processorExceptionHandler.exceptionExecution(this, throwable);
            }
        }

        return resultList;

    }


    /**
     * 批量消费
     * @param consumer
     * @throws Throwable
     */
    public void consumeBatch(Consumer<List<T>> consumer) throws Throwable {

        if(batchSize == 1){
            throw new IllegalAccessException("一个一个处理应该调用consumeOne");
        }

        int size = source.size();
        for ( ; index < size; index += batchSize) {
            try {
                List<T> subList = source.subList(index, Math.min(size, index + batchSize));
                consumer.accept(subList);
            } catch (Throwable throwable){
                processorExceptionHandler.exceptionExecution(this, throwable);
            }
        }

    }


    /**
     * 一个一个消费
     * @param consumer
     * @throws Throwable
     */
    public void consumeOne(Consumer<T> consumer) throws Throwable {

        if(batchSize > 1){
            throw new IllegalAccessException("批量处理应该调用consumeBatch");
        }

        for (T t : source) {
            try {
                consumer.accept(t);
            } catch (Throwable throwable){
                processorExceptionHandler.exceptionExecution(this, throwable);
            }
        }

    }

}
