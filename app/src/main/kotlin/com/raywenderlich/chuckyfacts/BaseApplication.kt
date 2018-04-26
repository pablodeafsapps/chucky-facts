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

package com.raywenderlich.chuckyfacts

import android.app.Activity
import android.app.Application

import com.raywenderlich.chuckyfacts.di.DaggerAppComponent

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {

    companion object {
        private val TAG = "BaseApplication"
        lateinit var INSTANCE: BaseApplication
    }

    @Inject
    lateinit var activityInjector : DispatchingAndroidInjector<Activity>
    // Routing layer (VIPER)
    lateinit var cicerone: Cicerone<Router>

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
        DaggerAppComponent.builder().application(this).build().inject(this)
        this.initCicerone()
    }

    private fun BaseApplication.initCicerone() {
        this.cicerone = Cicerone.create()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
