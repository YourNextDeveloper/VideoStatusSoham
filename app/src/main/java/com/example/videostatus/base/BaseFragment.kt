
package com.example.videostatus.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.videostatus.utils.PreferenceHelper.customPrefs
import com.example.videostatus.factory.ViewModelProviderFactory
import kotlin.reflect.KClass

abstract class BaseFragment<V : BaseViewModel, B : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: V
    protected abstract val modelClass: KClass<V>
    protected abstract val layoutId: Int
    protected lateinit var binding: B
    protected lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelProviderFactory(requireActivity().application)).get(modelClass.java)
        prefs = requireContext().customPrefs()
        initControls()
    }


    protected abstract fun initControls()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun startActivityForResult(intent: Intent, callback: (data: Intent?) -> Unit) {
        startActivityForResult(intent) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                callback(data)
            }
        }
    }

    private lateinit var resultCallback: (resultCode: Int, data: Intent?) -> Unit
    private fun startActivityForResult(intent: Intent, callback: (resultCode: Int, data: Intent?) -> Unit) {
        resultCallback = callback
        startActivityForResult(intent, 65533)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (::resultCallback.isInitialized && requestCode == 65533)
            resultCallback(resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
