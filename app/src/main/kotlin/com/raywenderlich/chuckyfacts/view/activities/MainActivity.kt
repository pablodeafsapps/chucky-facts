/*
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.chuckyfacts.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ProgressBar

import com.raywenderlich.chuckyfacts.BaseApplication
import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.R
import com.raywenderlich.chuckyfacts.entity.Joke
import com.raywenderlich.chuckyfacts.view.adapters.JokesListAdapter

import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*

import org.jetbrains.anko.toast

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    companion object {
        val TAG = "MainActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                val data = (command.transitionData as Joke)

                when (command.screenKey) {
                    DetailActivity.TAG -> startActivity(Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra("data", data as Parcelable))
                    else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
                }
            }
        }
    }
    //    @Inject
//    lateinit var dummyItem: DummyItem
    @Inject
    lateinit var presenter: MainContract.Presenter
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view }
    private val recyclerView: RecyclerView by lazy { rv_jokes_list_activity_main }
    private val progressBar: ProgressBar by lazy { prog_bar_loading_jokes_activity_main }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = JokesListAdapter({ joke -> presenter.listItemClicked(joke) }, null)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewCreated()
        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun showLoading() {
        recyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    override fun showInfoMessage(msg: String) {
        toast(msg)
    }

    override fun publishDataList(data: List<Joke>) {
        (recyclerView.adapter as JokesListAdapter).updateData(data)
    }
}
