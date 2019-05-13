package top.liuliyong.treatmentServer.common.util;

import java.util.regex.Pattern;

/**
 * 进制工具集
 *
 * @Author liyong.liu
 * @Date 2019/4/8
 **/
public class NumberSystemUtil {

    public static final int MONGO_ID_FORMAT = 24;

    /**
     * 判断输入的字符串是不是指定位数的
     *
     * @param Number
     * @param digit
     * @return
     */
    public static boolean checkHex(String Number, Integer digit) {
        String regString = "[a-f0-9A-F]+";
        if (digit > 0) {
            regString = "[a-f0-9A-F]{" + digit + "}";
        }
        if (Pattern.matches(regString, Number)) {
            return true;
        } else return false;
    }
}
