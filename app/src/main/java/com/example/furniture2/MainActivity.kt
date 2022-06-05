package com.example.furniture2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment
    private lateinit var selectedCategory: Category
    private fun getCurrentScene() = arFragment.arSceneView.scene
    var viewNodes = mutableListOf<Node>()
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(models) { model ->

            detailsAdapter.submitDetails(model.details)
            state = 1

            /// gan adapter cho recycler view hien tai
            rvModels.adapter = detailsAdapter
        }.also { adapter ->
            adapter.selectedModel.observe(this@MainActivity, Observer {
                this@MainActivity.selectedCategory = it
                var newTitle = "Furniture (${it.title})"
                tvModel.text = newTitle
            })
        }
    }
    private val detailsAdapter: DetailsAdapter = DetailsAdapter(listOf()) { detail ->
        resId = detail.detailsResourceId
    }

    @RawRes
    private var resId: Int = 0

    private val models = mutableListOf(
        Category(
            R.drawable.toilet, "Toilet",
            listOf(
                Detail(R.drawable.toilet, "Toilet1", R.raw.toilet),
                Detail(R.drawable.toilet, "Toilet2", R.raw.funiture1),
                Detail(R.drawable.toilet, "Toilet3", R.raw.clim)
            )
        ),
        Category(
            R.drawable.closet, "Closet",
            listOf(
                Detail(R.drawable.closet, "Closet1", R.raw.funiture1),
                Detail(R.drawable.closet, "Closet2", R.raw.funiture1),
                Detail(R.drawable.closet, "Closet3", R.raw.funiture1)
            )
        ),


        Category(R.drawable.clim, "Clim", listOf()),
        Category(R.drawable.imac, "IMac", listOf())
    )
//    private var toiletDetails = listOf(
//        Detail(R.drawable.toilet, "Toilet1", R.raw.toilet),
//        Detail(R.drawable.toilet, "Toilet2", R.raw.funiture1),
//        Detail(R.drawable.toilet, "Toilet3", R.raw.clim)
//    )
//    private val closetDetails = listOf(
//        Detail(R.drawable.closet, "Closet1", R.raw.funiture1),
//        Detail(R.drawable.closet, "Closet2", R.raw.funiture1),
//        Detail(R.drawable.closet, "Closet3", R.raw.funiture1)
//    )

    private var state: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = fragment as ArFragment
        setupBottomSheet()
        setupRecyclerView()
        setupDoubleTapArPlaneListener()

//        getCurentScene().addOnUpdateListener {
//            rotateviewNodetowardsUser()
//        }
    }

    private fun setupDoubleTapArPlaneListener() {
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->

//            var firstTagTime = 0L;
//            if(firstTagTime==0L){
//                firstTagTime = System.currentTimeMillis()
//            }else if(System.currentTimeMillis()-firstTagTime < DOUBLE_TAP_TOLERANCE_MS){
//                firstTagTime = 0L
//                loadModel { modelRenderable, viewRenderable ->
//                    addNodeToScene(hitResult.createAnchor(),modelRenderable,viewRenderable)
//                }
//            }else{
//                firstTagTime= System.currentTimeMillis()
//            }
            if (resId != 0) {
                loadModel(resId) { modelRenderable, viewRenderable ->
                    addNodeToScene(hitResult.createAnchor(), modelRenderable, viewRenderable)
                }
            } else {
                Toast.makeText(this, "Need choose item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        rvModels.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvModels.adapter = categoryAdapter

    }

    override fun onBackPressed() {
        if (state == 0) {
            super.onBackPressed()
        } else {
            rvModels.adapter = categoryAdapter
        }
    }

    private fun setupBottomSheet() {
        val bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehaviour.peekHeight =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                BOTTOM_SHEET_PEEK_HEIGHT,
                resources.displayMetrics
            ).toInt()
        bottomSheetBehaviour.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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
    ) {
        val anchorNode = AnchorNode(anchor)
        val modelNode = TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            setParent(anchorNode)
            getCurrentScene().addChild(anchorNode)
            select()
        }

        val viewNode = Node().apply {
            renderable = null
            setParent(modelNode)
            // val box = modelNode.renderable?.collisionShape as Box

            localPosition = Vector3(0f, modelNode.localPosition.y, 0f)
            (viewRenderable.view as Button).setOnClickListener {
                arFragment.arSceneView.scene.removeChild(anchorNode)
                getCurrentScene().removeChild(anchorNode)
                viewNodes.remove(this)
            }
        }
        viewNodes.add(viewNode)

        modelNode.setOnTapListener { _, _ ->
            if (!modelNode.isTransforming) {
                if (viewNode.renderable == null) {
                    viewNode.renderable = viewRenderable
                } else {
                    viewNode.renderable = null
                }
            }
        }
    }

    private fun loadModel(
        @RawRes modelResourceId: Int,
        callback: (ModelRenderable, ViewRenderable) -> Unit
    ) {
        val modelRenderable = ModelRenderable.builder()
            .setSource(this, modelResourceId)
            .build()
        val viewRenderable = ViewRenderable.builder()
            .setView(this, createDeleteButton())
            .build()
        CompletableFuture.allOf(modelRenderable, viewRenderable)
            .thenAccept {
                callback(modelRenderable.get(), viewRenderable.get())
            }
            .exceptionally {
                Toast.makeText(this, "Error loading... $it", Toast.LENGTH_LONG).show()
                null

            }
    }

    private companion object {
        const val BOTTOM_SHEET_PEEK_HEIGHT = 50f
        const val DOUBLE_TAP_TOLERANCE_MS = 1000L
    }
}
