package com.example.diplom2022.views.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.diplom2022.R
import com.example.diplom2022.databinding.ActivityMenuBinding
import com.example.diplom2022.models.SharedPref
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.*


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    private lateinit var sharedPreferences: SharedPref
    lateinit var  nav: DrawerLayout

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =  SharedPref(this)

        if (sharedPreferences.loadDarkThemeState() == true)
            this.setTheme(R.style.darkTheme)
        else
            this.setTheme(R.style.LightTheme)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        if (sharedPreferences.loadDarkThemeState() == true) {
//            this.setTheme(R.style.darkTheme_NoActionBar)
//        } else
//            this.setTheme(R.style.LightTheme_NoActionBar)

        auth = FirebaseAuth.getInstance()

        nav = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val imageView:ImageView = headerView.findViewById(R.id.navProfilePhoto)
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
        navigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.nav_exit) {
                auth.signOut()
                finish()
            }
            if(item.itemId == R.id.nav_guide){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bi4iska.wixsite.com/it-beginner-2022/post/user-manual"))
                startActivity(browserIntent)
            }

            else {
                onNavDestinationSelected(item, navController)
                drawerLayout.closeDrawers()
            }
            false
        }
    }

    override fun onStart() {
        super.onStart()

        if (sharedPreferences.loadDarkThemeState() == true)
            this.setTheme(R.style.darkTheme)
        else
            this.setTheme(R.style.LightTheme)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            TODO()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}




