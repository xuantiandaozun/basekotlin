package com.system.mylibrary.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jeremyliao.liveeventbus.LiveEventBus
import com.system.mylibrary.BaseConfig
import com.system.mylibrary.utils.SPUtils
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

import java.util.HashMap

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

abstract class BaseActivity : SupportActivity() {
    //lateinit  修饰符 变量需要在定义之后赋值
    lateinit var mtoken: String
    lateinit var mUser: String
    lateinit var mUserId: String

    /**
     * 设置主题
     *
     * @return
     */
    protected abstract val themeResId: Int

    /**
     * 返回布局资源ID
     *
     * @return
     */
    protected abstract val layoutResId: Int
    /**
     * 获取当前Context
     *
     * @return
     */
    val thisContext: Context
        get() = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
        setTheme(themeResId)
        setContentView(
            LayoutInflater.from(this).inflate(
                layoutResId, null
            )
        )
        //初始化数据
        ButterKnife.bind(this)
        //Arouter注入
        ARouter.getInstance().inject(this)
        initInfo()
        initViewModel()
        initDatas()
        LiveEventBus.get()
            .with("base_activity", String::class.java)
            .observe(this, Observer { s ->
                when (s) {
                    "initInfo" -> initInfo()
                }
            })

    }

    /**
     * 初始化基本参数
     */
    private fun initInfo() {
        val token = BaseConfig.getToken(thisContext)
        mtoken = token

        val user = SPUtils.get(thisContext, BaseConfig.USER, "") as String
        mUser = user

        val userid = SPUtils.get(thisContext, BaseConfig.USERID, "") as String
        mUserId = userid

    }

    /**
     * 初始化
     */
    protected abstract fun init(savedInstanceState: Bundle?)

    /**
     * 实现功能，填充数据
     */
    protected abstract fun initDatas()

    /**
     * 初始化ViewModel
     */
    protected abstract fun initViewModel()

    /**
     * 接口回调
     * @param myProducts
     */
    protected abstract fun dataCallBack(myProducts: String, value: JsonObject)
    /**
     * 接口加载失败回调
     * @param myProducts
     */
    protected  fun erroCallBack(myProducts: String, value: JsonObject){

    }

    /**
     * 订阅数据
     * @param liveData
     */
    protected fun subscribeUi(liveData: LiveData<JsonObject>) {
        liveData.observe(this, Observer { myProducts ->
            val keySet = myProducts.keySet()
            val iterator = keySet.iterator()
            while (iterator.hasNext()){
                val next = iterator.next()
                val value = myProducts.get(next).asJsonObject
                dataCallBack(next,value)
            }
            })
    }
    /**
     * 订阅加载失败数据
     * @param liveData
     */
    protected fun subscribeErroUi(liveData: LiveData<JsonObject>) {
        liveData.observe(this, Observer { myProducts ->
            val keySet = myProducts.keySet()
            val iterator = keySet.iterator()
            while (iterator.hasNext()){
                val next = iterator.next()
                val value = myProducts.get(next).asJsonObject
                erroCallBack(next,value)
            }
        })
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    /**
     * 根据String获取参数
     *
     * @param params
     * @return
     */
    fun getHashMapByParams(params: String): HashMap<String, Any> {
        if (TextUtils.isEmpty(params)) {
            return HashMap()
        }
        val type = object : TypeToken<HashMap<String, Any>>() {

        }.type
        return Gson().fromJson(params, type)
    }

    /**
     * 关闭Activity
     */
    fun CloseActivity() {
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
