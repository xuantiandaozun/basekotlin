package com.system.mylibrary

import android.content.Context
import com.system.mylibrary.utils.SPUtils


class BaseConfig {
    companion object {
        /**
         * token令牌
         */
        var TOKEN = "token"
        /**
         * 自动登录标识
         */
        var ISLOGIN = "isLogin"
        /**
         * 管理员标识
         */
        var ISADMIN = "isAdmin"
        /**
         * 管理员标识
         */
        var USERINFO = "userinfo"
        /**
         * 账号
         */
        var USER = "user"
        /**
         * 密码
         */
        var PASSWORD = "password"
        /**
         * 用户id
         */
        var USERID = "userid"
        /**
         * 管理员标识
         */
        var TECENTTOKEN = "tecentsign"

        fun setToken(context: Context?, token: String) {
            SPUtils.put(context, TOKEN, token)
        }

        fun getToken(context: Context?): String {
            return SPUtils.get(context, TOKEN, "") as String
        }
    }


}
