package com.experion.myfirstapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(){
    var userLiveData = MutableLiveData<String>()
    var passLiveData = MutableLiveData<String>()
    var passwordValue = "Passwor@123"
    var userName = "Admin"
    var loginStateLiveData = MutableLiveData(false)
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