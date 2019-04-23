package executors;

import Pair.Tuple;
import interfaceExecutable.IExecutable;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Md5Executor implements IExecutable {
    private HashMap<String, String> cache = new HashMap<>();

    public Tuple process(File file){
        if(!file.isDirectory()) {
            try (FileInputStream fis = new FileInputStream(file)) {

                byte[] byteArray = new byte[1024];
                int bytesCount = 0;

                MessageDigest digest = MessageDigest.getInstance("MD5");
                while ((bytesCount = fis.read(byteArray)) != -1) {
                    digest.update(byteArray, 0, bytesCount);
                }

                fis.close();

                byte[] bytes = digest.digest();

                StringBuilder checksum = new StringBuilder();
                for (byte oneByte : bytes) {
                    checksum.append(Integer.toString((oneByte & 0xff) + 0x100, 16).substring(1));
                }

                this.cache.put(file.getPath(), checksum.toString());
                return new Tuple<>(file, checksum.toString());

            } catch (IOException ex) {
                ex.printStackTrace();
                return new Tuple<>(file, "IO exception!");

            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                return new Tuple<>(file, "Digest exception!");
            }
        }

        else{
            String directoryHash = "";

            for(File attachment: file.listFiles()){
                if(attachment.isFile()) {
                    String hash = cache.get(attachment.getPath());
                    directoryHash += hash.equals("null") ? hash:"";
                }
            }

            try{
                MessageDigest dirDigest = MessageDigest.getInstance("MD5");
                dirDigest.update(directoryHash.getBytes());

                byte[] dirDigestBytes = dirDigest.digest();
                StringBuilder checksum = new StringBuilder();
                for (byte oneByte : dirDigestBytes) {
                    checksum.append(Integer.toString((oneByte & 0xff) + 0x100, 16).substring(1));
                }

                this.cache.put(file.getPath(), checksum.toString());
                return new Tuple<>(file, checksum.toString());
            }
            catch(NoSuchAlgorithmException ex){
                ex.printStackTrace();
                return new Tuple<>(file, "Digest exception!");
            }
        }
    }
}
