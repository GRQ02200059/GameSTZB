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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.HeroEntityWithAttr;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.itgowolib.itgowoNetTool;
import com.itgowo.views.SuperDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeroListActivity extends BaseActivity {
    private RecyclerView heroListLV;
    private List<HeroEntityWithAttr> currentList = new ArrayList<>();
    private List<HeroEntityWithAttr> srcList;
    private TextView sortRuleTv;
    private TextView FilterRuleTv;
    private String[] sortRuleListItem = {"默认", "数量", "稀有度", "攻击距离", "Cost", "初始攻击", "初始攻城", "初始谋略", "初始防御", "初始速度", "攻击成长", "攻城成长", "谋略成长", "防御成长", "速度成长"};
    private String[] filterRuleListItem = {"全部", "已收集", "未收集"};
    private int sortRulePosition = 0;

    public static void go(Context context) {
        context.startActivity(new Intent(context, HeroListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_list);
        initView();
        initListener();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        heroListLV.setLayoutManager(layoutManager);
        heroListLV.setAdapter(new HeroListAdapter());
        STZBManager.showWaitDialog(this, null, "正在请求数据");
        NetManager.getHeroListWithUserAndAttr(new itgowoNetTool.onReceviceDataListener<BaseResponse<List<HeroEntityWithAttr>>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<List<HeroEntityWithAttr>> result) {
                if (result != null && result.isSuccess()) {
                    BaseConfig.putUserData(BaseConfig.USER_HEROLIST, JSON.toJSONString(result.getData()));
                    currentList.clear();
                    currentList.addAll(result.getData());
                    srcList = result.getData();
                    heroListLV.getAdapter().notifyDataSetChanged();
                    STZBManager.hideWaitDialog();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                STZBManager.hideWaitDialog();
                throwable.printStackTrace();
            }
        });
    }

    private void doSortRule(int position) {
        currentList.clear();
        sortRulePosition = position;
        sortRuleTv.setText("排序规则：" + sortRuleListItem[position]);
        FilterRuleTv.setText("过滤规则：" + filterRuleListItem[0]);
        currentList.addAll(srcList);
        switch (position) {
            case 0:
                currentList.addAll(srcList);
                break;
            case 1://数量
                Collections.sort(currentList, (o1, o2) -> o2.getUserCount() - o1.getUserCount());
                break;
            case 2://稀有度
                Collections.sort(currentList, (o1, o2) -> o2.getQuality() - o1.getQuality());
                break;
            case 3://攻击距离
                Collections.sort(currentList, (o1, o2) -> o2.getDistance() - o1.getDistance());
                break;
            case 4://Cost
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getCost() * 1000 - o1.getCost() * 1000));
                break;
            case 5://初始攻击
                Collections.sort(currentList, (o1, o2) -> (o2.getAttack() - o1.getAttack()));
                break;
            case 6://初始攻城
                Collections.sort(currentList, (o1, o2) -> (o2.getSiege() - o1.getSiege()));
                break;
            case 7://初始谋略
                Collections.sort(currentList, (o1, o2) -> (o2.getRuse() - o1.getRuse()));
                break;
            case 8://初始防御
                Collections.sort(currentList, (o1, o2) -> (o2.getDef() - o1.getDef()));
                break;
            case 9://初始速度
                Collections.sort(currentList, (o1, o2) -> (o2.getSpeed() - o1.getSpeed()));
                break;
            case 10://攻击成长
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getAttGrow() * 1000 - o1.getAttGrow() * 1000));
                break;
            case 11://攻城成长
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getSiegeGrow() * 1000 - o1.getSiegeGrow() * 1000));
                break;
            case 12://谋略成长
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getRuseGrow() * 1000 - o1.getRuseGrow() * 1000));
                break;
            case 13://防御成长
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getDefGrow() * 1000 - o1.getDefGrow() * 1000));
                break;
            case 14://速度成长
                Collections.sort(currentList, (o1, o2) -> (int) (o2.getSpeedGrow() * 1000 - o1.getSpeedGrow() * 1000));
                break;
            default:
                currentList.addAll(srcList);
        }
        heroListLV.getAdapter().notifyDataSetChanged();
    }

    private void doFilterRule(int position) {
        currentList.clear();
        FilterRuleTv.setText("过滤规则：" + filterRuleListItem[position]);
        sortRuleTv.setText("排序规则：" + sortRuleListItem[0]);
        sortRulePosition = 0;
        switch (position) {
            case 0:
                currentList.addAll(srcList);
                break;
            case 1:
                for (int i = 0; i < srcList.size(); i++) {
                    if (srcList.get(i).getUserCount() > 0) {
                        currentList.add(srcList.get(i));
                    }
                }
                break;
            case 2:
                for (int i = 0; i < srcList.size(); i++) {
                    if (srcList.get(i).getUserCount() == 0) {
                        currentList.add(srcList.get(i));
                    }
                }
                break;
            default:
                currentList.addAll(srcList);
        }
        heroListLV.getAdapter().notifyDataSetChanged();
    }

    private void initListener() {
        sortRuleTv.setOnClickListener((View v) -> {
            SuperDialog superDialog = new SuperDialog(context);
            List<SuperDialog.DialogMenuItem> dialogMenuItems = new ArrayList<>();
            for (int i = 0; i < sortRuleListItem.length; i++) {
                dialogMenuItems.add(new SuperDialog.DialogMenuItem(sortRuleListItem[i], 0));
            }
            superDialog.setDialogMenuItemList(dialogMenuItems).setContent("选择排序规则").setListener((boolean isButtonClick, int position) -> {
                doSortRule(position);
            }).show();
        });
        FilterRuleTv.setOnClickListener(v -> {
            SuperDialog superDialog = new SuperDialog(context);
            List<SuperDialog.DialogMenuItem> dialogMenuItems = new ArrayList<>();
            for (int i = 0; i < filterRuleListItem.length; i++) {
                dialogMenuItems.add(new SuperDialog.DialogMenuItem(filterRuleListItem[i], 0));
            }
            superDialog.setDialogMenuItemList(dialogMenuItems).setContent("选择过滤规则").setListener((isButtonClick, position) -> {
                doFilterRule(position);
            }).show();
        });
    }

    private void initView() {
        heroListLV = findViewById(R.id.herolist_lv);
        sortRuleTv = findViewById(R.id.herolist_sortrule_tv);
        FilterRuleTv = findViewById(R.id.herolist_filterrule_tv);
    }

    class HeroListAdapter extends RecyclerView.Adapter<HeroViewHolder> {

        @NonNull
        @Override
        public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.list_herolist_item, null);
            return new HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
            HeroEntityWithAttr entity = currentList.get(position);
            holder.name.setText(entity.getName());
            int width = context.getResources().getDisplayMetrics().widthPixels / 6;
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width, width * 4 / 3));
            if (sortRulePosition == 0 && entity.getUserCount() <= 0) {
                holder.img.setImageResource(R.drawable.hero_1000);
            } else {
                STZBManager.bindView(entity.getId(), holder.img);
            }
            holder.num.setText(showMsg(entity));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                switch (entity.getQuality()) {
                    case 1:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_1));
                        break;
                    case 2:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_2));
                        break;
                    case 3:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_3));
                        break;
                    case 4:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_4));
                        break;
                    case 5:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_5));
                        break;
                    default:
                        holder.img.setForeground(getDrawable(R.drawable.hero_mask_5));
                        break;
                }
            }
        }

        private String showMsg(HeroEntityWithAttr entity) {
            switch (sortRulePosition) {
                case 0:
                case 1:
                    if (entity.getUserCount() <= 0) {
                        return "未收集";
                    } else {
                        return "已有 " + String.valueOf(entity.getUserCount());
                    }
                case 2://稀有度
                    return "稀有度 " + String.valueOf(entity.getQuality());
                case 3://攻击距离
                    return "攻击距离 " + String.valueOf(entity.getDistance());
                case 4://Cost
                    return "Cost " + String.valueOf(entity.getCost());
                case 5://初始攻击
                    return "初始攻击 " + String.valueOf(entity.getAttack());
                case 6://初始攻城
                    return "初始攻城 " + String.valueOf(entity.getSiege());
                case 7://初始谋略
                    return "初始谋略 " + String.valueOf(entity.getRuse());
                case 8://初始防御
                    return "初始防御 " + String.valueOf(entity.getDef());
                case 9://初始速度
                    return "初始速度 " + String.valueOf(entity.getSpeed());
                case 10://攻击成长
                    return "攻击成长 " + String.valueOf(entity.getAttGrow());
                case 11://攻城成长
                    return "攻城成长 " + String.valueOf(entity.getSiegeGrow());
                case 12://谋略成长
                    return "谋略成长 " + String.valueOf(entity.getRuseGrow());
                case 13://防御成长
                    return "防御成长 " + String.valueOf(entity.getDefGrow());
                case 14://速度成长
                    return "速度成长 " + String.valueOf(entity.getSpeedGrow());
                default:
                    if (entity.getUserCount() <= 0) {
                        return "未收集";
                    } else {
                        return "已有 " + String.valueOf(entity.getUserCount());
                    }
            }
        }

        @Override
        public int getItemCount() {
            return currentList == null ? 0 : currentList.size();
        }
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView num;
        private ImageView img;

        public HeroViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            num = itemView.findViewById(R.id.num);
            img = itemView.findViewById(R.id.img);
        }
    }
}
