package com.example.maker.novelapplication.request;

import android.content.Context;
import android.util.Log;

import com.example.maker.novelapplication.bean.Book;
import com.example.maker.novelapplication.http.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetBookByStr_Request extends BaseRequest {
    public GetBookByStr_Request(Context context, String params) {
        super(context, params);
    }

    @Override
    protected Object getHttpResult(String result) {

        List<Book> bookList = new ArrayList<>();
        try {
            JSONObject js = new JSONObject(result);
            if(js.getInt("code") == 200){

                JSONArray jsonArray = new JSONArray(js.getString("bookListAll"));
                Log.e("makerLog","getHistoryBook:"+jsonArray.length());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject single = jsonArray.getJSONObject(i);
                    //String id, String creatTime, String name, String chapter, String creaotrname, String detail, String img
                    Book book = new Book(
                      single.getString("id"),
                            single.getString("creatTime"),
                            single.getString("name"),
                            single.getString("chapter"),
                            single.getString("creaotrname"),
                            single.getString("detail"),
                            single.getString("img")
                    );
                    bookList.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    protected String setHttpURL() {
        return HttpUrl.getBookByStr;
    }
}
