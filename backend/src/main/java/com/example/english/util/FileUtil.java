package com.example.english.util;

import com.example.english.exception.StorageException;
import com.example.english.msg.Msg;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public final static String readFileToString(String fileName) {
        return readFileToString(new File(fileName));
    }

    public final static String readFileToString(File file) {
        try {
            if (!file.exists()) {
                throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {file.getAbsolutePath()}));
            }

            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {file.getAbsolutePath()}), e);
        }
    }

    public final static byte[] readFileToByteArray(String fileName) {
        return readFileToByteArray(new File(fileName));
    }

    public final static byte[] readFileToByteArray(File file) {
        try {
            if (!file.exists()) {
                throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {file.getAbsolutePath()}));
            }

            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {file.getAbsolutePath()}), e);
        }
    }

    public final static String encodeFileToBase64Binary(String fileName) {
        byte[] encoded = Base64.encodeBase64(readFileToByteArray(fileName));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public final static void decodeBase64BinaryToFile(String fileBase64, String filePath) {
        byte[] decoded = Base64.decodeBase64(fileBase64);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(decoded);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.save.file", new Object[] {filePath}), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw new StorageException.FileNotFound(Msg.getMessage("failed.to.save.file", new Object[] {filePath}), e);
                }
            }
        }
    }

    public final static byte[] decodeBase64BinaryToBytes(String base64) {
        return Base64.decodeBase64(base64);
    }

    public final static void createDirectories(String filePathDes) {
        Path parent = Paths.get(filePathDes).getParent();

        File desDirectory = parent.toFile();
        if (!desDirectory.exists() || !desDirectory.isDirectory()) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new StorageException.FileNotFound(Msg.getMessage("failed.to.create.directory", new Object[] {filePathDes}), e);
            }
        }
    }

    public final static String generateFileName(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            String ext = fileName.substring(lastIndex);
            fileName = fileName.substring(0, lastIndex);

            lastIndex = fileName.lastIndexOf("_");
            if (lastIndex >= 0) {
                String uuid = fileName.substring(lastIndex + 1);
                if (uuid.matches("^[0-9abcdef]{8}-[0-9abcdef]{4}-[0-9abcdef]{4}-[0-9abcdef]{4}-[0-9abcdef]{12}$")) {
                    fileName = fileName.substring(0, lastIndex);
                }
            }

            fileName += "_" + UUID.randomUUID().toString() + ext;
        } else {
            fileName = UUID.randomUUID().toString() + "_" +fileName;
        }

        return fileName;
    }
}
