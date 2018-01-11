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

package com.raywenderlich.chuckyfacts.interactor

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.raywenderlich.chuckyfacts.MainContract
import com.raywenderlich.chuckyfacts.entity.Joke

import javax.inject.Inject

class MainInteractor @Inject constructor() : MainContract.Interactor {

    companion object {
        val icndbUrl = "https://api.icndb.com/jokes"
    }

    lateinit var output: MainContract.InteractorOutput

    override fun loadJokesList() {
        icndbUrl.httpPost().responseJson { _, _, result ->
            when (result) {
                is Result.Failure -> {
                    output.onQueryError()
                }
                is Result.Success -> {
                    val jokesJsonObject = result.get().obj()

                    val type = object : TypeToken<List<Joke>>() {}.type
                    val jokesList: List<Joke> =
                            Gson().fromJson(jokesJsonObject.getJSONArray("value").toString(), type)

                    output.onQuerySuccess(jokesList)
                }
            }
        }
    }

    override fun setOutputEntity(interactorOutput: MainContract.InteractorOutput) {
        output = interactorOutput
    }
}
