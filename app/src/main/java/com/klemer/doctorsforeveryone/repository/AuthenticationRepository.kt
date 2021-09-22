package com.klemer.doctorsforeveryone.repository

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore
    private val userRepository = UserRepository()

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    fun signInGoogleOnActivityResult(
        requestCode: Int,
        data: Intent?,
        activity: Activity,
        callback: (FirebaseUser?, String?) -> Unit
    ) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!, activity) { user, error ->
                    callback(user, error)
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    // [START signin]
    fun signIn(fragment: Fragment, context: Context) {
        val signInIntent = configureGoogleSignIn(context).signInIntent

        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun currentUser(): FirebaseUser? = auth.currentUser

    fun signOut() {
        auth.signOut()
    }

    private fun configureGoogleSignIn(context: Context): GoogleSignInClient {
        // Congigure Google Sign In
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build().let { gso ->
                return GoogleSignIn.getClient(context, gso)
            }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(
        idToken: String,
        activity: Activity,
        callback: (FirebaseUser?, String?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    println("signInWithCredential:success")
                    val user = auth.currentUser
                    user?.let { firebaseUser ->
                        CoroutineScope(Dispatchers.Default).launch {
                            try {
                                val result = userRepository.getUser(firebaseUser.uid)
                                val userResult = User.fromDocument(result)
                                createUserAtCollection(userResult.id)
                            } catch (e: Exception) {

                            }
                        }
                    }
                    callback(user, null)
                } else {
                    println("signInWithCredential:failure ${task.exception}")
                    callback(null, task.exception.toString())
                }
            }
    }

    private fun createUserAtCollection(userId: String) {
        val collection = "users"
        val data = hashMapOf(
            "admin" to false,
            "name" to "",
            "age" to "",
            "weight" to "",
            "height" to "",
            "gender" to ""
        )

        database.collection(collection).document(userId).set(data)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}