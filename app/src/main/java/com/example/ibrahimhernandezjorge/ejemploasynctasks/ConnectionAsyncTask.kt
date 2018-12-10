package com.example.ibrahimhernandezjorge.ejemploasynctasks

import android.os.AsyncTask
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.*
import java.lang.Thread.sleep
import java.net.HttpURLConnection
import java.net.URL

/**
 * Clase para realizar las peticiones Web en background para evitar la congelación del hilo principal de nuestra aplicación
 * @author Ibrahim Hernández Jorge
 */
internal class ConnectionAsyncTask(private var delegate: AsyncResponse?): AsyncTask<String, Void, Bitmap?>() {
    /**
     * Método que hace la llamada y descarga en background la respuesta de la API
     * @param params Consulta a ejecutar
     * @return Número de respuesta HTTP
     */
    override fun doInBackground(vararg params: String): Bitmap? {
        sleep(2500)
        return descargarImagen(params[0])
    }

    /**
     * Función que llama al método processFinish de la clase que llamó a esta tarea asíncrona
     * @param imagen
     */
    override fun onPostExecute(imagen: Bitmap?) {
        if(imagen != null)
            delegate!!.processFinish(imagen)
        else
            delegate!!.processFailed()
    }

    private fun descargarImagen(imageHttpAddress: String): Bitmap? {
        var imagen: Bitmap? = null

        try {
            val imageUrl = URL(imageHttpAddress)
            val connection = imageUrl.openConnection() as HttpURLConnection

            connection.connect()
            imagen = BitmapFactory.decodeStream(connection.inputStream)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return imagen
    }
}


