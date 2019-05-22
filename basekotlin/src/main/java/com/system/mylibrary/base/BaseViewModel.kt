package com.system.mylibrary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonObject

abstract class BaseViewModel<K : BaseRepository>(application: Application) : AndroidViewModel(application) {
    var mRepository: K

    var liveData: MediatorLiveData<JsonObject> = MediatorLiveData()
    var erroLiveData: MediatorLiveData<JsonObject> = MediatorLiveData()

    protected abstract val resitory: K


    init {

        mRepository = resitory

    }

}
