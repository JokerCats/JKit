package net.jkcat.jkit.base

import net.jkcat.core.base.BaseApplication
import net.jkcat.jkit.network.JNetConfig
import net.jkcat.network.base.NetworkApi

/**
 * @author JokerCats on 2021.05.05
 */
class JKApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        NetworkApi.init(JNetConfig())
    }

}