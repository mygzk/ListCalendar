package calendar.gzk.com.simple;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("test","==onRefresh==");
            }
        });
        recyclerView = (LoadMoreRecyclerView) findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            datas.add("pos:" + i);
        }


        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new MyAdapter(this, datas));
       // recyclerView.setAutoLoadMoreEnable(true);


        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("test","==loadmore==");
                recyclerView.setLoadingMore(false);
                recyclerView.notifyMoreFinish(true);
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        Context context;
        List<String> datas;

        public MyAdapter(Context context, List<String> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setGravity(Gravity.CENTER);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 96);
            textView.setLayoutParams(layoutParams);
            return new Holder(textView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.textView.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView textView;

            public Holder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;


            }
        }
    }


}
