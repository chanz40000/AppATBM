package view2;

import Decorate.Image;
import Decorate.RoundedButton;
import controller.javaKhongHoTro.BlowFish;
import controller.javaKhongHoTro.RC4Encryption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RC4UI extends JPanel {
    private JComboBox<String> cipherOptions;
    private JTextArea inputText, encryptedText, decryptedText;
    private RoundedButton encryptButton, decryptButton, restartButton;
    private RoundedButton loadFileButton;
    private RoundedButton encryptFile;
    private RoundedButton decryptFile;
    JTextField nameFile;
    String nameOfFile, pathFile;

    RC4Encryption rc4Encryption;

    public RC4UI() {
        rc4Encryption = new RC4Encryption();

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Initialize components
        cipherOptions = new JComboBox<>(new String[]{"RC4"});
        inputText = new JTextArea(3, 20);
        encryptedText = new JTextArea(3, 20);
        decryptedText = new JTextArea(3, 20);

        encryptButton = new RoundedButton("Mã hóa");
        decryptButton = new RoundedButton("Giải mã");
        restartButton = new RoundedButton("Khởi động lại");

        // Configure text areas
        encryptedText.setEditable(false);
        decryptedText.setEditable(false);

        // Main layout with GridLayout
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainPanel.setBackground(new Color(173, 216, 235));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Thêm viền 5px cho mainPanel
        add(mainPanel, BorderLayout.CENTER);

        // Cipher option selection
        JPanel cipherSelectionPanel = new JPanel(new GridLayout(1, 2));
        cipherSelectionPanel.setBackground(new Color(173, 216, 235));
        cipherSelectionPanel.add(new JLabel("Chọn phương pháp mã hóa:"));
        cipherSelectionPanel.add(cipherOptions);
        mainPanel.add(cipherSelectionPanel);

        JPanel fileArea = new JPanel();
        fileArea.setBackground(new Color(173, 216, 235));
        fileArea.setBounds(17, 200, 648, 54);
        fileArea.setBackground(new Color(217, 217, 217));
        fileArea.setLayout(new BorderLayout());
        nameFile = new JTextField();
        loadFileButton = new RoundedButton("Open File");
        loadFileButton.setBackground(new Color(215, 187, 18));
        loadFileButton.setBounds(10, 12, 100, 30);
        loadFileButton.setIcon(new ImageIcon(Image.open_file));

        fileArea.add(nameFile, BorderLayout.CENTER);
        fileArea.add(loadFileButton, BorderLayout.EAST);

        JPanel buttonFile = new JPanel();
        buttonFile.setBackground(new Color(173, 216, 235));
        encryptFile = new RoundedButton("Encrypted File");
        encryptFile.setBounds(400, 82, 120, 37);
        encryptFile.setIcon(new ImageIcon(Image.img_encrypt_file));

        decryptFile = new RoundedButton("Decrypted File");
        decryptFile.setBounds(400, 82, 120, 37);
        decryptFile.setIcon(new ImageIcon(Image.img_decrypt_file));
        buttonFile.add(encryptFile);
        buttonFile.add(decryptFile);

        JPanel allFile = new JPanel();
        allFile.setBackground(new Color(173, 216, 235));
        allFile.setLayout(new GridLayout(2, 1));
        allFile.add(fileArea);
        allFile.add(buttonFile);

        mainPanel.add(allFile);
        // Input text area
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(173, 216, 235));
        inputPanel.add(new JLabel("Chuỗi đầu vào:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputText), BorderLayout.CENTER);
        mainPanel.add(inputPanel);


        // Encrypted text area
        JPanel encryptedPanel = new JPanel(new BorderLayout());
        encryptedPanel.setBackground(new Color(173, 216, 235));
        encryptedPanel.add(new JLabel("Dữ liệu đã mã hóa:"), BorderLayout.NORTH);
        encryptedPanel.add(new JScrollPane(encryptedText), BorderLayout.CENTER);
        mainPanel.add(encryptedPanel);

        // Decrypted text area
        JPanel decryptedPanel = new JPanel(new BorderLayout());
        decryptedPanel.setBackground(new Color(173, 216, 235));
        decryptedPanel.add(new JLabel("Dữ liệu sau khi giải mã:"), BorderLayout.NORTH);
        decryptedPanel.add(new JScrollPane(decryptedText), BorderLayout.CENTER);
        mainPanel.add(decryptedPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 235));

        encryptButton = new RoundedButton("Encrypt");
        encryptButton.setBounds(400, 82, 120, 37);
        encryptButton.setIcon(new ImageIcon(Image.img_encrypt));


        decryptButton = new RoundedButton("Decrypt");
        decryptButton.setBounds(400, 82, 120, 37);
        decryptButton.setIcon(new ImageIcon(Image.img_decrypt));

        restartButton = new RoundedButton("Restart");
        restartButton.setBounds(400, 82, 120, 37);
        restartButton.setIcon(new ImageIcon(Image.img_reset));

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(restartButton);
        mainPanel.add(buttonPanel);

        restartButton.addActionListener(e -> resetFields());

        // Placeholder for encryption/decryption logic
        encryptButton.addActionListener(e -> {
            try {
                encrypt();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, " Lỗi khi mã hóa: "+ ex);
            }
        });
        decryptButton.addActionListener(e -> {
            try {
                decrypt();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, " Lỗi khi giải mã: "+ ex);
            }
        });
        loadFileButton.addActionListener(e -> loadFile());
        encryptFile.addActionListener(e -> saveEncryptedFile());
        decryptFile.addActionListener(e -> saveDecryptedFile());

        setVisible(true);
    }

    private void saveDecryptedFile() {
        String selectedAlgorithm = (String) cipherOptions.getSelectedItem();
        String src = nameFile.getText();

        if(src==null||src.equals("")){
            JOptionPane.showMessageDialog(this, " Vui lòng chọn file để thao tác !!! ");
            return;
        }
        if(!src.contains("Encrypt")){
            JOptionPane.showMessageDialog(this, " File này chưa được mã hóa !!! ");
            nameFile.setText("");
            return;
        }
        String des = src.replace("Encrypt", "Decrypt");

        try {
            switch (selectedAlgorithm) {

                case "RC4":
                    rc4Encryption.decryptFile(src, des);
                    break;
            }
            JOptionPane.showMessageDialog(this, " Giải mã thành công !!! ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi giải mã: " + e.getMessage());
        }
    }
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nameOfFile = selectedFile.getName();
            pathFile = selectedFile.getParent();
            try (FileReader reader = new FileReader(selectedFile)) {
                nameFile.setText(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
            }
        }
    }

    private void saveEncryptedFile(){
        String selectedAlgorithm = (String) cipherOptions.getSelectedItem();
        String src = nameFile.getText();
        if(src==null||src.equals("")){
            JOptionPane.showMessageDialog(this, " Vui lòng chọn file để thao tác !!! ");
            return;
        }
        String des =pathFile+"/"+ "Encrypt_"+ nameOfFile;
        try {
            rc4Encryption.encryptFile(src, des);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JOptionPane.showMessageDialog(this, " Mã hóa file thành công!!! ");

    }
    private void resetFields() {
        inputText.setText("");
        encryptedText.setText("");
        decryptedText.setText("");
        nameFile.setText("");
    }

    private void encrypt() throws Exception {
        String selectedCipher = (String) cipherOptions.getSelectedItem();
        String input = inputText.getText();

        // Check if the input text is empty
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu trước khi mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        encryptedText.setText(rc4Encryption.encrypt(input));
        // Implement encryption logic for each cipher type here
    }

    private void decrypt() throws Exception {
        String selectedCipher = (String) cipherOptions.getSelectedItem();
        String encrypted = encryptedText.getText();

        // Check if the input text is empty
        if (encrypted.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Du lieu chua duoc ma hoa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Implement decryption logic for each cipher type here
        decryptedText.setText(rc4Encryption.decrypt(encrypted));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RC4UI::new);
    }
}
