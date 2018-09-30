package com.jollychic.data.ab.analysis;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Copyright (c) 2013-2018 JOLLY INFORMATION TECHNOLOGY CO.LTD.
 * All rights reserved.
 * This software is the confidential and proprietary information of JOLLY
 * INFORMATION Technology CO.LTD("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with JOLLY.
 * Website：http://www.jollycorp.com
 */
public class GZIPUtils {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";


    public final static Logger logger = Logger.getLogger(GZIPUtils.class);

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            logger.error("gzip compress error.", e);
        }
        return out.toByteArray();
    }

    /**
     * GZIP解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            logger.error("gzip uncompress error.", e);
        }

        return out.toByteArray();
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     *
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            logger.error("gzip uncompress to string error.", e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        String str ="\"46\",1,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,9999999,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,0,0,0,0,0,0,0,0,0,0,0,0.9,0.9,0.9,0.9,0,117,34,34,33,3,3,30,29,31,30,0.088235,0.090909,0.967742,0.966667,0.25641,0.852941,67,22,22,22,2,2,20,20,20,20,0.090909,0.090909,1,1,0.298507,0.909091,35,14,14,14,2,2,12,12,12,12,0.142857,0.142857,1,1,0.342857,0.857143,14,9,9,9,2,2,7,7,7,7,0.222222,0.222222,1,1,0.5,0.777778,3,3,3,3,0,0,3,3,3,3,0,0,1,1,1,1,1,7274,3462,3711,3330,393,390,2989,2630,3252,2877,0.105901,0.117117,0.919127,0.914147,0.410916,0.759676,5115,2489,2691,2446,308,305,2123,1887,2317,2078,0.114456,0.124693,0.916271,0.908085,0.415054,0.758136,2864,1383,1560,1406,187,185,1203,1056,1307,1158,0.119872,0.131579,0.920428,0.911917,0.420042,0.763557,1503,766,912,818,108,106,671,584,738,649,0.118421,0.129584,0.909214,0.899846,0.44644,0.762402,170,145,260,230,24,22,164,139,170,145,0.092308,0.095652,0.964706,0.958621,0.964706,0.958621,7436,3528,3784,3393,401,398,3046,2678,3317,2932,0.105973,0.1173,0.9183,0.91337,0.409629,0.75907,5115,2489,2691,2446,308,305,2123,1887,2317,2078,0.114456,0.124693,0.916271,0.908085,0.415054,0.758136,2864,1383,1560,1406,187,185,1203,1056,1307,1158,0.119872,0.131579,0.920428,0.911917,0.420042,0.763557,1503,766,912,818,108,106,671,584,738,649,0.118421,0.129584,0.909214,0.899846,0.44644,0.762402,170,145,260,230,24,22,164,139,170,145,0.092308,0.095652,0.964706,0.958621,0.964706,0.958621,95945,45747,48708,43809,5590,5517,37728,33192,42107,37367,0.114766,0.125933,0.896003,0.88827,0.393225,0.725556,58439,29461,32003,29003,3751,3717,24165,21314,27232,24352,0.117208,0.128159,0.887375,0.875246,0.413508,0.723465,31015,15833,18039,16294,2054,2043,13350,11717,14965,13317,0.113864,0.125384,0.892082,0.879853,0.430437,0.740037,16700,8961,10824,9779,1125,1117,7617,6674,8679,7728,0.103936,0.114224,0.877636,0.863613,0.456108,0.744783,2058,1793,3375,3022,297,295,1967,1702,2058,1793,0.088,0.097617,0.955782,0.949247,0.955782,0.949247,1245742,493738,616285,470764,63763,61389,480325,345435,541127,400082,0.103463,0.130403,0.887638,0.863411,0.385573,0.699632,773800,328771,406236,322637,42869,41667,311770,232174,351686,271356,0.105527,0.129145,0.886501,0.855607,0.402908,0.706188,425773,178875,232667,182795,23009,22495,176446,129531,197979,150686,0.098892,0.123061,0.891236,0.859609,0.414413,0.724143,229139,99830,138457,108093,12668,12430,101311,73475,114113,86049,0.091494,0.114994,0.887813,0.853874,0.442138,0.736001,25261,18318,40004,30947,3069,3016,24382,17454,25261,18318,0.076717,0.097457,0.965203,0.952833,0.965203,0.952833";
//        String str = "test";
        System.out.println("原长度：" + str.length());
        long stattime = System.currentTimeMillis();
        byte[] result = GZIPUtils.compress(str);
        // 为了压缩后的内容能够在网络上传输，一般采用Base64编码
        String bs = Base64.getEncoder().encodeToString(result);
        bs = URLEncoder.encode( bs, "UTF-8" );
        long endtime = System.currentTimeMillis();
        System.out.println("耗时:"+(endtime-stattime)+"毫秒 压缩后字符串：" + bs);
        System.out.println("压缩后字符串长度：" + bs.length());
//        System.out.println("解压缩后字符串：" + new String(GZIPUtils.uncompress(Base64.getDecoder().decode(bs))));
        System.out.println("解压缩后字符串：" + GZIPUtils.uncompressToString(Base64.getDecoder().decode(URLDecoder.decode(bs, "UTF-8"))));
    }

}
