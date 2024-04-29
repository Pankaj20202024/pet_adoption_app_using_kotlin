package com.example.petadoptionapp.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.petadoptionapp.R
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class FirstPage : AppCompatActivity(), SensorEventListener {
    private lateinit var auth: FirebaseAuth

    private lateinit var SignInButtonWithEmail: Button
    private lateinit var SingUpButtonWithEmail: Button
    private lateinit var dogImg: ImageView // Add this line
    private lateinit var singinlayout : LinearLayout

    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val window: Window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        auth = FirebaseAuth.getInstance()
        SingUpButtonWithEmail = findViewById(R.id.Login_With_Email)
        SignInButtonWithEmail = findViewById(R.id.already_have_account)
        dogImg = findViewById(R.id.imageView4)
        singinlayout = findViewById(R.id.singinlayout)



        // Animation code
        val transAnim = TranslateAnimation(
            0f, 0f, -1200f, 10f
        )
        transAnim.startOffset = 500
        transAnim.duration = 2500
        transAnim.fillAfter = true

        transAnim.interpolator = BounceInterpolator()
        transAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                dogImg.clearAnimation()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        dogImg.startAnimation(transAnim)

        // Fade In Animation for Buttons
        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = 1000
        SingUpButtonWithEmail.startAnimation(fadeInAnimation)


        // Fade In Animation for TextView
        val fadeInAnimation3 = AlphaAnimation(0f, 1f)
        fadeInAnimation3.duration = 1000
        singinlayout.startAnimation(fadeInAnimation3)

        // Initialize SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Get light sensor
        lightSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)


        if (lightSensor == null) {
            Toast.makeText(this , "Light sensor is not their in your device...." , Toast.LENGTH_SHORT).show()
        }

        SingUpButtonWithEmail.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        SignInButtonWithEmail.setOnClickListener{
            val intent = Intent(this , SignInActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Register light sensor listener when the activity is resumed
        lightSensor?.also { sensor ->
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister light sensor listener when the activity is paused
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LIGHT) {
                // Light sensor event
                // Ambient light level in SI lux units
                val lightLevel = it.values[0]
                // Display a toast message with the light level
//                Toast.makeText(this , "Light level: $lightLevel lux", Toast.LENGTH_SHORT).show()
                // Adjust screen brightness based on light level
                adjustBrightness(lightLevel)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }

    private fun adjustBrightness(lightLevel: Float) {
        val brightnessMode = Settings.System.getInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        )

        if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            // If automatic brightness is enabled, adjust system brightness
            val brightnessValue = (lightLevel / SensorManager.LIGHT_SUNLIGHT_MAX) * 255
            Settings.System.putInt(
                contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightnessValue.toInt()
            )
        }
    }
}