package Decorate;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ComboBoxRenderer extends DefaultListCellRenderer {
    private final Map<String, Icon> iconMap;

    public ComboBoxRenderer(Map<String, Icon> iconMap) {
        this.iconMap = iconMap;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Hiển thị icon nếu có
        if (value != null) {
            String text = value.toString();
            label.setIcon(iconMap.get(text));
            label.setText(text); // Hiển thị tên
        }
        return label;
    }
}
