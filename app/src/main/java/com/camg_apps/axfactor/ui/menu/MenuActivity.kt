package com.camg_apps.axfactor.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.camg_apps.axfactor.R
import com.camg_apps.axfactor.ui.main.MainActivity
import com.camg_apps.axfactor.ui.main.ROL

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val vmLoggedIn: LoggedInViewModel by viewModels()

    private var uid = ""
    private var correo = ""
    private var rol = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)

        /*
        vmLoggedIn.userLiveData!!.observe(this, Observer { firebaseUser ->
            if(firebaseUser!=null){
                navView.getHeaderView(0).findViewById<TextView>(R.id.textViewEmail).text = firebaseUser.email
            }
        })
        */

        navView.menu.getItem(2).setOnMenuItemClickListener {
            logOut()
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_formulas, R.id.nav_new_formula
            ), drawerLayout
        )

        getValuesSharedPreferences(navView)


       setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    @SuppressLint("SetTextI18n")
    private fun getValuesSharedPreferences(navView: NavigationView) {
        val sharedPref = getSharedPreferences("PREFERENCE_FILE_KEY",Context.MODE_PRIVATE)
        correo = sharedPref.getString("correo", "")?: ""
        uid = sharedPref.getString("uid", "")?: ""
        rol = sharedPref.getString("rol", "")?: ""
    }


    private fun logOut() {
        vmLoggedIn.logOut()
       val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}