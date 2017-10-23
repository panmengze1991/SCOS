package es.source.code.utils;

import es.source.code.model.Event;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @ClassName: RxBus.java
 * @Description: 基于RxJava的事件处理总线
 * @author Daniel
 * @date 2017/10/18 14:50
 */
public class RxBus {
    private final FlowableProcessor<Event> mBus;

    private RxBus() {
//        this.mBus = PublishProcessor<Event>.create().toSerialized();
        PublishProcessor<Event> publishProcessor = PublishProcessor.create();
        this.mBus = publishProcessor.toSerialized();
    }

    private static class Holder{
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance(){
        return Holder.instance;
    }

    public void post(@NonNull Event event){
        mBus.onNext(event);
    }

//    public <T> Flowable<T> register(Class<T> clz){
//        return mBus.ofType(clz);
//    }

    public Flowable<Event> register(){
        return mBus;
    }

    public void unregisterAll(){
        mBus.onComplete();
    }

    public boolean hasSubscribers(){
        return mBus.hasSubscribers();
    }
}
