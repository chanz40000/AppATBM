package Decorate;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private Color backgroundColor = new Color(245, 245, 220); // Màu be (beige)
    private Color borderColor = Color.BLACK; // Màu viền đen
    private int cornerRadius = 15; // Bo góc

    // Constructor
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Tắt nền mặc định
        setFocusPainted(false);      // Tắt hiệu ứng viền khi nút được focus
        setBorderPainted(false);    // Tắt viền mặc định
    }

    // Phương thức vẽ lại nút
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Bật khử răng cưa
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ nền bo góc
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Vẽ viền đen
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Gọi phương thức gốc để vẽ chữ và hình ảnh
        super.paintComponent(g2);

        g2.dispose();
        //super.paintComponent(g);
    }

    // Đặt màu nền
    @Override
    public void setBackground(Color bg) {
        this.backgroundColor = bg;
        repaint();
    }

    // Đặt bo góc
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    // Đặt màu viền
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
}
