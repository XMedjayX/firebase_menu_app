package com.example.firebase_menu_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebase_menu_app.R
import com.example.firebase_menu_app.databinding.FragmentHomeBinding
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.FirebaseAnalytics

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val firebaseAnalytics = Firebase.analytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_dashboard).setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
            logButtonClickEvent("Dashboard_Button_Clicked", "go-to-dashboard")
            logScreenView("/firebase_meta/dashboard")
        }

        view.findViewById<Button>(R.id.btn_notifications).setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
            logButtonClickEvent("Notifications_Button_Clicked", "go-to-notifications")
            logScreenView("/firebase_meta/notifications")
        }
    }

    private fun logButtonClickEvent(buttonName: String, buttonText: String) {
        val bundle = Bundle().apply {
            putString("custom_section", "home")
            putString("custom_type", "botao")
            putString("custom_title", buttonText)
        }
        firebaseAnalytics.logEvent("clique", bundle)
    }

    private fun logScreenView(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}