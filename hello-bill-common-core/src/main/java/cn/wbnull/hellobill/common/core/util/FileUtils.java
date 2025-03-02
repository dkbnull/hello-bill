package cn.wbnull.hellobill.common.core.util;

import cn.wbnull.hellobill.common.core.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File 工具类
 *
 * @author null 2018-08-08
 * https://github.com/dkbnull/HelloUtil
 */
public class FileUtils {

    public static final int FILE_TAG_WRITE = 0;
    public static final int FILE_TAG_APPEND = 1;
    public static final int FILE_TAG_APPEND_NEW_LINE = 2;

    private FileUtils() {
    }

    /**
     * 写文件
     *
     * @param path    路径
     * @param value   内容
     * @param charset 字符集
     * @param tag     保存方式 0:保存为新文件;1:追加保存;2:另起一行追加保存
     * @throws Exception
     */
    public static void writeFile(String path, String value, String charset, int tag) throws Exception {
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            File file = new File(path);

            if (FILE_TAG_WRITE == tag) {
                if (file.exists()) {
                    file.delete();
                }

                outputStream = new FileOutputStream(file);
            } else if (FILE_TAG_APPEND == tag) {
                outputStream = new FileOutputStream(file, true);
            } else {
                outputStream = new FileOutputStream(file, true);
                outputStream.write("\r\n".getBytes());
            }
            outputStreamWriter = new OutputStreamWriter(outputStream, charset);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(value);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     *
     * @param path    路径
     * @param charset 字符集
     * @return 文件内容List
     * @throws Exception
     */
    public static List<String> readFile(String path, String charset) throws Exception {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(path);

            if (!file.exists()) {
                throw new Exception("file not exist:" + path);
            }

            List<String> listValue = new ArrayList<>();
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, charset);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                listValue.add(str);
            }

            return listValue;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> readFile(String path, String charset, int size) throws Exception {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(path);

            if (!file.exists()) {
                throw new Exception("file not exist:" + path);
            }

            List<String> listValue = new ArrayList<>();
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, charset);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                listValue.add(str);

                if (listValue.size() >= size) {
                    break;
                }
            }

            return listValue;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("all")
    public static File transfer(String path, MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("file 不能为空");
        }

        File filePath = new File(path);
        if (!filePath.exists()) {
            boolean result = filePath.mkdirs();
            if (!result) {
                throw new BusinessException("创建资源目录失败，请重试");
            }
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;

        filePath = new File(filePath.getAbsolutePath(), fileName);
        if (filePath.exists()) {
            filePath.delete();
        }

        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new BusinessException("上传文件失败：" + e.getMessage());
        }

        return filePath;
    }
}
