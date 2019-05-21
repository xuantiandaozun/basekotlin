package com.system.mylibrary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class BaseViewModel<T, K : BaseRepository>(application: Application) : AndroidViewModel(application) {
    var mRepository: K

    var liveData: LiveData<T> = MediatorLiveData()

    protected abstract val resitory: K


    init {

        mRepository = resitory

    }

}
