package com.madan.bookhotel.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madan.bookhotel.R
import com.madan.bookhotel.adapter.DestinationAdpater
import com.madan.bookhotel.db.UserDB
import com.madan.bookhotel.entity.Destination
import com.madan.bookhotel.repository.DestinationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DestinationFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private lateinit var destinationsss: MutableList<Destination>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_destination, container, false)
        rv = view.findViewById(R.id.RvRecyclerView)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val destination = DestinationRepository()
                val response = destination.getDestination()
                if (response.success == true) {
                    //put all the destination details in lstdestination

                    val lstDestination = response.data
                    if (lstDestination != null) {
                        withContext(Dispatchers.Main) {
                            context?.let { UserDB.getInstance(it).getDestinationDAO().deletedestination() }
                            context?.let { UserDB.getInstance(it).getDestinationDAO().insertDestinations(lstDestination) }
                            destinationsss = UserDB.getInstance(requireContext()).getDestinationDAO().getDestinations()

                            rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            rv.adapter = DestinationAdpater(container!!.context, response.data as MutableList<Destination>)
                        }
                    }
                }
            }
            catch (ex : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }

            }
        }
        return view

    }

//    private fun uploadImage(studentId: String) {
//        if (dimage != null) {
//            val file = File(dimage!!)
//            val reqFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
//            val body =
//                    MultipartBody.Part.createFormData("file", file.name, reqFile)
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val destinationRepository = DestinationRepository()
//                    val response = destinationRepository.uploadImage(studentId, body)
//                    if (response.success == true) {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(this@DestinationFragment, "Uploaded", Toast.LENGTH_SHORT)
//                                    .show()
//                        }
//                    }
//                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Log.d("Mero Error ", ex.localizedMessage)
//                        Toast.makeText(
//                                this@DestinationFragment,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        }
//    }
}






