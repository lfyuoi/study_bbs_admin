package com.bbs.cloud.admin.activity.result;

import com.bbs.cloud.admin.activity.result.vo.ActivityVO;

import java.util.List;
import java.util.Map;

public class ActivityPageResult {

    private List<ActivityVO> data;

    private Map<Integer,String> statusMap;

    private Map<Integer,String> typeMap;

    public List<ActivityVO> getData() {
        return data;
    }

    public void setData(List<ActivityVO> data) {
        this.data = data;
    }

    public Map<Integer, String> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<Integer, String> statusMap) {
        this.statusMap = statusMap;
    }

    public Map<Integer, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<Integer, String> typeMap) {
        this.typeMap = typeMap;
    }
}
