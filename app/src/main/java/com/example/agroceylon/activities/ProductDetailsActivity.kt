package com.example.agroceylon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.agroceylon.R
import com.example.agroceylon.models.ProductModel
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var tvProductID: TextView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductLocation: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductQuantity : TextView
    private lateinit var tvProductDate: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        initView()
        setValuesToView()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("productID").toString(),
                intent.getStringExtra("productName").toString()

            )
        }
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("productID").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id)
        val pTask = dbRef.removeValue()

        pTask.addOnSuccessListener {
            Toast.makeText(this,"Product Deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,ProductFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()

        }
    }


    private fun initView() {
        tvProductID = findViewById(R.id.tvProductID)
        tvProductName = findViewById(R.id.tvProductName)
        tvProductLocation = findViewById(R.id.tvProductLocation)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductQuantity = findViewById(R.id.tvProductQuantity)
        tvProductDate = findViewById(R.id.tvProductDate)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }



    private fun setValuesToView(){
        tvProductID.text = intent.getStringExtra("productID")
        tvProductName.text = intent.getStringExtra("productName")
        tvProductLocation.text = intent.getStringExtra("productLocation")
        tvProductPrice.text = intent.getStringExtra("productPrice")
        tvProductQuantity.text = intent.getStringExtra("productQuantity")
        tvProductDate.text = intent.getStringExtra("productDate")
    }

    private fun openUpdateDialog(
        productID: String,
        productName: String
    ){
        val pDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val pDialogView = inflater.inflate(R.layout.update_product_dialog,null)

        pDialog.setView(pDialogView)

        val etProductName = pDialogView.findViewById<EditText>(R.id.etProductName)
        val etProductLocation = pDialogView.findViewById<EditText>(R.id.etLocation)
        val etProductPrice = pDialogView.findViewById<EditText>(R.id.etPrice)
        val etProductQuantity = pDialogView.findViewById<EditText>(R.id.etQuantity)
        val etProductDate = pDialogView.findViewById<EditText>(R.id.etDate)

        val btnUpdateData = pDialogView.findViewById<Button>(R.id.btnUpdateData)

        etProductName.setText(intent.getStringExtra("productName").toString())
        etProductLocation.setText(intent.getStringExtra("productLocation").toString())
        etProductPrice.setText(intent.getStringExtra("productPrice").toString())
        etProductQuantity.setText(intent.getStringExtra("productQuantity").toString())
        etProductDate.setText(intent.getStringExtra("productDate").toString())

        pDialog.setTitle("Updating $productName record")

        val alertDialog = pDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateProductData(
                productID,
                etProductName.text.toString(),
                etProductLocation.text.toString(),
                etProductPrice.text.toString(),
                etProductQuantity.text.toString(),
                etProductDate.text.toString()
            )
            Toast.makeText(applicationContext,"Product data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textView
            tvProductName.text = etProductName.text.toString()
            tvProductLocation.text = etProductLocation.text.toString()
            tvProductPrice.text = etProductPrice.text.toString()
            tvProductQuantity.text = etProductQuantity.text.toString()
            tvProductDate.text = etProductDate.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateProductData(
        id: String,
        productName: String,
        productLocation: String,
        productPrice: String,
        productQuantity: String,
        productDate: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id)
        val productInfo = ProductModel(id,productName,productLocation,productPrice,productQuantity,productDate)
        dbRef.setValue(productInfo)
    }

}