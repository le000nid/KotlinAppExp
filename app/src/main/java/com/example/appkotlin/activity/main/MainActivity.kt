package com.example.appkotlin.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appkotlin.R
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appkotlin.CoroutinesDispatcherProvider
import com.example.appkotlin.Factory.ViewModelFactory
import com.example.appkotlin.Users
import com.example.appkotlin.db.UsersDao
import com.example.appkotlin.recyclerview.UsersAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : DaggerAppCompatActivity() {

    private val mainActivityViewModel : MainActivityViewModel by viewModels{ viewModelFactory }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var usersDao: UsersDao
    @Inject
    lateinit var coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    lateinit var adapter : UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewState()
        observeUsersInDatabase()
    }

    private fun observeViewState() {
        mainActivityViewModel.state.observe(this,  Observer { state ->
            when(state){
                is MainActivityViewState.ShowLoading -> {
                    initialUiState()
                    showLoading()
                }
                is MainActivityViewState.ShowError -> {
                    showError(state.error)
                }
            }
        })
    }

    private fun observeUsersInDatabase() {
        CoroutineScope(coroutinesDispatcherProvider.main).launch {
            usersDao.getAllUsersDistinctUntilChanged().collect {
                    users -> showData(users)
            }
        }
    }

    private fun initialUiState(){
        progress_circular.visibility = View.GONE
        recyclerview.visibility = View.GONE
        adapter = UsersAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
    }

    private fun showLoading(){
        progress_circular.visibility = View.VISIBLE
    }

    private fun showData(data: List<Users>) {
        removeProgressDialog()
        recyclerview.visibility = View.VISIBLE
        adapter.submitList(data)
    }

    private fun showError(error: Throwable) {
        removeProgressDialog()
        showToast(error.message, Toast.LENGTH_LONG)
    }

    private fun removeProgressDialog() {
        progress_circular.visibility = View.GONE
    }
}