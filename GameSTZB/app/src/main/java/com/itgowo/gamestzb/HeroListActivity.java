package com.itgowo.gamestzb;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.HeroDetailEntity;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.itgowolib.itgowoNetTool;

import java.util.List;

public class HeroListActivity extends BaseActivity {
    private RecyclerView heroListLV;
    private List<HeroEntity> list;
    public static void go(Context context){
        context.startActivity(new Intent(context,HeroListActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_list);
        initView();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        heroListLV.setLayoutManager(layoutManager);
        heroListLV.setAdapter(new HeroListAdapter());
        NetManager.getHeroList(new itgowoNetTool.onReceviceDataListener<BaseResponse<List<HeroEntity>>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<List<HeroEntity>> result) {
                if (result!=null&&result.isSuccess()){
                    list=result.getData();
                    heroListLV.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void initView() {
        heroListLV = findViewById(R.id.herolist_lv);
    }

    class HeroListAdapter extends RecyclerView.Adapter<HeroViewHolder> {

        @NonNull
        @Override
        public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HeroViewHolder(new ImageView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
            ImageView imageView = (ImageView) holder.itemView;
            int width=context.getResources().getDisplayMetrics().widthPixels/6;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width,width*4/3));
            HeroEntity entity = list.get(position);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                switch (entity.getQuality()) {
//                    case 1:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_1));
//                        break;
//                    case 2:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_2));
//                        break;
//                    case 3:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_3));
//                        break;
//                    case 4:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_4));
//                        break;
//                    case 5:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_5));
//                        break;
//                    default:
//                        imageView.setForeground(getDrawable(R.drawable.hero_mask_5));
//                        break;
//                }
//            }
            STZBManager.bindView(entity.getId(), imageView);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {
        public HeroViewHolder(View itemView) {
            super(itemView);

        }
    }
}
