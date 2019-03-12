package com.rarcher.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rarcher.Adapter.Events;
import com.rarcher.Adapter.Events_Adapter;
import com.rarcher.Adapter.RecyAdapter;
import com.rarcher.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.rarcher.Figure.FingerprintUtil.context;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements RecyAdapter.OnItemClickListener {


    CardView news,life,yangsheng,zhengce,quwei;
    TextView newstv,lifetv,yangshengtv,zhengcetv,quweitv;
    ImageView newsiv,lifeiv,yangshengiv,zhengceiv,quweiiv;
    ListView small_lv;
    private List<Events> eventsList = new ArrayList<>();
    Events_Adapter adapter;
    RecyclerView recyclerview;
    private Integer[] mImgIds = {R.drawable.html, R.drawable.you_define_me, R.drawable.html, R.drawable.you_define_me, R.drawable.html, R.drawable.you_define_me, R.drawable.html, R.drawable.you_define_me};
    private List<Integer> datas = new ArrayList<>();
    private RecyAdapter recyAdapter;
    private Handler mHandler = new Handler();
    private LinearLayoutManager layoutManager;
    private int oldItem = 0;


    public Home() {
        // Required empty public constructor
    }

    private void init(View view){
        news = view.findViewById(R.id.news);
        life = view.findViewById(R.id.shenghuo);
        yangsheng = view.findViewById(R.id.yangsheng);
        zhengce= view.findViewById(R.id.zhengce);
        quwei = view.findViewById(R.id.quwei);
        recyclerview = view.findViewById(R.id.recycler);
        small_lv = view.findViewById(R.id.small_lv);

        newstv = news.findViewById(R.id.the_name);
        lifetv = life.findViewById(R.id.the_name);
        yangshengtv = yangsheng.findViewById(R.id.the_name);
        zhengcetv = zhengce.findViewById(R.id.the_name);
        quweitv = quwei.findViewById(R.id.the_name);

        newsiv = news.findViewById(R.id.the_image);
        lifeiv = life.findViewById(R.id.the_image);
        yangshengiv = yangsheng.findViewById(R.id.the_image);
        zhengceiv = zhengce.findViewById(R.id.the_image);
        quweiiv = quwei.findViewById(R.id.the_image);


        newstv.setText("新闻");
        lifetv.setText("生活");
        yangshengtv.setText("养生");
        zhengcetv.setText("政策");
        quweitv.setText("趣味");

        newsiv.setImageResource(R.drawable.figure);
        lifeiv.setImageResource(R.drawable.figure);
        yangshengiv.setImageResource(R.drawable.figure);
        zhengceiv.setImageResource(R.drawable.figure);
        quweiiv.setImageResource(R.drawable.figure);

        init_events();
        adapter = new Events_Adapter(getContext(),R.layout.events_items,eventsList);
        small_lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(small_lv);
    }
    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
    private void init_events(){
        for (int i= 0;i<10;i++){
            Events events = new Events("华北电力大学",R.drawable.ic_avatar_cat);
            eventsList.add(events);
            Events events1 = new Events("计算机系",R.drawable.ic_avatar_bear);
            eventsList.add(events1);
            Events events2 = new Events("社工",R.drawable.ic_avatar_monkey);
            eventsList.add(events2);
            Events events3 = new Events("保定校区",R.drawable.ic_avatar_panda);
            eventsList.add(events3);
            Events events4 = new Events("北京校区",R.drawable.ic_avatar_pig);
            eventsList.add(events4);
            Events events5= new Events("华北电力高中",R.drawable.ic_avatar_rhino);
            eventsList.add(events5);
            Events events6= new Events("华北Gay力高中",R.drawable.ic_avatar_raccoon);
            eventsList.add(events6);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  img = view.findViewById(R.id.img);
        hideInputManager(getContext(),view);

        init(view);
        initData();
        initRecy();
      //  img.setImageResource(datas.get(0));
        recyAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(scrollRunnable, 10);
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(scrollRunnable);
    }
    /**
     * 隐藏输入软键盘
     * @param context
     * @param view
     */
    public static void hideInputManager(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view !=null && imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏
        }
    }
    @Override
    public void onItemClick(View view, int tag) {
        Toast.makeText(getContext(), "第" + tag + "张图片被点击了", Toast.LENGTH_SHORT).show();
    }

    Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            recyclerview.scrollBy(3, 0); //      int firstItem = layoutManager.findFirstVisibleItemPosition();
            int firstItem = layoutManager.findFirstVisibleItemPosition();
            if (firstItem != oldItem && firstItem > 0) {
                oldItem = firstItem;
               // img.setImageResource(datas.get(oldItem % datas.size()));
            }
            Log.e(TAG, "run: firstItem:" + firstItem);
            mHandler.postDelayed(scrollRunnable, 50);
        }
    };

    private void initRecy() {
        recyAdapter = new RecyAdapter(getContext(), datas);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyAdapter);
    }

    private void initData() {
        for (int i = 0; i < mImgIds.length; i++) {
            datas.add(mImgIds[i]);
        }
    }

}
