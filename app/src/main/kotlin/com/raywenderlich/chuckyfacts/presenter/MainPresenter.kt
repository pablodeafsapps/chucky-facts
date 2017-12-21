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

package com.raywenderlich.chuckyfacts.presenter

import com.raywenderlich.chuckyfacts.BaseApplication
import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.di.DaggerAppComponent
import com.raywenderlich.chuckyfacts.entity.Joke
import com.raywenderlich.chuckyfacts.view.activities.DetailActivity

import ru.terrakok.cicerone.Router

import javax.inject.Inject

class MainPresenter @Inject constructor(private var view: MainContract.View?) : MainContract.Presenter, MainContract.InteractorOutput {

    @Inject
    lateinit var interactor: MainContract.Interactor
//    @Inject
//    lateinit var dummyItem: DummyItem
    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }

    init {
        DaggerAppComponent.builder().application(BaseApplication.INSTANCE).build().inject(this)
    }

    override fun listItemClicked(joke: Joke?) {
        router?.navigateTo(DetailActivity.TAG, joke)
    }

    override fun onViewCreated() {
        view?.showLoading()
        interactor.setOutputEntity(this)
        interactor.loadJokesList()
    }

    override fun onQuerySuccess(data: List<Joke>) {
        view?.hideLoading()
        view?.publishDataList(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data")
    }
}
