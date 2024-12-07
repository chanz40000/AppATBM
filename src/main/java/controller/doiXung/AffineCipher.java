package controller.doiXung;

import java.util.Scanner;

public class AffineCipher {
    private static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final int mUpper = ALPHABET_UPPER.length(); // Số lượng ký tự trong bảng chữ cái chữ hoa
    private static final int mLower = ALPHABET_LOWER.length(); // Số lượng ký tự trong bảng chữ cái chữ thường

    int a;
    int b;
    public void setAandB(int a, int b){
        this.a = a;
        this.b = b;
    }
    // Tính toán phép nghịch đảo modulo
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1; // Mặc định nếu không tìm thấy
    }

    // Mã hóa
    public  String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int x = ALPHABET_UPPER.indexOf(ch);
                int encryptedChar = (a * x + b) % mUpper;
                encryptedText.append(ALPHABET_UPPER.charAt(encryptedChar));
            } else if (Character.isLowerCase(ch)) {
                int x = ALPHABET_LOWER.indexOf(ch);
                int encryptedChar = (a * x + b) % mLower;
                encryptedText.append(ALPHABET_LOWER.charAt(encryptedChar));
            } else {
                encryptedText.append(ch); // Giữ nguyên ký tự không phải chữ cái
            }
        }
        return encryptedText.toString();
    }

    // Giải mã
    public  String decrypt(String ciphertext) {
        int aInv = modInverse(a, mUpper); // Tìm phép nghịch đảo của a cho chữ hoa
        StringBuilder decryptedText = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                int y = ALPHABET_UPPER.indexOf(ch);
                int decryptedChar = (aInv * (y - b + mUpper)) % mUpper; // Thêm mUpper để tránh số âm
                decryptedText.append(ALPHABET_UPPER.charAt(decryptedChar));
            } else if (Character.isLowerCase(ch)) {
                int y = ALPHABET_LOWER.indexOf(ch);
                int decryptedChar = (aInv * (y - b + mLower)) % mLower; // Thêm mLower để tránh số âm
                decryptedText.append(ALPHABET_LOWER.charAt(decryptedChar));
            } else {
                decryptedText.append(ch); // Giữ nguyên ký tự không phải chữ cái
            }
        }
        return decryptedText.toString();
    }
    public boolean areBothNumbers(String a, String b) {
        return isInteger(a) && isInteger(b);
    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isCoprimeWith26(int number) {
        return gcd(number, 26) == 1;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

}
