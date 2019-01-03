package com.yuanxueyuan.customedailog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanxueyuan on 2018/11/28.
 */

public class Util {

    private static Map<String, String> loginMap = null;
    public static Map<String, String> getLoginMap() {
        if (loginMap == null) {
            loginMap = new HashMap<String, String>();// 登录用户信息
            for (int i = 0; i<10; i++) {
                loginMap.put("w"+i,"e"+i);
            }
        }
        return loginMap;
    }
}
