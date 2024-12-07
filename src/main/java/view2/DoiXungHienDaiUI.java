package view2;

import Decorate.Image;
import Decorate.RoundedButton;
import controller.doiXung.DoiXungHienDai;
import controller.javaKhongHoTro.RC4Encryption;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;

public class DoiXungHienDaiUI extends JPanel {
    private JComboBox<String> algorithmBox;
    private JComboBox<String> modeBox;
    private JComboBox<String> paddingBox;
    private JComboBox<Integer> keySizeBox;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextArea decryptTextArea;
    private RoundedButton encryptButton;
    private RoundedButton checkButton;
    private RoundedButton reset;
    private RoundedButton loadFileButton;
    private RoundedButton encryptFile;
    private RoundedButton decryptFile;
    private RoundedButton loadKey;
    private RoundedButton loadFileKey;
    JTextField keyText ;
    RoundedButton saveKey ;
    JTextField nameFile;
    String nameOfFile, parentFile;


    public DoiXungHienDaiUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
//        setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.BLACK, 2), // Viền 2px màu xanh
//                new EmptyBorder(10, 10, 10, 10) // Margin 10px ở tất cả các phía
//        ));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        //panel.setBackground(new Color(255, 182, 193));
        JPanel pBox = new JPanel();
        pBox.setLayout(new GridLayout(4, 1));

        JPanel pAlgorithm = new JPanel();
        pAlgorithm.setLayout(new GridLayout(1, 2));
        pAlgorithm.setBackground(new Color(173, 216, 230));
        JLabel jLabel = new JLabel("Algorithm:");
        pAlgorithm.add(jLabel);
        String[] algorithms = {"AES", "DES", "Blowfish", "Twofish", "Camellia", "IDEA", "RC2"};
        algorithmBox = new JComboBox<>(algorithms);
        pAlgorithm.add(algorithmBox);
        pBox.add(pAlgorithm);

        JPanel pKeySize = new JPanel();
        pKeySize.setLayout(new GridLayout(1, 2));
        pKeySize.setBackground(new Color(173, 216, 230));
        JLabel jLabel2 = new JLabel("Key Size:");
        pKeySize.add(jLabel2);
        keySizeBox = new JComboBox<>();
        updateKeySizeOptions();
        pKeySize.add(keySizeBox);
        pBox.add(pKeySize);

        JPanel pMode = new JPanel();
        pMode.setLayout(new GridLayout(1, 2));
        pMode.setBackground(new Color(173, 216, 230));
        JLabel modeLabel = new JLabel("Mode:");
        pMode.add(modeLabel);
        String[] modes = {"ECB", "CBC", "CFB", "OFB", "PCBC"};
        modeBox = new JComboBox<>(modes);
        pMode.add(modeBox);
        pBox.add(pMode);

        JPanel pPadding = new JPanel();
        pPadding.setLayout(new GridLayout(1, 2));
        pPadding.setBackground(new Color(173, 216, 230));
        JLabel paddingLabel = new JLabel("Padding:");
        pPadding.add(paddingLabel);
        String[] paddings = {"PKCS5Padding", "NoPadding"};
        paddingBox = new JComboBox<>(paddings);
        pPadding.add(paddingBox);
        pBox.add(pPadding);

        panel.add(pBox);


        JPanel keyArea = new JPanel();
       // keyArea.setBorder(new EmptyBorder(10, 0, 10, 0));
        keyArea.setBackground(new Color(173, 216, 230));
        keyArea.setBounds(17, 200, 648, 54);
        keyArea.setBackground(new Color(173, 216, 230));
        keyArea.setLayout(new BorderLayout());
        JLabel keyLabel = new JLabel(" Nhập Key: ");
        keyText = new JTextField();

        keyArea.add(keyLabel, BorderLayout.WEST);
        keyArea.add(keyText, BorderLayout.CENTER);

        JPanel buttonKey = new JPanel();
        buttonKey.setBackground(new Color(173, 216, 230));
        loadKey = new RoundedButton("Load Key");
        loadKey.setBounds(400, 82, 120, 37);
        loadKey.setIcon(new ImageIcon(Image.img_load_key));

        loadFileKey = new RoundedButton("Load File Key");
        loadFileKey.setBounds(400, 82, 120, 37);
        loadFileKey.setIcon(new ImageIcon(Image.img_load_key));

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
        fileArea.setBorder(new EmptyBorder(10, 0, 0, 0));
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

