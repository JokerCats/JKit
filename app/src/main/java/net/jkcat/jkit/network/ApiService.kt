package net.jkcat.jkit.network

import io.reactivex.Observable
import net.jkcat.jkit.bean.ActivityBean
import net.jkcat.jkit.java.activity.ActionDetailResult
import net.jkcat.jkit.java.list.ActionListResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @author JokerCats on 2021.05.05
 */
interface ApiService {

    @GET("advertise/getOnAdvertising")
    fun getActivityInfo(): Observable<ActivityBean>

    @GET("t_car/api/activity/info")
    fun getActionDetail(
        @Header("token") token: String,
        @Query("id") actionId: String
    ): Observable<ActionDetailResult>

    @GET("t_car/api/activity/list")
    fun getActionList(
        @Query("current") page: String,
        @Query("limit") size: String,
        @Query("value") key: String
    ): Observable<ActionListResult>

}