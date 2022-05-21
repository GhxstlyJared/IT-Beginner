package com.example.diplom2022.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.diplom2022.R
import com.example.diplom2022.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    lateinit var gridView: GridView
    private var arrayLesson = arrayOf("Урок 1", "Урок 2", "Урок 3", "Урок 4", "Урок 5", "Урок 6", "Урок 7", "Урок 8",)

    // Авторизация с Google
    //lateinit var imageView: ImageView
    lateinit var tvName: TextView
    lateinit var tvEmail: TextView
    lateinit var auth: FirebaseAuth
    // Выход через меню
    lateinit var  nav: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //val currentUser = auth.currentUser

//        tvName = findViewById(R.id.textName)
//        tvEmail = findViewById(R.id.textEmail)
//
//        tvName.text = currentUser?.displayName
//        tvEmail.text = currentUser?.email

        nav = findViewById(R.id.drawer_layout)

//        gridView = findViewById(R.id.grid)
//        val data = arrayLesson
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
//        gridView.adapter = adapter


        setSupportActionBar(binding.appBarMenu.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun setUpNavHeader() {
//        //val nh = supportActionBar
//        val openDialog = Dialog(this)
//        val imageView = openDialog.findViewById<ImageView>(R.id.imageView)
//        Thread {
//            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
//            val dIcon = BitmapDrawable(resources, bMap).bitmap
//            runOnUiThread {
//                imageView.setImageBitmap(dIcon)
//                //nh?.setDisplayHomeAsUpEnabled(true)
//                //nh?.setHomeAsUpIndicator(dIcon)
//                //nh?.title = auth.currentUser?.displayName
//            }
//        }.start()
//
//    }

}




