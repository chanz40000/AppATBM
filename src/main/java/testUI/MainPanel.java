package testUI;

import view2.*;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new BorderLayout());

        // Tạo JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(246, 227, 232));

        tabbedPane.addTab("Home ", new HomeFrame());
        tabbedPane.addTab("Căn Bản ", new DoiXungCoBan());
        tabbedPane.addTab("Hiện đại", new DoiXungHienDaiUI());
        tabbedPane.addTab("RC4", new RC4UI());
        tabbedPane.addTab("RSA", new RSAEncryptionUI());
        tabbedPane.addTab("Hash", new HashView());
        tabbedPane.addTab("Chữ Ký Điện Tử ", new ChuKyDienTuUI());

        // Thêm tabbedPane vào layout chính
        add(tabbedPane, BorderLayout.CENTER);
    }
}
