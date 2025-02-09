package com.example.furniture2

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene.getCurrentScene
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
    private lateinit var selectedDetail: Detail
    private fun getCurrentScene() = arFragment.arSceneView.scene
    //private val currentScene get() = arFragment.arSceneView.scene
    var viewNodes = mutableListOf<Node>()

    @RawRes
    private var resId: Int = 0
    //private var selectedNode: Node? = null


    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(categories) { model ->
            // detailsAdapter.submitDetails(model.details)
            rvModels.adapter = furnitureAdapter
            furnitureAdapter.submitFurnitures(model.furnitures)
        }.also { adapter ->
            adapter.selectedCategory.observe(this@MainActivity, Observer {
                var newTitle = "${it.title}"
                tvModel.text = newTitle
            })
        }
    }

    private val furnitureAdapter: FurnitureAdapter by lazy {
        FurnitureAdapter(listOf()) { furniture ->
            detailsAdapter.submitDetails(furniture.details)
            rvModels.adapter = detailsAdapter
        }.also { adapter ->
            adapter.selectedFurniture.observe(this@MainActivity, Observer {
                var newTitleFurniture = "${it.titleFurniture}"
                tvModel.text = newTitleFurniture
            })
        }
    }


    private val detailsAdapter: DetailsAdapter by lazy {
        DetailsAdapter(listOf()) { detail ->
            resId = detail.detailsResourceId
        }.also { adapter ->
            adapter.selectedDetail.observe(this@MainActivity, Observer {
                this@MainActivity.selectedDetail = it
                var newTitleDetail = "${it.titleDetails}"
                tvModel.text = newTitleDetail
            })

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = fragment as ArFragment
        setupBottomSheet()
        setupRecyclerView()
        setupDoubleTapArPlaneListener()
        //deleteFloatingActionButton()


//        getCurentScene().addOnUpdateListener {
//            rotateviewNodetowardsUser()
//        }
    }

//    private fun deleteFloatingActionButton() {
//        floataction.setOnClickListener { view ->
//            selectedNode?.let { node ->
//                arFragment.arSceneView.scene.removeChild(node)
//                currentScene.removeChild(node)
//                viewNodes.remove(node)
//            }
//
//        }
//    }

    private fun setupDoubleTapArPlaneListener() {

        var firstTagTime = 0L

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->



//            if (firstTagTime == 0L) {
//                firstTagTime = System.currentTimeMillis()
//            } else if (System.currentTimeMillis() - firstTagTime < DOUBLE_TAP_TOLERANCE_MS) {
//                firstTagTime = 0L
//
//
//                } else {
//                    Toast.makeText(this, "Need choose item", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                firstTagTime = System.currentTimeMillis()
//            }
//

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
        when (rvModels.adapter) {
            is DetailsAdapter -> {
                rvModels.adapter = furnitureAdapter
            }
            is FurnitureAdapter -> {
                rvModels.adapter = categoryAdapter
            }
            is CategoryAdapter -> {
                super.onBackPressed()
            }
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

//    private fun removeAnchorNode(nodeToremove: AnchorNode) {
//
//        var nodeToremove: AnchorNode? = nodeToremove
//        if (nodeToremove != null) {
//            arFragment.arSceneView.scene.removeChild(nodeToremove)
//            currentScene.removeChild(nodeToremove)
//            //anchorNodeList.remove(nodeToremove)
//            nodeToremove.anchor!!.detach()
//            nodeToremove.setParent(null)
//            nodeToremove = null
//        } else {
//
//        }
//    }

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

            localPosition = Vector3(modelNode.localPosition.x, modelNode.localPosition.y, 0f)
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
