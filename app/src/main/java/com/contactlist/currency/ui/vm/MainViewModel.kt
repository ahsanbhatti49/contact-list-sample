package com.contactlist.currency.ui.vm

import android.content.res.AssetManager
import android.util.Log
import android.util.Xml
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.contactlist.currency.repo.ContactListRepo
import com.contactlist.currency.utils.XmlParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ContactListRepo
) : ViewModel() {
    @BindingAdapter("onClick")
    fun onClick(view: View, onClick: () -> Unit) {
        view.setOnClickListener {
            onClick()
        }
    }

    fun startParsing(assets: AssetManager) {
        val assetManager: AssetManager = assets
        val xmlStream: InputStream = assetManager.open("ab.xml")
        try {
            val list = XmlParser.parse(xmlStream)
            Log.d("done", list.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}