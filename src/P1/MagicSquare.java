package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;


public class MagicSquare {

    public static boolean generateMagicSquare(int n) {
        if (n < 0) {
            System.out.println("错误：n为负数");
            return false;
        }

        if (n % 2 == 0) {
            System.out.println("错误：n为偶数");
            return false;
        }

        int[][] magic = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }


        FileOutputStream fileOutputStream;
        File file = new File("src/P1/txt/6.txt");
        try {
            fileOutputStream = new FileOutputStream(file);

            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    String s = magic[i][j] + "\t";
                    fileOutputStream.write(s.getBytes());
                }
                fileOutputStream.write("\n".getBytes());
            }

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    public static boolean isLegalMagicSquare(String filename) {

        ArrayList<String> readList = new ArrayList<>();
        try {
            File file = new File(filename);

            if (file.isFile() && file.exists()) { //判断文件是否存在

                InputStreamReader read = new InputStreamReader(

                        Files.newInputStream(file.toPath()), "GBK");

                BufferedReader bufferedReader = new BufferedReader(read);

                String lineTxt;


                while ((lineTxt = bufferedReader.readLine()) != null) {
                    readList.add(lineTxt);
                }


                read.close();


            } else {

                System.out.println("找不到指定的文件");

            }

        } catch (Exception e) {

            System.out.println("读取文件内容出错");

            e.printStackTrace();

        }


        int len = 0;
        ArrayList<Integer> len1 = new ArrayList<>();

        int temp1;
        int temp2;

        String s = readList.get(0);


        for (String r : readList) {
            for (int j = 0; j < r.length(); j++) {
                if (r.charAt(j) != '\t' && (r.charAt(j) < 48 || r.charAt(j) > 57)) {
                    System.out.println("含有非法字符");
                    return false;
                }
            }
        }

        for (int i = 0; i < readList.size(); i++) {
            len++;
        }
        for (int i = 0; i < len; i++) {
            len1.add(0);
            String ss = readList.get(i);
            String[] sss = ss.split("\t");
            len1.set(i, sss.length);
        }


        for (int i = 0; i < len; i++) {
            if (!Objects.equals(len1.get(i), len1.get(0)) || len1.get(i) != len) {
                System.out.println("不是正方形");
                return false;
            }
        }

        if(len == 1){
            return true;
        }

        for (int i = 1; i < len; i++) {
            s = s.concat("\t");
            s = s.concat(readList.get(i));
        }


        ArrayList<String> numbersString = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> add = new ArrayList<>();

        Collections.addAll(numbersString, s.split("\t+"));


        try {
            for (int i = 0; i < len * len; i++) {
                numbers.add(Integer.parseInt(numbersString.get(i)));
            }

        } catch (Exception e) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            add.add(0);
            for (int j = 0; j < len; j++) {
                add.set(i, add.get(i) + numbers.get(j));
            }
        }


        for (int i = 1; i < len; i++) {
            if (!add.get(i).equals(add.get(0))) {
                System.out.println("不是幻方");
                return false;
            }
        }
        temp1 = add.get(0);


        for (int i = 0; i < len; i++) {
            add.set(i, 0);
            for (int j = 0; j < len; j++) {
                add.set(i, add.get(i) + numbers.get(j * len + i));
            }
        }


        for (int i = 1; i < len; i++) {
            if (!add.get(i).equals(add.get(0))) {
                System.out.println("不是幻方");
                return false;
            }
        }
        temp2 = add.get(0);

        if (temp1 != temp2) {
            System.out.println("不是幻方");
            return false;
        }

        add.set(0, 0);
        add.set(1, 0);
        for (int i = 0; i < len; i++) {
            add.set(0, add.get(0) + numbers.get(len * i + i));
            add.set(1, add.get(1) + numbers.get(len * (len - 1 - i) + i));
        }

        if (add.get(0) != temp1 || add.get(1) != temp1) {
            System.out.println("不是幻方");
            return false;
        }


        return true;

    }


    public static void main(String[] args) {

        int n = 0;
        try {
            System.out.print("请输入n的值:");
            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("错误：输入的n不是整数");
        }


        System.out.println("1.txt:");
        System.out.println(isLegalMagicSquare("src/P1/txt/1.txt"));
        System.out.println();

        System.out.println("2.txt:");
        System.out.println(isLegalMagicSquare("src/P1/txt/2.txt"));
        System.out.println();

        System.out.println("3.txt:");
        System.out.println(isLegalMagicSquare("src/P1/txt/3.txt"));
        System.out.println();

        System.out.println("4.txt:");
        System.out.println(isLegalMagicSquare("src/P1/txt/4.txt"));
        System.out.println();

        System.out.println("5.txt:");
        System.out.println(isLegalMagicSquare("src/P1/txt/5.txt"));
        System.out.println();

        if(generateMagicSquare(n)) {
            System.out.println("6.txt:");
            System.out.println(isLegalMagicSquare("src/P1/txt/6.txt"));
            System.out.println();
        }
    }
}
