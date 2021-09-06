package net.jkcat.network.base

/**
 * @author JokerCats on 2021.05.05
 */
interface NetworkRequiredInfo {

    fun isDebug(): Boolean

    fun getVersionName(): String

    fun getVersionCode(): String

}