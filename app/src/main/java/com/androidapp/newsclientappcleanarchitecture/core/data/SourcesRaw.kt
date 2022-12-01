package com.androidapp.newsclientappcleanarchitecture.core.data

import com.google.gson.annotations.SerializedName

data class SourcesRaw (
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name : String
        )