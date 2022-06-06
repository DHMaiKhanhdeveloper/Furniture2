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
import kotlinx.android.synthetic.main.item_details.*
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment
    private lateinit var selectedCategory: Category
    private lateinit var selectedDetail: Detail
    private fun getCurrentScene() = arFragment.arSceneView.scene
    var viewNodes = mutableListOf<Node>()

    private var state: Int = 0

    @RawRes
    private var resId: Int = 0

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(models) { model ->
            detailsAdapter.submitDetails(model.details)
            state = 1
            rvModels.adapter = detailsAdapter
        }.also { adapter ->
            adapter.selectedCategory.observe(this@MainActivity, Observer {
                var newTitle = "Furniture(${it.title})"
                tvModel.text = newTitle
            })
        }
    }

    private val detailsAdapter: DetailsAdapter by  lazy {
        DetailsAdapter(listOf()) { detail ->
            resId = detail.detailsResourceId
        }.also { adapter ->
            adapter.selectedDetail.observe(this@MainActivity, Observer{
                this@MainActivity.selectedDetail = it
                var newTitleDetail = "Furniture(${it.titleDetails})"
                tvModel.text = newTitleDetail
            })

        }
    }



    private val models = mutableListOf(

        Category(
            R.drawable.car1, "Car",
            listOf(
                Detail(R.drawable.car1, "Car1", R.raw.car2),
                Detail(R.drawable.car2, "Car2", R.raw.car2),
                Detail(R.drawable.car3, "Car3", R.raw.car3),
                Detail(R.drawable.car4, "Car4", R.raw.car4)
            )
        ),
        Category(
            R.drawable.chair1, "Chair",
            listOf(
                Detail(R.drawable.chair1, "Chair1", R.raw.chair1),
                Detail(R.drawable.chair2, "Chair2", R.raw.chair2),
                Detail(R.drawable.chair3, "Chair3", R.raw.chair3),
                Detail(R.drawable.chair4, "Chair4", R.raw.chair4),
                Detail(R.drawable.chair5, "Chair5", R.raw.chair5),
                Detail(R.drawable.chair6, "Chair6", R.raw.chair6)

            )
        ),
        Category(
            R.drawable.kitchen, "Kitchen",
            listOf(
                Detail(R.drawable.kitchen1, "Kitchen1", R.raw.kitchen1),
                Detail(R.drawable.kitchen2, "Kitchen2", R.raw.kitchen2),
                Detail(R.drawable.kitchen3, "Kitchen3", R.raw.kitchen3),
                Detail(R.drawable.kitchen4, "Kitchen4", R.raw.kitchen4),
                Detail(R.drawable.kitchen5, "Kitchen5", R.raw.kitchen5),
                Detail(R.drawable.kitchen6, "Kitchen6", R.raw.kitchen6),
                Detail(R.drawable.kitchen7, "Kitchen7", R.raw.kitchen7),
                Detail(R.drawable.kitchen8, "Kitchen8", R.raw.kitchen8)

            )
        ),
        Category(
            R.drawable.lamp1, "Lamp",
            listOf(
                Detail(R.drawable.lamp1, "Lamp1", R.raw.lamp1),
                Detail(R.drawable.lamp2, "Lamp2", R.raw.lamp2),
                Detail(R.drawable.lamp3, "Lamp3", R.raw.lamp3),
                Detail(R.drawable.lamp4, "Lamp4", R.raw.lamp4),
                Detail(R.drawable.lamp5, "Lamp5", R.raw.lamp5)


            )
        )



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
//        rvModels.adapter = CategoryAdapter(models).apply {
//            selectedCategory.observe(this@MainActivity , Observer {
//                this@MainActivity.selectedCategory = it
//                var newTitle = "Furniture (${it.title})"
//                tvModel.text = newTitle
//            })
//        }
//        rvModels.adapter = DetailsAdapter(models){
//            selectedDetail.observe(this@MainActivity , Observer {
//                this@MainActivity.selectedDetail = it
//                var newTitle = "Furniture (${it.titleDetails})"
//                tvModel.text = newTitle
//            })
//        }

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
