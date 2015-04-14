package com.example.sdcard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Random;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileHelper {
    private Context context; 

    /** SD卡是否存在**/ 

//    private boolean hasSD = false; 

    /** SD卡的路径**/ 

    private String SDPATH; 
    
//    String fileName="data.txt";

    /** 当前程序包的路径**/ 

//    private String FILESPATH; 

 

    public FileHelper(Context context) { 

        this.context = context; 

//        hasSD = Environment.getExternalStorageState().equals( 
//
//                android.os.Environment.MEDIA_MOUNTED); 
 //       SDPATH = "storage/usb0/GM KPI Iphone music 500"; 
          SDPATH = "/data/system/mtp/mtp1/Music/GM KPI Iphone music 500"; 

//        FILESPATH = this.context.getFilesDir().getPath(); 

    } 

    public void process(){
       readSDFiles();
//       deleteSDFile("data.txt");
    }

    /**

     * 在SD卡上创建文件

     * 

     * @throws IOException

     */ 
    

    public File createSDFile(String fileName) throws IOException { 

        File file = new File(SDPATH + "/" + fileName); 

        if (!file.exists()) { 

            file.createNewFile(); 

        } 

        return file; 

    } 

 

    /**

     * 删除SD卡上的文件

     * 

     * @param fileName

     */ 

    public boolean deleteSDFile(String fileName) { 

        File file = new File(SDPATH + "/" + fileName); 

        if (file == null || !file.exists() || file.isDirectory()) 

            return false; 

        return file.delete(); 

    } 

 

    /**

     * 读取SD卡中文本文件

     * 

     * @param fileName

     * @return

     */ 

    public void readSDFiles(){
            FileOutputStream output;
            double startTime = 0.0;
//            try {
//                createSDFile("data.txt");
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
            File file = new File(SDPATH);
            if (!file.isDirectory()) {
                    System.out.println("文件");
                    System.out.println("path=" + file.getPath());
                    System.out.println("absolutepath=" + file.getAbsolutePath());
                    System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                    String[] filelist = file.list();
                    for (int i = 0; i < filelist.length; i++) {
                            if(i%100==0){
                                Log.i("fileProcess", "读取100个文件...");
                                startTime=System.currentTimeMillis();
                                Log.i("fileProcess", "系统时间" + System.currentTimeMillis());
                            }
                            File readfile = new File(SDPATH + "/" + filelist[i]);
                            if (readfile.isDirectory()) {
                                    System.out.println("path=" + readfile.getPath());
                                    System.out.println("absolutepath="
                                                    + readfile.getAbsolutePath());
                                    System.out.println("name=" + readfile.getName());

                            } else {
//                                int l=readAllSDFile(SDPATH + "/" + filelist[i]);
                                  readSDFile(SDPATH + "/" + filelist[i]);
//                                Log.i("fileProcess", "第" + i + "个文件大小为" + l +"个byte");
//                                byte [] buff=new byte[]{};
//                                
//                                try {
//                                    output = new FileOutputStream(SDPATH + "/" + "data.txt");
//                                    buff=s.getBytes();
//                                    
//                                    try {
//                                        output.write(buff, 0, buff.length);
//                                    } catch (IOException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                } catch (FileNotFoundException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
                                
                            }
                            if(i%100==99){
                                Log.i("fileProcess", "读取该100个文件结束");
                                Log.i("fileProcess", "系统时间"+System.currentTimeMillis());
                                Log.i("fileProcess", "读取该100个文件所用时间"+(System.currentTimeMillis()-startTime));
                            }
                    }

            }

    }
    
    public void readSDFile(String filepath) { 


        File file = new File(filepath); 

        try { 

//            FileInputStream fis = new FileInputStream(file); 
            RandomAccessFile randomFile = new RandomAccessFile(file, "r");
            long fileLength=randomFile.length();

            byte[] bs = new byte[2000];  
            randomFile.read(bs); //得到内容 
            int byteOffset=(int)(fileLength/5);
            for (int i = 1; i < 5; i++) {
//                fis.read(sbt, byteOffset, 2000);
//                sb.append(sbt);
//                randomFile.seek(byteOffset);
                randomFile.skipBytes(byteOffset);
                randomFile.read(bs); //得到内容 
            }

            randomFile.close(); 

        } catch (FileNotFoundException e) { 

            e.printStackTrace(); 

        } catch (IOException e) { 

            e.printStackTrace(); 

        } 


    } 

    public int readAllSDFile(String filepath) { 
        
        File file = new File(filepath);
        InputStream in = null;
        int length=0;
//        try {
////            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
//            // 一次读一个字节
//            in = new FileInputStream(file);
//            int tempbyte;
//            while ((tempbyte = in.read()) != -1) {
//                System.out.write(tempbyte);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
        try {
//            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[2000];
           
            int byteread = 0;
            in = new FileInputStream(file);
//            ReadFromFile.showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                length+=byteread;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return length;
    } 

}
