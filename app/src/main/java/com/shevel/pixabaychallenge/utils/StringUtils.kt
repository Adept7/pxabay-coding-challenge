package com.shevel.pixabaychallenge.utils

import android.content.res.Resources
import com.shevel.pixabaychallenge.R

fun tagsFormatted(tags: String): String {
    return StringBuilder().apply {
        tags.split(", ").forEachIndexed { i, tag ->
            if (tags.lastIndex == i){
                append("#$tag")
            } else {
                append("#$tag ")
            }
        }
    }.toString()
}

fun usernameFormatted(resources: Resources, username: String) = resources.getString(R.string.username_mask, username)