package com.example.furniture2

import android.app.Activity
import android.graphics.Bitmap
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import android.widget.Toast
import com.google.ar.sceneform.ArSceneView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotoSaver(
    private val activity: Activity
) {
    private fun generateFilename(): String?{
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)?.absolutePath +
                "/TryOutFurniture/${date}_screenshot.jpg"
    }

    private fun saveBitmaptoGallery(bmp: Bitmap,filename: String){
        val out = File(filename)
        if(!out.parentFile.exists()){
            out.parentFile.mkdir()
        }
        try {
            val outputStream = FileOutputStream(filename)
            val outputData = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG,100,outputData)
            outputData.writeTo(outputStream)
            outputData.flush()
            outputData.close()
        }catch (e: IOException){
            Toast.makeText(activity,"Failed to save bitmap to library",Toast.LENGTH_LONG).show()
        }
    }
//    fun takePhoto(arSceneView: ArSceneView){
//        val bitmap = Bitmap.createBitmap(arSceneView.width,arSceneView.height,Bitmap.Config.ARGB_8888)
//        val handlerThread = HandlerThread("PixelCopyThread")
//        handlerThread.start()
//
//        PixelCopy.request(arSceneView,bitmap,{
//
//    }, Handler(handlerThread.looper))
}