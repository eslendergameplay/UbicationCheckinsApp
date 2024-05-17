package com.example.CheckinsApp.Fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.CheckinsApp.interfaces.PasaraActv
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentFirstBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    var fab:FloatingActionButton? = null

    private var _binding: FragmentFirstBinding? = null

    var interfaz:PasaraActv? =null

    var vista:View? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PasaraActv){
            interfaz = context

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        vista = inflater.inflate(R.layout.fragment_first,container,false)

        return vista!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = vista?.findViewById(R.id.floatingActionButton)

        fab?.setOnClickListener {
            interfaz?.pasar()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}