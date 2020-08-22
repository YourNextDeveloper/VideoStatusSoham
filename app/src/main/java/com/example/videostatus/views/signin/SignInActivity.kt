package com.example.videostatus.views.signin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.example.videostatus.R
import com.example.videostatus.base.BaseActivity
import com.example.videostatus.databinding.ActivitySignInBinding
import com.example.videostatus.utils.PreferenceHelper
import com.example.videostatus.utils.USER_DATA
import com.example.videostatus.utils.get
import com.example.videostatus.viewmodels.SignInViewModel
import com.facebook.*
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONException
import kotlin.reflect.KClass

class SignInActivity : BaseActivity<SignInViewModel,ActivitySignInBinding>() {

    override val modelClass: KClass<SignInViewModel> = SignInViewModel::class
    override val layoutId: Int = R.layout.activity_sign_in

    private val publicProfile = "public_profile";
    private val email = "email";

    private lateinit var callbackManager: CallbackManager

    override fun initControls() {

        callbackManager = CallbackManager.Factory.create()
        addObserver()

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (isLoggedIn) {
            getUserProfile(AccessToken.getCurrentAccessToken())
        }

        fbLogin.setReadPermissions(listOf(email, publicProfile));
        // If you are using in a fragment, call loginButton.setFragment(this);

        fbLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                getUserProfile(loginResult.accessToken)
            }

            override fun onCancel() {
                // App code
//                Toast.makeText(this@SignInActivity, "Cancel", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                // App code
//                Toast.makeText(this@SignInActivity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addObserver() {
        viewModel.userSignInData.observe(this, Observer {

            PreferenceHelper.saveObject(USER_DATA,it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        val fbData = data!!.extras.toString()

        Log.e("TAG", data.extras.toString())

    }

    private fun getUserProfile(currentAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            currentAccessToken
        ) { `object`, _ ->
            Log.d("TAG", `object`.toString())

            try {
                viewModel.name.value ="${`object`.getString("first_name")} ${`object`.getString("last_name")}"
                viewModel.email.value = `object`.getString("email")
                viewModel.id.value = `object`.getString("id")
                viewModel.profileImageUrl.value = "https://graph.facebook.com/" + viewModel.id.value + "/picture?type=normal"

                viewModel.addUser(this)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}