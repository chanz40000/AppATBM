package controller.doiXung;

public class VigenereCipher {

    // Mã hóa Vigenère Cipher
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        key = generateKey(plaintext, key); // Tạo khóa lặp lại khớp độ dài plaintext

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            // Mã hóa nếu là ký tự chữ cái
            if (Character.isLetter(plainChar)) {
                char base = Character.isUpperCase(plainChar) ? 'A' : 'a'; // Phân biệt chữ hoa/ thường
                char encryptedChar = (char) ((plainChar - base + (keyChar - (Character.isUpperCase(keyChar) ? 'A' : 'a'))) % 26 + base);
                ciphertext.append(encryptedChar);
            }
            // Mã hóa nếu là số
            else if (Character.isDigit(plainChar)) {
                char encryptedChar = (char) ((plainChar - '0' + (keyChar - 'A')) % 10 + '0'); // Mã hóa số
                ciphertext.append(encryptedChar);
            } else {
                // Giữ nguyên các ký tự không phải chữ cái hoặc số (ví dụ dấu cách, dấu câu, v.v.)
                ciphertext.append(plainChar);
            }
        }

        return ciphertext.toString();
    }

    // Giải mã Vigenère Cipher
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        key = generateKey(ciphertext, key); // Tạo khóa lặp lại khớp độ dài ciphertext

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i);

            // Giải mã nếu là ký tự chữ cái
            if (Character.isLetter(cipherChar)) {
                char base = Character.isUpperCase(cipherChar) ? 'A' : 'a'; // Phân biệt chữ hoa/ thường
                char decryptedChar = (char) ((cipherChar - base - (keyChar - (Character.isUpperCase(keyChar) ? 'A' : 'a')) + 26) % 26 + base);
                plaintext.append(decryptedChar);
            }
            // Giải mã nếu là số
            else if (Character.isDigit(cipherChar)) {
                char decryptedChar = (char) ((cipherChar - '0' - (keyChar - 'A') + 10) % 10 + '0'); // Giải mã số
                plaintext.append(decryptedChar);
            } else {
                // Giữ nguyên các ký tự không phải chữ cái hoặc số
                plaintext.append(cipherChar);
            }
        }

        return plaintext.toString();
    }

    // Tạo khóa lặp lại khớp với độ dài văn bản
    private static String generateKey(String text, String key) {
        StringBuilder repeatedKey = new StringBuilder(key);
        while (repeatedKey.length() < text.length()) {
            repeatedKey.append(key);
        }
        return repeatedKey.substring(0, text.length());
    }

    // Hàm main để kiểm tra
    public static void main(String[] args) {
        String plaintext = "Hello World 12)!"; // Văn bản chứa cả chữ cái và số, bao gồm dấu câu
        String key = "KEY"; // Khóa có chữ cái

        // Mã hóa văn bản
        String encryptedText = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + encryptedText);

        // Giải mã văn bản
        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
