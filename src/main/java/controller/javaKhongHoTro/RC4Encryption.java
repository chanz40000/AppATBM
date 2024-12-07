package controller.javaKhongHoTro;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Security;
import java.util.Base64;

public class RC4Encryption {
    SecretKey secretKey;

    public RC4Encryption() {
        // Đăng ký Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());
        try {
            secretKey = generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String keyFile =System.getProperty("user.dir")+"//src//main//java//key//RC4Key.txt";

    public static void main(String[] args) throws Exception {


        // Tạo khóa RC4
        RC4Encryption rc4Encryption = new RC4Encryption();
        // Văn bản cần mã hóa
        String plainText = "Hello, this is a test for RC4 encryption!";
        System.out.println("Plain Text: " + plainText);

        // Mã hóa chuỗi
        String encryptedText = rc4Encryption.encrypt(plainText);
        System.out.println("Encrypted Text: " + encryptedText);

        // Giải mã chuỗi
        String decryptedText = rc4Encryption.decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);

        // File mã hóa
        String file = "C:\\Users\\ADMIN\\Documents\\21130574_NgoThuyTrang.docx";
        String file2 = "C:\\Users\\ADMIN\\Documents\\21130574_NgoThuyTrang2.docx";
        String file3 = "C:\\Users\\ADMIN\\Documents\\21130574_NgoThuyTrang3.docx";

        // Mã hóa file
        rc4Encryption.encryptFile(file, file2);
        System.out.println("File encrypted: " + file2);

        // Giải mã file
        rc4Encryption.decryptFile(file2, file3);
        System.out.println("File decrypted: " + file3);
    }


    // Tạo khóa RC4
    public  SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("RC4");
        return keyGen.generateKey();
    }

    // Hàm mã hóa chuỗi
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Hàm giải mã chuỗi
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    // Hàm mã hóa file
    public void encryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                fos.write(finalBytes);
            }
        }
    }

    // Hàm giải mã file
    public  void decryptFile(String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("RC4");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                fos.write(finalBytes);
            }
        }
    }
}
