package com.example.agroceylon.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.agroceylon.R
import com.example.agroceylon.databinding.ActivityBuyerFetchingBinding
import com.example.agroceylon.models.BuyerPostModel
import com.google.firebase.database.FirebaseDatabase

class BuyerDetailsActivity : AppCompatActivity() {

    private lateinit var tvBuyerPostViewTitle: TextView
//    for price
    private lateinit var tvBuyerPostViewAmount: TextView
    private lateinit var chipBuyerPostsViewType: TextView
    private lateinit var tvBuyerPostViewReqDate: TextView
//    for quantity
    private lateinit var tvBuyerPostViewImageCaption: TextView
    private lateinit var tvBuyerPostViewPhone: TextView
    private lateinit var tvBuyerPostViewName: TextView

    private lateinit var btnUpdateBuyerPost: Button
    private lateinit var btnDeleteBuyerPost: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_details)

        initView()
        setValuesToViews()

        btnUpdateBuyerPost.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("postId").toString(),
                intent.getStringExtra("postTitle").toString()
            )
        }

        btnDeleteBuyerPost.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("postId").toString()
            )
        }

    }

    private fun initView() {
        tvBuyerPostViewTitle = findViewById(R.id.tvBuyerPostViewTitle)
        tvBuyerPostViewAmount = findViewById(R.id.tvBuyerPostViewAmount)
        chipBuyerPostsViewType = findViewById(R.id.chipBuyerPostsViewType)
        tvBuyerPostViewReqDate = findViewById(R.id.tvBuyerPostViewReqDate)
        tvBuyerPostViewImageCaption = findViewById(R.id.tvBuyerPostViewImageCaption)
        tvBuyerPostViewPhone = findViewById(R.id.tvBuyerPostViewPhone)
        tvBuyerPostViewName = findViewById(R.id.tvBuyerPostViewName)
//        tvBuyerPostViewLocation = findViewById(R.id.tvBuyerPostViewLocation)

        btnUpdateBuyerPost = findViewById(R.id.btnUpdateBuyerPost)
        btnDeleteBuyerPost = findViewById(R.id.btnDeleteBuyerPost)
    }

    private fun setValuesToViews() {
        tvBuyerPostViewTitle.text = intent.getStringExtra("postTitle")
        tvBuyerPostViewAmount.text = intent.getStringExtra("postQty")
        chipBuyerPostsViewType.text = intent.getStringExtra("postType")
        tvBuyerPostViewReqDate.text = intent.getStringExtra("postDate")
        tvBuyerPostViewReqDate.text = intent.getStringExtra("postDate")
        tvBuyerPostViewImageCaption.text = intent.getStringExtra("postPrice")
        tvBuyerPostViewName.text = intent.getStringExtra("postName")
//        tvBuyerPostViewLocation.text = intent.getStringExtra("postLocation")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("BuyerPosts").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Buyer Post data deleted", Toast.LENGTH_LONG).show()

            // add fetching activity
            val intent = Intent(this, BuyerFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        postId: String,
        postTitle: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.buyer_update_dialog, null)

        mDialog.setView(mDialogView)

        val tvBuyerPostUpdateTitle = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateTitle)
        val tvBuyerPostUpdateQty = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateQty)
        val tvBuyerPostUpdatePrice = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdatePrice)
//        val tvBuyerPostUpdateLocation = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateLocation)
        val tvBuyerPostUpdateReqDate = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateReqDate)
        val tvBuyerPostUpdateName = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateName)
        val tvBuyerPostUpdateType = mDialogView.findViewById<EditText>(R.id.tvBuyerPostUpdateType)

        val btnBuyerPostUpdateSave = mDialogView.findViewById<Button>(R.id.btnBuyerPostUpdateSave)

        tvBuyerPostUpdateTitle.setText(intent.getStringExtra("postTitle").toString())
        tvBuyerPostUpdateQty.setText(intent.getStringExtra("postQty").toString())
        tvBuyerPostUpdatePrice.setText(intent.getStringExtra("postPrice").toString())
//        tvBuyerPostUpdateLocation.setText(intent.getStringExtra("postLocation").toString())
        tvBuyerPostUpdateReqDate.setText(intent.getStringExtra("postDate").toString())
        tvBuyerPostUpdateName.setText(intent.getStringExtra("postName").toString())
        tvBuyerPostUpdateType.setText(intent.getStringExtra("postType").toString())


        mDialog.setTitle("Updating $postTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnBuyerPostUpdateSave.setOnClickListener {
            updateEmpData(
                postId,
                tvBuyerPostUpdateTitle.text.toString(),
                tvBuyerPostUpdateReqDate.text.toString(),
                tvBuyerPostUpdatePrice.text.toString(),
//                tvBuyerPostUpdateLocation.text.toString(),
                tvBuyerPostUpdateType.text.toString(),
                tvBuyerPostUpdateQty.text.toString(),
                tvBuyerPostUpdateName.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvBuyerPostViewTitle.text = tvBuyerPostUpdateTitle.text.toString()
            tvBuyerPostViewAmount.text = tvBuyerPostUpdateQty.text.toString()
            tvBuyerPostViewImageCaption.text = tvBuyerPostUpdatePrice.text.toString()
//            tvBuyerPostViewLocation.text = tvBuyerPostUpdateLocation.text.toString()
            tvBuyerPostViewReqDate.text = tvBuyerPostUpdateReqDate.text.toString()
            tvBuyerPostViewName.text = tvBuyerPostUpdateName.text.toString()
            chipBuyerPostsViewType.text = tvBuyerPostUpdateType.text.toString()
            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        title: String,
        date: String,
        price: String,
//        location: String,
        type: String,
        qty: String,
        name: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("BuyerPosts").child(id)
        val empInfo = BuyerPostModel(id, title, date, price, type, qty, name)
        dbRef.setValue(empInfo)
    }

}