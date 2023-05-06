package com.example.agroceylon.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.agroceylon.activities.LoginActivity
import com.example.agroceylon.activities.RegisterActivity
import com.example.agroceylon.models.UserModel
import com.example.agroceylon.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.database.FirebaseDatabase

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    private val mDB = FirebaseDatabase.getInstance().getReference("Users")

    fun registerUser(activity: RegisterActivity, userModelInfo: UserModel) {

        //"users" is a collection name. if the collection is already created then it will not create the same collection
        mFireStore.collection(Constants.USERS)
            //document ID for users fields.Here the document ID is the User ID
            .document(userModelInfo.id)
            //here the userInfo are field and the Setoption is set to merge.IT is for if we wants to merge later
            .set(userModelInfo, SetOptions.merge())
            .addOnSuccessListener{

                //here call a function of base activity for trnsferring the result to it.
                activity.userRegistrationSuccess()

            }
            .addOnFailureListener{
                e -> activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    fun getCurrentUserId(): String {
        //instance of current user using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        //a variable to assign the current user id if it is not null or else it will be blank
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                val userModel = document.toObject(UserModel::class.java)!!

//                if (user != null) {

                    val sharedPref = activity.getSharedPreferences(
                        Constants.AGRO_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                   val editor: SharedPreferences.Editor = sharedPref.edit()
                   // KEY : logged_int_username
                   // Value :  Sithija Santhiya
                   editor.putString(
                        Constants.LOGGED_IN_USERNAME,
                        "${userModel.firstName} ${userModel.lastName}"
                    )
                    editor.apply()

                    when (activity) {
                        is LoginActivity -> activity.userLoggedInSuccess(userModel)
                       // is RegisterActivity -> activity.userLoggedInSuccess(user)
                       // is SettingsActivity -> activity.userDetailSuccess(user)
                    }
               // }

            }.addOnFailureListener { exception ->
                when (activity) {
                    is LoginActivity -> activity.hideProgressDialog()
                    //is RegisterActivity -> activity.hideProgressDialog()
                    //is SettingsActivity -> activity.hideProgressDialog()
                }
                Log.e(activity.javaClass.simpleName, exception.message.toString())
            }
    }

}