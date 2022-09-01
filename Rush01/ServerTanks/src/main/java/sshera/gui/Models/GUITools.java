package sshera.gui.Models;

// Набор методов

import javax.swing.*;
import java.awt.*;

public class GUITools
{
	/**
	 * Метод определения отступа компонентов от границ слева и справа
	 * @param buttons список кнопок
	 */
	public static void createRecommendedMargin(JButton[] buttons)
	{
		for (int i = 0; i < buttons.length; i++) {
			// Объект Insets хранит расстояние от текста до границ кнопки
			Insets margin = buttons[i].getMargin();
			margin.left = 12;
			margin.right = 12;
			buttons[i].setMargin(margin);
		}
	}
	/**
	 * Определение компонентам размера самого большого (по ширине) компонента в группе
	 * Метод придания группе компонентов одинаковых размеров (минимальных,
	 * предпочтительных и максимальных).
	 * @param components список компонентов
	 */
	public static void makeSameSize(JComponent[] components)
	{
		// Массив компонентов
		int[] array = new int[components.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = components[i].getPreferredSize().width;
		}
		// Получение максимального размера
		int maxSizePos = maximumElementPosition(array);
		Dimension maxSize = components[maxSizePos].getPreferredSize();
		// Установка компонентам одинаковых размеров
		for (int i=0; i<components.length; i++) {
			components[i].setPreferredSize(maxSize);
			components[i].setMinimumSize(maxSize);
			components[i].setMaximumSize(maxSize);
		}
	}
	// Корректировка размера текстового поля JTextField
	public static void fixTextFieldSize(JTextField field)
	{
		Dimension size = field.getPreferredSize();
		//	чтобы текстовое поле по-прежнему могло увеличивать свой размер в длину
		size.width = field.getMaximumSize().width;
		//	Определение максимального размера текстового поля
		field.setMaximumSize(size);
	}
	// Метод определения позиции максимального элемента массива
	private static int maximumElementPosition(int[] array)
	{
		int maxPos = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array [maxPos])
				maxPos = i;
		}
		return maxPos;
	}
}