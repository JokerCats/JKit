package net.jkcat.jkit.bean

import net.jkcat.network.BaseResult

data class ActivityBean(
    val data: InfoBean,
) : BaseResult()

data class InfoBean(
    val coverImg: String,
    val id: Int,
    val info: Any,
    val intro: Any,
    val isDel: Int,
    val isDisplay: Int,
    val position: Int,
    val sort: Int,
    val title: Any,
    val type: Int,
    val url: String
)