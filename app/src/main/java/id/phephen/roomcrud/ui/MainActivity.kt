package id.phephen.roomcrud.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.phephen.roomcrud.adapter.UserAdapter
import id.phephen.roomcrud.databinding.ActivityMainBinding
import id.phephen.roomcrud.model.User
import id.phephen.roomcrud.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnAdd: Button
    private lateinit var rvUser: RecyclerView

    private lateinit var userAdapter: UserAdapter
    private val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        onBtnAddClicked()
        setupRecyclerView()
    }

    private fun initView() {
        btnAdd = binding.btnAdd
        rvUser = binding.rvUser
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            userAdapter.setData(db.userDao().getUsers())
            withContext(Dispatchers.Main) {
                userAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView () {
        userAdapter = UserAdapter(
            arrayListOf(),
            object : UserAdapter.OnAdapterListener {
                override fun onClick(user: User) {
                    onUserClicked(user)
                }

                override fun onDelete(user: User) {
                    onDeleteClicked(user)
                }


            })

        rvUser.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userAdapter
        }

    }

    private fun onDeleteClicked(user: User) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${user.name}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteUser(user)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }

    private fun onUserClicked(user: User) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnAddClicked() {
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
}