package com.example.diplom2022.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.diplom2022.R
import com.example.diplom2022.databinding.ActivityMenuBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    private var arrayLesson = arrayOf(
        "Урок 1",
        "Урок 2",
        "Урок 3",
        "Урок 4",
        "Урок 5",
        "Урок 6",
        "Урок 7",
        "Урок 8"
    )

    lateinit var auth: FirebaseAuth
    lateinit var  nav: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        nav = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val imageView:ImageView = headerView.findViewById(R.id.navImageView)
        val userNameTextView : TextView = headerView.findViewById(R.id.userNameTextView)
        val userEmailTextView : TextView = headerView.findViewById(R.id.userEmailTextView)

        Picasso.get().load(auth.currentUser?.photoUrl).fit().centerCrop()
            .placeholder(R.drawable.prof)
            .error(R.drawable.prof)
            .into(imageView);
        userNameTextView.text = auth.currentUser?.displayName
        userEmailTextView.text = auth.currentUser?.email

        setSupportActionBar(binding.appBarMenu.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_lesson,
                R.id.nav_test,
                R.id.nav_guide,
                R.id.nav_settings,
                R.id.nav_info,
                R.id.nav_exit
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_exit -> {
                auth.signOut()
                finish()
            }
        }
        nav.closeDrawer(GravityCompat.START)
        return onNavigationItemSelected(menuItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out) {
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}




