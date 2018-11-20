package tiki.com.app.application

import android.databinding.DataBindingUtil
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import tiki.com.app.R
import tiki.com.app.databinding.ActivityMainBinding
import tiki.com.app.libs.BaseActivity
import tiki.com.app.libs.qualifers.RequireActivityViewModel
import tiki.com.app.libs.tranforms.Transformers

@RequireActivityViewModel(MainViewModel.ViewModel::class)
class MainActivity : BaseActivity<MainViewModel.ViewModel>() {

    private var adapter = TagAdapter()
    lateinit var viewDataBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewDataBinding.viewModel = viewModel
        var ll = LinearLayoutManager(this)
        ll.orientation = LinearLayoutManager.HORIZONTAL
        viewDataBinding.recyclerView.layoutManager = ll
        viewDataBinding.recyclerView.adapter =adapter
        viewModel!!.outPut.renderData()
                .compose(bindToLifecycle())
                .compose(Transformers.observeForUI())
                .subscribe {
                    adapter.addData(it.toMutableList())
                }
        viewModel!!.error.showErrorMessage()
                .compose(bindToLifecycle())
                .subscribe {
                    showErrorMessage(it)
                }
    }
}
