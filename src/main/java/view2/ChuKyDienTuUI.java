package view2;

import Decorate.Image;
import Decorate.RoundedButton;
import controller.chuKyDienTu.DS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ChuKyDienTuUI extends JPanel {
    private JButton reset;
    private JTextArea inputText;
    private JTextArea outputText;
    private JTextField checkText, publicKey, privateKey, nameFile, checkFile;
    private RoundedButton encryptButton, decryptButton, loadFileButton, encryptFile, decryptFile, btn_privateKey;

    String nameOfFile, pathFile;
    DS ds = new DS();

    String pass = "admin";
    String string_privateKey =ds.privateToString();
    public ChuKyDienTuUI(){
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Viền 2px màu xanh
                new EmptyBorder(10, 10, 10, 10) // Margin 10px ở tất cả các phía
        ));
        // Panel nhập liệu và thiết lập
        JPanel panel0 = new JPanel();
        panel0.setLayout(new BorderLayout());



        //panel thấy publicKey
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        jPanel.setLayout(new GridLayout(2, 2));
        jPanel.setBackground(new Color(173, 216, 230));
        jPanel.setBounds(17, 200, 648, 54);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Để thành phần chiếm hết không gian ô.

        JLabel jLabelPublicKey = new JLabel("Public Key:  ");
        jLabelPublicKey.setHorizontalAlignment(SwingConstants.RIGHT); // Canh phải cho đẹp
        JLabel jLabelPrivateKey = new JLabel("Private Key:  ");
        jLabelPrivateKey.setHorizontalAlignment(SwingConstants.RIGHT); // Canh phải cho đẹp

        gbc.gridx = 0; // Cột 0
        gbc.gridy = 0; // Hàng 0
        gbc.weightx = 0.2; // Chiếm 1/5 không gian ngang
        jPanel.add(jLabelPublicKey, gbc);


        publicKey = new JTextField(ds.publicKeyToString());
        gbc.gridx = 1; // Cột 1
        gbc.gridy = 0; // Hàng 0
        gbc.weightx = 0.75; // Chiếm 3/4 không gian ngang
        jPanel.add(publicKey, gbc);

        jPanel.add(jLabelPrivateKey, gbc);
        privateKey = new JTextField("");
        gbc.gridx = 1; // Cột 1
        gbc.gridy = 0; // Hàng 0
        gbc.weightx = 0.75; // Chiếm 3/4 không gian ngang
        jPanel.add(privateKey, gbc);

        add(jPanel);

        add(jPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(173, 216, 230));


        //khu vuc ky file
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
        buttonFile.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonFile.setBackground(new Color(173, 216, 230));
        encryptFile = new RoundedButton("Sign File");
        encryptFile.setBounds(400, 82, 120, 37);
        encryptFile.setIcon(new ImageIcon(Image.img_encrypt_file));

        decryptFile = new RoundedButton("Verify File");
        decryptFile.setBounds(400, 82, 120, 37);
        decryptFile.setIcon(new ImageIcon(Image.img_decrypt_file));

        btn_privateKey = new RoundedButton("Private Key");
        btn_privateKey.setBounds(400, 82, 120, 37);
        btn_privateKey.setIcon(new ImageIcon(Image.img_decrypt_file));
        //btn_privateKey.setBackground(new Color(248, 238, 241));


        buttonFile.add(btn_privateKey);
        buttonFile.add(encryptFile);
        buttonFile.add(decryptFile);

        JPanel allFile = new JPanel();
        allFile.setBackground(new Color(173, 216, 230));
        allFile.setLayout(new GridLayout(2, 1));
        allFile.add(fileArea);
        allFile.add(buttonFile);

        panel.add(allFile);
        // Khu vực nhập văn bản
        inputText = new JTextArea(5, 30);
        inputText.setBorder(BorderFactory.createTitledBorder("Trước khi ký text"));
        panel.add(new JScrollPane(inputText));

        // Khu vực hiển thị văn bản mã hóa hoặc giải mã
        outputText = new JTextArea(5, 30);
        outputText.setBorder(BorderFactory.createTitledBorder("Sau khi ký Text"));
        outputText.setEditable(false);
        panel.add(new JScrollPane(outputText));

        panel0.add(panel, BorderLayout.CENTER);

        checkText = new JTextField();
        checkText.setBorder(BorderFactory.createTitledBorder("Kết quả check text"));
        //panel0.add(checkText, BorderLayout.SOUTH);
        add(panel0, BorderLayout.CENTER);

        // Panel các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));
        encryptButton = new RoundedButton("Sign");
        encryptButton.setBounds(400, 82, 120, 37);
        encryptButton.setIcon(new ImageIcon(Image.img_encrypt));


        decryptButton = new RoundedButton("Verify");
        decryptButton.setBounds(400, 82, 120, 37);
        decryptButton.setIcon(new ImageIcon(Image.img_decrypt));

        reset = new RoundedButton("Restart");
        reset.setBounds(400, 82, 120, 37);
        reset.setIcon(new ImageIcon(Image.img_reset));

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(reset);

        add(buttonPanel, BorderLayout.SOUTH);


        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptText();
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptText();
            }
        });

        encryptFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptFile();
            }
        });

        decryptFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyFile();
            }
        });

        reset.addActionListener(e -> resetFields());
        loadFileButton.addActionListener(e -> loadFile());
        btn_privateKey.addActionListener(e -> lookPrivateKey());
        
        
    }

    private void lookPrivateKey() {
        // Hiển thị hộp thoại nhập mật khẩu
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
                this,
                passwordField,
                "Enter Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Kiểm tra xem người dùng đã nhập mật khẩu và nhấn OK
        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            // Kiểm tra mật khẩu
            if (pass.equals(password)) { // Mật khẩu đúng là "123456"
                privateKey.setText(string_privateKey);
                JOptionPane.showMessageDialog(
                        this,
                        "Private key đã được hiển thị",
                        "Đã cấp quyền truy cập",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Nhập sai password!",
                        "Từ chối quyền truy cập",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void resetFields() {
        inputText.setText("");
        outputText.setText("");
        checkText.setText("");
        nameFile.setText("");
        //checkFile.setText("");
    }
    // Phương thức mã hóa văn bản
    private void encryptText() {
        String textToEncrypt = inputText.getText();
        System.out.println(textToEncrypt);
        if (textToEncrypt == null || textToEncrypt.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liệu để mã hóa!!! ");
            return;
        }

        try {
            outputText.setText(ds.sign(textToEncrypt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void encryptFile() {
        String textToEncrypt = nameFile.getText();
        System.out.println(textToEncrypt);
        if (textToEncrypt == null || textToEncrypt.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, " Vui lòng chọn file để thao tác !!! ");
            return;
        }

        try {
            outputText.setText(ds.signFile(textToEncrypt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void verifyFile() {
        try {
            boolean test = ds.verifyFile(nameFile.getText(), outputText.getText());
            checkText.setText("Kết quả kiểm tra: "+(test?"":"không")+" giống với giữ liệu đưa vào");

            JOptionPane.showMessageDialog(this, "Kết quả kiểm tra: "+(test?"":"không")+" giống với giữ liệu đưa vào");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Decryption error: " + e.getMessage());
        }
    }

    // Phương thức giải mã văn bản
    private void decryptText() {
        try {
            boolean test = ds.verify(inputText.getText(), outputText.getText());
            checkText.setText("Kết quả kiểm tra: "+(test?"":"không")+" giống với giữ liệu đưa vào");
            JOptionPane.showMessageDialog(this, "Kết quả kiểm tra: "+(test?"":"không")+" giống với giữ liệu đưa vào");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Decryption error: " + e.getMessage());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ChuKyDienTuUI().setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
