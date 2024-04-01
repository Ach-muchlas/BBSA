package com.am.bbsa.ui.admin.menu.nasabah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.data.response.DataItemDetailNasabah
import com.am.bbsa.databinding.FragmentDetailNasabahBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class DetailNasabahFragment : Fragment() {
    private var _binding: FragmentDetailNasabahBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNasabahBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupView() {
        /*mengambil data yang dikirimkan dari nasabah page*/
        val receive = arguments
        val data = receive?.getInt(NasabahFragment.KEY_NASABAH_ID)
        viewModel.showDetailNasabahById(data!!, token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupDisplayData(resource.data?.data)
                    }

                    Status.ERROR -> {}
                }
            }
    }

    private fun setupDisplayData(data: DataItemDetailNasabah?) {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.cardPersonalInformation.apply {
            textValueName.text = data?.user?.name
            textValueNIK.text = data?.NIK
            textValueNumberPhone.text = data?.user?.phoneNumber
        }

        binding.cardPersonalIdentity.apply {
            textValueGender.text = data?.user?.gender
            textValueAddress.text = data?.user?.address
        }

        binding.cardBalance.apply {
            textValueBalance.text = Formatter.formatCurrency(data?.balance ?: 0)
            textValueTemporaryBalance.text =
                Formatter.formatCurrency(data?.temporaryBalance ?: 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
        _binding = null
    }

}