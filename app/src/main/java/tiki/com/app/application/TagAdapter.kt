package tiki.com.app.application

import android.view.View
import tiki.com.app.R
import tiki.com.app.libs.BaseAdapter
import tiki.com.app.libs.BaseViewHolder

class TagAdapter() : BaseAdapter() {
    private val SECTION_DATA = 0

    init {
        insertSection(SECTION_DATA, mutableListOf<String>())
    }

    override fun layout(sectionRow: SectionRow): Int = R.layout.item_tag

    override fun viewHolder(layout: Int, view: View): BaseViewHolder = ItemTagViewHolder(view)
    fun addData(mutableList: MutableList<Pair<String, Int>>) {
        setSection(SECTION_DATA, mutableList)
        notifyDataSetChanged()
    }
}
