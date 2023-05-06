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
import com.example.agroceylon.models.TransporterModel
import com.google.firebase.database.FirebaseDatabase

class TransporterDetailsActivity : AppCompatActivity() {

    private lateinit var tvTrId: TextView
    private lateinit var tvTrfName: TextView
    private lateinit var tvVehiModel: TextView
    private lateinit var tvVehiId: TextView
    private lateinit var tvTrMobile: TextView
    private lateinit var tvTrLocation: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transporter_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("trId").toString(),
                intent.getStringExtra("trfName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("trId").toString()
            )
        }

    }

    private fun initView() {
        tvTrId = findViewById(R.id.tvTrId)
        tvTrfName = findViewById(R.id.tvTrfName)
        tvVehiModel = findViewById(R.id.tvVehiModel)
        tvVehiId = findViewById(R.id.tvVehiId)
        tvTrMobile = findViewById(R.id.tvTrMobile)
        tvTrLocation = findViewById(R.id.tvTrLocation)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvTrId.text = intent.getStringExtra("trId")
        tvTrfName.text = intent.getStringExtra("trfName")
        tvVehiModel.text = intent.getStringExtra("vehiModel")
        tvVehiId.text = intent.getStringExtra("vehiId")
        tvTrMobile.text = intent.getStringExtra("trMobile")
        tvTrLocation.text = intent.getStringExtra("trLocation")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Transporter").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "transporter data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, TransporterFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        trId: String,
        trfName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_transport, null)

        mDialog.setView(mDialogView)

        val etTrfName = mDialogView.findViewById<EditText>(R.id.etTrfName)
        val etVehiModel = mDialogView.findViewById<EditText>(R.id.etVehiModel)
        val etVehiId = mDialogView.findViewById<EditText>(R.id.etVehiId)
        val etTrMobile = mDialogView.findViewById<EditText>(R.id.etTrMobile)
        val etTrLocation = mDialogView.findViewById<EditText>(R.id.etTrLocation)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etTrfName.setText(intent.getStringExtra("trfName").toString())
        etVehiModel.setText(intent.getStringExtra("vehiModel").toString())
        etVehiId.setText(intent.getStringExtra("vehiId").toString())
        etTrMobile.setText(intent.getStringExtra("trMobile").toString())
        etTrLocation.setText(intent.getStringExtra("trLocation").toString())

        mDialog.setTitle("Updating $trfName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateTranspData(
                trId,
                etTrfName.text.toString(),
                etVehiModel.text.toString(),
                etVehiId.text.toString(),
                etTrMobile.text.toString(),
                etTrLocation.text.toString()
            )

            Toast.makeText(applicationContext, "Taranspoter Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvTrfName.text = etTrfName.text.toString()
            tvVehiModel.text = etVehiModel.text.toString()
            tvVehiId.text = etVehiId.text.toString()
            tvTrMobile.text = etTrMobile.text.toString()
            tvTrLocation.text = etTrLocation.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateTranspData(
        trId: String,
        trfName:String,
        vehimodel: String,
        vehiid:String,
        trmobile: String,
        trlocation: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Transporter").child(trId)
        val transpInfo = TransporterModel(trId, trfName, vehimodel, vehiid,trmobile, trlocation )
        dbRef.setValue(transpInfo)
    }

}
