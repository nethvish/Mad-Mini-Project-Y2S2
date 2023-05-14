package com.example.agroceylon.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agroceylon.R
import com.example.agroceylon.models.BuyerPostModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BuyerInsertionActivity : AppCompatActivity() {

    private lateinit var etBuyerPostCreateTitle: EditText
    private lateinit var etBuyerPostCreateQty: EditText
    private lateinit var etBuyerPostCreatePrice: EditText
    private lateinit var etBuyerPostCreateLocation: EditText
    private lateinit var etBuyerPostCreateReqDate: EditText
    private lateinit var etBuyerPostCreateType: EditText
    private lateinit var etBuyerPostCreateName: EditText

    private lateinit var btnBuyerPostCreateSave: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_insertion)

        etBuyerPostCreateTitle = findViewById(R.id.etBuyerPostCreateTitle)
        etBuyerPostCreateQty = findViewById(R.id.etBuyerPostCreateQty)
        etBuyerPostCreatePrice = findViewById(R.id.etBuyerPostCreatePrice)
        etBuyerPostCreateLocation = findViewById(R.id.etBuyerPostCreateLocation)
        etBuyerPostCreateReqDate = findViewById(R.id.etBuyerPostCreateReqDate)
        etBuyerPostCreateType = findViewById(R.id.etBuyerPostCreateType)
        etBuyerPostCreateName = findViewById(R.id.etBuyerPostCreateName)


        btnBuyerPostCreateSave = findViewById(R.id.btnBuyerPostCreateSave)

        dbRef = FirebaseDatabase.getInstance().getReference("BuyerPosts")

        btnBuyerPostCreateSave.setOnClickListener {
            saveBuyerPostData()
        }
    }

    private fun saveBuyerPostData() {

        //getting values
        val postTitle = etBuyerPostCreateTitle.text.toString()
        val postQty = etBuyerPostCreateQty.text.toString()
        val postPrice = etBuyerPostCreatePrice.text.toString()
        val postLocation = etBuyerPostCreateLocation.text.toString()
        val postDate = etBuyerPostCreateReqDate.text.toString()
        val postType = etBuyerPostCreateType.text.toString()
        val postName = etBuyerPostCreateName.text.toString()


        if (postTitle.isEmpty()) {
            etBuyerPostCreateTitle.error = "Please enter title"
        }
        if (postQty.isEmpty()) {
            etBuyerPostCreateQty.error = "Please enter qty"
        }
        if (postPrice.isEmpty()) {
            etBuyerPostCreatePrice.error = "Please enter price"
        }
        if (postLocation.isEmpty()) {
            etBuyerPostCreateLocation.error = "Please enter location"
        }
        if (postDate.isEmpty()) {
            etBuyerPostCreateReqDate.error = "Please enter date"
        }
        if (postType.isEmpty()) {
            etBuyerPostCreateType.error = "Please enter type"
        }
        if (postName.isEmpty()) {
            etBuyerPostCreateName.error = "Please enter buyer name"
        }

        val buyerPostId = dbRef.push().key!!

        val buyerPost = BuyerPostModel(postTitle, postQty, postPrice, postLocation, postDate, postType, postName)

        dbRef.child(buyerPostId).setValue(buyerPost)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etBuyerPostCreateTitle.text.clear()
                etBuyerPostCreateQty.text.clear()
                etBuyerPostCreatePrice.text.clear()
                etBuyerPostCreateLocation.text.clear()
                etBuyerPostCreateReqDate.text.clear()
                etBuyerPostCreateType.text.clear()
                etBuyerPostCreateName.text.clear()



            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}