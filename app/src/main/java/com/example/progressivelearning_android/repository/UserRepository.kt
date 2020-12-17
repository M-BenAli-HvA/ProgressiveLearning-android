package com.example.progressivelearning_android.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.progressivelearning_android.api.ProgressiveLearningApi
import com.example.progressivelearning_android.model.User
import com.example.progressivelearning_android.services.AuthenticationApiService
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.withTimeout
import okhttp3.Headers
import retrofit2.Retrofit

class UserRepository {

   private var listOfUsers: ArrayList<User> = arrayListOf(
        User(null, "mohamed_ben.ali@hotmail.com", "dummy1234", false)
    )
    private val TAG = "UserRepository"
    private val plApi: Retrofit = ProgressiveLearningApi.createApi()
    private val authenticationApiService = plApi.create(AuthenticationApiService::class.java)
    private var _loggedInUser: MutableLiveData<User?> = MutableLiveData()
    private var _authenticationToken: MutableLiveData<String?> =  MutableLiveData()

    val loggedInUser: LiveData<User?>
        get() = _loggedInUser

    val authenticationToken: LiveData<String?>
        get() = _authenticationToken

    suspend fun signUp(email: String, password: String,
                       confirmPassword: String) {
        val user = User(null, email, password, false)
        if (user.password == confirmPassword) {
            try {
                val result = withTimeout(5_000) {
                    authenticationApiService.createUser(user)
                }
                _loggedInUser.value = result.body()
                val token: String? = result.headers().get("Authorization")?.split(" ")?.get(1)
                _authenticationToken.value = token
//                Log.d(TAG, result.headers().get("Authorization")?.split(" ")?.get(1).toString())
            } catch (e: Error) {
                Log.d("UserRepository", e.message.toString())
            }
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val result = withTimeout(5_000) {
                val user: User = User(null, email, password)
                authenticationApiService.loginUser(user)
            }
            _loggedInUser.value = result.body()
        } catch(e: Error) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun getUser(user: User): User? {
        var foundUser: User? = null
        for (u in listOfUsers) {
            if(user.email == u.email && user.password == u.password) {
                foundUser = user
            }
        }
        return foundUser
    }
}