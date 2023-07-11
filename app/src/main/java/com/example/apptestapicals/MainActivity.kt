package com.example.apptestapicals


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.telephony.SmsManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.apptestapicals.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1
    private val RECEIVE_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Check And Get Permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_DENIED
            ) {
                Log.d("permission", "permission denied to RECEIVE_SMS - requesting it")
                val permissions = arrayOf<String>(Manifest.permission.RECEIVE_SMS)
                requestPermissions(permissions, RECEIVE_PERMISSION_REQUEST_CODE)
                Log.d("result", permissions.toString())
            } else if ((checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                        == PackageManager.PERMISSION_GRANTED)){
                Log.d("Receive sms permissions", "Permissions Granted")
            }

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED
            ) {
                Log.d("permission", "permission denied to SEND_SMS - requesting it")
                val permissions = arrayOf<String>(Manifest.permission.SEND_SMS)
                requestPermissions(permissions, SEND_SMS_PERMISSION_REQUEST_CODE)
                Log.d("result", permissions.toString())
            } else if ((checkSelfPermission(Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED)){
                Log.d("Send sms permissions", "Permissions Granted")
            }
        }

        binding.appBarMain.fab.setOnClickListener { view ->

            Log.d("onClik button", "button clicked");

           /*
                try {
                    GlobalScope.launch {
                        Log.d("on send sms", "on send sms");
                        if (Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.M) {
                            val smsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage("+243810994698", null, "response", null, null)
                            Log.d("send sms status", "sent: response");
                        } else {
                            val smsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage("+243810994698", null, "response", null, null)
                            Log.d("send sms status", "sent: response");
                        }
                    }
                } catch (e: Exception) {
                    Log.d("error", e.toString());
                }

            */



/*

            GlobalScope.launch {
                println("***start coroutines.")
                try {
                    val BASE_URL = "https://eppt.graciasgroup.com/api/sms/send/base64?phone_number=+243825937168&message=ZXBwdCB2ZXJpZnkgLWMgMTIyMjM0NDUzIC12IDIwMDA"

                    val body = mapOf(
                        "phoneNumber" to "0824019836",
                        "message" to "OK"
                    )

                    var response = "";

                    val url = URL(BASE_URL)
                    val connection = url.openConnection()
                    BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
                        var line: String?
                        while (inp.readLine().also { line = it } != null) {
                            response = line.toString();
                            Log.d("Response: ", line.toString());
                        }
                    }

                    // val listResult = UserApi.retrofitService.sendUser();
                    // Log.d("result:", listResult.toString());

                    if (response.contains("SMSSendDto")) {
                        if (Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.M) {
                            val smsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage("+243810994698", null, "open", null, null)
                            Log.d("send sms status", "sent: $response");
                        } else {
                            val smsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage("+243810994698", null, "open", null, null)
                            Log.d("send sms status", "sent: $response");
                        }
                    } else {
                        val smsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage("+243810994698", null, "close", null, null)
                        Log.d("send sms status", "sent: close");
                    }

                } catch (e: Exception) {
                    Log.d("error on coroutines:", e.toString());
                }
            }

 */



            /* val thread = Thread {
                println("${Thread.currentThread()} has run.")
            }
            thread.start();
             */
            Log.d("onclick", "click it");
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//               .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}