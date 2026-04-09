package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static String read(String key){
        return read(key,Constants.CONFIG_FILE_PATH);
    }
    public static String read(String key,String filepath){
        Properties properties=new Properties();
        try(FileInputStream fis=new FileInputStream(filepath)){
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read config file: "+filepath,e);
        }
        return properties.getProperty(key);
    }
}
