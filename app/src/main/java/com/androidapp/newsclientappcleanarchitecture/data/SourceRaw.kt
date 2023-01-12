package com.androidapp.newsclientappcleanarchitecture.data

import com.google.gson.annotations.SerializedName

data class SourceRaw (
    @SerializedName("id")
    val id : String?,
    @SerializedName("name")
    val name : String?
    )