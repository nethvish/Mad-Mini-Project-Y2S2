package com.example.agroceylon.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.agroceylon.R
import com.example.agroceylon.databinding.ActivityToolDetailsBinding
import com.example.agroceylon.models.ToolPostModel
import com.google.firebase.database.FirebaseDatabase

class ToolDetailsActivity : AppCompatActivity() {



    private lateinit var tvToolTitle: TextView
    private lateinit var tvToolPrice: TextView
    private lateinit var tvToolLocation: TextView
    private lateinit var tvToolType: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_details)

        initView()
        setValuesToViews()

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("toolId").toString(),
                intent.getStringExtra("toolTitle").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("toolId").toString()
            )
        }

    }

    private fun initView() {
        tvToolTitle = findViewById(R.id.tvToolTitle)
        tvToolPrice = findViewById(R.id.tvToolPrice)
        tvToolLocation = findViewById(R.id.tvToolLocation)
        tvToolType = findViewById(R.id.tvToolType)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvToolTitle.text = intent.getStringExtra("toolTitle")
        tvToolPrice.text = intent.getStringExtra("toolPrice")
        tvToolLocation.text = intent.getStringExtra("toolLocation")
        tvToolType.text = intent.getStringExtra("toolType")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Tools").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Post data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingToolsActivity::class.java)
            finish()
            startActivity(intent)

        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        toolId: String,
        toolTitle: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.tool_update, null)

        mDialog.setView(mDialogView)

        val etToolTitle = mDialogView.findViewById<EditText>(R.id.etToolTitle)
        val etToolPrice = mDialogView.findViewById<EditText>(R.id.etToolPrice)
        val etToolLocation = mDialogView.findViewById<EditText>(R.id.etToolLocation)
        //val etToolType = mDialogView.findViewById<RadioButton>(R.id.etToolType)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etToolTitle.setText(intent.getStringExtra("toolTitle").toString())
        etToolPrice.setText(intent.getStringExtra("toolPrice").toString())
        etToolLocation.setText(intent.getStringExtra("toolPrice").toString())
       // etToolType.setText(intent.getStringExtra("toolType").toString())
//        val etToolType = if (binding.rbAgTool.isChecked) {
//            Constants.AGRO_TOOL
//        } else {
//            Constants.FERTILIZER
//        }

        mDialog.setTitle("Updating $toolTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateToolData(
                toolId,
                etToolTitle.text.toString(),
                etToolPrice.text.toString(),
                etToolLocation.text.toString(),
               // etToolType.text.toString(),
            )

            Toast.makeText(applicationContext, "Post Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvToolTitle.text = etToolTitle.text.toString()
            tvToolPrice.text = etToolPrice.text.toString()
            tvToolLocation.text = etToolLocation.text.toString()
          //  tvToolType.text = etToolType.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateToolData(
        id: String,
        title: String,
        price: String,
        location: String,
        //type: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Tools").child(id)
        val toolInfo = ToolPostModel(id, title, price, location)
        dbRef.setValue(toolInfo)
    }

}
