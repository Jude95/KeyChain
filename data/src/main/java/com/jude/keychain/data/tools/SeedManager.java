package com.jude.keychain.data.tools;

import android.text.TextUtils;

import com.jude.utils.JUtils;

import java.util.Random;

/**
 * Created by zhuchenxi on 15/11/7.
 */
public class SeedManager {
    private static String mSeed;

    //如果校验码为空。则是第一次启动。清空数据并返回true
    public static boolean isEmpty(){
        if (TextUtils.isEmpty(JUtils.getSharedPreference().getString("checkString", ""))){
            JUtils.getSharedPreference().edit().clear().apply();
            return true;
        }
        return false;
    }

    //用种子去解密校验码。如果解密出来为纯数字。则为正确。
    public static boolean checkSeed(String seed){
        String checkString = JUtils.getSharedPreference().getString("checkString","");
        JUtils.Log("Encrypted Check:"+checkString);
        try {
            checkString = Encryptor.decrypt(seed,checkString);
        } catch (Exception e) {
            e.printStackTrace();
            checkString = "Error";
        }
        JUtils.Log("Decrypted Check:"+checkString);
        if (TextUtils.isDigitsOnly(checkString)){
            mSeed = seed;
            return true;
        }else{
            return false;
        }
    }

    //用种子去加密随机生成的纯数字校验码，并保存
    public static void setSeed(String seed){
        mSeed = seed;
        String mCheckString = Math.abs(new Random().nextInt())+"";
        JUtils.Log("Check:"+mCheckString);
        try {
            mCheckString = Encryptor.encrypt(seed,mCheckString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JUtils.Log("Encrypted Check:"+mCheckString);
        JUtils.getSharedPreference().edit().putString("checkString", mCheckString).apply();
    }

    static String getSeed(){
        return mSeed;
    }
}
