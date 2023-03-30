package club.fuwenhao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fuwenhao
 * @createDate 2023/3/24 10:51
 * @descripton 邮箱校验工具类
 */
public class EmailUtil {
    /**
     * @author fuwenhao
     * @createDate 2023/3/24 10:51
     * @descripton 校验JD邮箱
     */
    public static boolean isValidEmail(String email) {
//        String regex = "^[a-zA-Z0-9._%+-]+@jd.com$";
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
