package com.example.postproyects.ui.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.postproyects.R
import com.example.postproyects.ui.viewmodel.MoveViewModel
import com.example.postproyects.ui.viewmodel.SocketViewModel
import com.example.postproyects.utils.ConnectivityManager
import com.example.postproyects.utils.color
import com.example.postproyects.utils.navBottom
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private val cm : ConnectivityManager by viewModels()
    private val mvm : MoveViewModel by viewModels()
    private val svm : SocketViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        cm.registerConnectionObserver(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()
        val state: TextView = findViewById(R.id.textView3)
        cm.isNetworkAvailable.observe(this, Observer {
            if (!it){
                state.text = "Sin conexión"
                state.setBackgroundColor(color(R.color.black))
                state.setTextColor(color(R.color.white))
                state.visibility = View.VISIBLE
            } else {
                state.text = "De nuevo en linea"
                state.setBackgroundColor(color(R.color.susses))
                state.setTextColor(color(R.color.white))
                Handler().postDelayed({
                    state.visibility = View.GONE
                },800)
            }
        })
        mvm.stayFragment.observe(this, Observer {
            if (it){
                val bttNav = mvm.bttomNav.value as BottomNavigationView
                navBottom(bttNav,findNavController(mvm.navCtrl.value as Int))
            }
        })
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
    override fun onBackPressed() {
        //super.onBackPressed()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (svm.socket.value != null) svm.closeConnection()
        cm.unregisterConnectionObserver(this)
    }
}

