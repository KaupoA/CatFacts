package com.example.catfacts.catfact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.catfacts.R
import com.example.catfacts.di
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class CatFactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catFactViewModel = di.catFactViewModel
        catFactViewModel.observableState.observe(this, Observer { state ->
            renderState(state)
        })
        getFactButton.setOnClickListener {
            catFactViewModel.dispatch(CatFactAction.GetFactButtonClicked)
        }
    }

    private fun renderState(state: CatFactState) {
        with(state) {
            if (catFact.isNotEmpty()) {
                catFactView.text = catFact
            }
            loadingIndicator.isVisible = loading
            getFactButton.isEnabled = !loading
            errorView.isVisible = displayError
        }
    }
}
