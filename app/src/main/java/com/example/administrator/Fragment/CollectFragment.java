package com.example.administrator.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.activity.NewsInfoActivity;
import com.example.administrator.activity.R;
import com.example.administrator.adapter.MainAdapter;
import com.example.administrator.database.FavoriteDataBase;
import com.example.administrator.entity.MainData;
import com.example.administrator.util.Contents;

import java.util.ArrayList;

/**
 * 收藏碎片
 * Created by Administrator on 2017-04-25.
 */

public class CollectFragment extends Fragment {
    MainAdapter adapter;//适配器
    ListView listView;//子条目
    public static ArrayList<MainData> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_collect, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.lv_collect);
        FavoriteDataBase favoriteDataBase = new FavoriteDataBase(getContext());
        mList = favoriteDataBase.research();//将数据库查询的结果赋值给mList
        adapter = new MainAdapter(mList, getContext(), R.layout.item_main_collect);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsInfoActivity.class);
                intent.putExtra(Contents.NEWS_LINKS,adapter.mList.get(position-1).getLink());//新闻链接
                intent.putExtra(Contents.NEWS_ID,adapter.mList.get(position-1).getNid()+"");//新闻id
                startActivity(intent);
            }
        });

    }
}
