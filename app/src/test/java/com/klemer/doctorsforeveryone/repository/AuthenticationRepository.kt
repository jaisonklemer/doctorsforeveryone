package com.klemer.doctorsforeveryone.repository

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.klemer.doctorsforeveryone.interfaces.ResponseListener
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AuthenticationRepositoryFake(
    private val observer: ResponseListener,
    private val auth: FirebaseAuth
) {

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).apply {
            if (this.isSuccessful) observer.success()
            else observer.failure()
        }
    }
}

@RunWith(JUnit4::class)
class AuthenticationRepositoryTest : ResponseListener {

    private lateinit var repository: UserRepository

    @Mock
    private lateinit var successTask: Task<AuthResult>

    @Mock
    private lateinit var failureTask: Task<AuthResult>

    @Mock
    private lateinit var authenticate: FirebaseAuth

    @Mock
    private lateinit var firestore: FirebaseFirestore


    private var result = DEFAULT

    private lateinit var authFake: AuthenticationRepositoryFake

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = 2
        private const val DEFAULT = 0
    }


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = UserRepository(firestore)
        authFake = AuthenticationRepositoryFake(this, authenticate)
    }

    @Test
    fun loginWithEmailAndPasswordSuccess() {
        val email = "testando@gmail.com"
        val password = "123456"
        Mockito.`when`(successTask.isSuccessful).thenReturn(true)
        Mockito.`when`(authenticate.signInWithEmailAndPassword(email, password))
            .thenReturn(successTask)

        authFake.logIn(email, password)
        assertThat(result).isEqualTo(SUCCESS)
    }

    @Test
    fun loginWithEmailAndPasswordFailure() {
        val email = "testando@gmail.com"
        val password = "123456"
        Mockito.`when`(successTask.isSuccessful).thenReturn(false)
        Mockito.`when`(authenticate.signInWithEmailAndPassword(email, password))
            .thenReturn(failureTask)

        authFake.logIn(email, password)
        assertThat(result).isEqualTo(FAILURE)

    }

    override fun success() {
        result = SUCCESS
    }

    override fun failure() {
        result = FAILURE
    }
}