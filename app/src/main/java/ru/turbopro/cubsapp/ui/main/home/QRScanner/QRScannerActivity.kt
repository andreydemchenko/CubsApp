package ru.turbopro.cubsapp.ui.main.home.QRScanner

import android.Manifest.permission.CAMERA
import android.Manifest.permission.VIBRATE
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import eu.livotov.labs.android.camview.ScannerLiveView
import eu.livotov.labs.android.camview.ScannerLiveView.ScannerViewEventListener
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder
import ru.turbopro.cubsapp.databinding.ActivityQrscannerBinding


class QRScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrscannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkPermission()) {
            // if permission is already granted display a toast message
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        binding.camview.scannerViewEventListener = object : ScannerViewEventListener {
            override fun onScannerStarted(scanner: ScannerLiveView) {
                // method is called when scanner is started
                Toast.makeText(this@QRScannerActivity, "Scanner Started", Toast.LENGTH_SHORT).show()
            }

            override fun onScannerStopped(scanner: ScannerLiveView) {
                // method is called when scanner is stopped.
                Toast.makeText(this@QRScannerActivity, "Scanner Stopped", Toast.LENGTH_SHORT).show()
            }

            override fun onScannerError(err: Throwable) {
                // method is called when scanner gives some error.
                Toast.makeText(
                    this@QRScannerActivity,
                    "Scanner Error: " + err.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCodeScanned(data: String) {
                // method is called when camera scans the
                // qr code and the data from qr code is
                // stored in data in string format.
                binding.idTVscanned.text = data
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val decoder = ZXDecoder()
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.scanAreaPercent = 0.8
        // below method will set secoder to camera.
        binding.camview.decoder = decoder
        binding.camview.startScanner()
    }

    override fun onPause() {
        // on app pause the
        // camera will stop scanning.
        binding.camview.stopScanner()
        super.onPause()
    }

    private fun checkPermission(): Boolean {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        val camera_permission = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        val vibrate_permission = ContextCompat.checkSelfPermission(applicationContext, VIBRATE)
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission() {
        // this method is to request
        // the runtime permission.
        val PERMISSION_REQUEST_CODE = 200
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA, VIBRATE), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // this method is called when user
        // allows the permission to use camera.
        if (grantResults.isNotEmpty()) {
            val camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            val vibrate_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
            if (camera_accepted && vibrate_accepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Permission Denined \n You cannot use app without providing permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}