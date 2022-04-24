package demo.docker.temp.util.file;

import demo.docker.temp.util.sytem.SystemCommandBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * lihui
 * 2022/4/23
 * FileUtils
 *
 * @description
 */
public class FileUtils {

    public static void copyDir(String source, String target) {
        SystemCommandBuilder.CommandExecutor executor;
        if (isWindows()) {
            executor = SystemCommandBuilder.winCommandE();
        } else {
            executor = SystemCommandBuilder.linuxCommandE();
        }
        executor.copyDir();
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("win");
    }
}
