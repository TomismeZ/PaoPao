package com.zdk.paopao.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zdk.paopao.R;
import com.zdk.paopao.adapter.ImageAdapter;
import com.zdk.paopao.adapter.ImagesAdapter;
import com.zdk.paopao.bean.ImageInfo;
import com.zdk.paopao.gson.Image;
import com.zdk.paopao.util.HttpCallbackListener;
import com.zdk.paopao.util.HttpHelper;
import com.zdk.paopao.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ImageInfo> imageInfos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //标题栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //侧滑菜单
        drawerLayout = findViewById(R.id.drawer_layout);
        //导航栏
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        //让导航按钮显示出来
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        //NavigationView的点击事件
        navView.setCheckedItem(R.id.nav_Call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"FAB clicked",Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Data commit", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "commit success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        imageInfos = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        requestData(); //初始化数据

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        imagesAdapter=new ImagesAdapter(imageInfos);
        recyclerView.setAdapter(imagesAdapter);

        //        ListView lv = findViewById(R.id.lv);
//        ImageAdapter adapter = new ImageAdapter(imageInfos, this);
//        lv.setAdapter(adapter);


    }

    /**
     * 请求数据
     */
    private void requestData() {
//        imageInfos.clear();

        HttpUtil.sendHttpRequest("http://paopao.myncic.com/plugin_1?c=api&a=quanTopicList&clientapp=android&tid=0&area=%E9%81%B5%E4%B9%89%E5%B8%82", new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MainActivity", response);

//                Gson gson = new Gson();
//
//                Image image = gson.fromJson(response, Image.class);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String temData = jsonObject.getString("data");
                            Log.d("MainActivity", temData);
                            JSONArray jsonArray = new JSONArray(temData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                ImageInfo imageInfo = new ImageInfo();
                                imageInfo.setAvatar(jsonObject1.getString("avatar"));
                                Log.d("MainActivity", imageInfo.getAvatar());
                                imageInfo.setUsername(jsonObject1.getString("nickname"));
                                Log.d("MainActivity", "nickname: " + imageInfo.getUsername());
                                imageInfo.setContent(jsonObject1.getString("txt"));
                                Log.d("MainActivity", "txt: " + imageInfo.getContent());

                                imageInfo.setPublishTime(jsonObject1.getString("showdate"));
                                imageInfo.setAgree(jsonObject1.getInt("agree"));
                                Log.d("MainActivity", "agree: " + imageInfo.getAgree());
                                imageInfo.setComment(jsonObject1.getInt("comment"));
                                Log.d("MainActivity", "comment: " + imageInfo.getComment());

                                String ext = jsonObject1.getString("ext");
                                JSONObject jsonObject2 = new JSONObject(ext);

                                JSONArray jsonArray1 = new JSONArray(jsonObject2.getString("image"));
                                JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
                                List<String> imageUrls = new ArrayList<>();
                                //图片内容
                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject4 = jsonArray1.getJSONObject(j);
                                    String imageUrl = jsonObject4.getString("url");
                                    imageUrls.add(imageUrl);
                                }

                                imageInfo.setImageUrls(imageUrls);

                                imageInfo.setImageUrl(jsonObject3.getString("url"));
                                imageInfos.add(imageInfo);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            }

            @Override
            public void onError(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static String http_quanTopicList(String tid) {
        Log.e("tag", "http_quanTopicList");
        String write_data = "clientapp=android"
                + "&tid=" + tid + "&area=遵义市";
        return HttpHelper.get_Http_Post("http://paopao.myncic.com/plugin_1?c=api&a=quanTopicList", write_data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
