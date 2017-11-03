package es.source.code.model;

/**
 * Author        Daniel
 * Class:        Event
 * Date:         2017/10/29 20:13
 * Description:  事件实体类
 */
public class Event<T> {

    private String eventKey;
    private T eventData;

    public Event(String eventKey) {
        this.eventKey = eventKey;
    }

    public Event(String eventKey, T eventData) {
        this.eventKey = eventKey;
        this.eventData = eventData;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public T getEventData() {
        return eventData;
    }

    public void setEventData(T eventData) {
        this.eventData = eventData;
    }
}
