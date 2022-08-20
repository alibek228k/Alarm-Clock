package kz.ali.alarmclock.ui.presentation.createalarm.numberfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R

class NumberFragment : Fragment(R.layout.time_element_item) {

    companion object {
        private const val KEY = "KEY"
        fun newInstance(number: Int): NumberFragment {
            val args = Bundle()
            args.putInt(KEY, number)
            val fragment = NumberFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var number: MaterialTextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        number = view.findViewById(R.id.number)
        number?.text = if (arguments?.getInt(KEY) in 0..9) "0${
            arguments?.getInt(KEY)?.toString()
        }" else arguments?.getInt(KEY)?.toString()

    }

}