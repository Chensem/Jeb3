import com.pnfsoftware.jebglobal.Ct;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import com.pnfsoftware.jebglobal.qZ;
public class PathJar {
    /*
    * 使用方法：
    *   将jeb bin目录下的jeb.jar放在src上一级目录
    *   运行项目
    *   由于javasist项目不知道如何修改静态块，还有一个buildtype字段没修改
    *   通过直接修改字节码达到我们的目的src中的class文件是修改好的
    *
    * */
    public static boolean DEBUG = true;

    public static void main(String[] args) throws Exception{
        patchClass();
    }
    private static void patchClass() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        //load jar file
        pool.insertClassPath("./jeb.jar");

        //patch network update
        String network_ping = "com.pnfsoftware.jeb.client.AbstractClientContext";
        CtClass network_ping_class = pool.getCtClass(network_ping);
        CtMethod network_ping_method = network_ping_class.getDeclaredMethod("ping"); // no overload methods
        network_ping_method.setBody("{return 0;}");
        network_ping_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(network_ping);


        //patch file hash check (JDK loadClass check) delete the RSA in jar
        /*String file_check = "sun.security.util.ManifestEntryVerifier";
        CtClass file_check_class = pool.getCtClass(file_check);
        CtMethod verify_method = file_check_class.getDeclaredMethod("verify");
        verify_method.setBody("{return this.signers;}");
        file_check_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(file_check);
        */

        //patch wasm decompiler limit
        String wasm_ast_limit = "com.pnfsoftware.jebglobal.kA";
        CtClass wasm_ast_class = pool.getCtClass(wasm_ast_limit);
        CtConstructor wasm_ast_cons = wasm_ast_class.getDeclaredConstructor(new CtClass[]
        {
            pool.get(qZ.class.getName()),
            pool.get(String.class.getName()),
            pool.get(byte[].class.getName())

        });
        wasm_ast_cons.insertAfter("this.fY=false;");
        wasm_ast_class.writeFile("./");
        writeIntoJar(wasm_ast_limit);

        // patch integrity_failed check
        String integrity_check = "com.pnfsoftware.jeb.client.AbstractContext";
        CtClass integrity_check_class = pool.getCtClass(integrity_check);
        CtConstructor[] integrity_check_cons = integrity_check_class.getDeclaredConstructors();
        integrity_check_cons[0].insertAfter("this.integrity_failed=false;");
        integrity_check_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(integrity_check);

        // patch buildtype  don't know how to modify static block

        String licensing = "com.pnfsoftware.jeb.client.Licensing";
        CtClass licensing_class = pool.get(licensing);
//        CtConstructor[] licensing_cons = licensing_class.getDeclaredConstructors();
//        licensing_cons[0].insertAfter("build_type=254;");
        CtMethod getExpireStamp = licensing_class.getDeclaredMethod("getExpirationTimestamp");
        getExpireStamp.setBody("{return 1915146549;}");
        licensing_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(licensing);

        // costum config
        /*
        user_name = var2.np();
        user_group = var2.fY();
        user_email = var2.TG();
        user_id = var2.EJ();
        license_id = var2.iW();
        user_count = var2.IW();
        license_ts = var2.vH();
        license_validity = var2.fD();
        build_type = var3;
        buildkey = var2.bs();
        real_license_ts = license_ts;
        */
        String config = "com.pnfsoftware.jebglobal.gi";
        CtClass config_class = pool.get(config);
        CtMethod username_method = config_class.getDeclaredMethod("np",null);
        CtMethod user_group_method = config_class.getDeclaredMethod("fY");
        CtMethod user_email_method = config_class.getDeclaredMethod("TG");
        CtMethod user_count_method = config_class.getDeclaredMethod("IW");
        CtMethod license_validity_method = config_class.getDeclaredMethod("fD");
        CtMethod buildkey_method = config_class.getDeclaredMethod("bs");

        username_method.setBody("{return \"Chensem\";}");
        user_group_method.setBody("{return \"KanXue Cracker\";}");
        user_email_method.setBody("{return \"0xdeadbeef@cc.com\";}");
        user_count_method.setBody("{return 1;}");
        license_validity_method.setBody("{return 999;}");
        buildkey_method.setBody("{return \"JEB Pro\";}");

        config_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(config);

        // patch register
        CtClass checkLicense_class = pool.get("com.pnfsoftware.jeb.client.AbstractClientContext");//class is as the same as network_ping_class
        if(checkLicense_class.isFrozen()){ checkLicense_class.defrost(); }
        CtMethod check_li_method = checkLicense_class.getDeclaredMethod("prepareCheckLicenseKey");
        check_li_method.setBody("{return;}");
        checkLicense_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(network_ping);

        //patch windows pop
//        String pop_windows = "com.pnfsoftware.jeb.rcpclient.RcpClientContext";
//        CtClass pop_windows_class = pool.get(pop_windows);
//        CtMethod pop_windows_method = pop_windows_class.getDeclaredMethod("displayDemoInformation");
//        pop_windows_method.setBody("{return;}");
//        pop_windows_class.writeFile("./");
//
//        if(DEBUG)
//            writeIntoJar(pop_windows);

        /* patch debug apk

         */
        String debug_apk = "com.pnfsoftware.jeb.corei.parsers.apk.Zj";
        CtClass debug_apk_class = pool.get(debug_apk);
        CtMethod debug_apk_method = debug_apk_class.getDeclaredMethod("isDebuggable");
        debug_apk_method.setBody("{return true;}");
        debug_apk_class.writeFile("./");
        if(DEBUG)
            writeIntoJar(debug_apk);

        System.out.println("[!] Patch OK");
    }

    private static int writeIntoJar(String classpath) throws Exception{
        classpath = classpath.replace(".","/") + ".class";
        Process process = Runtime.getRuntime().exec("jar uvf " + "jeb.jar " + classpath);
        int status = process.waitFor();
        return status;
    }

}
