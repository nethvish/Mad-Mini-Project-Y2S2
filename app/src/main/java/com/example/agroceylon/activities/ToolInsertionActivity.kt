package com.example.agroceylon.activities

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.agroceylon.databinding.ActivityToolInsertionBinding
import com.example.agroceylon.models.ToolPostModel
import com.example.agroceylon.utils.Constants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ToolInsertionActivity : BaseActivity() {

    private lateinit var binding: ActivityToolInsertionBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }

        //creating the collection
        dbRef = FirebaseDatabase.getInstance().getReference("Tools")
        storageRef = FirebaseStorage.getInstance().getReference("images")

        binding.toolBtnSubmit.setOnClickListener {
            saveToolPostData()
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.ivToolPhoto.setImageURI(it)
            if(it != null){
                uri = it
            }
        }

        binding.ivToolPhoto.setOnClickListener{

            pickImage.launch("image/*")
        }

    }

    private fun saveToolPostData() {

        //getting values
        val toolTitle = binding.etToolTitle.text.toString()
        val toolPrice = binding.etToolPrice.text.toString()
        val toolLocation = binding.etToolLocation.text.toString()
        val toolType = if (binding.rbAgTool.isChecked) {
            Constants.AGRO_TOOL
        } else {
            Constants.FERTILIZER
        }

        if (toolTitle.isEmpty()) {
            binding.etToolTitle.error = "Please enter post title"
        }
        if (toolPrice.isEmpty()) {
            binding.etToolPrice.error = "Please enter unit price"
        }
        if (toolLocation.isEmpty()) {
            binding.etToolLocation.error = "Please enter location"
        }


        val toolId = dbRef.push().key!!
       // (toolId, toolTitle, toolPrice, toolLocation, toolType)
        var toolPost : ToolPostModel

        uri?.let{
            storageRef.child(toolId).putFile(it)
                .addOnSuccessListener{ task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                            val imgUrl = url.toString()

                            toolPost = ToolPostModel(toolId, toolTitle, toolPrice, toolLocation, toolType, imgUrl)

                            dbRef.child(toolId).setValue(toolPost)
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Post added successfully", Toast.LENGTH_LONG).show()

                                    binding.etToolTitle.text?.clear()
                                    binding.etToolPrice.text?.clear()
                                    binding.etToolLocation.text?.clear()


                                }.addOnFailureListener { err ->
                                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()

                                }

                        }
                }
        }

    }
}