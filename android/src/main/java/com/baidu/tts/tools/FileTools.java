/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class FileTools {
    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean isFileExist(Object ... args) {
        File file = null;
        if (args.length == 1) {
            Object object = args[0];
            if (object instanceof File) {
                file = (File)object;
            } else if (object instanceof String) {
                String string = (String)object;
                file = FileTools.createFile(string);
            }
        } else if (args.length == 2) {
            String string = (String)args[0];
            String string2 = (String)args[1];
            file = FileTools.createFile(string, string2);
        } else {
            throw new UnknownError();
        }
        return file != null ? file.exists() : false;
    }

    public static String jointPathAndName(String path, String name) {
        String string = null;
        string = path.endsWith(File.separator) ? path + name : path + File.separator + name;
        return string;
    }

    public static boolean deleteFile(String path) {
        File file = FileTools.createFile(path);
        return FileTools.deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        return !file.exists() || file.delete();
    }

    public static boolean fileCopy(String source, String target) throws FileNotFoundException {
        File file = FileTools.createFile(source);
        File file2 = FileTools.createFile(target);
        return FileTools.fileCopy(file, file2);
    }

    public static boolean fileCopy(String sPath, String sName, String tPath, String tName) throws FileNotFoundException {
        File file = FileTools.createFile(sPath, sName);
        File file2 = FileTools.createFile(tPath, tName);
        return FileTools.fileCopy(file, file2);
    }

    public static boolean fileCopy(FileDescriptor source, FileDescriptor target) {
        return FileTools.fileCopy(new FileInputStream(source), new FileOutputStream(target));
    }

    public static boolean fileCopy(File source, File target) throws FileNotFoundException {
        return FileTools.fileCopy(new FileInputStream(source), new FileOutputStream(target));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean fileCopy(FileInputStream source, FileOutputStream target) {
        boolean bl = false;
        try {
            bl = FileTools.fileCopy(source.getChannel(), target.getChannel());
        }
        catch (Exception exception) {
        }
        finally {
            try {
                source.close();
                target.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean fileCopy(FileChannel source, FileChannel target) {
        try {
            source.transferTo(0L, source.size(), target);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        finally {
            try {
                source.close();
                target.close();
                return true;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
                return false;
            }
        }
    }

    public static boolean writeFile(String data, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public static void createDir(String path) {
        File file = new File(path);
        file.mkdirs();
    }

    public static String extractFileName(String fullName) {
        int n2 = fullName.lastIndexOf(File.separator);
        return fullName.substring(n2 + 1);
    }

    public static File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (path.endsWith(File.separator)) {
                file.mkdirs();
            } else {
                int n2 = path.lastIndexOf(File.separator);
                String string = path.substring(0, n2);
                File file2 = new File(string);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        return file;
    }

    public static File getFile(String path, String fileName) {
        String string = FileTools.jointPathAndName(path, fileName);
        return FileTools.getFile(string);
    }

    public static File createFile(String absPath) {
        return FileTools.getFile(absPath);
    }

    public static File createFile(String path, String name) {
        return FileTools.getFile(FileTools.jointPathAndName(path, name));
    }
}

