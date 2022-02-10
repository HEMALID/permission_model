package com.example.permission_model

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var Fine_Location_rq = 101
    var Camera_rq = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            checkForPermission(android.Manifest.permission.CAMERA,"Camera",Camera_rq)
        }

        var  btn2=findViewById<Button>(R.id.button1)
        btn2.setOnClickListener {
            checkForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,"Location",Fine_Location_rq)
        }
    }

    private fun checkForPermission(permission: String, name: String, reqestcode: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(
                        applicationContext,
                        "$name Permission Granted",
                        Toast.LENGTH_LONG
                    ).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    reqestcode
                )

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), reqestcode)
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innercheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Refused", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
        when(requestCode){
            Fine_Location_rq -> innercheck("Location")
            Camera_rq -> innercheck("Camera")
        }
    }

    private fun showDialog(Permission: String, name: String, reqestcode: Int) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to Your $name is required to use this app")
            setTitle("Permisssion Required")
            setPositiveButton("ok") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Permission),
                    reqestcode
                )
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}