package com.multi.trivia.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multi.trivia.data.model.User
import com.multi.trivia.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val statusRegister: MutableLiveData<Long> = MutableLiveData()

    // Temporal
    lateinit var statusUpdate: MutableLiveData<Int>
    lateinit var statusDelete: MutableLiveData<Int>

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun register(user: User) = viewModelScope.launch(Dispatchers.IO) {
        statusRegister.postValue(userRepository.register(user))
    }

    fun update(user: User) = viewModelScope.launch(Dispatchers.IO) {
        statusUpdate.postValue(userRepository.update(user))
    }

    fun delete(userId: Long) = viewModelScope.launch(Dispatchers.IO) {
        statusDelete.postValue(userRepository.delete(userId))
    }

}