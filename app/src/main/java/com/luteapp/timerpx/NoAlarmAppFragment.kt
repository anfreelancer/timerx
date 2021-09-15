package com.luteapp.timerpx

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luteapp.timerpx.databinding.NoAlarmAppBinding

class NoAlarmAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = NoAlarmAppBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnInstallClock.setOnClickListener {
            if(BillingClientSetup.isUpgraded(activity)){
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.deskclock")
                    )
                )
            }

        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NoAlarmAppFragment()
    }
}