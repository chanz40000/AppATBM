package view2;

import Decorate.Image;
import Decorate.RoundedButton;
import controller.batDoiXung.RSA;
import controller.doiXung.DoiXungHienDai;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAEncryptionUI extends JPanel {
    private JComboBox<String> algorithmBox;
    private JComboBox<Integer> keySizeBox;
    private JTextArea inputTextArea;
    private JTextArea encryptText;
    private JTextArea decryptTextArea;
    private RoundedButton encryptButton;
    private RoundedButton checkButton;
    private RoundedButton reset;
    private RoundedButton loadFileButton;

    private RoundedButton loadKey;
    JTextField publicKey, privateKey ;
    RoundedButton saveKey ;
    JTextField nameFile;
    private RoundedButton loadFileKey;

    RSA rsa = new RSA();

    public RSAEncryptionUI() {

        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
//        setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.BLACK, 2), // Viền 2px màu xanh
//                new EmptyBorder(10, 10, 10, 10) // Margin 10px ở tất cả các phía
//        ));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        //panel.setBackground(new Color(255, 182, 193));
        JPanel pBox = new JPanel();
        pBox.setLayout(new GridLayout(2, 1));

        JPanel pAlgorithm = new JPanel();
        pAlgorithm.setBorder(new EmptyBorder(10, 0, 10, 0));
        pAlgorithm.setLayout(new GridLayout(1, 2));
        pAlgorithm.setBackground(new Color(173, 216, 230));
        JLabel jLabel = new JLabel("Algorithm:");
        pAlgorithm.add(jLabel);
        String[] algorithms = {"RSA"};
        algorithmBox = new JComboBox<>(algorithms);
        pAlgorithm.add(algorithmBox);
        pBox.add(pAlgorithm);

        JPanel pKeySize = new JPanel();
        pKeySize.setBorder(new EmptyBorder(5, 0, 10, 0));
        pKeySize.setLayout(new GridLayout(1, 2));
        pKeySize.setBackground(new Color(173, 216, 230));
        JLabel jLabel2 = new JLabel("Key Size:");
        pKeySize.add(jLabel2);
        keySizeBox = new JComboBox<>(new Integer[]{1024, 2048, 4096});
        pKeySize.add(keySizeBox);
        pBox.add(pKeySize);

        panel.add(pBox);


        JPanel keyArea = new JPanel();
        keyArea.setBackground(new Color(173, 216, 230));
        keyArea.setBounds(17, 200, 648, 54);
        keyArea.setBackground(new Color(173, 216, 230));
        keyArea.setLayout(new GridLayout(2, 1));

        JPanel keyArea1 = new JPanel();
        keyArea1.setBackground(new Color(173, 216, 230));
        keyArea1.setBounds(17, 200, 648, 54);
        keyArea1.setBackground(new Color(173, 216, 230));
        keyArea1.setLayout(new BorderLayout());
        JLabel keyLabel = new JLabel(" Public key: ");
        publicKey = new JTextField();
        keyArea1.add(keyLabel, BorderLayout.WEST);
        keyArea1.add(publicKey, BorderLayout.CENTER);

        JPanel keyArea2 = new JPanel();
        keyArea2.setBackground(new Color(173, 216, 230));
        keyArea2.setBounds(17, 200, 648, 54);
        keyArea2.setBackground(new Color(173, 216, 230));
        keyArea2.setLayout(new BorderLayout());
        JLabel keyLabel2 = new JLabel(" Private key: ");
        privateKey = new JTextField();
        keyArea2.add(keyLabel2, BorderLayout.WEST);
        keyArea2.add(privateKey, BorderLayout.CENTER);

        keyArea.add(keyArea1);
        keyArea.add(keyArea2);

        JPanel buttonKey = new JPanel();
        buttonKey.setBackground(new Color(173, 216, 230));

        loadFileKey = new RoundedButton("Load File Key");
        loadFileKey.setBounds(400, 82, 120, 37);
        loadFileKey.setIcon(new ImageIcon(Image.img_load_key));

        loadKey = new RoundedButton("Load Key");
        loadKey.setBounds(400, 82, 120, 37);
        loadKey.setIcon(new ImageIcon(Image.img_load_key));

        saveKey = new RoundedButton("Save Key");
        saveKey.setBounds(400, 82, 120, 37);
        saveKey.setIcon(new ImageIcon(Image.img_key));

        buttonKey.add(loadFileKey);
        buttonKey.add(loadKey);
        buttonKey.add(saveKey);

        JPanel allKey = new JPanel();
        allKey.setLayout(new GridLayout(2, 1));
        allKey.add(keyArea);
        allKey.add(buttonKey);

        panel.add(allKey);

        JPanel fileArea = new JPanel();
        fileArea.setBackground(new Color(173, 216, 230));
        fileArea.setBounds(17, 200, 648, 54);
        fileArea.setBackground(new Color(173, 216, 230));
        fileArea.setLayout(new BorderLayout());
        nameFile = new JTextField();
        loadFileButton = new RoundedButton("Open File");
        loadFileButton.setBackground(new Color(215, 187, 18));
        loadFileButton.setBounds(10, 12, 100, 30);
        loadFileButton.setIcon(new ImageIcon(Image.open_file));

        fileArea.add(nameFile, BorderLayout.CENTER);
        fileArea.add(loadFileButton, BorderLayout.EAST);


        JPanel pInputText = new JPanel(new BorderLayout());
        pInputText.add(new JLabel("Input Text:"), BorderLayout.NORTH);
        pInputText.setBackground(new Color(173, 216, 230));
        inputTextArea = new JTextArea(5, 20);
        JScrollPane inputScroll = new JScrollPane(inputTextArea);
        pInputText.add(inputScroll, BorderLayout.CENTER);
        panel.add(pInputText);

        JPanel pOutputText = new JPanel(new BorderLayout());
        pOutputText.setBackground(new Color(173, 216, 230));
        pOutputText.add(new JLabel("Mã hóa:"), BorderLayout.NORTH);
        encryptText = new JTextArea(5, 20);
        JScrollPane outputScroll = new JScrollPane(encryptText);
        pOutputText.add(outputScroll, BorderLayout.CENTER);
        panel.add(pOutputText);

        JPanel pDecryptText = new JPanel(new BorderLayout());
        pDecryptText.setBackground(new Color(173, 216, 230));
        pDecryptText.add(new JLabel(" Giải mã:"), BorderLayout.NORTH);
        decryptTextArea = new JTextArea(5, 20);
        JScrollPane decryptScroll = new JScrollPane(decryptTextArea);
        pDecryptText.add(decryptScroll, BorderLayout.CENTER);
        panel.add(pDecryptText);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));


        encryptButton = new RoundedButton("Encrypt");
        encryptButton.setBounds(400, 82, 120, 37);
        encryptButton.setIcon(new ImageIcon(Image.img_encrypt));


        checkButton = new RoundedButton("Decrypt");
        checkButton.setBounds(400, 82, 120, 37);
        checkButton.setIcon(new ImageIcon(Image.img_decrypt));

        reset = new RoundedButton("Restart");
        reset.setBounds(400, 82, 120, 37);
        reset.setIcon(new ImageIcon(Image.img_reset));

        //buttonPanel.add(loadKey);
        buttonPanel.add(encryptButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(reset);

        add(buttonPanel, BorderLayout.SOUTH);

        encryptButton.addActionListener(e -> encryptText());
        checkButton.addActionListener(e -> checkDecryption());
        reset.addActionListener(e -> resetFields());
        loadKey.addActionListener(e -> loadKey());
        saveKey.addActionListener(e -> saveKeys());
        loadFileKey.addActionListener(e -> loadKeys());

        setVisible(true);
    }

    private void loadKeys() {
        // Mở hộp thoại để chọn tệp
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn tệp chứa Key");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Người dùng đã chọn một tệp
            File selectedFile = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                String publicKeyText = "";
                String privateKeyText = "";

                // Đọc từng dòng của tệp
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("PublicKey=")) {
                        publicKeyText = line.substring("PublicKey=".length());
                    } else if (line.startsWith("PrivateKey=")) {
                        privateKeyText = line.substring("PrivateKey=".length());
                    }
                }
                // Kiểm tra và hiển thị vào JTextField
                if (!publicKeyText.isEmpty() && !privateKeyText.isEmpty()) {
                    publicKey.setText(publicKeyText);
                    privateKey.setText(privateKeyText);
                    JOptionPane.showMessageDialog(this, "Key đã được tải thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Tệp không hợp lệ hoặc không chứa đủ thông tin Key!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải key: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không chọn tệp nào. Hủy thao tác tải key.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void saveKeys() {
        String privateKeyText = privateKey.getText();
        String publicKeyText = publicKey.getText();
        if (privateKeyText.isEmpty() || privateKeyText.equals("")||publicKeyText.isEmpty() || publicKeyText.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ key !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mở hộp thoại để chọn thư mục lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn thư mục lưu key");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Người dùng đã chọn thư mục
            File selectedDirectory = fileChooser.getSelectedFile();

            // Hỏi người dùng nhập tên file
            String fileName = JOptionPane.showInputDialog(this, "Nhập tên file cho key:", "Đặt tên file", JOptionPane.PLAIN_MESSAGE);

            if (fileName == null || fileName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên file không được để trống !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo file .txt để lưu key
            File fileToSave = new File(selectedDirectory, fileName + ".txt");

            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write("PublicKey=" + publicKeyText + "\n");
                writer.write("PrivateKey=" + privateKeyText + "\n");
                writer.flush();
                JOptionPane.showMessageDialog(this, "Key đã được lưu thành công tại: " + fileToSave.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu key: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không chọn thư mục nào. Hủy thao tác lưu key.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void loadKey() {
        int keySize = (Integer) keySizeBox.getSelectedItem();
        try {
            String[] key = rsa.generateKey(keySize);
            publicKey.setText(key[0]);
            privateKey.setText(key[1]);
            JOptionPane.showMessageDialog(this, "Load Key Thành công !!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Key Thất bại, vui lòng load lại giúp tôi !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void encryptText() {

        try {
            if(inputTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, " Vui lòng nhập dữ liệu!!! ");
            }
            String publicKeyText = publicKey.getText();
            String privateKeyText = privateKey.getText();
            if(publicKeyText.isEmpty()||publicKeyText.equals("")||privateKeyText.isEmpty()||privateKeyText.equals("")){
                loadKey();
            }
            if(!rsa.validateRSAKeys(publicKey.getText(), privateKey.getText())){
                JOptionPane.showMessageDialog(this, "Key nhập vào không hợp lệ !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                publicKey.setText("");
                privateKey.setText("");
                return;
            }
            PublicKey publicKey1 = rsa.getPublicKeyFromString(publicKey.getText());
            rsa.setPublicKey(publicKey1);
            String test = rsa.encryptBase64(inputTextArea.getText());
            encryptText.setText(test);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mã hóa: " + e.getMessage());
        }
    }

    private void resetFields() {
        inputTextArea.setText("");
        decryptTextArea.setText("");
        nameFile.setText("");
        encryptText.setText("");
        publicKey.setText("");
        privateKey.setText("");

    }
    private void checkDecryption() {
        try {
            if(encryptText.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, " Vui lòng nhập dữ liệu!!! ");
            }
            String publicKeyText = publicKey.getText();
            String privateKeyText = privateKey.getText();
            if(publicKeyText.isEmpty()||publicKeyText.equals("")||privateKeyText.isEmpty()||privateKeyText.equals("")){
                loadKey();
            }
            if(!rsa.validateRSAKeys(publicKeyText, privateKeyText)){
                JOptionPane.showMessageDialog(this, "Key nhập vào không hợp lệ !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                publicKey.setText("");
                privateKey.setText("");
                return;
            }
            PrivateKey privateKey1 = rsa.getPrivateKeyFromString(privateKey.getText());
            rsa.setPrivateKey(privateKey1);
            String test = rsa.decrypt(encryptText.getText());
            decryptTextArea.setText(test);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi giải mã: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RSAEncryptionUI::new);

    }}
