package com.mika.materialcomponents.botoomnavigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mika.materialcomponents.R

class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_notifications -> {
                    navView.removeBadge(R.id.navigation_notifications)
                    true
                }
                R.id.navigation_dashboard -> {
                    val badge = navView.getOrCreateBadge(R.id.navigation_notifications)
                    badge.isVisible = true
                    badge.number = 50
                    true
                }
                else -> {
                    false
                }
            }
        }

        //添加提示
        val badge = navView.getOrCreateBadge(R.id.navigation_notifications)
        badge.isVisible = true
// An icon only badge will be displayed unless a number is set:
//        badge.number = 99

        //移除掉提示
//        navView.removeBadge(R.id.navigation_notifications)
    }

}