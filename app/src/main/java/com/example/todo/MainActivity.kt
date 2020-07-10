package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.view_pager_acticity.*

class MainActivity : AppCompatActivity() {

    private val customAdapter by lazy { CustomAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize(){
        initLayout()
    }

    private fun initLayout(){
        initClick1()
        initViewpager2()
        initTabLayout()
    }

    private fun initClick1(){
        fab.setOnClickListener{
            addMemo()
        }

    }
    private fun initClick2(){
        deleteButton.setOnClickListener{
            addMemo()
        }

    }

    private fun initViewpager2(){
        viewPager2.apply{
            adapter = customAdapter
            offscreenPageLimit=customAdapter.itemCount

        }
    }
    private fun addMemo(){

        val editText=EditText(this).apply {
            hint="やることを入力"
        }
        AlertDialog.Builder(this)
            .setTitle("やるべきことの入力")
            .setMessage("期日までに必ずやること")
            .setPositiveButton("OK"){_,_->
                saveMemo(editText.text.toString())
            }
            .show()
    }

    private  fun saveMemo(memoTitle:String){
    Realm.getDefaultInstance().use{
        it.executeTransaction{realm ->
            realm.insertOrUpdate(ListObject().apply {
                title=memoTitle
            })
        }
    }
        initData()
    }

    private fun deleteMemo(data: ListObject) {
        ListObject.delete(data)
        initData()
    }



    private fun initTabLayout() {
        val a = listOf<String>("やること","済")
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "${a[position]}"
        }.attach()
}
    private fun initData(){
        val list=ListObject.findAll()
        customAdapter.refresh(list)
    }

}