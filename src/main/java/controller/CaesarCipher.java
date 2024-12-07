package controller;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

public class CaesarCipher {
    SecretKey secretKey;

    // Phương thức mã hóa một chuỗi


    // Phát sinh khóa mới và lưu vào file
    public SecretKey generateAndSaveKey(String mode, int size) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(mode);
        keyGen.init(size); // Khóa AES 128 bit
        SecretKey secretKey = keyGen.generateKey();

        // Lưu khóa vào file
        try (FileOutputStream keyOut = new FileOutputStream(System.getProperty("user.dir")+"//src//main//java//key//"+mode+size+".txt")) {
            keyOut.write(secretKey.getEncoded());
        }
        System.out.println("Khóa AES mới đã được phát sinh và lưu vào file.");
        return secretKey;
    }

    // Tải khóa từ file nếu đã có
    public SecretKey loadKeyFromFile(String mode, int size) throws Exception {
        File keyFile = new File(System.getProperty("user.dir")+"//src//main//java//key//"+mode+size+".txt");
        if (!keyFile.exists()||keyFile.length()==0) {
            System.out.println("Không tìm thấy file khóa AES. Phát sinh khóa mới...");
            return generateAndSaveKey(mode, size);
        }

        // Đọc khóa từ file
        byte[] keyBytes = new byte[(int) keyFile.length()];
        try (FileInputStream keyIn = new FileInputStream(keyFile)) {
            keyIn.read(keyBytes);
        }

        System.out.println("Khóa AES đã được tải từ file.");
        return new SecretKeySpec(keyBytes, "AES");
    }
    //thuat toan ma hoa doi xung co ban
    //ma hoa van ban su dung AES
    public String encryptText(String plainText)throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[]encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    //giai ma doan van ban
    public String decryptText(String encyptText)throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[]decodedBytes = Base64.getDecoder().decode(encyptText);
        byte[]decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, "UTF-8");


    }

    // Mã hóa dữ liệu
    public static String encrypt(String algorithm, SecretKey key, String mode, String padding, String data, int size) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Giải mã dữ liệu
    public static String decrypt(String algorithm, SecretKey key, String mode, String padding, String encryptedData, int size) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
    // Phát sinh khóa nếu người dùng chưa có khóa
    public static SecretKey generateKey(String algorithm, int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(keySize, new SecureRandom());
        return keyGen.generateKey();
    }


}
