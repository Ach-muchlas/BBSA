package com.am.bbsa.ui.admin.menu.history_deposit_admin
/*tampilan dari fragment ini mengambil dari fragment penimbangan setoran */
/*dengan menampilkan total hasil setoran dan menghilangkan button*/
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentDetailDepositWeighingBinding

class DetailHistoryDepositFragment : Fragment() {

    private var _binding : FragmentDetailDepositWeighingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailDepositWeighingBinding.inflate(inflater, container, false)

        return binding.root
    }

}