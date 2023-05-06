package com.example.agroceylon.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.agroceylon.R
import com.example.agroceylon.models.TransporterModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TransporterInsertionActivity : AppCompatActivity() {

    private lateinit var etTrfName: EditText
    private lateinit var etVehiModel: EditText
    private lateinit var etVehiId: EditText
    private lateinit var etTrMobile: EditText
    private lateinit var etTrLocation: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transporter_insertion)

        etTrfName = findViewById(R.id.etTrfName)
        etVehiModel = findViewById(R.id.etVehiModel)
        etVehiId = findViewById(R.id.etVehiId)
        etTrMobile = findViewById(R.id.etTrMobile)
        etTrLocation = findViewById(R.id.etTrLocation)
        btnSaveData = findViewById(R.id.btnSave)

        //creating the collection
        dbRef = FirebaseDatabase.getInstance().getReference("Transporter")

        btnSaveData.setOnClickListener {
            saveTransporterData()
        }
    }

    private fun saveTransporterData() {

        //getting values
        val trfName = etTrfName.text.toString()
        val vehiModel = etVehiModel.text.toString()
        val vehiId = etVehiId.text.toString()
        val trMobile = etTrMobile.text.toString()
        val trLocation = etTrLocation.text.toString()

        if (trfName.isEmpty()) {
            etTrfName.error = "Please enter name"
        }
        if (vehiModel.isEmpty()) {
            etVehiModel.error = "Please enter vehicle model"
        }
        if (vehiId.isEmpty()) {
            etVehiId.error = "Please enter vehicle number"
        }
        if (trMobile.isEmpty()) {
            etTrMobile.error = "Please enter mobile number"
        }
        if (trLocation.isEmpty()) {
            etTrLocation.error = "Please enter mobile number"
        }

        val trId = dbRef.push().key!!

        val transporter = TransporterModel(trId, trfName, vehiModel, vehiId, trMobile, trLocation)

        dbRef.child(trId).setValue( transporter)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etTrfName.text.clear()
                etVehiModel.text.clear()
                etVehiId.text.clear()
                etTrMobile.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}