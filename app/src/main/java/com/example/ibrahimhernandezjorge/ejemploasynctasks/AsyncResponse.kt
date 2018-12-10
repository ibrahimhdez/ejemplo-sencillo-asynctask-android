package com.example.ibrahimhernandezjorge.ejemploasynctasks

import android.graphics.Bitmap

interface AsyncResponse {
    fun processFinish(imagen: Bitmap)
    fun processFailed()
}