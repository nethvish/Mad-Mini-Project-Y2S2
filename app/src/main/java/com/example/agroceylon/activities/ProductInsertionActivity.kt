package com.example.agroceylon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.agroceylon.R
import com.example.agroceylon.models.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProductInsertionActivity : AppCompatActivity() {

    private lateinit var etProductName : EditText
    private lateinit var etLocation : EditText
    private lateinit var etPrice : EditText
    private lateinit var etQuantity : EditText
    private lateinit var etDate : EditText
    private lateinit var btnSaveData: Button


    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_insertion)

        etProductName = findViewById(R.id.etProductName)
        etLocation = findViewById(R.id.etLocation)
        etPrice = findViewById(R.id.etPrice)
        etQuantity = findViewById(R.id.etQuantity)
        etDate = findViewById(R.id.etDate)
        btnSaveData = findViewById(R.id.btn_save_data)

        //creating the collection
        dbRef = FirebaseDatabase.getInstance().getReference("Products")

        btnSaveData.setOnClickListener {
            saveProductData()
        }

    }

    private fun saveProductData(){

        //getting values
        val productName = etProductName.text.toString()
        val productLocation = etLocation.text.toString()
        val productPrice = etPrice.text.toString()
        val productQuantity = etQuantity.text.toString()
        val productDate = etDate.text.toString()

        if (productName.isEmpty()) {
            etProductName.error = "Please enter product Name"
        }
        if (productLocation.isEmpty()) {
            etLocation.error = "Please enter Location"
        }
        if (productPrice.isEmpty()) {
            etPrice.error = "Please enter price"
        }
        if (productQuantity.isEmpty()) {
            etQuantity.error = "Please enter quantity"
        }
        if (productDate.isEmpty()) {
            etDate.error = "Please enter Date"
        }

        val productID = dbRef.push().key!!

        val product = ProductModel(productID, productName,productLocation,productPrice,productQuantity,productDate)

        dbRef.child(productID).setValue(product)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_LONG).show()

                etProductName.text.clear()
                etLocation.text.clear()
                etPrice.text.clear()
                etQuantity.text.clear()
                etDate.text.clear()

            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }
    }
}