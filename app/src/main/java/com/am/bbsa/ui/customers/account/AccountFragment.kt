package com.am.bbsa.ui.customers.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        setupCardView()

        return binding.root
    }

    private fun setupCardView() {

        /*profile*/
        binding.cardMenuAccount.cardChangePassword.apply {
            iconCard.setImageResource(R.drawable.icon_secure)
            textTitleCard.text = getString(R.string.change_password)
        }

        /*bahasa*/
        binding.cardMenuAccount.cardLanguage.apply {
            iconCard.setImageResource(R.drawable.icon_laguange)
            textTitleCard.text = getString(R.string.language)
        }

        /*logout*/
        binding.cardMenuAccount.cardLogout.apply {
            Toast.makeText(requireContext(), "Keluar", Toast.LENGTH_SHORT).show()
        }
    }

}