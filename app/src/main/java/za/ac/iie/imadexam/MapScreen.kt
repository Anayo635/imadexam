package za.ac.iie.imadexam

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val artistLocations = mapOf(
        "Artist A" to LatLng(-33.852, 151.211),  // Sydney
        "Artist B" to LatLng(40.7128, -74.0060), // New York
        "Artist C" to LatLng(51.5074, -0.1278),  // London
        "Artist D" to LatLng(35.6762, 139.6503)  // Tokyo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapscreen)

        // Obtain the SupportMapFragment and get notified when the map is ready
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<Button>(R.id.btnBackToMain).setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add markers for each artist location
        artistLocations.forEach { (artist, location) ->
            mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(artist)
            )
        }

        // Move camera to first location
        artistLocations.values.firstOrNull()?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 2f))
        }

        // Set marker click listener
        mMap.setOnMarkerClickListener { marker ->
            Toast.makeText(
                this,
                "Artist: ${marker.title}\nLocation: ${marker.position.latitude}, ${marker.position.longitude}",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
    }
}