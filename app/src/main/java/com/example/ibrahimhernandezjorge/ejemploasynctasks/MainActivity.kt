package com.example.ibrahimhernandezjorge.ejemploasynctasks

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.content.DialogInterface
import android.app.ProgressDialog

class MainActivity : AppCompatActivity(), AsyncResponse {
    private var asyncTask: ConnectionAsyncTask? = null
    private var imageView: ImageView? = null
    private var okButton: Button? = null
    private var failedButton: Button? = null
    private var progressDialog: ProgressDialog? = null

    companion object {
        const val URL_BUENA = "http://www.brandemia.org/wp-content/uploads/2012/10/logo_principal.jpg"
        const val URL_MALA = "http://www.pagina-web-inventada.com/imagen.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarComponentes()
    }

    private fun iniciarComponentes() {
        iniciarDialog()
        imageView = findViewById(R.id.imageView)
        imageView!!.visibility = View.INVISIBLE
        imageView!!.setOnClickListener {
            imageView!!.setImageBitmap(null)
        }

        okButton = findViewById(R.id.okButton)
        failedButton = findViewById(R.id.failedButton)

        okButton!!.setOnClickListener {
            showDialog()
            instanciarAsyncTask()
            asyncTask!!.execute(URL_BUENA)
        }

        failedButton!!.setOnClickListener {
            showDialog()
            instanciarAsyncTask()
            asyncTask!!.execute(URL_MALA)
        }
    }

    private fun iniciarDialog() {
        progressDialog = ProgressDialog(this).apply {
            setMessage("Downloading Image...")
            setCancelable(false)
            setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", DialogInterface.OnClickListener { dialog, _ ->
                if (asyncTask != null) {
                    asyncTask!!.cancel(true)
                    Toast.makeText(this@MainActivity, "La descarga ha sido cancelada!", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()
            })
        }
    }

    private fun instanciarAsyncTask() {
        asyncTask = ConnectionAsyncTask(this)
    }

    override fun processFinish(imagen: Bitmap) {
        progressDialog!!.dismiss()
        imageView!!.setImageBitmap(imagen)
        imageView!!.visibility = View.VISIBLE
    }

    override fun processFailed() {
        progressDialog!!.dismiss()
        Toast.makeText(this, "Hubo un error descargando la imagen!", Toast.LENGTH_LONG).show()
    }

    private fun showDialog() {
        progressDialog!!.show()
    }
}
