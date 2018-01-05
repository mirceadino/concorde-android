package com.ubb.mirko.concorde.dao;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mirko on 22/12/2017.
 */

public class PriceConverter {
    @TypeConverter
    public static List<Integer> toList(String str) {
        if (str == null) {
            return new ArrayList<>();
        }

        List<String> listAsString;
        listAsString = Arrays.asList(str.split(" "));

        List<Integer> list = new ArrayList<>();
        for (String s : listAsString) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    @TypeConverter
    public static String toString(List<Integer> list) {
        if (list == null || list.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i)).append(" ");
        }
        sb.append(list.get(list.size() - 1));

        return sb.toString();
    }
}
