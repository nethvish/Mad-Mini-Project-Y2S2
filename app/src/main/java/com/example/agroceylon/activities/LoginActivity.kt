package com.example.agroceylon.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.agroceylon.R
import com.example.agroceylon.databinding.ActivityLoginBinding
import com.example.agroceylon.models.UserModel
import com.example.agroceylon.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting rid of top bar (optional)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        //click event assigned to forget password text
        binding.tvForgotPassword.setOnClickListener(this)
        //click event assgined to register button
        binding.tvRegister.setOnClickListener (this)
        //click event assigned to login text
        binding.btnLogin.setOnClickListener(this)

    }

    fun userLoggedInSuccess(userModel: UserModel) {
        //hide the progress dialog
        hideProgressDialog()

        Log.i("First Name: ", userModel.firstName)
        Log.i("Last Name: ", userModel.lastName)
        Log.i("Email: ", userModel.email)

        //directing users to different screen depending on their status
        if (userModel.profileCompleted == 0) {
            //if user profile is incomplete then launch the userprofile activity.
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, userModel) //parsing details via intent
            startActivity(intent)
        } else {
            //redirect to main screen after log in
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        finish()

    }

    //clickable components are login button, forgotPassword text and register text
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
               R.id.tv_forgot_password -> {
                  val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    loginUser()
                }
                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    //validating login details
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter an email id.", true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter a password.", true)
                false
            }
            else -> {
                true
            }

        }
    }

    //login user
    private fun loginUser() {

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val currentUser = FirebaseAuth.getInstance().currentUser


        if (validateLoginDetails()) {

            showProgressDialog(getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)

                    } else {
//
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)

                    }
                }
        }
    }

}