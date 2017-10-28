package es.source.code.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import es.source.code.App;
import es.source.code.callback.SimpleObserver;
import es.source.code.model.Food;
import es.source.code.model.FoodCollection;
import es.source.code.utils.CommonUtil;
import es.source.code.utils.Const;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Author        Daniel
 * Class:        ServerObserverService
 * Date:         2017/10/25 10:35
 * Description:  获取数据服务
 */
public class ServerObserverService extends Service {

    private static final String TAG = "ServerObserverService";

    Context mContext;

    Observable<FoodCollection> foodObservable;

//    FoodCollection foodCollection;

    private boolean run = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sMessenger.getBinder();
    }

    private Handler cMessageHandler = new CMessageHandler();

    public Messenger sMessenger = new Messenger(cMessageHandler);
    public Messenger cMessenger;

    Disposable disposable;

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    /**
     * author:      Daniel
     * description: 来自客户端消息的处理
     */
    private class CMessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Const.EventKey.MSG_FOOD_GET_START:
                    // TODO: 2017/10/25 运行动态获取数据。
                    Log.d(TAG, "MSG_FOOD_GET_START");
                    cMessenger = msg.replyTo;
                    setRun(true);
                    if (foodObservable == null) {
                        foodObservable = getFoodObservable();
                    }
                    if (disposable == null) {
                        foodObservable.subscribe(foodObserver);
                    }
                    break;
                case Const.EventKey.MSG_FOOD_GET_STOP:
                    setRun(false);
                    break;
            }
        }
    }

    private Observable<FoodCollection> getFoodObservable() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.newThread())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        Log.d(TAG,"修改数据 start");
//                        setFoodStoreAmount(foodCollection.getColdFoodList());
//                        setFoodStoreAmount(foodCollection.getHotFoodList());
//                        setFoodStoreAmount(foodCollection.getSeaFoodList());
//                        setFoodStoreAmount(foodCollection.getDrinkingList());
//                        Log.d(TAG,"修改数据 end");
//                    }
//                })
//                .subscribeOn(Schedulers.computation())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        // 开始文件处理
//                        foodCollection = App.getInstance().getFoodCollection();
//                        Log.d(TAG,"取到数据");
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Function<Long, ObservableSource<FoodCollection>>() {
//                    @Override
//                    public ObservableSource<FoodCollection> apply(@NonNull Long aLong) throws Exception {
//                        return Observable.create(new ObservableOnSubscribe<FoodCollection>() {
//                            @Override
//                            public void subscribe(@NonNull ObservableEmitter<FoodCollection> e) throws Exception {
//                                // 开始文件处理
//                                FoodCollection foodCollection = App.getInstance().getFoodCollection();
//                                Log.d(TAG,"取到数据");
//
//                                Log.d(TAG,"修改数据 start");
//                                setFoodStoreAmount(foodCollection.getColdFoodList());
//                                setFoodStoreAmount(foodCollection.getHotFoodList());
//                                setFoodStoreAmount(foodCollection.getSeaFoodList());
//                                setFoodStoreAmount(foodCollection.getDrinkingList());
//                                Log.d(TAG,"修改数据 end");
//                            }
//                        });
//                    }
//                })
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(@NonNull Long aLong) throws Exception {
                        // 这里处理轮询器开关和模拟取到新数据（假设每五次访问取到新数据）
                        return isRun() && aLong % 5 == 0;
                    }
                })
                .map(new Function<Long, FoodCollection>() {
                    @Override
                    public FoodCollection apply(@NonNull Long run) throws Exception {
                        // 开始文件处理]=
                        FoodCollection foodCollection = App.getInstance().getFoodCollection();
                        Log.d(TAG, "取到数据");

                        Log.d(TAG, "修改数据 start");
                        setFoodStoreAmount(foodCollection.getColdFoodList());
                        setFoodStoreAmount(foodCollection.getHotFoodList());
                        setFoodStoreAmount(foodCollection.getSeaFoodList());
                        setFoodStoreAmount(foodCollection.getDrinkingList());
                        Log.d(TAG, "修改数据 end");
                        return foodCollection;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    FoodObserver foodObserver = new FoodObserver();

    private class FoodObserver extends SimpleObserver<FoodCollection> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            super.onSubscribe(d);
            disposable = d;
        }

        @Override
        public void onEvent(FoodCollection foodCollection) {
            if (CommonUtil.isProcessRunning(mContext, "es.source.code") && cMessenger != null) {
                // 发送消息到Activity
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.BundleKey.FOOD_COLLECTION, new Gson().toJson(foodCollection));
                    Message message = Message.obtain(null, Const.EventKey.MSG_FOOD_GET_SUCCESS);
                    message.setData(bundle);
                    cMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * author:      Daniel
     * description: 模拟生成库存
     */
    private void setFoodStoreAmount(List<Food> foodList) {
        for (Food food : foodList) {
            food.setStore((int) (Math.random() * 20));
        }
    }

}
