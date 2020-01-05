package com.fortrue.common.tool.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 异步同步通用的状态机
 */
public enum Status {

    /**
     * 五种状态：初始状态，新建状态，请求中状态，请求成功状态，请求失败状态，请求超时状态
     * @since jdk9  List.of
     */
    NONE(List.of(Event.CREATE), "初始状态"),
    DRAFT(List.of(Event.REQUEST), "新建状态"),
    REQUESTING(List.of(Event.RETURN_SUCCESS, Event.RETURN_FAILURE, Event.RETURN_TIME_OUT), "请求中状态"),
    SUCCESS(new ArrayList<>(), "请求成功状态"),
    FAILURE(List.of(Event.RE_REQUEST), "请求失败状态"),
    TIME_OUT(List.of(Event.RE_REQUEST), "请求超时状态");

    /**
     * 状态转换逻辑
     */
    private static final StatusConversion STATUS_CONVERSION =
            (source, event) -> Optional.of(event)
                    .filter(t -> source.events.contains(t))
                    .map(t -> t.destStatus)
                    .orElseThrow(() -> new IllegalArgumentException("源状态与事件无关"));

    /**
     * 状态幂等判断
     */
    private static final StatusIdempotent STATUS_IDEMPOTENT =
            (source, event) -> Optional.of(event)
                    .filter(t -> t.destStatus.equals(source))
                    .map(Objects::nonNull)
                    .orElse(false);


    /**
     * 定义初始状态能接受的事件
     */
    private List<Event> events;
    /**
     * 状态名称
     */
    private String statusName;

    Status(List<Event> events, String statusName) {
        this.events = events;
        this.statusName = statusName;
    }

    /**
     * 判断是否幂等
     * @param event
     * @return
     */
    public boolean isIdempotent(Event event){
        return STATUS_IDEMPOTENT.isIdempotent(this, event);
    }

    /**
     * 转换状态
     * @param event
     * @return
     */
    public Status convertStatus(Event event){
        return STATUS_CONVERSION.convert(this, event);
    }



    enum Event {
        /**
         * 创建，请求，返回成功，返回失败，重新请求，请求超时
         */
        CREATE(Status.DRAFT, "创建"),
        REQUEST(Status.REQUESTING, "请求"),
        RETURN_SUCCESS(Status.SUCCESS, "返回成功"),
        RETURN_FAILURE(Status.FAILURE, "返回失败"),
        RE_REQUEST(Status.REQUESTING, "重新请求"),
        RETURN_TIME_OUT(Status.TIME_OUT, "请求超时");

        /**
         * 事件目标状态
         */
        private Status destStatus;
        /**
         * 事件名称
         */
        private String eventName;

        Event(Status destStatus, String eventName) {
            this.destStatus = destStatus;
            this.eventName = eventName;
        }
    }

}
