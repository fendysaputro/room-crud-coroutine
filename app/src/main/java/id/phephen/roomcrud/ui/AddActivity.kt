package id.phephen.roomcrud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import id.phephen.roomcrud.databinding.ActivityAddBinding
import id.phephen.roomcrud.model.User
import id.phephen.roomcrud.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    private val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        onBtnSaveClicked()
    }

    private fun initView() {
        etName = binding.etName
        etDate = binding.etDate
        etPhone = binding.etPhone
        btnSave = binding.btnSave
    }

    private fun onBtnSaveClicked() {
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                        User(0,
                        etName.text.toString(),
                        etDate.text.toString(),
                            etPhone.text.toString())
                )
                finish()
            }
        }
    }
}