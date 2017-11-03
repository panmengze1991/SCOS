package es.source.code.utils;

import es.source.code.model.Event;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Author        Daniel
 * Class:        RxBus
 * Date:         2017/10/29 20:12
 * Description:  基于RxJava的事件处理总线
 */
public class RxBus {
    private final FlowableProcessor<Event> mBus;

    private RxBus() {
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
