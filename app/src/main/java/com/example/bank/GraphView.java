package com.example.bank;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * \brief класс реализует view, которая рисует график
 *
 */
public class GraphView extends View {

    ///Данные графика
    private float[] data;
    ///Цвет линии
    private int graphColor;
    ///Цвет осей
    private int axisColor;
    ///Объект для рисования
    private Paint paint;

    ///Индекс текущей точки графика
    private int index;
    ///Объект для анимации
    private ValueAnimator animator;

    /// Конструктор класса, который принимает контекст и атрибуты
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Инициализируем поля значениями по умолчанию или из атрибутов
        data = new float[0]; // пустой массив данных
        graphColor = Color.BLUE; // синий цвет для графика
        axisColor = Color.BLACK; // черный цвет для осей
        paint = new Paint(); // новый объект для рисования
        index = 0; // начальный индекс точки графика
        animator = null; // пока нет аниматора
        // Получаем атрибуты из xml файла, если они есть
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GraphView, 0, 0);
        try {
            // Устанавливаем цвета из атрибутов, если они заданы
            graphColor = a.getColor(R.styleable.GraphView_graphColor, graphColor);
            axisColor = a.getColor(R.styleable.GraphView_axisColor, axisColor);
        } finally {
            // Освобождаем ресурсы
            a.recycle();
        }
    }

    /// Устанавливает данные для графика, нормализует и расширяет для красоты
    public void setData(float[] data) {
        this.data = data;
        this.data = addIntermediateElements(this.data,10);
        this.data=normalization(this.data);
        //this.data=normalization(this.data);
        index = 0; // сбрасываем индекс точки графика
        createAnimator(); // создаем аниматор
        animator.start(); // запускаем анимацию
    }

    /// Создания аниматора
    private void createAnimator() {
        if (animator != null) { // если уже есть аниматор
            animator.cancel(); // отменяем его
        }
        animator = ValueAnimator.ofInt(0, data.length - 1); // создаем новый аниматор от 0 до последнего индекса массива данных
        animator.setDuration(2000); // устанавливаем длительность анимации в 3000 миллисекунд (3 секунды)
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // устанавливаем интерполятор для равномерной скорости анимации
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // добавляем слушатель обновления анимации
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                index = (int) animation.getAnimatedValue(); // получаем текущее значение анимации и присваиваем его индексу точки графика
                invalidate(); // перерисовываем view
            }
        });
    }

    /// Метод рисующий график
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Получаем ширину и высоту view в пикселях
        int width = getWidth();
        int height = getHeight();
        // Рисуем оси координат
        paint.setColor(axisColor); // устанавливаем цвет кисти для осей
        paint.setStrokeWidth(2); // устанавливаем толщину линии в 2 пикселя
        canvas.drawLine(0, height / 2, width, height / 2, paint); // рисуем горизонтальную ось по центру высоты
        canvas.drawLine(width / 2, 0, width / 2, height, paint); // рисуем вертикальную ось по центру ширины
        // Рисуем график, если есть данные
        if (data.length > 0) {
            paint.setColor(graphColor); // устанавливаем цвет кисти для графика
            paint.setStrokeWidth(4); // устанавливаем толщину линии в 4 пикселя
            float xStep = (float) width / (data.length - 1); // вычисляем шаг по оси x в пикселях
            float yScale = (float) (height / (getMax(data) - getMin(data)));
            float prevX = 0; // координата x предыдущей точки графика
            float prevY = height / 2 - data[0] * yScale; // координата y предыдущей точки графика
            for (int i = 1; i <= index; i++) { // проходим по всем элементам массива до текущего индекса анимации
                float x = prevX + xStep; // координата x текущей точки графика
                float y = height / 2 - data[i] * yScale; // координата y текущей точки графика
                canvas.drawLine(prevX, prevY, x, y, paint); // рисуем линию от предыдущей точки к текущей
                prevX = x; // обновляем координату x предыдущей точки
                prevY = y; // обновляем координату y предыдущей точки
            }
        }

    }

    /// Находит максимум массива
    private float getMax(float[] array) {
        float max = array[0]; // инициализируем максимум первым элементом массива
        for (int i = 1; i < array.length; i++) { // проходим по остальным элементам массива
            if (array[i] > max) { // если текущий элемент больше максимума
                max = array[i]; // обновляем максимум
            }
        }
        return max; // возвращаем максимум
    }

    /// Находит минимум массива
    private float getMin(float[] array) {
        float min = array[0]; // инициализируем минимум первым элементом массива
        for (int i = 1; i < array.length; i++) { // проходим по остальным элементам массива
            if (array[i] < min) { // если текущий элемент меньше минимума
                min = array[i]; // обновляем минимум
            }
        }
        return min; // возвращаем минимум
    }

    ///Добавляет данные между уже существующими, чтобы график рисовался плавнее
    public float[] addIntermediateElements(float[] array, int n) {//чтобы график рисовался не рывками а плавно
        // Проверяем, что массив не пустой и не содержит один элемент
        if (array == null || array.length <= 1) {
            return array;
        }
        // Проверяем, что n не отрицательное число
        if (n < 0) {
            return array;
        }
        // Создаем новый массив с достаточным размером для всех элементов
        int newSize = (array.length - 1) * (n + 1) + 1;
        float[] newArray = new float[newSize];
        // Заполняем новый массив промежуточными элементами
        int index = 0; // Индекс для нового массива
        for (int i = 0; i < array.length - 1; i++) {
            // Копируем исходный элемент
            newArray[index] = array[i];
            index++;
            // Вычисляем шаг между промежуточными элементами
            float step = (array[i + 1] - array[i]) / (n + 1);
            // Добавляем промежуточные элементы
            for (int j = 0; j < n; j++) {
                newArray[index] = array[i] + step * (j + 1);
                index++;
            }
        }
        // Копируем последний элемент исходного массива
        newArray[index] = array[array.length - 1];
        return newArray;
    }
    ///Нормализует данные(вычитает Mx из каждой)
    public float[] normalization(float[] data){
        float avg=0;
        for(int i=0;i<data.length;i++){
            avg+=data[i];
        }
        avg=avg/data.length;
        for(int i=0;i<data.length;i++){
            data[i]=data[i]-avg;
        }
        return data;
    }
}