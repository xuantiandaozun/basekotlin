package com.system.mylibrary.requestlistener


import android.content.Intent

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.orhanobut.logger.Logger
import com.system.mylibrary.BaseMainApp

import org.json.JSONException

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

import androidx.lifecycle.MediatorLiveData
import com.system.mylibrary.utils.GsonUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/5/3
 * @author 18081
 */

class BaseHttpSubscriber(
    private val liveData: MediatorLiveData<JsonObject>, private val type: String) : Observer<Any> {


    override fun onSubscribe(d: Disposable) {

    }


    override fun onNext(t: Any) {
        val gson = Gson()
        val json = gson.toJson(t)
        if (json.contains("登录过期")) {
            val intent = Intent("com.system.main")
            intent.putExtra("name", "logout")
            BaseMainApp.context!!.sendBroadcast(intent)
        } else {
            val gsonToBean = GsonUtil.GsonToBean(json, JsonObject::class.java)
            var jsonObject = JsonObject()
            jsonObject.add(type, gsonToBean)
            liveData.setValue(jsonObject)
        }

    }

    override fun onError(e: Throwable) {
        var erroContent: String? = null
        if (e is HttpException) {
            val code = e.code()
            try {
                val string = e.response().errorBody()!!.string()
                Logger.e(string)
            } catch (e1: IOException) {
                e1.printStackTrace()
            }

            if (code == 500 || code == 404) {
                erroContent = "服务器出错"
            } else {
                erroContent = "网络异常"
            }
        } else if ((e is JsonSyntaxException) or (e is JsonParseException) or (e is JSONException)) {
            erroContent = "json解析异常"
        } else if (e is ConnectException) {
            erroContent = "网络断开,请打开网络!"
        } else if (e is SocketTimeoutException) {
            erroContent = "网络连接超时!!"
        } else {
            erroContent = "发生未知错误"
        }
        Logger.e(erroContent)


    }

    override fun onComplete() {

    }
}
