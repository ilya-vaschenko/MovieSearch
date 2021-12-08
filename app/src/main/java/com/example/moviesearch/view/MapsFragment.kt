package com.example.moviesearch.view

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentMapsBinding
import com.example.moviesearch.model.Country
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsFragment : Fragment() {

    companion object {

        const val COUNTRIES_EXTRA = "COUNTRIES_EXTRA"

        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        Log.d("fff", "onMapReady")

        initSearchByAddress(getCountryList(country))
    }


    lateinit var country: Country

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        country = arguments?.getParcelable(DetailFragment.COUNTRIES_EXTRA) ?: Country()
        Log.d("fff", "${country}")

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun getCountryList(country: Country): List<String> { //метод для разделения одного стринга на массив стрингов
        val countriesList = mutableListOf<String>()
        val list = country.name.split(",")
            .toTypedArray() //country.name = "Россия, Канада", countriesList = ["Россия","Канада"]
        countriesList.addAll(list)
        return countriesList

    }

    private fun initSearchByAddress(list: List<String>) {

        val geoCoder = Geocoder(context) //класс, кторый отображает адрес по координатам
        list.forEach {
            try {
                val addresses = geoCoder.getFromLocationName(it, 1)
                if (addresses.size > 0) {

                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(addresses[0].latitude, addresses[0].longitude))
                            .title(it)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker32))
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}