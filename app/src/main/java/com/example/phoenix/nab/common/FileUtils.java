package com.example.phoenix.nab.common;

/**
 * Created by Phoenix on 3/14/17.
 */

public class FileUtils {
//    private static String writeResponseBodyToDisk(ResponseBody body, String outputFilePath) throws IOException {
//        int count;
//        byte data[] = new byte[1024 * 4];
//        long fileSize = body.contentLength();
//        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
//        File outputFile = new File(outputFilePath);
//        OutputStream output = new FileOutputStream(outputFile);
//        long total = 0;
//        long startTime = System.currentTimeMillis();
//        int timeCount = 1;
//        while ((count = bis.read(data)) != -1) {
//            total += count;
//            double current = Math.round(total / (Math.pow(1024, 2)));
//            int progress = (int) ((total * 100) / fileSize);
//            long currentTime = System.currentTimeMillis() - startTime;
//            output.write(data, 0, count);
//        }
//        output.flush();
//        output.close();
//        bis.close();
//
//
//        return true;
//    }
}
