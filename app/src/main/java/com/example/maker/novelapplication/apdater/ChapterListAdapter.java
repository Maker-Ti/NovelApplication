package com.example.maker.novelapplication.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.TransTool;
import com.example.maker.novelapplication.bean.Book;

import java.util.List;

public class ChapterListAdapter extends BaseAdapter {
    private Context context;

    public ChapterListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ActionController.selectedChapterData.size();
    }

    @Override
    public Object getItem(int i) {
        return ActionController.selectedChapterData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_chapter,null);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.title);
            viewHolder.index = view.findViewById(R.id.index);
            viewHolder.background = view.findViewById(R.id.background);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(ActionController.selectedChapterData.get(i).getTitle());
        viewHolder.index.setText("第"+(ActionController.selectedChapterData.get(i).getIndex()+1)+"章");
        if(ActionController.selectedChapter.getIndex() == i){
            viewHolder.background.setBackgroundResource(R.drawable.full_grey);
        }else {
            viewHolder.background.setBackgroundResource(R.drawable.bc_none);
        }
        return view;
    }
    class ViewHolder{
        TextView index;
        TextView title;
        RelativeLayout background;
    }
}
