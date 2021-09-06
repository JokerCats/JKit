package net.jkcat.jkit.network

import net.jkcat.jkit.BuildConfig
import net.jkcat.network.base.NetworkRequiredInfo

/**
 * @author JokerCats on 2021.05.05
 */
class JNetConfig : NetworkRequiredInfo {

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun getVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }
}