package com.madan.bookhotelwear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madan.bookhotel.adapter.DestinationAdpater
import com.madan.bookhotel.repository.DestinationRepository
import com.madan.bookhotelwear.Entity.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : WearableActivity() {
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        rv = findViewById(R.id.RvRecyclerView)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val destination = DestinationRepository()
                val response = destination.getDestination()
                if (response.success == true) {
                    //put all the destination details in lstdestination

                    val lstDestination = response.data
                    println(lstDestination!!.size)
                    if (lstDestination != null) {
                        withContext(Dispatchers.Main) {

                            rv.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                            rv.adapter = DestinationAdpater(this@MainActivity, response.data as MutableList<Destination>)
                        }
                    }
                }
            }
            catch (ex : Exception){
                println(ex.printStackTrace())
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
