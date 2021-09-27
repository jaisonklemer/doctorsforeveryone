package com.klemer.doctorsforeveryone.repository

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AuthenticationRepositoryTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var successTask: Task<AuthResult>

    @Mock
    private lateinit var failureTask: Task<AuthResult>

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Mock
    private lateinit var authenticate: FirebaseAuth

    @Mock
    private lateinit var authResult: AuthResult

    @Mock
    private lateinit var loginAuthResult: AuthResult

    @Mock
    private lateinit var authException: Exception

    @Mock
    private lateinit var firestore: FirebaseFirestore

    @Mock
    private lateinit var task: Task<AuthResult>

    private lateinit var authRepository: AuthenticationRepository

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(firestore)

        authRepository = AuthenticationRepository(authenticate, firestore, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun loginWithEmailAndPasswordSuccess(): Unit = runBlocking {
        val email = "testando@gmail.com"
        val password = "1234567"

        launch(Dispatchers.Main) {

            Mockito.`when`(authenticate.signInWithEmailAndPassword(email, password))
                .thenReturn(failureTask)

            Mockito.`when`(successTask.isSuccessful).thenReturn(false)

            Mockito.`when`(successTask.result).thenReturn(authResult)

            Mockito.`when`(authResult.user).thenReturn(firebaseUser)

            Mockito.`when`(task.isComplete).thenReturn(true)

            Mockito.`when`(task.result).thenReturn(loginAuthResult)

            Mockito.`when`(authRepository.signInWithEmailAndPassword(email, password))
                .thenReturn(task)

            Mockito.`when`(task.await()).thenReturn(authResult)

            val response = authRepository.signInWithEmailAndPassword(email, password).await()

            assertThat(response.user).isEqualTo(firebaseUser)
        }
    }

    @Test
    fun loginWithEmailAndPasswordFailure(): Unit = runBlocking {
        val email = "testando@gmail.com"
        val password = "1234567"

        launch(Dispatchers.Main) {

            Mockito.`when`(authenticate.signInWithEmailAndPassword(email, password))
                .thenReturn(successTask)

            Mockito.`when`(successTask.isSuccessful).thenReturn(true)

            Mockito.`when`(successTask.result).thenReturn(authResult)

            Mockito.`when`(authResult.user).thenReturn(firebaseUser)

            Mockito.`when`(task.isComplete).thenReturn(true)

            Mockito.`when`(task.result).thenReturn(loginAuthResult)

            Mockito.`when`(authRepository.signInWithEmailAndPassword(email, password))
                .thenReturn(task)

            Mockito.`when`(task.await()).thenReturn(loginAuthResult)

            val response = authRepository.signInWithEmailAndPassword(email, password).await()

            assertThat(response.user).isNull()
        }
    }
}
