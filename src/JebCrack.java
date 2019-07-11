
import com.pnfsoftware.jeb.rcpclient.Launcher;

import java.security.MessageDigest;

public class JebCrack {
    public static void main(String[] args) throws Exception{
//        String md5 = calcMD5("123");
//        System.out.println(md5);
        Launcher.main(new String[]{"-Xss4M","-Xmx8G","-XstartOnFirstThread","-Dorg.eclipse.swt.internal.carbon.smallFonts"});

    }

    private static String calcMD5(String s) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(s.getBytes("utf-8"));
            char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 15]);
                ret.append(HEX_DIGITS[bytes[i] & 15]);
            }
            return ret.toString().toLowerCase();
        } catch (Exception e) {
            return "abcdefg";
        }
    }
}
