package cn.wbnull.hellobill.common.core.util;

import cn.wbnull.hellobill.common.core.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * File 工具类
 *
 * @author null
 * @date 2018-08-08
 * @link <a href="https://github.com/dkbnull/HelloUtil">GitHub</a>
 */
public class FileUtils {

    private FileUtils() {
    }

    public static File transfer(String path, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("file 不能为空");
        }

        File filePath = new File(path);
        if (!filePath.exists() && !filePath.mkdirs()) {
            throw new BusinessException("创建资源目录失败，请重试");
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
