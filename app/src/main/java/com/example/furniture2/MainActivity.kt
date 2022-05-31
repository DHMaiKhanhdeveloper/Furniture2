package com.example.furniture2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.filament.Box
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.CompletableFuture

private const val BOTTOM_SHEET_PEEK_HEIGHT =50f
private const val DOUBLE_TAP_TOLERANCE_MS =1000L

class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment
    private lateinit var selectedModel: Model
    private fun getCurentScene() = arFragment.arSceneView.scene

    private val models = mutableListOf(
        Model(R.drawable.toilet,"Toilet",R.raw.toilet),
        Model(R.drawable.closet,"Closet",R.raw.funiture1),
        Model(R.drawable.clim,"Clim",R.raw.clim),
        Model(R.drawable.imac,"Imac",R.raw.imac)
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = fragment as ArFragment
        setupBottomSheet()
        setupRecyclerView()
        setupDoubleTapArPlaneListener()
    }
    private fun setupDoubleTapArPlaneListener(){


        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->

//            var firstTagTime = 0L;
//            if(firstTagTime==0L){
//                firstTagTime= System.currentTimeMillis()
//            }else if(System.currentTimeMillis()-firstTagTime < DOUBLE_TAP_TOLERANCE_MS){
//                firstTagTime = 0L
//                loadModel { modelRenderable, viewRenderable ->
//                    addNodeToScene(hitResult.createAnchor(),modelRenderable,viewRenderable)
//                }
//            }else{
//                firstTagTime= System.currentTimeMillis()
//            }
            loadModel { modelRenderable, viewRenderable ->
                   addNodeToScene(hitResult.createAnchor(),modelRenderable,viewRenderable)             }
        }
    }
    private fun setupRecyclerView(){
        rvModels.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        rvModels.adapter = ModelAdapter(models).apply {
            selectedModel.observe(this@MainActivity , Observer {
                this@MainActivity.selectedModel = it
                var newTitle = "Furniture (${it.title})"
                tvModel.text = newTitle
            })
        }
    }
    private fun setupBottomSheet(){
        val bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehaviour.peekHeight =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                BOTTOM_SHEET_PEEK_HEIGHT,
                resources.displayMetrics
            ).toInt()
        bottomSheetBehaviour.addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
               bottomSheet.bringToFront()
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

        })
    }



    private fun createDeleteButton(): Button {
        return Button(this).apply {
            text = "Delete"
            setBackgroundColor(android.graphics.Color.RED)
            setTextColor(android.graphics.Color.WHITE)
        }
    }

    private fun addNodeToScene(
        anchor: Anchor,
        modelRenderable: ModelRenderable,
        viewRenderable: ViewRenderable
    ){
        val anchorNode = AnchorNode(anchor)
        val modelNode = TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            setParent(anchorNode)
            getCurentScene().addChild(anchorNode)
            select()
        }

        val viewNode = Node().apply {
            renderable = null
            setParent(modelNode)
           // val box = modelNode.renderable?.collisionShape as Box

           localPosition = Vector3(0f,modelNode.localPosition.y,0f)
            (viewRenderable.view as Button).setOnClickListener {
                arFragment.arSceneView.scene.removeChild(anchorNode)
                getCurentScene().removeChild(anchorNode)
            }




        }
        modelNode.setOnTapListener{ _, _ ->
            if(!modelNode.isTransforming) {
                if(viewNode.renderable == null) {
                    viewNode.renderable = viewRenderable
                } else {
                    viewNode.renderable = null
                }
            }
        }
    }

    private fun loadModel(callback: (ModelRenderable,ViewRenderable) -> Unit){
        val modelRenderable = ModelRenderable.builder()
            .setSource(this,selectedModel.modelResourceId)
            .build()
        val viewRenderable = ViewRenderable.builder()
            .setView(this,createDeleteButton())
            .build()
        CompletableFuture.allOf(modelRenderable,viewRenderable)
            .thenAccept(){
                callback(modelRenderable.get(),viewRenderable.get())
            }
            .exceptionally {
                Toast.makeText(this,"Error loading... $it",Toast.LENGTH_LONG).show()
                null

            }
    }
}
