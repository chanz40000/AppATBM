package testUI;

import Decorate.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;


public class Main extends JFrame {

    public Main() {
        setTitle("Ứng Dụng Mã Hóa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);

        // Đặt biểu tượng cho ứng dụng
        setIconImage(new ImageIcon(Image.img_avatar).getImage());

        add(new MainPanel());

        setVisible(true);
    }


}
