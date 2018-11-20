package tiki.com.app.application

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.graphics.Color
import android.view.View
import tiki.com.app.R
import tiki.com.app.databinding.ItemTagBinding
import tiki.com.app.libs.BaseViewHolder
import java.util.*

class ItemTagViewHolder(view: View) : BaseViewHolder(view) {
    var name = ObservableField<String>()
    lateinit var viewBinding: ItemTagBinding
    override fun onBind() {

    }

    override fun bindData(data: Any, position: Int) {
        viewBinding = DataBindingUtil.bind(itemView)!!
        viewBinding.viewHolder = this
        var tag = data as Pair<String, Int>
        name.set(tag.first)
        viewBinding.textView.setBackgroundResource(tag.second)
    }

    override fun onClick(p0: View?) {

    }

}
