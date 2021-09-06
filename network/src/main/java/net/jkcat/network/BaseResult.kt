package net.jkcat.network

import com.google.gson.annotations.SerializedName

/**
 * @author JokerCats on 2021.05.07
 */
open class BaseResult {

    @SerializedName("code")
    val code: Int = -1

    @SerializedName("msg")
    val msg: String = ""
}
