package com.szy.demos.ui.activity.mvp.utils;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class DataUtils {
    private static DataUtils dataUtils;

    private DataUtils() {
    }

    public static DataUtils newInstance() {
        if (dataUtils == null) {
            synchronized (DataUtils.class) {
                if (dataUtils == null) {
                    dataUtils = new DataUtils();
                }
            }
        }
        return dataUtils;
    }

    /*
    * 把数据1添加到数据2中
    * */
    public void addData(List<PoiInfo> allPoi, List<PoiInfo> datas) {
        boolean isHave;
        for (int i = 0; i < allPoi.size(); i++) {
            PoiInfo info = allPoi.get(i);
            isHave = false;
            for (int j = 0; j < datas.size(); j++) {
                PoiInfo poiInfo = datas.get(j);
                if (info.name.equals(poiInfo.name) && info.address.equals(poiInfo.address)) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                datas.add(info);
            }
        }
    }
}
