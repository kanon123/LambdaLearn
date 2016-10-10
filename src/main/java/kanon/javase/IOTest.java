package kanon.javase;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.io.*;
import java.util.function.Predicate;

/**
 * Created by kanon on 2016/9/25.
 */
public class IOTest {
    public static void main(String[] args) {
        new IOTest();
    }

    public IOTest() {
        init();
    }

    public void init() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        try {
            while ((str = br.readLine()) != null) {
                if ("exit".equals(str)) {
                    System.out.println("退出");
                    break;
                }
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File------------------------------");

        try {
            File file = new File("F:/./file.txt");
            System.out.println(file.getName());
            System.out.println(file.getAbsoluteFile());
            System.out.println(file.getPath());
            System.out.println(file.getParent());
            System.out.println(file.getCanonicalPath());
            System.out.println(file.getFreeSpace());
            System.out.println(file.getUsableSpace());
            System.out.println(file.isAbsolute());
            System.out.println(file.isDirectory());
            System.out.println(file.isFile());
            System.out.println(file.exists());
            file.renameTo(new File("f:/file1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("printStream------------------------------");
        try {
            PrintStream printStream = new PrintStream(new BufferedOutputStream(new FileOutputStream("e:" + File.separator + "system.txt")));
            PrintStream temp = System.out;
            System.setOut(printStream);
            System.out.println("aaaa");
            System.out.println("bbbb");
            printStream.close();
            System.setOut(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("File------------------------------");
        try {
            File file = new File("E:\\Test\\java\\file");
            if (file.isDirectory() == true) {
                String[] files = file.list();
                for (String temp : files) {
                    System.out.println(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("FilenameFilter------------------------------");
        FilenameFilter filenameFilter = (file, name) -> {
            if (name.endsWith("txt")) {
                return true;
            } else {
                return false;
            }
        };
        try {
            File file = new File("E:\\Test\\java\\file");
            if (file.isDirectory() == true) {
                String[] files = file.list(filenameFilter);
                for (String temp : files) {
                    System.out.println(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("FileFilter------------------------------");
        FileFilter fileFilter = (file)->{
            if(file.isDirectory())
                return true;
            else
                return false;
        };
        try {
            File file = new File("E:\\Test\\java\\file");
            if (file.isDirectory() == true) {
               File[] files = file.listFiles(fileFilter);
                for (File temp : files) {
                    System.out.println(temp.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }







    }
}
