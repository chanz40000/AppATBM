package view2;

import Decorate.Image;
import Decorate.RoundedButton;
import controller.Hash.Hash;
import controller.doiXung.AffineCipher;
import controller.doiXung.HillCipher;
import controller.doiXung.ShiftEncoding;
import controller.doiXung.SubstitutionCipher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HashView extends JPanel {
    private JComboBox<String> cipherOptions;
    private JTextArea inputText, encryptedText;
    private RoundedButton encryptButton, restartButton;

    private RoundedButton loadFileButton;
    private RoundedButton encryptFile;
    JTextField nameFile;
    String nameOfFile, parentFile;
    Hash hash = new Hash();

    public HashView() {

        // Initialize components
        cipherOptions = new JComboBox<>(new String[]{"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"});

        inputText = new JTextArea(3, 20);
        encryptedText = new JTextArea(3, 20);
        encryptButton = new RoundedButton("Mã hóa");
        restartButton = new RoundedButton("Khởi động lại");

        // Configure text areas
        encryptedText.setEditable(false);

        // Main layout with GridLayout
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Viền 2px màu xanh
                new EmptyBorder(10, 10, 10, 10) // Margin 10px ở tất cả các phía
        ));
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainPanel.setBackground(new Color(173, 216, 230));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Thêm viền 5px cho mainPanel
        add(mainPanel, BorderLayout.CENTER);

        // Cipher option selection
        JPanel cipherSelectionPanel = new JPanel(new GridLayout(1, 2));
        cipherSelectionPanel.setBackground(new Color(173, 216, 230));
        cipherSelectionPanel.setPreferredSize(new Dimension(650, 50));


        cipherSelectionPanel.add(new JLabel("   Chọn phương pháp mã hóa:"));
        cipherSelectionPanel.add(cipherOptions);

        add(cipherSelectionPanel, BorderLayout.NORTH);


        JPanel file = new JPanel();
        file.setBorder(new EmptyBorder(10, 0, 10, 0));
        file.setBackground(new Color(173, 216, 230));
        file.setBounds(17, 200, 648, 54);
        file.setLayout(new GridLayout(2, 1));

        JPanel fileArea = new JPanel();
        fileArea.setBorder(new EmptyBorder(10, 0, 10, 0));
        fileArea.setBackground(new Color(173, 216, 230));
        fileArea.setBounds(17, 200, 648, 54);
        //fileArea.setBackground(new Color(217, 217, 217));
        fileArea.setLayout(new BorderLayout());
        nameFile = new JTextField();
        loadFileButton = new RoundedButton("Open File");
        loadFileButton.setBackground(new Color(215, 187, 18));
        loadFileButton.setBounds(10, 12, 100, 30);
        loadFileButton.setIcon(new ImageIcon(Image.open_file));

        fileArea.add(nameFile, BorderLayout.CENTER);
        fileArea.add(loadFileButton, BorderLayout.EAST);

        JPanel buttonFile = new JPanel();
        buttonFile.setBackground(new Color(173, 216, 230));
        encryptFile = new RoundedButton("Hash File");
        encryptFile.setBounds(400, 82, 120, 37);
        encryptFile.setIcon(new ImageIcon(Image.img_encrypt_file));


        buttonFile.add(encryptFile);

        file.add(fileArea);
        file.add(buttonFile);

        mainPanel.add(file);
        // Input text area
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(173, 216, 230));
        inputPanel.add(new JLabel("Chuỗi đầu vào:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputText), BorderLayout.CENTER);
        mainPanel.add(inputPanel);

        // Encrypted text area
        JPanel encryptedPanel = new JPanel(new BorderLayout());
        encryptedPanel.setBackground(new Color(173, 216, 230));
        encryptedPanel.add(new JLabel("Dữ liệu đã mã hóa:"), BorderLayout.NORTH);
        encryptedPanel.add(new JScrollPane(encryptedText), BorderLayout.CENTER);
        mainPanel.add(encryptedPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(173, 216, 230));

        encryptButton = new RoundedButton("Sign");
        encryptButton.setBounds(400, 82, 120, 37);
        encryptButton.setIcon(new ImageIcon(Image.img_encrypt));

        restartButton = new RoundedButton("Restart");
        restartButton.setBounds(400, 82, 120, 37);
        restartButton.setIcon(new ImageIcon(Image.img_reset));

        buttonPanel.add(encryptButton);
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);


        restartButton.addActionListener(e -> resetFields());
        loadFileButton.addActionListener(e -> loadFile());
        // Placeholder for encryption/decryption logic
        encryptButton.addActionListener(e -> encrypt());
        encryptFile.addActionListener(e -> encryptedFile());


        setVisible(true);
    }
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            nameOfFile = selectedFile.getName();
            parentFile = selectedFile.getParent();
            try (FileReader reader = new FileReader(selectedFile)) {
                nameFile.setText(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
            }
        }
    }

    private void encryptedFile() {
        String path = nameFile.getText();
        if(path.equals("")||path.isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String selectedAlgorithm = (String) cipherOptions.getSelectedItem();
        try {
            String hashText = hash.hashFile(path, selectedAlgorithm);
            encryptedText.setText(hashText);
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "Error: "+ e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: "+ e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void resetFields() {
        inputText.setText("");
        encryptedText.setText("");
        nameFile.setText("");
    }

    private void encrypt() {
        String selectedAlgorithm = (String) cipherOptions.getSelectedItem();
        String input = inputText.getText();

        // Check if the input text is empty
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu trước khi mã hóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String encryptText = hash.hash(input, selectedAlgorithm);
            encryptedText.setText(encryptText);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HashView::new);
    }
}
