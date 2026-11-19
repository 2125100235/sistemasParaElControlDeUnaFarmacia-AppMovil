package com.logimedtech.farmacia.network

import com.logimedtech.farmacia.model.EmpleadosResponse
import com.logimedtech.farmacia.model.LoginResponse
import com.logimedtech.farmacia.model.OperacionResponse
import com.logimedtech.farmacia.model.Producto
import com.logimedtech.farmacia.model.ProductosResponse
import com.logimedtech.farmacia.model.Totales
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Enviamos correo y clave por POST (Formulario) y esperamos un LoginResponse
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("correo") correo: String,
        @Field("clave") clave: String
    ): LoginResponse

    // Consultamos la lista de productos por GET y esperamos una List<Producto>
    @GET("obtener_productos.php")
    suspend fun obtenerProductos(
        @Query("accion") accion: String = "leer",
        @Query("buscar") buscar: String? = null
    ): ProductosResponse
    @FormUrlEncoded
    @POST("obtener_productos.php?accion=crear")
    suspend fun crearProducto(
        @Field("nombre") nombre: String,
        @Field("tipo_medicamento") tipoMedicamento: String,
        @Field("laboratorio") laboratorio: String,
        @Field("lote") lote: String,
        @Field("fecha_caducidad") fechaCaducidad: String,
        @Field("existencia") existencia: Int,
        @Field("precio_venta") precioVenta: Double,
        @Field("id_proveedor") idProveedor: Int? = null
    ): OperacionResponse
    @FormUrlEncoded
    @POST("obtener_productos.php?accion=actualizar")
    suspend fun actualizarProducto(
        @Field("codigo") codigo: Int,
        @Field("nombre") nombre: String,
        @Field("tipo_medicamento") tipoMedicamento: String,
        @Field("laboratorio") laboratorio: String,
        @Field("lote") lote: String,
        @Field("fecha_caducidad") fechaCaducidad: String,
        @Field("existencia") existencia: Int,
        @Field("precio_venta") precioVenta: Double,
        @Field("id_proveedor") idProveedor: Int? = null
    ): OperacionResponse
    @FormUrlEncoded
    @POST("obtener_productos.php?accion=eliminar")
    suspend fun eliminarProducto(
        @Field("codigo") codigo: Int
        ): OperacionResponse
    @FormUrlEncoded
    @POST("obtener_productos.php?accion=buscar")
    suspend fun buscarProducto(
        @Field("buscar") buscar: String
    ): OperacionResponse

    // --- PERSONAL (CRUD usando empleados.php) ---
    @GET("empleados.php")
    suspend fun obtenerEmpleados(
        @Query("accion") accion: String = "leer",
        @Query("buscar") buscar: String? = null
    ): EmpleadosResponse

    @FormUrlEncoded
    @POST("empleados.php?accion=crear")
    suspend fun crearEmpleado(
        @Field("nombre") nombre: String,
        @Field("apellido_paterno") apellidoPaterno: String,
        @Field("apellido_materno") apellidoMaterno: String,
        @Field("clave") clave: String,
        @Field("correo") correo: String,
        @Field("telefono") telefono: String,
        @Field("rol") rol: String
    ): OperacionResponse

    @FormUrlEncoded
    @POST("empleados.php?accion=actualizar")
    suspend fun actualizarEmpleado(
        @Field("id_empleado") idEmpleado: Int,
        @Field("nombre") nombre: String,
        @Field("apellido_paterno") apellidoPaterno: String,
        @Field("apellido_materno") apellidoMaterno: String,
        @Field("clave") clave: String?, // Opcional, por si no cambia la contraseña
        @Field("correo") correo: String,
        @Field("telefono") telefono: String,
        @Field("rol") rol: String
    ): OperacionResponse

    @FormUrlEncoded
    @POST("empleados.php?accion=eliminar")
    suspend fun eliminarEmpleado(
        @Field("id_empleado") idEmpleado: Int
    ): OperacionResponse

    @GET("obtener_totales.php")
    suspend fun getTotales(): Totales
}
