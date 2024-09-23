package com.example.a18_firebase_app

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel : ViewModel() {

    // Firebase Auth Instance
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthState()
    }

    // Check current auth state
    private fun checkAuthState() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.UnAuthenticated
        }
    }

    // Login with email and password
    fun login(email: String, password: String, context: Context) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    Log.d("Login", "login: success ${_authState.value}")
                    Toast.makeText(context, "Authentication login success.",Toast.LENGTH_SHORT).show()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                    Log.d("Login", "login: ${task.exception?.message}")
                }
            }
    }


    // Create new account with email and password
    fun signUp(email: String, password: String, context: Context) {
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't by empty")
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    Toast.makeText(context, "Authentication register success.",Toast.LENGTH_SHORT).show()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")

                    Log.d("Create Account", "signUp: ${task.exception?.message}")

                    Toast.makeText(context, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Sign out current user
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }

}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object UnAuthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}