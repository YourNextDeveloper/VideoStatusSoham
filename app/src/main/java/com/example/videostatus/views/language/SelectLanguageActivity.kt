package com.example.videostatus.views.language

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videostatus.databinding.ActivitySelectLanguageBinding
import com.example.videostatus.Retrofit.CatLanDataModel
import com.example.videostatus.base.BaseActivity

import com.example.videostatus.viewmodels.SelectLanguageViewModel
import com.example.videostatus.views.home.HomeActivity
import kotlinx.android.synthetic.main.activity_select_language.*
import kotlin.reflect.KClass

class SelectLanguageActivity :
    BaseActivity<SelectLanguageViewModel, ActivitySelectLanguageBinding>() {
    override val modelClass: KClass<SelectLanguageViewModel> = SelectLanguageViewModel::class


    override val layoutId: Int = com.example.videostatus.R.layout.activity_select_language
    private lateinit var languageAdapter: LanguageAdapter
    private lateinit var manger: GridLayoutManager
    var dataList: ArrayList<CatLanDataModel.DataLanguage> = ArrayList()


    override fun initControls() {
        binding.lifecycleOwner = this
        manger = GridLayoutManager(this, 2)
        btnDone.setOnClickListener {

            goToActivity(this, HomeActivity::class)
            finishAffinity()
        }

        addObserver()
        viewModel.getCatLang(this)
    }

    private fun addObserver() {
        viewModel.langCatData.observe(this, mObserver)



    }

    val mObserver = Observer<Any> {

       dataList.addAll((it  as CatLanDataModel).dataLanguage!!)
        initLanguage()
    }


    private fun initLanguage() {
        languageAdapter = LanguageAdapter(this,dataList)
        rvLanguage.layoutManager = manger
        rvLanguage.adapter = languageAdapter

    }
}