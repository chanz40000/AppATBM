package controller.javaKhongHoTro;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Security;
import java.util.Base64;

public class RC2Encryption {

    public static void main(String[] args) throws Exception {
        // Đăng ký Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());

        // Tạo khóa RC2
        KeyGenerator keyGen = KeyGenerator.getInstance("RC2");
        keyGen.init(128); // RC2 hỗ trợ các kích thước khóa 40, 64, 128 bits
        SecretKey secretKey = keyGen.generateKey();

        // Văn bản cần mã hóa
        String plainText = "This is a test for RC2 encryption!";
        System.out.println("Plain Text: " + plainText);

        // Mã hóa
        String encryptedText = encrypt(plainText, secretKey);
        System.out.println("Encrypted Text: " + encryptedText);

        // Giải mã
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    // Hàm mã hóa
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RC2/ECB/PKCS5Padding"); // Chọn mode và padding phù hợp
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Hàm giải mã
    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RC2/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}

