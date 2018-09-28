package com.example.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by T011689 on 2018/9/27.
 */
public class ScriptUtil {
    /**
     * return lua script String
     *
     * @param path
     * @return
     */
    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();
        try(InputStream stream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream))
        ) {

            String str = "";
            while ((str = br.readLine()) != null) {
                sb.append(str).append(System.lineSeparator());
            }

        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
        return sb.toString();
    }
    public static void main(String[] args){
        System.out.println(getScript("luaScripts/idGenerator.lua"));
    }
}
