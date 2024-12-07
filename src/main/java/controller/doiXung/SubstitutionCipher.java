package controller.doiXung;

import java.util.*;

public class SubstitutionCipher {
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static String keyUpper ; // Key cho chữ hoa
    private static String keyLower; // Key cho chữ thường

    // Tạo bản đồ thay thế cho cả chữ hoa và chữ thường dựa trên key
    private Map<Character, Character> generateCipherMap(String keyUpper, String keyLower) {
        Map<Character, Character> cipherMap = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            cipherMap.put(ALPHABET.charAt(i), keyUpper.charAt(i)); // Mã hóa chữ hoa
            cipherMap.put(alphabet.charAt(i), keyLower.charAt(i)); // Mã hóa chữ thường
        }
        return cipherMap;
    }

    // Mã hóa
    public String encrypt(String plaintext) {
        if(keyUpper==null)keyUpper = generateRandomKey(true);
        keyLower = keyUpper.toLowerCase();
        Map<Character, Character> cipherMap = generateCipherMap(keyUpper, keyLower);
        StringBuilder encryptedText = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            encryptedText.append(cipherMap.getOrDefault(ch, ch)); // Thay thế ký tự hoặc giữ nguyên nếu không trong bảng chữ cái
        }
        return encryptedText.toString();
    }

    // Giải mã
    public String decrypt(String ciphertext) {
        Map<Character, Character> cipherMap = generateCipherMap(keyUpper, keyLower);
        Map<Character, Character> reverseCipherMap = new HashMap<>();

        for (Map.Entry<Character, Character> entry : cipherMap.entrySet()) {
            reverseCipherMap.put(entry.getValue(), entry.getKey());
        }

        StringBuilder decryptedText = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            decryptedText.append(reverseCipherMap.getOrDefault(ch, ch));
        }
        return decryptedText.toString();
    }
    // Generate a random 26-character key
    public String generateRandomKey(boolean isUpperCase) {
        List<Character> characters = new ArrayList<>();
        for (char ch : (isUpperCase ? ALPHABET : alphabet).toCharArray()) {
            characters.add(ch);
        }
        Collections.shuffle(characters); // Shuffle the characters randomly

        StringBuilder key = new StringBuilder(26);
        for (char ch : characters) {
            key.append(ch);
        }
        return key.toString();
    }
}
