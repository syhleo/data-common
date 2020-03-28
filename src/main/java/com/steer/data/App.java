package com.steer.data;

import com.steer.data.common.utils.DataAnal;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        String s = "的萨芬打赏aaa";
        System.out.println("62  DR3E1220190316094705DR3E0174        881276T2101         1" + DataAnal.hexStringToString("0A"));
        System.out.println(("62  DR3E1220190316094705DR3E0174        881276T2101         1" + DataAnal.hexStringToString("0A")).length());
        System.out.println("41  999999201903231111003EDR9   " + DataAnal.hexStringToString("0A"));
        System.out.println(("41  999999201903231111003EDR9   " + DataAnal.hexStringToString("0A")).length());

        System.out.println(new App().hashCode());
        System.out.println(new App().hashCode());

    }
}
