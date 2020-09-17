package com.example.maker.novelapplication.request;

import android.content.Context;

import com.example.maker.novelapplication.bean.Chapter;
import com.example.maker.novelapplication.http.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetChapter extends BaseRequest {
    public GetChapter(Context context, String params) {
        super(context, params);
    }

    @Override
    protected Object getHttpResult(String result) {
        List<Chapter> data = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getInt("code") == 200){
                JSONArray jsonArray = jsonObject.getJSONArray("chapterListAll");
                data = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject single = jsonArray.getJSONObject(i);
                    if(single.getInt("status") == 1){
                        Chapter chapter = new Chapter(
                                single.getString("title"),
                                single.getString("content"),
                                i
                        );
                        data.add(chapter);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected String setHttpURL() {
        return HttpUrl.getChapterByBookId;
    }
}
