package id.phephen.roomcrud.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import id.phephen.roomcrud.databinding.ActivityDetailUserBinding
import id.phephen.roomcrud.model.User

class DetailUserActivity : FragmentActivity(), OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var ivProfile: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var mapView: MapView

    private var user: User? = null
    private var mMap: GoogleMap? = null
    private lateinit var mLastLocation: Location
    private lateinit var mLocationResult: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private var mCurrLocationMarker: Marker? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        var mapView: MapView? = null
//        mapView = binding.map
//        mapView.getMapAsync(this)
        getIntentData()
        initView()
        initialize()
        setupUI()
    }

    private fun getIntentData () {
        if (intent.hasExtra("data")) {
            val newUser: User = intent.getParcelableExtra("data")!!
            newUser.let {
                user = it
            }
        }
    }

    private fun initialize () {
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        mapView.getMapAsync(this)
    }

    private fun initView() {
        ivProfile = binding.ivProfile
        tvName = binding.tvName
        tvEmail = binding.tvEmail
        tvPhone = binding.tvPhone
        mapView = binding.map
    }

    private fun setupUI () {
        tvName.text = user!!.name
        tvEmail.text = user!!.email
        tvPhone.text = user!!.phone
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))

        //stop location updates
        if (mGoogleApiClient != null) {
            mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
    }

    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            mFusedLocationClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(applicationContext, "connection suspended", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(applicationContext, "connection failed", Toast.LENGTH_SHORT).show()
    }
}