package view2;

import Decorate.ComboBoxRenderer;
import Decorate.RoundedButton;
import controller.doiXung.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class DoiXungCoBan extends JPanel {
    private JComboBox<String> cipherOptions;
    private JTextArea inputText, encryptedText, decryptedText;
    private JTextField hillMatrixInput, affineAInput, affineBInput, vigenereKeyInput;
    private RoundedButton encryptButton, decryptButton, restartButton;
    private JPanel hillPanel, affinePanel, vigenerePanel;
    SubstitutionCipher substitutionCipher;
    ShiftEncoding shiftEncoding;
    AffineCipher affineCipher;
    HillCipher hillCipher;
    VigenereCipher vigenereCipher; // Đối tượng VigenereCipher

    public DoiXungCoBan() {
        substitutionCipher = new SubstitutionCipher();
        shiftEncoding = new ShiftEncoding();
        affineCipher = new AffineCipher();
        hillCipher = new HillCipher();
        vigenereCipher = new VigenereCipher(); // Khởi tạo đối tượng VigenereCipher

        // Tạo ComboBox với các phương pháp mã hóa
        cipherOptions = new JComboBox<>(new String[]{"Substitution", "Shift Encoding", "Hill Cipher", "Affine Cipher", "Vigenère Cipher"});
        Map<String, Icon> iconMap = new HashMap<>();
        iconMap.put("Substitution", new ImageIcon(Decorate.Image.img_subtitution));
        iconMap.put("Shift Encoding", new ImageIcon(Decorate.Image.img_shif));
        iconMap.put("Hill Cipher", new ImageIcon(Decorate.Image.img_hill));
        iconMap.put("Affine Cipher", new ImageIcon(Decorate.Image.img_affine));
        iconMap.put("Vigenère Cipher", new ImageIcon(Decorate.Image.img_vigenere));

        cipherOptions.setRenderer(new ComboBoxRenderer(iconMap));

        inputText = new JTextArea(3, 20);
        encryptedText = new JTextArea(3, 20);
        decryptedText = new JTextArea(3, 20);
        hillMatrixInput = new JTextField(20);
        affineAInput = new JTextField(10);
        affineBInput = new JTextField(10);
        vigenereKeyInput = new JTextField(20); // Trường nhập cho khóa Vigenère

        // Cấu hình các JTextArea
        encryptedText.setEditable(false);
        decryptedText.setEditable(false);

        // Main layout with GridLayout
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel, BorderLayout.CENTER);

        // Phần chọn phương pháp mã hóa
        JPanel cipherSelectionPanel = new JPanel(new GridLayout(1, 2));
        cipherSelectionPanel.setBackground(new Color(173, 216, 230));
        cipherSelectionPanel.add(new JLabel("Chọn phương pháp mã hóa:"));
        cipherSelectionPanel.add(cipherOptions);
        mainPanel.add(cipherSelectionPanel);

        // Phần nhập chuỗi đầu vào
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(173, 216, 230));
        inputPanel.add(new JLabel("Chuỗi đầu vào:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputText), BorderLayout.CENTER);
        mainPanel.add(inputPanel);

        // Phần nhập liệu cho Hill Cipher
        hillPanel = new JPanel(new GridLayout(1, 2));
        hillPanel.add(new JLabel("Nhập ma trận khóa 2x2 (dưới dạng: a b c d):"));
        hillPanel.add(hillMatrixInput);
        hillPanel.setVisible(false);
        mainPanel.add(hillPanel);

        // Phần nhập liệu cho Affine Cipher
        affinePanel = new JPanel(new GridLayout(2, 2));
        affinePanel.add(new JLabel("Nhập a (khác 0, NTCN với 26):"));
        affinePanel.add(affineAInput);
        affinePanel.add(new JLabel("Nhập b:"));
        affinePanel.add(affineBInput);
        affinePanel.setVisible(false);
        mainPanel.add(affinePanel);

        // Phần nhập liệu cho Vigenère Cipher
        vigenerePanel = new JPanel(new GridLayout(1, 2));
        vigenerePanel.add(new JLabel("Nhập khóa Vigenere:"));
        vigenerePanel.add(vigenereKeyInput);
        vigenerePanel.setVisible(false);
        mainPanel.add(vigenerePanel);

        // Phần hiển thị dữ liệu mã hóa và giải mã
        JPanel encryptedPanel = new JPanel(new BorderLayout());
        encryptedPanel.setBackground(new Color(173, 216, 230));
        encryptedPanel.add(new JLabel("Dữ liệu đã mã hóa:"), BorderLayout.NORTH);
        encryptedPanel.add(new JScrollPane(encryptedText), BorderLayout.CENTER);
        mainPanel.add(encryptedPanel);

        JPanel decryptedPanel = new JPanel(new BorderLayout());
        decryptedPanel.setBackground(new Color(173, 216, 230));
        decryptedPanel.add(new JLabel("Dữ liệu sau khi giải mã:"), BorderLayout.NORTH);
        decryptedPanel.add(new JScrollPane(decryptedText), BorderLayout.CENTER);
        mainPanel.add(decryptedPanel);

        // Phần nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 235));

        encryptButton = new RoundedButton("Sign");
        encryptButton.setIcon(new ImageIcon(Decorate.Image.img_encrypt));

        decryptButton = new RoundedButton("Verify");
        decryptButton.setIcon(new ImageIcon(Decorate.Image.img_decrypt));

        restartButton = new RoundedButton("Restart");
        restartButton.setIcon(new ImageIcon(Decorate.Image.img_reset));

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(restartButton);
        mainPanel.add(buttonPanel);

        // Event listeners
        cipherOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCipher = (String) cipherOptions.getSelectedItem();
                hillPanel.setVisible("Hill Cipher".equals(selectedCipher));
                affinePanel.setVisible("Affine Cipher".equals(selectedCipher));
                vigenerePanel.setVisible("Vigenere Cipher".equals(selectedCipher)); // Hiển thị panel Vigenère Cipher
            }
        });

        restartButton.addActionListener(e -> resetFields());

        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());

        setVisible(true);
    }

    private void resetFields() {
        inputText.setText("");
        encryptedText.setText("");
        decryptedText.setText("");
        hillMatrixInput.setText("");
        affineAInput.setText("");
        affineBInput.setText("");
        vigenereKeyInput.setText(""); // Xóa trường khóa Vigenère
    }

    public boolean isValidVigenereKey(String key) {
        // Kiểm tra nếu khóa không rỗng và chỉ chứa các ký tự chữ cái (A-Z, a-z)
        return key != null && !key.isEmpty() && key.matches("[a-zA-Z]+");
    }

    private void encrypt() {
        String selectedCipher = (String) cipherOptions.getSelectedItem();
        String input = inputText.getText();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu trước khi mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (selectedCipher) {
            case "Substitution":
                encryptedText.setText(substitutionCipher.encrypt(input));
                break;
            case "Shift Encoding":
                encryptedText.setText(shiftEncoding.encrypt(input));
                break;
            case "Hill Cipher":
                String hillKey = hillMatrixInput.getText();
                if(!hillCipher.isFourIntegersSeparatedBySpaces(hillKey)){
                    JOptionPane.showMessageDialog(this, "Dãy số nhập vào không hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                //kiem tra hop le
                int[][]matrix = hillCipher.convertStringToMatrix(hillKey);
                if(!HillCipher.inverseMatrix2(matrix, 52)){
                    JOptionPane.showMessageDialog(this, "Không có ma trận nghịch đảo, vui lòng nhập ma trận khác! vd: 11 8 3 7", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }else{
                    this.hillCipher.setKeyMatrix(hillKey);
                    encryptedText.setText(hillCipher.encrypt(input));
                }

                break;
            case "Affine Cipher":
                String a = affineAInput.getText();
                String b = affineBInput.getText();

                if(!affineCipher.areBothNumbers(a, b)){
                    JOptionPane.showMessageDialog(this, "a và b phải là số!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                int intA = Integer.parseInt(a);
                if(!affineCipher.isCoprimeWith26(intA)||intA==0){
                    JOptionPane.showMessageDialog(this, "a phải NTCN với 26 và khác 0!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                this.affineCipher.setAandB(intA, Integer.parseInt(b));
                encryptedText.setText(affineCipher.encrypt(input));
                break;
            case "Vigenere Cipher":
                String vigenereKey = vigenereKeyInput.getText();
                if (vigenereKey.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa Vigenere.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                if (!isValidVigenereKey(vigenereKey)) {
                    JOptionPane.showMessageDialog(this, "Key không hợp lệ! Vui lòng chỉ nhập chữ.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                encryptedText.setText(vigenereCipher.encrypt(input, vigenereKey));
                break;
        }
    }

    private void decrypt() {
        String selectedCipher = (String) cipherOptions.getSelectedItem();
        String encrypted = encryptedText.getText();

        if (encrypted.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Du lieu chua duoc ma hoa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch (selectedCipher) {
            case "Substitution":
                decryptedText.setText(substitutionCipher.decrypt(encrypted));
                break;
            case "Shift Encoding":
                decryptedText.setText(shiftEncoding.decrypt(encrypted));
                break;
            case "Hill Cipher":
                decryptedText.setText(hillCipher.decrypt(encrypted));
                break;
            case "Affine Cipher":
                decryptedText.setText(affineCipher.decrypt(encrypted));
                break;
            case "Vigenère Cipher":
                String vigenereKey = vigenereKeyInput.getText();
                if (vigenereKey.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập khóa Vigenère.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                decryptedText.setText(vigenereCipher.decrypt(encrypted, vigenereKey));
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoiXungCoBan::new);
    }
}
