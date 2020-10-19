package com.jesusbadenas.goldenspearchallenge.splash

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.util.showError
import com.jesusbadenas.goldenspearchallenge.viewmodel.SplashViewModel
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        subscribe()
    }

    override fun onStart() {
        super.onStart()
        requestPermissions()
    }

    private fun subscribe() {
        viewModel.navigateAction.observe(this) {
            navigateToArtist()
        }
        viewModel.uiError.observe(this) { uiError ->
            showError(uiError)
        }
    }

    private fun navigateToArtist() {
        navigator.navigateToArtistActivity(this)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_ACCOUNTS_AND_CALENDAR)
    private fun requestPermissions() {
        val perms = arrayOf(
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CALENDAR,
        )
        // Already have permission
        if (EasyPermissions.hasPermissions(this, *perms)) {
            viewModel.refreshToken()
        }
        // Do not have permissions, request them now
        else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permissions_rationale),
                RC_ACCOUNTS_AND_CALENDAR,
                *perms
            )
        }
    }

    companion object {
        private const val RC_ACCOUNTS_AND_CALENDAR = 1000
    }
}
