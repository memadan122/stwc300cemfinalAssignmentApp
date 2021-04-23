package com.madan.bookhotel

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.madan.bookhotel.Fragment.DestinationFragment
import com.madan.bookhotel.Fragment.HomeFragment
import com.madan.bookhotel.Fragment.InfoFragment
import com.madan.bookhotel.notification.NotificationChannels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {


    var fragments:MutableList<Fragment> = mutableListOf(HomeFragment(),DestinationFragment(),InfoFragment())
    var fragId:MutableList<Int> = mutableListOf(R.id.ic_home,R.id.ic_destination,R.id.ic_info)

    private val homeFragment = HomeFragment()
    private val destinationFragment = DestinationFragment()
    private val infoFragment = InfoFragment()

    private lateinit var sensorManager: SensorManager
    private var proximitysensor : Sensor? = null
    private var lightsensor : Sensor? = null
    private var accelerometersensor : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(destinationFragment)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if(!checkSensor())
            return
        else{
            lightsensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            proximitysensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            accelerometersensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, proximitysensor,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, lightsensor,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, accelerometersensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

        bottom_navigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_destination -> replaceFragment(destinationFragment)
                R.id.ic_info -> replaceFragment(infoFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
    private fun checkSensor(): Boolean {
        var flag =true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            flag = false
        }
        return flag

    }

    override fun onSensorChanged(event: SensorEvent?) {


        if(event!!.sensor.type == Sensor.TYPE_PROXIMITY)
        {
            val values = event.values[0]
            if(values < 4)
            {
                val notificationManager = NotificationManagerCompat.from(this)
                val notificationChannels = NotificationChannels(this)
                notificationChannels.createNotificationChannels()

                val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle("Object Detect")
                    .setContentText("Object has been Detected!")
                    .setColor(Color.BLUE)
                    .build()

                notificationManager.notify(1, notification)
            }
        }
        if(event.sensor.type == Sensor.TYPE_LIGHT)
        {
            val values = event.values[0]
            if(values > 20000)
            {
                val notificationManager = NotificationManagerCompat.from(this)
                val notificationChannels = NotificationChannels(this)
                notificationChannels.createNotificationChannels()

                val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle("Light Detection")
                    .setContentText("High amount of light detected!")
                    .setColor(Color.BLUE)
                    .build()

                notificationManager.notify(1, notification)
                    }
                }
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            val values = event.values
            val xAxis = values[0]

            if(xAxis<(-7)) {
                replaceFragment(InfoFragment() )
            }
        }
            }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
        }



