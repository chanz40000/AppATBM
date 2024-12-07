package controller.chuKyDienTu;

import java.io.*;
import java.security.*;
import java.util.Base64;

public class DS {
    KeyPair keyPair;
    SecureRandom secureRandom;
    Signature signature;
    PublicKey publicKey;
    PrivateKey privateKey;

    public DS(){
        try {
            //loadKey("DSA", "SHA1PRNG", "SUN");
            saveKey("DSA", "SHA1PRNG", "SUN");
            genKey();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }


    // Phương thức lưu cặp khóa xuống file
    public void saveKey(String alg, String algRandom, String prov) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(alg, prov);
        secureRandom = SecureRandom.getInstance(algRandom, prov);
        keyPairGenerator.initialize(1024, secureRandom);
        keyPair = keyPairGenerator.genKeyPair();
        signature = Signature.getInstance(alg, prov);
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"//src//main//java//key//DSKeyPair.txt"))) {
//            oos.writeObject(keyPair); // Lưu KeyPair
//            oos.writeObject(secureRandom); // Lưu SecureRandom
//            oos.writeObject(signature.getAlgorithm()); // Lưu thuật toán của Signature
//        }
    }



    // Phương thức tải cặp khóa từ file
    public void loadKey(String alg, String algRandom, String prov) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchProviderException {
        File file = new File(System.getProperty("user.dir")+"//src//main//java//key//DSKeyPair.txt");
        if(!file.exists()||file.length()==0)saveKey(alg, algRandom, prov);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            keyPair = (KeyPair) ois.readObject(); // Tải KeyPair
            secureRandom = (SecureRandom) ois.readObject(); // Tải SecureRandom
            String sigAlg = (String) ois.readObject(); // Tải thuật toán Signature
            signature = Signature.getInstance(sigAlg); // Tạo Signature từ thuật toán
        }catch (Exception e){
            file.delete();
            loadKey(alg, algRandom, prov);
        }
    }


    public void genKey(){
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }

    public String sign(String mes) throws InvalidKeyException, SignatureException {
        byte[]data = mes.getBytes();
        signature.initSign(privateKey);
        signature.update(data);
        byte[]sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public String signFile(String src) throws IOException, SignatureException, InvalidKeyException {
        signature.initSign(privateKey);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[]data = new byte [1024];
        int read;
        while ((read=bis.read(data))!=-1){
           signature.update(data, 0, read);
        }
        bis.close();
        byte[]buff = signature.sign();
        return Base64.getEncoder().encodeToString(buff);
    }
    public boolean verify(String mes, String sign) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        byte[]data = mes.getBytes();
        signature.update(data);
        byte[]signs = Base64.getDecoder().decode(sign);
        return  signature.verify(signs);
    }

    public boolean verifyFile(String src, String sign) throws InvalidKeyException, IOException, SignatureException {
        signature.initVerify(publicKey);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[]data = new byte[1024];
        int read;
        while ((read=bis.read(data))!=-1){
            signature.update(data, 0, read);
        }
        bis.close();
        byte[]signs = Base64.getDecoder().decode(sign);
        return signature.verify(signs);
    }

    public String publicKeyToString(){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    public String privateToString(){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

}
