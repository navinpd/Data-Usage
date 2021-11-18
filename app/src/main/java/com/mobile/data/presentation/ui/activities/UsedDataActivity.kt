package com.mobile.data.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.data.R
import com.mobile.data.presentation.ui.fragment.UsedDataFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsedDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_used_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UsedDataFragment.newInstance())
                .commitNow()
        }
    }
}