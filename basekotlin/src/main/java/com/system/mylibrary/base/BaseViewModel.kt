package com.system.mylibrary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData

abstract class BaseViewModel<T, K : BaseRepository>(application: Application) : AndroidViewModel(application) {
    var mRepository: K

    var liveData: MediatorLiveData<T>

    protected abstract val resitory: K


    init {
        liveData = MediatorLiveData()

        mRepository = resitory

    }

}
