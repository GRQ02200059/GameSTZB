package com.itgowo.gamestzb.Main;

import android.content.Context;

import com.itgowo.gamestzb.Base.BasePresenter;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.itgowolib.itgowoNetTool;
import com.itgowo.views.SuperDialog;

import java.io.File;
import java.util.List;

public class MainPresenter extends BasePresenter{
    private onMainActivityActionListener actionListener;

    public MainPresenter(Context context, onMainActivityActionListener actionListener) {
        super(context);
        this.actionListener = actionListener;
    }

    public void CheckAndInitHeroListData(){
        NetManager.getHeroListAndDown(new itgowoNetTool.onReceviceDataListener<BaseResponse<List<HeroEntity>>>() {
            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<List<HeroEntity>> result) {
                if (result != null && result.isSuccess() && result.getData() != null) {
                    STZBManager.deleteHeroImage(result.getData());
                    File file = context.getDir("hero", Context.MODE_PRIVATE);
                    int num = 0;
                    if (!file.exists()) {
                        num = result.getData().size();
                    } else {
                        num = result.getData().size() - file.listFiles().length;
                    }
                    if (num > 0) {
                        int finalNum = num;
                        SuperDialog dialog = new SuperDialog(context).setContent("共" + result.getData().size() + "名武将数据，有" + num + "名武将数据缺失，需要更新， 如果您不是使用wifi上网，下载可能消耗您的流量，请点击确定下载更新，点击其他区域或者返回键取消").setListener(new SuperDialog.onDialogClickListener() {
                            @Override
                            public void click(boolean isButtonClick, int position) {
                                STZBManager.showWaitDialog(context,"正在同步武将数据","");
                                STZBManager.downHeroImage(result.getData(), finalNum);
                            }
                        });
                        dialog.show();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    public interface onMainActivityActionListener extends onActionListener{

    }
}
