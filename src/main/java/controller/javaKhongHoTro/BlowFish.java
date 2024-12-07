package controller.javaKhongHoTro;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class BlowFish {
    private SecretKey key;
    private String transformation;

    public BlowFish(){
        this.setTransformation("Blowfish/CBC/PKCS5Padding");
        try {
            this.createKeyRandom(128);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public SecretKey createKeyRandom(int key_size) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("Blowfish");
        key_generator.init(key_size); // Độ dài của khóa từ 32 đến 448 bit
        key = key_generator.generateKey();
        return key;
    }

    public byte[] encrypt(String text) throws Exception {
        if (key == null) return new byte[]{};
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        byte[] text_bytes = text.getBytes("UTF-8");
        return cipher.doFinal(text_bytes);
    }


    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        byte[] text_bytes = text.getBytes("UTF-8");
        byte[] encrypted_text_bytes = cipher.doFinal(text_bytes);
        return Base64.getEncoder().encodeToString(encrypted_text_bytes);
    }


    public void encryptFile(String srcFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File(srcFile);
            if (file.isFile()) {
                // Sử dụng Cipher với thuật toán bạn chọn
                Cipher cipher = Cipher.getInstance(transformation);

                // Nếu sử dụng ECB, không cần IV, ngược lại cần IV
                if (transformation.contains("ECB")) {
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8])); // IV là byte array có kích thước 8
                }

                fis = new FileInputStream(file);
                byte[] input_byte = new byte[1024];
                int bytes_read;

                // Đọc nội dung từ file và mã hóa
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                while ((bytes_read = fis.read(input_byte)) != -1) {
                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) byteArrayOutputStream.write(output_byte);
                }

                // Thực hiện mã hóa cho phần dữ liệu còn lại nếu có
                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) byteArrayOutputStream.write(output_byte);

                // Ghi lại kết quả mã hóa vào file gốc
                byte[] encryptedData = byteArrayOutputStream.toByteArray();
                fos = new FileOutputStream(file); // Ghi đè lên file gốc
                fos.write(encryptedData);
                fos.flush();

                System.out.println("File has been encrypted and saved.");
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }
    public void decryptFile(String srcFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File(srcFile);
            if (file.isFile()) {
                // Sử dụng Cipher với thuật toán bạn chọn
                Cipher cipher = Cipher.getInstance(transformation);

                // Nếu sử dụng ECB, không cần IV, ngược lại cần IV
                if (transformation.contains("ECB")) {
                    cipher.init(Cipher.DECRYPT_MODE, key);
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8])); // IV là byte array có kích thước 8
                }

                fis = new FileInputStream(file);
                byte[] input_byte = new byte[1024];
                int bytes_read;

                // Đọc nội dung từ file và mã hóa
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                while ((bytes_read = fis.read(input_byte)) != -1) {
                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) byteArrayOutputStream.write(output_byte);
                }

                // Thực hiện mã hóa cho phần dữ liệu còn lại nếu có
                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) byteArrayOutputStream.write(output_byte);

                // Ghi lại kết quả mã hóa vào file gốc
                byte[] encryptedData = byteArrayOutputStream.toByteArray();
                fos = new FileOutputStream(file); // Ghi đè lên file gốc
                fos.write(encryptedData);
                fos.flush();

                System.out.println("File has been encrypted and saved.");
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }



    public String decrypt(byte[] encrypt) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        byte[] decrypted_text_bytes = cipher.doFinal(encrypt);
        return new String(decrypted_text_bytes,"UTF-8");
    }

    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        byte[] encrypted_text_bytes = Base64.getDecoder().decode(text);
        byte[] decrypted_text_bytes = cipher.doFinal(encrypted_text_bytes);
        return new String(decrypted_text_bytes,"UTF-8");
    }


    public void decryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {

            File file = new File(srcFile);
            if (file.isFile()) {

                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int byte_read;

                while ((byte_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, byte_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

                fos.flush();
                System.out.println("Done Decrypted File");
            }

        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }


    public String exportKey() throws Exception {
        if (key == null) return "";
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


    public SecretKey importKey(String keyText) throws Exception {

        if (keyText == null || keyText.isEmpty()) {
            throw new IllegalArgumentException("Invalid key text");
        }

        try {
            byte[] key_bytes = Base64.getDecoder().decode(keyText.getBytes());
            key = new SecretKeySpec(key_bytes, "Blowfish");
            return key;
        } catch (Exception e) {
            throw new Exception("Failed to import key: " + e.getMessage());
        }
    }
    public void encryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            File file = new File(srcFile);
            if (file.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int bytes_read;

                while ((bytes_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

                fos.flush();
                System.out.println("Done Encrypted File");
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }

    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

}