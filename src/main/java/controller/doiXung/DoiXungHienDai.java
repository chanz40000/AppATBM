package controller.doiXung;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class DoiXungHienDai {
    SecretKey key;
    public IvParameterSpec iv;

    public DoiXungHienDai() {
        Security.addProvider(new BouncyCastleProvider());
    }

    //tra ve key duoi dang chuoi
    public String generateKey(String algorithm, int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(keySize, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public SecretKey stringToKey(String encodedKey, String algorithm) throws Exception {
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        // Tạo đối tượng SecretKey từ mảng byte
        return new SecretKeySpec(decodedKey, algorithm);
    }

    //chuyen tu chuoi ve key
    public SecretKey getKeyFromBase64(String base64Key, String algorithm) {
        // Giải mã Base64 để lấy byte[]
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // Tạo SecretKeySpec với byte[] và thuật toán
        return new SecretKeySpec(keyBytes, algorithm);
    }


    public boolean isKeyValid(String base64Key, String algorithm, int keySize) {
        try {
            // Giải mã Base64 để lấy byte[]
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);

            // Kiểm tra kích thước byte[] có khớp với kích thước yêu cầu
            int expectedKeyBytesLength = keySize / 8; // Kích thước khóa tính theo byte
            if (keyBytes.length != expectedKeyBytesLength) {
                return false; // Kích thước không hợp lệ
            }

            // Tạo SecretKeySpec và kiểm tra thuật toán
            SecretKey secretKey = new SecretKeySpec(keyBytes, algorithm);

            // Thuật toán trong SecretKey phải khớp
            return secretKey.getAlgorithm().equalsIgnoreCase(algorithm);
        } catch (IllegalArgumentException | NullPointerException e) {
            // Nếu có lỗi trong quá trình giải mã Base64 hoặc tạo khóa
            return false;
        }
    }
    public SecretKey loadKeyFromFileBase64(String algorithm, String filePath) throws Exception {
        File keyFile = new File(filePath);

        // Kiểm tra file có tồn tại và không rỗng
        if (!keyFile.exists() || keyFile.length() == 0) {
            throw new FileNotFoundException("File khóa không tồn tại hoặc rỗng: " + filePath);
        }

        // Đọc khóa từ file (Base64)
        String encodedKey;
        try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
            encodedKey = reader.readLine(); // Đọc dòng đầu tiên chứa khóa Base64
        }

        // Giải mã Base64 để lấy byte[]
        byte[] keyBytes = Base64.getDecoder().decode(encodedKey);


        System.out.println("Khóa đã được tải từ file: " + filePath);
        // Tạo SecretKeySpec từ byte[] với thuật toán tương ứng
        return new SecretKeySpec(keyBytes, algorithm);
    }

    private void loadIV(int size) {
            String ivBase64 = (size==8?"Ecya44rKcJs=":"rWdP2I3iZiofcp6/vKNUxg==");  // Đọc chuỗi IV dạng Base64 từ file
                byte[] ivBytes = Base64.getDecoder().decode(ivBase64);  // Giải mã từ Base64 về mảng byte
                iv = new IvParameterSpec(ivBytes);  // Tạo đối tượng IvParameterSpec
                System.out.println("IV loaded from iv.txt");

    }

    // Phương thức tạo IV và lưu vào file iv.txt
    private void genIV(int size) {
        byte[] ivBytes = new byte[size];  // IV phải có độ dài 16 byte
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);  // Tạo ngẫu nhiên IV
        iv = new IvParameterSpec(ivBytes);  // Khởi tạo IvParameterSpec với IV đã tạo

        // Lưu IV dưới dạng Base64 vào file iv.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"//src//main//java//iv//iv"+size+".txt"))) {
            String ivBase64 = Base64.getEncoder().encodeToString(ivBytes);
            bw.write(ivBase64);  // Ghi IV dưới dạng chuỗi Base64 vào file
            System.out.println("New IV generated and saved to iv.txt");
        } catch (IOException e) {
            System.err.println("Error while writing IV to file: " + e.getMessage());
        }
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }


    public String encrypt(String algorithm, String mode, String padding, String data, int keySize, String keyString) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        key =stringToKey(keyString, algorithm);
        if(mode.equals("ECB")){
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }else{
            int sizeIV = (algorithm.equals("AES")||algorithm.equals("Twofish")||algorithm.equals("Camellia")? 16:8);
            loadIV(sizeIV);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        }

        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Giải mã dữ liệu
    public String decrypt(String algorithm, String mode, String padding, String encryptedData, int keySize, String keyString) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        key =stringToKey(keyString, algorithm);
        if(mode.equals("ECB")){
            cipher.init(Cipher.DECRYPT_MODE, key);
        }else{
            int sizeIV = (algorithm.equals("AES")||algorithm.equals("Twofish")||algorithm.equals("Camellia")? 16:8);
            loadIV(sizeIV);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        }

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
    public boolean encryptFile(String src, String des, String algorithm, String mode, String padding, int keySize, String keyString) throws Exception {
        //SecretKey key = loadKeyFromFile(algorithm, keySize);
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        key =stringToKey(keyString, algorithm);
        if(mode.equals("ECB")){
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }else{
            int sizeIV = (algorithm.equals("AES")||algorithm.equals("Twofish")||algorithm.equals("Camellia")? 16:8);
            loadIV(sizeIV);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        }
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
                CipherOutputStream cos = new CipherOutputStream(bos, cipher) // Sử dụng CipherOutputStream để mã hóa
        ) {
            int i;
            byte[] read = new byte[1024];
            while ((i = bis.read(read)) != -1) {
                cos.write(read, 0, i); // Ghi dữ liệu vào CipherOutputStream
            }
            cos.flush(); // Đảm bảo ghi tất cả dữ liệu ra tệp
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean decryptFile(String src, String des, String algorithm, String mode, String padding, int keySize, String keyString) throws Exception {
        //SecretKey key = loadKeyFromFile(algorithm, keySize);
        Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
        key =stringToKey(keyString, algorithm);
        if(mode.equals("ECB")){
            cipher.init(Cipher.DECRYPT_MODE, key);
        }else{
            int sizeIV = (algorithm.equals("AES")||algorithm.equals("Twofish")||algorithm.equals("Camellia")? 16:8);
            loadIV(sizeIV);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        }
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
                CipherInputStream cis = new CipherInputStream(bis, cipher) // Sử dụng CipherInputStream để giải mã
        ) {
            int i;
            byte[] read = new byte[1024];
            while ((i = cis.read(read)) != -1) {
                bos.write(read, 0, i); // Ghi dữ liệu giải mã vào tệp đích
            }
            bos.flush(); // Đảm bảo ghi tất cả dữ liệu ra tệp
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
