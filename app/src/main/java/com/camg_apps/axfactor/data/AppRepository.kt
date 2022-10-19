package com.camg_apps.axfactor.data

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.camg_apps.axfactor.data.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AppRepository() {
    private var firebaseAuth: FirebaseAuth? = null
    var userLiveData: MutableLiveData<FirebaseUser>
    var armadorasLiveData: MutableLiveData<List<Armadora>>
    var laboratoriosLiveData: MutableLiveData<List<String>>
    var formulasLiveData: MutableLiveData<List<Formula>>
    var usersLiveData: MutableLiveData<List<User>>
    var loggedOutLiveData: MutableLiveData<Boolean>
    var database: DatabaseReference

    init{
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        database = Firebase.database.reference
        armadorasLiveData = MutableLiveData()
        usersLiveData = MutableLiveData()
        formulasLiveData = MutableLiveData()
        laboratoriosLiveData = MutableLiveData()


        if (firebaseAuth!!.currentUser != null) {
            userLiveData.postValue(firebaseAuth!!.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }
/*
    fun login(email: String?, password: String?, activity: Activity) {
        firebaseAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        database.child("users").child(firebaseAuth!!.currentUser!!.uid).get().addOnSuccessListener {user ->
                            if(user.exists()){
                                    saveUser(firebaseAuth!!.currentUser!!.uid, user.child("correo").getValue(String::class.java)?: "", ROL.USER.name, activity)
                            }else{
                                database.child("admins").child(firebaseAuth!!.currentUser!!.uid).get().addOnSuccessListener {admin ->
                                    if(admin.exists()){
                                    saveUser(firebaseAuth!!.currentUser!!.uid, admin.child("correo").getValue(String::class.java)?: "", ROL.ADMIN.name, activity)

                                    }
                                }
                        }
                    }
                    } else {
                        Toast.makeText(activity, "Login Failure: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
    }

 */

    fun saveUser(uid: String, email: String, rol: String, activity: Activity) {
       val prefs = activity.getSharedPreferences("PREFERENCE_FILE_KEY", Context.MODE_PRIVATE).edit()
        prefs.putString("correo", email)
        prefs.putString("rol", rol)
        prefs.putString("uid", uid)
        prefs.apply()
        userLiveData.postValue(firebaseAuth!!.currentUser)
    }

    fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getArmadoras(){
        database.child("Armadoras").get().addOnSuccessListener {
            if(it.exists()){
                val listArmadora = arrayListOf<Armadora>()
                it.children.forEach{armadora ->
                     listArmadora.add(Armadora(armadora.value as String?))
                }
                armadorasLiveData.postValue(listArmadora)
            }
        }.addOnCanceledListener {
            Log.w("FIREBASE_GET_MARCAS", "loadPost:onCancelled")
        }
    }


/*
    fun getFormulasOneTime(){
        database.child("users").child("EQQq3yNMM7Xeoh6fAIlVH01Q2fC3").child("formulas").get().addOnSuccessListener {
            if(it.exists()){
                val listFormula = arrayListOf<Formula>()
                it.children.forEach{formula ->
                    var materiales = arrayListOf<Material>()
                    formula.child("materiales").children.forEach{
                        materiales.add(it.getValue(Material::class.java)!!)
                    }
                    listFormula.add(Formula(
                           codigo =  formula.key,
                           armadora =  formula.child("armadora").value as String,
                           color =  formula.child("color").value as Long,
                           linea = formula.child("linea").value as String,
                           materiales = materiales
                    ))
                }
                formulasLiveData.postValue(listFormula)
            }
        }.addOnCanceledListener {
            Log.w("FIREBASE_GET_FORMULAS", "loadPost:onCancelled")
        }
    }

 */

    fun writeNewFormula(formula: Formula, codigo: String, onSuccess: () -> Unit, onError: () -> Unit){
        database.child("formulas").child(codigo).setValue(formula).addOnSuccessListener {
                onSuccess()
        }.addOnFailureListener {
            Log.w("FIREBASE_WRITE_FORMULA", "loadPost:onCancelled")
            Log.w("FIREBASE_WRITE_FORMULA", it.message.toString())
            onError()
        }
    }

    fun writeNewMaterial(material: Tint, userId: String, codigo: String): Boolean {
        database.child("formulas").child(userId).child(codigo).child("tints").push().setValue(material)
        return false
    }

    fun deleteMaterial(userId: String, codigo: String, key: String) {
        database.child("formulas").child(userId).child(codigo).child("tints").child(key).removeValue()

    }


}