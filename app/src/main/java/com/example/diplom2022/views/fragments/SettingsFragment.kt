package com.example.diplom2022.views.fragments

import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.diplom2022.R
import com.example.diplom2022.databinding.FragmentSettingsBinding
import com.example.diplom2022.models.SharedPref
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val photoRequestCode = 1

    private lateinit var sharedPreferences: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = context?.let { SharedPref(it) }!!

        layoutInit()

        saveSettingsBtn.setOnClickListener { save() }

        setPhotoBtn.setOnClickListener { setPhoto() }
    }

    private fun setPhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, photoRequestCode)
    }

    private fun setTheme() {
        if (switchTheme.isChecked) {
            requireContext().setTheme(R.style.darkTheme)
        } else
            requireContext().setTheme(R.style.darkTheme)
//
//        val am = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        am[AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 500] =
//            PendingIntent.getActivity(
//                activity, 0, requireActivity().intent, PendingIntent.FLAG_ONE_SHOT
//                        or PendingIntent.FLAG_CANCEL_CURRENT
//            )
//        val i = requireActivity().baseContext.packageManager
//            .getLaunchIntentForPackage(requireActivity().baseContext.packageName)
//        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        startActivity(i)
        val packageManager = requireContext().packageManager
        val intent = packageManager.getLaunchIntentForPackage(requireContext().packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        requireContext().startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    private fun save() {
        sharedPreferences.setFavoriteState(switchFavorite.isChecked)
        sharedPreferences.setDarkThemeState(switchTheme.isChecked)
        setTheme()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        if (requestCode == photoRequestCode && resultCode == RESULT_OK) {
            try {
                val imageUri: Uri = imageReturnedIntent?.data as Uri
                val imageStream: InputStream? =
                    requireContext().contentResolver.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)

                val navigationView = activity?.findViewById<View>(R.id.nav_view) as NavigationView
                val headerView: View = navigationView.getHeaderView(0)
                val imageView: ImageView = headerView.findViewById(R.id.navProfilePhoto)
                imageView.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun layoutInit() {
        if (sharedPreferences.loadDarkThemeState() == true) {
            requireContext().setTheme(R.style.darkTheme)
            switchTheme.isChecked = true
        } else
            requireContext().setTheme(R.style.LightTheme)
        if (sharedPreferences.loadFavoriteState() == true) {
            switchFavorite.isChecked = true
        }
//        val packageManager = requireContext().packageManager
//        val intent = packageManager.getLaunchIntentForPackage(requireContext().packageName)
//        val componentName = intent!!.component
//        val mainIntent = Intent.makeRestartActivityTask(componentName)
//        requireContext().startActivity(mainIntent)
//        Runtime.getRuntime().exit(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}