package com.system.mylibrary.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import butterknife.Unbinder
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jeremyliao.liveeventbus.LiveEventBus
import com.system.mylibrary.BaseConfig
import com.system.mylibrary.utils.SPUtils
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment

import java.util.HashMap

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

abstract class BaseFragment : SupportFragment() {
    /*分页相关*/
    /* 每页最大分页数量 */
    var mPageSize = 20
    /* 当前页码 */
    var mPageIndex = 1
    //Kotlin中定义的变量，要么是定义时就初始化，要么就定义成抽象的
    //lateinit  修饰符 变量需要在定义之后赋值
    lateinit var rootView: View
    lateinit var mtoken: String
    lateinit var mUser: String
    lateinit var mUserId: String
    private var unbinder: Unbinder? = null

    /**
     * 返回布局资源ID
     *
     * @return
     */
    protected abstract val layoutResId: Int

    /**
     * 获取context
     *
     * @return
     */
    val thisContext: Context?
        get() = activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Arouter注入
        ARouter.getInstance().inject(this)
        init()
        initInfo()
        LiveEventBus.get()
            .with("base_fragment", String::class.java)
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
        val token = BaseConfig.getToken(context)

        mtoken = token

        val user = SPUtils.get(thisContext!!, BaseConfig.USER, "") as String

        mUser = user

        val userid = SPUtils.get(thisContext!!, BaseConfig.USERID, "") as String

        mUserId = userid

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutResId, container, false)
        unbinder = ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initDatas()
    }

    /**
     * 初始化
     */
    protected abstract fun init()

    /**
     * 初始化ViewModel
     */
    protected abstract fun initViewModel()

    /**
     * 实现功能，填充数据
     */
    protected abstract fun initDatas()

    /**
     * 实现功能，填充数据
     * @param myProducts
     */
    protected abstract fun dataCallBack(myProducts: String, value: JsonObject)

    /**
     * 订阅数据
     * @param liveData
     */
    protected fun subscribeUi(liveData: LiveData<JsonObject>) {
        liveData.observe(this, Observer { myProducts ->
            liveData.observe(this, Observer { myProducts ->
                val keySet = myProducts.keySet()
                val iterator = keySet.iterator()
                while (iterator.hasNext()){
                    val next = iterator.next()
                    val value = myProducts.get(next).asJsonObject
                    dataCallBack(next,value)
                }
            })})
    }

    /**
     * 关闭Fragment
     */
    fun closeFragment() {
        val bundle = Bundle()
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        pop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
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

    companion object {
        val mMaxPageSize = Integer.MAX_VALUE
        val mNormalPageSize = 20
    }
}
