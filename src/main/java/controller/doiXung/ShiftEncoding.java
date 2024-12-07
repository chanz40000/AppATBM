package controller.doiXung;

import java.util.Random;

public class ShiftEncoding {
    public int shift =0;

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(25) + 1; // nextInt(26) gives 0-25, so we add 1 to get 1-26
    }
    public String encrypt(String text) {
        if(shift==0) {
            shift=getRandomNumber();
        }
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            // Kiểm tra xem ký tự có phải là chữ cái không
            // Dịch chuyển ký tự 'ch' trong bảng chữ cái
            // (ch - base): chuyển 'ch' về vị trí 0-25 trong bảng chữ cái (A->0, B->1,...)
            // + shift: dịch chuyển ký tự theo số bước 'shift' (ví dụ: 3 bước)
            // % 26: đảm bảo kết quả luôn nằm trong bảng chữ cái (0-25)
            // + base: chuyển kết quả về ký tự ASCII phù hợp, trả về ký tự đã mã hóa
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch - base + shift) % 26 + base); // Dịch chuyển trong bảng chữ cái
            }

            encryptedText.append(ch);
        }

        return encryptedText.toString();
    }

    // Phương thức giải mã một chuỗi
    public String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            char ch = encryptedText.charAt(i);

            // Kiểm tra xem ký tự có phải là chữ cái không
            // Dịch chuyển ký tự 'ch' trong bảng chữ cái
            // (ch - base): chuyển 'ch' về vị trí 0-25 trong bảng chữ cái (A->0, B->1,...)
            // + shift: dịch chuyển ký tự theo số bước 'shift' (ví dụ: 3 bước)
            // % 26: đảm bảo kết quả luôn nằm trong bảng chữ cái (0-25)
            // + base: chuyển kết quả về ký tự ASCII phù hợp, trả về ký tự đã mã hóa
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) ((ch - base + 26 - shift) % 26 + base); // Dịch chuyển trong bảng chữ cái
            }

            decryptedText.append(ch);
        }
        return decryptedText.toString();
    }
}
