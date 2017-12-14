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

package com.raywenderlich.chuckyfacts.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.raywenderlich.chuckyfacts.R
import com.raywenderlich.chuckyfacts.entity.JokeModel

import org.jetbrains.anko.find

class JokesListAdapter(private val ctx: Context, private val resource: Int, private var dataList: List<JokeModel.Joke>) : BaseAdapter() {

    // Creating card_view_custom_layout ViewHolder to speed up the performance
    private class ViewHolder {
        var tvTitle: TextView? = null
        var tvJoke: TextView? = null
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view

        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(resource, null)

            // Configure card_view_custom_layout 'ViewHolder'
            viewHolder = ViewHolder()
            viewHolder.tvTitle = convertView.find(R.id.tv_title_card_view_custom_layout)
            viewHolder.tvJoke = convertView.find(R.id.tv_joke_card_view_custom_layout)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        // 'viewHolder' can be now populated
        viewHolder.tvTitle?.text = dataList[position].id.toString()
        viewHolder.tvJoke?.text = dataList[position].text

        return convertView
    }

    override fun getItem(position: Int) = dataList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount(): Int = dataList.size

    fun getDataList(): List<JokeModel.Joke> = dataList

    fun updateData(list: List<JokeModel.Joke>) {
        this.dataList = list
        this.notifyDataSetChanged()
    }
}