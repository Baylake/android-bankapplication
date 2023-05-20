package com.example.bank;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

// Создаем класс ButtonTouchListener, который реализует интерфейс View.OnTouchListener
public class ButtonTouchListener implements View.OnTouchListener {

    // Объявляем поля для хранения цветов и контекста
    private final int fromColor;
    private final int toColor;
    private final Context context;

    // Создаем конструктор класса, который принимает цвета и контекст
    public ButtonTouchListener(int fromColor, int toColor, Context context) {
        // Инициализируем поля значениями из параметров
        this.fromColor = fromColor;
        this.toColor = toColor;
        this.context = context;
    }

    // Переопределяем метод onTouch, который обрабатывает касания
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // В зависимости от действия касания
        switch (event.getAction()) {
            // Если нажали на кнопку
            case MotionEvent.ACTION_DOWN: {
                // Создаем аниматор цвета фона кнопки от fromColor к toColor
                ObjectAnimator colorFade = ObjectAnimator.ofObject(v, "backgroundColor", new ArgbEvaluator(), context.getColor(fromColor), context.getColor(toColor));
                // Устанавливаем длительность анимации в 1000 миллисекунд
                colorFade.setDuration(300);
                // Устанавливаем задержку перед началом анимации в 200 миллисекунд
                //colorFade.setStartDelay(200);
                // Запускаем анимацию
                colorFade.start();
                break;
            }
            // Если отпустили кнопку
            case MotionEvent.ACTION_UP: {
                // Создаем аниматор цвета фона кнопки от toColor к fromColor
                ObjectAnimator colorFade = ObjectAnimator.ofObject(v, "backgroundColor", new ArgbEvaluator(), context.getColor(toColor), context.getColor(fromColor));
                // Устанавливаем длительность анимации в 1000 миллисекунд
                colorFade.setDuration(300);
                // Устанавливаем задержку перед началом анимации в 200 миллисекунд
                //colorFade.setStartDelay(200);
                // Запускаем анимацию
                colorFade.start();
                break;
            }
        }
        return false;
    }
}
