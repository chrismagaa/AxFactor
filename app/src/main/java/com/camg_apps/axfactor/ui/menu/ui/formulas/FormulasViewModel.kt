package com.camg_apps.axfactor.ui.menu.ui.formulas

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.camg_apps.axfactor.data.AppRepository
import com.camg_apps.axfactor.data.model.Armadora
import com.camg_apps.axfactor.data.model.Formula
import com.camg_apps.axfactor.data.model.Material
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class FormulasViewModel : ViewModel() {
    private var appRepository: AppRepository? = null
    private var userUID: String = ""
    private lateinit var referenceFormulas: DatabaseReference
    var armadorasLiveData: MutableLiveData<List<Armadora>>
    var formulasLiveData: MutableLiveData<List<Formula>>



    init {
        appRepository = AppRepository()
        armadorasLiveData = appRepository!!.armadorasLiveData
        formulasLiveData = MutableLiveData()
    }

    fun setReferenceFormulas(activity: Activity){
        val sharedPref = activity.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        userUID = sharedPref.getString("uid", "")?: ""
        Toast.makeText(activity, userUID, Toast.LENGTH_SHORT).show()
        //userUID = "EQQq3yNMM7Xeoh6fAIlVH01Q2fC3"
        referenceFormulas = appRepository!!.database.child("formulas")

    }

    fun getReferenceFormulas(){
        referenceFormulas.get().addOnSuccessListener {
            Log.d("FormulasViewModel", "getFormulas: ${it.value}")
            var listFormulas = ArrayList<Formula>()

                it.children.forEach{formula ->
                    var materiales = arrayListOf<Material>()
                    formula.child("materiales").children.forEach{
                        it.key
                        materiales.add(Material(it.child("name").value as String, it.child("weight").value.toString().toDouble()))
                    }
                    listFormulas.add(Formula(
                        code_reference = formula.child("code_reference").value as String,
                        description = formula.child("description").value as String,
                        color =formula.child("color").value as Long,
                        materiales = materiales
                    ))
                }
                formulasLiveData.postValue(listFormulas)


        }.addOnFailureListener(){

        }
    }

    /*
    fun getFormulas() {
        val formulasListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var listFormulas = ArrayList<Formula>()
                if(dataSnapshot.exists()){
                dataSnapshot.children.forEach{formula ->
                    var materiales = arrayListOf<Material>()
                    formula.child("materiales").children.forEach{
                        it.key
                       //materiales.add(it.getValue(Material::class.java)!!)
                       materiales.add(Material(it.key, it.child("codigo").value as String, it.child("gramos").value.toString().toDouble()))
                    }
                    listFormulas.add(Formula(codigo = formula.key,
                           armadora = formula.child("armadora").value as String,
                           linea = formula.child("linea").value as String,
                            color =formula.child("color").value as Long,
                           materiales = materiales
                            ))
                }
                formulasLiveData.postValue(listFormulas)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseFormulas", "loadPost:onCancelled", databaseError.toException())
            }
        }

        referenceFormulas.addValueEventListener(formulasListener)
    }

     */


}