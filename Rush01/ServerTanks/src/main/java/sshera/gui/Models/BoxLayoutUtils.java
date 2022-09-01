package sshera.gui.Models;

import javax.swing.*;

public class BoxLayoutUtils
{
	// Выравнивание компонентов по оси X для группы компонентов
	public static void setGroupAlignmentX(JComponent[] components, float alignment) {
		for (int i = 0; i < components.length; i++)
			components[i].setAlignmentX(alignment);
	}
	// Выравнивание компонентов по оси Y для группы компонентов
	public static void setGroupAlignmentY(JComponent[] components, float alignment) {
		for (int i = 0; i < components.length; i++)
			components[i].setAlignmentY(alignment);
	}
	// Создание панели с вертикальным расположением
	public static JPanel createVerticalPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		return panel;
	}
	// Создание панели с горизонтальным расположением
	public static JPanel createHorizontalPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		return panel;
	}
}