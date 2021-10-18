package com.example.headupp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_new_celebrity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {


    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    var found = false
    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private lateinit var progressDialog: ProgressDialog

    private lateinit var btAdd: Button
    private lateinit var etCelebrity: EditText
    private lateinit var btDetails: Button

    private lateinit var celebrities: ArrayList<headsupCelebrity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            celebrities = arrayListOf()

            rvMain = findViewById(R.id.rvMain)
            rvAdapter = RVAdapter(this,celebrities)
            rvMain.adapter = rvAdapter
            rvMain.layoutManager = LinearLayoutManager(this)

            btAdd = findViewById(R.id.bAdd)
            btAdd.setOnClickListener {
                intent = Intent(applicationContext, NewCelebrity::class.java)
                val celebrityNames = arrayListOf<String>()
                for(c in celebrities){
                    celebrityNames.add(c.name.lowercase())
                }
                intent.putExtra("celebrityNames", celebrityNames)
                startActivity(intent)
            }
            etCelebrity = findViewById(R.id.edCName)
            btDetails = findViewById(R.id.bSubmet)
            btDetails.setOnClickListener {
                if(etCelebrity.text.isNotEmpty()){
                    updateCelebrity()
                }else{
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
                }
            }

            progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please Wait")
            progressDialog.show()

            getCelebrities()
        }

        private fun getCelebrities(){
            apiInterface.getCelebrities().enqueue(object: Callback<ArrayList<headsupCelebrity>>{
                override fun onResponse(
                    call: Call<ArrayList<headsupCelebrity>>,
                    response: Response<ArrayList<headsupCelebrity>>
                ) {
                    progressDialog.dismiss()
                    celebrities = response.body()!!
                    rvAdapter.update(celebrities)
                }

                override fun onFailure(call: Call<ArrayList<headsupCelebrity>>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "Unable to get data", Toast.LENGTH_LONG).show()
                }
            })
        }

        private fun updateCelebrity(){
            var celebrityID = 0
            for(celebrity in celebrities){
                if(etCelebrity.text.toString().capitalize() == celebrity.name){
                    celebrityID = celebrity.pk
                    intent = Intent(applicationContext, DeleteUpdate::class.java)
                    intent.putExtra("celebrityID", celebrityID)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "${etCelebrity.text.toString().capitalize()} not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }