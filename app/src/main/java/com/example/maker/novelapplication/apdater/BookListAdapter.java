package com.example.maker.novelapplication.apdater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.TransTool;
import com.example.maker.novelapplication.bean.Book;

import java.util.List;

public class BookListAdapter extends BaseAdapter {
    private List<Book> data;
    private Context context;

    public BookListAdapter(List<Book> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_book,null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.chapter = view.findViewById(R.id.chapter);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.creator = view.findViewById(R.id.creator);
            viewHolder.detail = view.findViewById(R.id.detail);
            viewHolder.img = view.findViewById(R.id.img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Book book = data.get(i);
        viewHolder.name.setText(book.getName());
        viewHolder.chapter.setText("章节数："+book.getChapter());
        viewHolder.time.setText("上传时间："+book.getChapter());
        viewHolder.creator.setText("作者："+book.getCreaotrname());
        viewHolder.detail.setText("简介："+book.getDetail());
        viewHolder.img.setImageBitmap(TransTool.base64ToBitmap(book.getImg()));

        return view;
    }
    class ViewHolder{
        TextView name;
        TextView chapter;
        TextView time;
        TextView creator;
        TextView detail;
        ImageView img;
    }
}
