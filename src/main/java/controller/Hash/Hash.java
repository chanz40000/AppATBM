package controller.Hash;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public String hash(String data, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[]bytes = data.getBytes();
        byte[]digest = md.digest(bytes);
        BigInteger re = new BigInteger(1, digest);
        return re.toString(16);
    }

    public String hashFile(String src, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        File f = new File(src);
        if(!f.exists())return null;
        InputStream in = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream di = new DigestInputStream(in, md);
        byte[]buff = new byte[1024];
        int read;
        do{
            read = di.read(buff);
        }while (read!=-1);
        BigInteger re = new BigInteger(1, di.getMessageDigest().digest());
        return re.toString(16);
    }

}
