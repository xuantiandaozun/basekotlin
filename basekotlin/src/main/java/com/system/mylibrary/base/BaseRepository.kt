package com.system.mylibrary.base


import com.system.mylibrary.requestlistener.BaseHttpSubscriber

import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonObject
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Repository基类
 *
 * @author weishixiong
 * @Time 2018-03-30
 */

open class BaseRepository {
    var liveData: MediatorLiveData<JsonObject> = MediatorLiveData()
    /**
     * RxJava Subscriber回调
     */
    private val baseHttpSubscriber: BaseHttpSubscriber? = null

}
