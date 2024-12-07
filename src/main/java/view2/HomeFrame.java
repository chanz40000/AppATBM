package view2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeFrame extends JPanel {

    public HomeFrame() {

        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                new EmptyBorder(10, 10, 10, 10) // Margin 10px ở tất cả các phía
        ));
        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.setBackground(new Color(173, 216, 230));

        JPanel mainPanel2 = new JPanel();
        mainPanel2.setLayout(new GridLayout(3, 1));
        mainPanel2.setBackground(new Color(173, 216, 230));

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setBackground(new Color(173, 216, 230));
        logoLabel.setBackground(new Color(173, 216, 230));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon logoIcon = new ImageIcon("src//main//resources//image//it-nlu.png"); // Đường dẫn tới file logo
        // Thay đổi kích thước logo
        Image scaledImage = logoIcon.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH); // Kích thước mới
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);

        // Thêm khoảng cách giữa logo và thông tin
        logoLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0)); // 100px khoảng cách dưới logo

        // Thông tin môn học
        JLabel titleLabel = new JLabel("An toàn bảo mật hệ thống thông tin", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titleLabel.setBackground(new Color(173, 216, 230));

        // Thông tin giảng viên và người thực hiện
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(new Color(173, 216, 230));

        JLabel teacherLabel = new JLabel("Giảng viên: Ths.Phan Đình Long", SwingConstants.CENTER);
        teacherLabel.setFont(new Font("Arial", Font.BOLD, 16)); // In đậm

        JLabel studentLabel = new JLabel("Người thực hiện: Ngô Thùy Trang", SwingConstants.CENTER);
        studentLabel.setFont(new Font("Arial", Font.BOLD, 14)); // In đậm

        JLabel mssv = new JLabel("MSSV: 21130574", SwingConstants.CENTER);
        mssv.setFont(new Font("Arial", Font.BOLD, 14)); // In đậm

        infoPanel.add(teacherLabel);
        infoPanel.add(studentLabel);
        infoPanel.add(mssv);

        // Thêm các thành phần vào main panel
        mainPanel.add(logoLabel);
        mainPanel2.add(titleLabel);
        mainPanel2.add(infoPanel);
        mainPanel.add(mainPanel2);

        // Thêm main panel vào frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        // Hiển thị giao diện
        SwingUtilities.invokeLater(() -> {
            HomeFrame frame = new HomeFrame();
            frame.setVisible(true);
        });
    }
}