        JPanel buttonFile = new JPanel();
        buttonFile.setBackground(new Color(173, 216, 230));
        encryptFile = new RoundedButton("Encrypted File");
        encryptFile.setBounds(400, 82, 120, 37);
        encryptFile.setIcon(new ImageIcon(Image.img_encrypt_file));

        decryptFile = new RoundedButton("Decrypted File");
        decryptFile.setBounds(400, 82, 120, 37);
        decryptFile.setIcon(new ImageIcon(Image.img_decrypt_file));

        buttonFile.add(encryptFile);
        buttonFile.add(decryptFile);

        JPanel allFile = new JPanel();
        allFile.setLayout(new GridLayout(2, 1));
        allFile.add(fileArea);
        allFile.add(buttonFile);

        panel.add(allFile, BorderLayout.NORTH);


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
        outputTextArea = new JTextArea(5, 20);
        JScrollPane outputScroll = new JScrollPane(outputTextArea);
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

        algorithmBox.addActionListener(e -> updateKeySizeOptions());
        loadFileButton.addActionListener(e -> loadFile());
        encryptFile.addActionListener(e -> saveEncryptedFile());
        decryptFile.addActionListener(e -> saveDecryptedFile());
        encryptButton.addActionListener(e -> encryptText());
        checkButton.addActionListener(e -> checkDecryption());
        reset.addActionListener(e -> resetFields());
        loadKey.addActionListener(e -> loadKey());
        saveKey.addActionListener(e -> saveKeys());
        loadFileKey.addActionListener(e -> loadFileKeyy());

