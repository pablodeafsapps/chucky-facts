/*
 * Copyright (c) 2018 Razeware LLC
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

package com.raywenderlich.chuckyfacts.di

import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.interactor.MainInteractor
import com.raywenderlich.chuckyfacts.presenter.MainPresenter
import com.raywenderlich.chuckyfacts.view.activities.MainActivity

import dagger.Binds
import dagger.Module

@Module
abstract class MainAbstractModule {
    // "@Binds" substitute "@Provides"
    // Inject interfaces (instead of the class) using the class object which implements it
    @Binds
    abstract fun bindMainView(mainActivity: MainActivity): MainContract.View

    @Binds
    abstract fun bindMainPresenter(mainPresenter: MainPresenter): MainContract.Presenter

    @Binds
    abstract fun bindMainInteractor(mainInteractor: MainInteractor): MainContract.Interactor
}
