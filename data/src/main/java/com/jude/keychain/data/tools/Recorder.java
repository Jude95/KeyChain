package com.jude.keychain.data.tools;

import android.support.annotation.Nullable;

import com.jude.keychain.domain.entities.KeyEntity;
import com.jude.utils.JUtils;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/4.
 */
public class Recorder {

    @Nullable
    public static List<KeyEntity> read() {
        String resource = JUtils.getSharedPreference().getString("data", "");
        try {
            return Encryptor.from(resource,SeedManager.getSeed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(List<KeyEntity> data) {
        String content = null;
        try {
            content = Encryptor.to(data, SeedManager.getSeed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JUtils.getSharedPreference().edit().putString("data",content).apply();
    }

}
