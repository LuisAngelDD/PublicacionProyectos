package com.example.postproyects.ui.view.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postproyects.databinding.UploadDataFragmentBinding
import com.example.postproyects.ui.viewmodel.LocalDataViewModel
import com.example.postproyects.ui.viewmodel.acceso_registro.UploadDataViewModel
import com.example.postproyects.utils.*
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class UploadDataFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private val uriPathHelper = RealPathUtil()
    private val cm : ConnectivityManager by activityViewModels()
    private val sldvm : LocalDataViewModel by activityViewModels()
    private lateinit var udvm : UploadDataViewModel
    private var _binding: UploadDataFragmentBinding? = null
    private val binding get() = _binding!!
    private var filePath : String? = ""
    private var ocupacion : String = ""
    private var flag = true
    private lateinit var file:File
    companion object {
        private const val GALLERY_REQ_CODE = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = UploadDataFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        udvm = ViewModelProvider(this)[UploadDataViewModel::class.java]
        binding.upLayoutData.setOnClickListener {
            hideKeyboard()
        }
        val items = arrayOf(
            "Estudiante",
            "Docente"
        )
        val itemAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line, items)
        with(binding.spinner3){
            setAdapter(itemAdapter)
            onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> ocupacion=parent?.getItemAtPosition(position).toString() }
        }
        binding.imgUpload.setOnClickListener {
            requestPermission()
            if (dataPermission()){
                val gallery = Intent(Intent.ACTION_PICK)
                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, GALLERY_REQ_CODE)
            }
        }
        binding.imageView10.setOnClickListener {
            hideKeyboard()
        }
        binding.imageView9.setOnClickListener {
            hideKeyboard()
        }
        binding.userName.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout.error = null
        }
        binding.aboutYou.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout2.error = null
        }
        binding.spinner3.doOnTextChanged { text, start, before, count ->
            binding.textInputLayout1.error = null
        }
        binding.btnUploadData.setOnClickListener {
            if (flag) {
                flag = false
                val a = arrayListOf<Int>()
                a.addAll(listOf(0, 0, 0))
                if (filePath.equals("")) {
                    a[0] = 1
                }
                if (ocupacion == "") {
                    a[1] = 1
                    binding.textInputLayout1.error = "Agregue una ocupacion"
                }
                if (binding.userName.text.toString() == "") {
                    a[2] = 1
                    binding.textInputLayout.error = "Agregue un nombre de usuario"
                }
                if (a[1] == 0 && a[2] == 0) {
                    val about = binding.aboutYou.text.toString()
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val userName = binding.userName.text.toString()
                        .toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val occupation =
                        ocupacion.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                    if (a[0] == 0) {
                        if (dataPermission()) {
                            val file = File(filePath)
                            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                            val img = MultipartBody.Part.createFormData("image", file.name, requestBody)
                            if (cm.isNetworkAvailable.value == true) {
                                udvm.upData(sldvm.data.value.toString(), img, userName, about, occupation)
                            } else {
                                toast("No hay conexion a internet")
                            }
                        }
                    } else {
                        if (cm.isNetworkAvailable.value == true) {
                            udvm.upData1(sldvm.data.value.toString(), userName, about, occupation)
                        } else {
                            toast("No hay conexion a internet")
                        }
                    }
                } else {
                    flag = true
                    toast("Hay campos vacios que son requeridos")
                }
            }else {
                toast("Espere hasta que la operacion se complete")
            }
        }
        udvm.answer.observe(viewLifecycleOwner, Observer {
            if (it == true){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                flag = true
            }
        })
        udvm.action.observe(viewLifecycleOwner, Observer{
            when (it) {
                true -> {
                    nav(Navigation.findNavController(view), UploadDataFragmentDirections.actionUploadDataFragmentToViewPageFragment())
                }
                false ->{
                    toast(udvm.message.value.toString())
                }
            }
        })
    }
    private fun dataPermission() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
     )
    private fun requestPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Se requieren permisos de alamacenamiento para poder acceder a tu multi media",
            GALLERY_REQ_CODE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        requestPermission()
    }
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>){
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_REQ_CODE){
                val uri : Uri = data!!.data!!
                filePath = uriPathHelper.getPath(requireContext(),uri)
                toast(filePath.toString())
                binding.imgUpload.setImageURI(uri)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}