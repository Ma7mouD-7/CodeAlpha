 package com.ma7moud27.onlinebookshop.ui.register.viewmodel 
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ma7moud27.onlinebookshop.ui.register.repository.RegisterRepository

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel(){
    private val _$liveData = MutableLiveData<$>()
    val $LiveData: LiveData<$> = _$LiveData
}