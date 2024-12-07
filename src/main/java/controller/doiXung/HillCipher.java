package controller.doiXung;

import java.util.Scanner;

public class HillCipher {
    private static final int SIZE = 2; // Kích thước ma trận
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int[][] keyMatrix;


    public void setKeyMatrix(String keyMatrix) {
        this.keyMatrix = new int[2][2];
        String[] elements = keyMatrix.trim().split("\\s+");

        if (elements.length != 2 * 2) {
            throw new IllegalArgumentException("Số phần tử không phù hợp với ma trận " + 2 + "x" + 2);
        }

        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.keyMatrix[i][j] = Integer.parseInt(elements[index++]);
            }
        }
    }
    public void setKeyMatrix2(int[][]matrix) {
        this.keyMatrix = matrix;

    }
    // Tính định thức của ma trận

    public static int determinant(int[][] matrix) {
        return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 52;
    }

    // Tính nghịch đảo của số a trong modulo mod
    public static int modInverse(int a, int mod) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1) {
                return x;
            }
        }
        return -1; // Không có nghịch đảo
    }

    // Tính ma trận nghịch đảo
    public static int[][] inverseMatrix(int[][] matrix, int mod) {
        int det = determinant(matrix);
        if (det == 0 || modInverse(det, mod) == -1) {
            throw new IllegalArgumentException("Matrix is not invertible");
        }
        int invDet = modInverse(det, mod);
        return new int[][] {
                { (matrix[1][1] * invDet) % mod, (-matrix[0][1] * invDet + mod) % mod },
                { (-matrix[1][0] * invDet + mod) % mod, (matrix[0][0] * invDet) % mod }
        };
    }
    // Tính ma trận nghịch đảo
    public static boolean inverseMatrix2(int[][] matrix, int mod) {
        int det = determinant(matrix);
        if (det == 0 || modInverse(det, mod) == -1) {
//            throw new IllegalArgumentException("Matrix is not invertible");
            return false;
        }
        return true;
    }

    // Mã hóa văn bản
    // Mã hóa văn bản
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        String[] words = text.split(" "); // Chia văn bản thành các từ

        for (String word : words) {
            StringBuilder encryptedWord = new StringBuilder();
            StringBuilder paddedWord = new StringBuilder(word.replaceAll("[^a-zA-Z]", "").toLowerCase());

            while (paddedWord.length() % SIZE != 0) { // Bổ sung ký tự nếu cần
                paddedWord.append('x');
            }

            for (int i = 0; i < paddedWord.length(); i += SIZE) {
                int[] block = new int[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    block[j] = ALPHABET.indexOf(paddedWord.charAt(i + j));
                }
                int[] encryptedBlock = new int[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    for (int k = 0; k < SIZE; k++) {
                        encryptedBlock[j] += keyMatrix[j][k] * block[k];
                    }
                    encryptedBlock[j] = (encryptedBlock[j] + 52) % 52; // Bảo đảm không âm
                    encryptedWord.append(ALPHABET.charAt(encryptedBlock[j]));
                }
            }

            result.append(encryptedWord).append(" "); // Thêm khoảng trắng giữa các từ
        }
        return result.toString().trim(); // Trả về kết quả
    }


    // Giải mã văn bản
    // Giải mã văn bản
    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        String[] words = text.split(" "); // Chia văn bản thành các từ
        int[][] invKeyMatrix = inverseMatrix(keyMatrix, 52);

        for (String word : words) {
            StringBuilder decryptedWord = new StringBuilder();

            for (int i = 0; i < word.length(); i += SIZE) {
                int[] block = new int[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    block[j] = ALPHABET.indexOf(word.charAt(i + j));
                }
                int[] decryptedBlock = new int[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    for (int k = 0; k < SIZE; k++) {
                        decryptedBlock[j] += invKeyMatrix[j][k] * block[k];
                    }
                    decryptedBlock[j] = (decryptedBlock[j] + 52) % 52; // Bảo đảm không âm
                    decryptedWord.append(ALPHABET.charAt(decryptedBlock[j]));
                }
            }

            // Loại bỏ ký tự 'x' ở cuối nếu nó có
            if (decryptedWord.length() > 0 && decryptedWord.charAt(decryptedWord.length() - 1) == 'x') {
                decryptedWord.setLength(decryptedWord.length() - 1); // Xóa ký tự cuối
            }

            result.append(decryptedWord).append(" "); // Thêm khoảng trắng giữa các từ
        }

        return result.toString().trim(); // Trả về kết quả
    }

    public int[][] convertStringToMatrix(String input) {
        int[][] matrix = new int[2][2];
        String[] elements = input.trim().split("\\s+");

        if (elements.length != 2 * 2) {
            throw new IllegalArgumentException("Số phần tử không phù hợp với ma trận " + 2 + "x" + 2);
        }

        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = Integer.parseInt(elements[index++]);
            }
        }

        return matrix;
    }

    public boolean isFourIntegersSeparatedBySpaces(String input) {
        // Xóa khoảng trắng ở đầu và cuối chuỗi
        input = input.trim();

        // Tách chuỗi thành các phần tử theo dấu cách
        String[] parts = input.split("\\s+");

        // Kiểm tra số lượng phần tử phải đúng là 4
        if (parts.length != 4) {
            return false;
        }

        // Kiểm tra từng phần tử có phải là số nguyên không
        for (String part : parts) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false; // Nếu không phải số nguyên, trả về false
            }
        }

        // Nếu tất cả phần tử đều là số nguyên và đúng 4 phần tử, trả về true
        return true;
    }

}
