package com.system.mylibrary.interfaces

interface PermissionInterface {
    /**
     * 可设置请求权限请求码
     */
    val permissionsRequestCode: Int

    /**
     * 设置需要请求的权限
     */
    val permissions: Array<String>

    /**
     * 请求权限成功回调
     */
    fun requestPermissionsSuccess()

    /**
     * 请求权限失败回调
     */
    fun requestPermissionsFail()
}