        setVisible(true);
    }
    private void loadFileKeyy() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Key File");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                keyText.setText(""); // Xóa nội dung cũ trong JTextArea
                String line;
                while ((line = reader.readLine()) != null) {
                    keyText.setText(line);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi load file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveKeys() {
        String key = keyText.getText();
        if (key.isEmpty() || key.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập key !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);
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
                writer.write(key);
                JOptionPane.showMessageDialog(this, "Key đã được lưu thành công tại: " + fileToSave.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu key: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không chọn thư mục nào. Hủy thao tác lưu key.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void loadKey() {
        String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
        String mode = (String) modeBox.getSelectedItem();
        String padding = (String) paddingBox.getSelectedItem();
        int keySize = (Integer) keySizeBox.getSelectedItem();
        DoiXungHienDai doiXungHienDai = new DoiXungHienDai();
        try {
            String key = doiXungHienDai.generateKey(selectedAlgorithm, keySize);
            keyText.setText(key);
            JOptionPane.showMessageDialog(this, "Load Key Thành công !!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Key Thất bại, vui lòng load lại giúp tôi !!!", "Thông báo", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void saveDecryptedFile() {
        String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
        String mode = (String) modeBox.getSelectedItem();
        String padding = (String) paddingBox.getSelectedItem();
        int keySize = (Integer) keySizeBox.getSelectedItem();

        String encryptFile = nameFile.getText();
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

        DoiXungHienDai aes = new DoiXungHienDai();
        boolean setKeyy =  setKey(selectedAlgorithm, keySize, aes);
        if(!setKeyy)return;
        String keyString = keyText.getText();
        boolean test;
        try {
            test = aes.decryptFile(encryptFile, des, selectedAlgorithm, mode, padding, keySize, keyString);
            JOptionPane.showMessageDialog(this, " Giải mã thành công !!! ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi giải mã: " + e.getMessage());
        }
    }

    private void updateKeySizeOptions() {
        keySizeBox.removeAllItems();
        String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
        if ("AES".equals(selectedAlgorithm)) {
            keySizeBox.addItem(128);
            keySizeBox.addItem(192);
            keySizeBox.addItem(256);
        } else if ("DES".equals(selectedAlgorithm)) {
            keySizeBox.addItem(56);
        } else if ("Blowfish".equals(selectedAlgorithm)) {
            keySizeBox.addItem(32);  // tối thiểu
            keySizeBox.addItem(64);
            keySizeBox.addItem(128); // phổ biến
            keySizeBox.addItem(192);
            keySizeBox.addItem(256); // tối đa
        } else if ("Twofish".equals(selectedAlgorithm)) {
            keySizeBox.addItem(128);
            keySizeBox.addItem(192);
            keySizeBox.addItem(256);
        } else if ("Camellia".equals(selectedAlgorithm)) {
            keySizeBox.addItem(128);
            keySizeBox.addItem(192);
            keySizeBox.addItem(256);
        } else if ("IDEA".equals(selectedAlgorithm)) {
            keySizeBox.addItem(128); // IDEA chỉ hỗ trợ khóa kích thước 128-bit
        } else if ("TripleDES".equals(selectedAlgorithm)) {
            keySizeBox.addItem(112); // 2-key Triple DES (mặc dù không an toàn lắm)
            keySizeBox.addItem(168); // 3-key Triple DES (phổ biến hơn)
        } else if ("RC2".equals(selectedAlgorithm)) {
            keySizeBox.addItem(40);
            keySizeBox.addItem(64);
            keySizeBox.addItem(128);
        } else if ("RC4".equals(selectedAlgorithm)) {
            keySizeBox.addItem(40);  // 40 bit (thường dùng trong các ứng dụng cũ)
            keySizeBox.addItem(56);  // 56 bit
            keySizeBox.addItem(128); // 128 bit
        }
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

    private void saveEncryptedFile(){
        String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
        String mode = (String) modeBox.getSelectedItem();
        String padding = (String) paddingBox.getSelectedItem();
        int keySize = (Integer) keySizeBox.getSelectedItem();
        String src = nameFile.getText();
        if(src==null||src.equals("")){
            JOptionPane.showMessageDialog(this, " Vui lòng chọn file để thao tác !!! ");
            return;
        }
        String des = parentFile+"/"+"Encrypt_"+ nameOfFile;

        DoiXungHienDai aes = new DoiXungHienDai();
        boolean setKeyy =  setKey(selectedAlgorithm, keySize, aes);
        if(!setKeyy)return;
        boolean test;
        String keyString = keyText.getText();
        try {
            test = aes.encryptFile(src, des,selectedAlgorithm, mode, padding, keySize, keyString);
            JOptionPane.showMessageDialog(this, " Mã hóa file thành công!!! ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mã hóa: " + e.getMessage());
        }
    }

    private void encryptText() {

        try {
            if(inputTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, " Vui lòng nhập dữ liệu!!! ");
                return;
            }
            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
            String mode = (String) modeBox.getSelectedItem();
            String padding = (String) paddingBox.getSelectedItem();
            int keySize = (Integer) keySizeBox.getSelectedItem();
            DoiXungHienDai aes = new DoiXungHienDai();

            boolean setKeyy =  setKey(selectedAlgorithm, keySize, aes);
            if(!setKeyy)return;

            String keyString = keyText.getText();
            String test = aes.encrypt(selectedAlgorithm, mode, padding, inputTextArea.getText(), keySize, keyString);
            outputTextArea.setText(test);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mã hóa: " + e.getMessage());
        }
    }
    private boolean setKey(String selectedAlgorithm, int keySize, DoiXungHienDai doiXungHienDai){
        String keyBase64 = keyText.getText();
        if(keyBase64.isEmpty()||keyBase64.equals("")){
            try {
                keyBase64 = doiXungHienDai.generateKey(selectedAlgorithm, keySize);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tạo khóa!!!" );
                return false;
            }
            keyText.setText(keyBase64);
        }

        if(!doiXungHienDai.isKeyValid(keyBase64, selectedAlgorithm, keySize)){
            JOptionPane.showMessageDialog(this, "Khóa không hợp lệ!!!" );
            keyText.setText("");
            return false;
        }
        doiXungHienDai.setKey(doiXungHienDai.getKeyFromBase64(keyBase64, selectedAlgorithm));
        return true;
    }

    private void resetFields() {
        inputTextArea.setText("");
        outputTextArea.setText("");
        nameFile.setText("");
        decryptTextArea.setText("");
        keyText.setText("");
    }
    private void checkDecryption() {
        try {
            if(outputTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, " Vui lòng nhập dữ liệu!!! ");
                return;
            }
            String selectedAlgorithm = (String) algorithmBox.getSelectedItem();
            String mode = (String) modeBox.getSelectedItem();
            String padding = (String) paddingBox.getSelectedItem();
            int keySize = (Integer) keySizeBox.getSelectedItem();
            DoiXungHienDai aes = new DoiXungHienDai();
            boolean setKeyy =  setKey(selectedAlgorithm, keySize, aes);
            if(!setKeyy)return;
            String keyString = keyText.getText();
            String test = aes.decrypt(selectedAlgorithm, mode, padding, outputTextArea.getText(), keySize, keyString);
            decryptTextArea.setText(test);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi giải mã: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoiXungHienDaiUI::new);

    }}
