package com.experion.myfirstapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){
    var userLiveData = MutableLiveData<String>()
    var passLiveData = MutableLiveData<String>()
    var passwordValue = "Password@123"
    var userName = "admin@gmail.com"
    var loginStateLiveData = MutableLiveData<Boolean>()
    init {
       userLiveData.value = ""
       passLiveData.value = ""
    }

    fun isValidLoginData(userNameText: String, password: String) {
        if(userNameText.isNotEmpty() && password.isNotEmpty()){
            loginStateLiveData.postValue(userName == userNameText && password == passwordValue)

        }
    }


}