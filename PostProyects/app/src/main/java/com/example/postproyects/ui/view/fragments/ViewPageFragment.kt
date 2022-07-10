package com.example.postproyects.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.example.postproyects.R
import com.example.postproyects.data.model.ViewPager
import com.example.postproyects.databinding.ViewPageFragmentBinding
import com.example.postproyects.ui.recycler.adapters.AdapterViewPager
import com.example.postproyects.utils.nav

class ViewPageFragment : Fragment() {
    private val slider = AdapterViewPager(listOf(
        ViewPager("Bienvenido","Conecta",R.drawable.ic_baseline_email_24),
        ViewPager("Phone","Descubre",R.drawable.ic_baseline_phone_24),
        ViewPager("Conoce","Inova",R.drawable.ic_baseline_person_24)))
    private var _binding: ViewPageFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ViewPageFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPage.adapter = slider
        setUpIndicator(slider.itemCount)
        setCurrent(0)
        binding.viewPage.isUserInputEnabled = false
        binding.viewPage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrent(position)
            }
        })
        binding.nextPage.setOnClickListener {
            binding.prevPage.visibility = View.VISIBLE
            if(binding.viewPage.currentItem + 1 < slider.itemCount){
                binding.viewPage.currentItem += 1
                if (binding.viewPage.currentItem == slider.itemCount) binding.nextPage.text = "Finalizar"
            }else{
                nav(Navigation.findNavController(view), ViewPageFragmentDirections.actionViewPageFragmentToMenuFragment())
            }
        }
        binding.prevPage.setOnClickListener {
            binding.nextPage.text = "siguiente"
            if(binding.viewPage.currentItem - 1 < slider.itemCount){
                binding.viewPage.currentItem -= 1
                if (binding.viewPage.currentItem == 0){
                    binding.prevPage.visibility = View.INVISIBLE
                }
            }
        }
    }
    private fun setUpIndicator(count:Int){
        val ind = arrayOfNulls<ImageView>(count)
        val layoutParams:LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in ind.indices){
            ind[i] = ImageView(context)
            ind[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,R.drawable.indicator_active
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicador.addView(ind[i])
        }
    }
    private fun setCurrent(index:Int){
        val child = binding.indicador.childCount
        for (i in 0 until child){
            val imageView = binding.indicador.get(i) as ImageView
            if (i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.indicator_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.indicator_inactive))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}