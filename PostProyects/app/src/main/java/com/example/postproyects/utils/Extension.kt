package com.example.postproyects.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.ui.setupWithNavController
import com.example.postproyects.data.database.entities.UserEntity
import com.example.postproyects.data.model.users.UserToken
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.regex.Pattern

fun Any?.isNull() = this == null
fun Fragment.toast(text:String,length:Int = Toast.LENGTH_SHORT)=Toast.makeText(context,text,length).show()
fun Activity.toast(text:String,length:Int = Toast.LENGTH_SHORT)=Toast.makeText(this,text,length).show()
fun Activity.color(@ColorRes color:Int) = ContextCompat.getColor(this,color)
fun Fragment.color(@ColorRes color:Int) = ContextCompat.getColor(requireContext(),color)
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
fun nav(navController: NavController, action: NavDirections) = navController.navigate(action)
fun navBottom(navView: BottomNavigationView,navController:NavController)=navView.setupWithNavController(navController)
fun UserToken.toDatabase() = UserEntity(token =  token)