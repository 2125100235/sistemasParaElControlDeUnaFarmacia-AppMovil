package com.logimedtech.farmacia.network

import com.logimedtech.farmacia.model.LoginResponse
import com.logimedtech.farmacia.model.Producto
import com.logimedtech.farmacia.model.Totales
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Enviamos correo y clave por POST (Formulario) y esperamos un LoginResponse
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("correo") correo: String,
        @Field("clave") clave: String
    ): LoginResponse

    // Consultamos la lista de productos por GET y esperamos una List<Producto>
    @GET("api/obtener_productos.php")
    suspend fun getProductos(): List<Producto>

    @GET("api/obtener_totales.php")
    suspend fun getTotales(): Totales

}
