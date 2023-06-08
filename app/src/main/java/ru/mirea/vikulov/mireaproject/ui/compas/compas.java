package ru.mirea.vikulov.mireaproject.ui.compas;

import static android.content.Context.SENSOR_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import ru.mirea.vikulov.mireaproject.R;
import ru.mirea.vikulov.mireaproject.databinding.FragmentCompasBinding;

public class compas extends Fragment implements SensorEventListener {

    //Объявляем картинку для компаса
    private ImageView HeaderImage;
    //Объявляем функцию поворота картинки
    private float RotateDegree = 0f;
    //Объявляем работу с сенсором устройства
    private SensorManager mSensorManager;
    //Объявляем объект TextView
    TextView CompOrient;
    private Context mContext;

    private FragmentCompasBinding binding;


    private boolean isWork = false;
    private static final int REQUEST_CODE_PERMISSION = 100;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompasBinding.inflate(inflater, container, false);
        mContext = inflater.getContext();
        //Инициализируем возможность работать с сенсором устройства:
        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
        checkPerm();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    public void checkPerm(){
        //Связываем объект ImageView с нашим изображением:
        HeaderImage = binding.CompassView;
        //TextView в котором будет отображаться градус поворота:
        CompOrient = binding.Header;
        int locationPermStatus = ContextCompat.checkSelfPermission(mContext, Manifest.permission.LOCATION_HARDWARE);
        if (locationPermStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.LOCATION_HARDWARE}, REQUEST_CODE_PERMISSION);
        }
        System.out.println("Инициализировано");
    }


    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this,mSensorManager
                .getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();

        //Останавливаем при надобности слушателя ориентации
        //сенсора с целью сбережения заряда батареи:
        mSensorManager.unregisterListener(this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        //Получаем градус поворота от оси, которая направлена на север, север = 0 градусов:
        float degree = event.values[0]*100;
        CompOrient.setText("Отклонение от севера: " + Float.toString(degree) + " градусов");

        //Создаем анимацию вращения:
        RotateAnimation rotateAnimation = new RotateAnimation(
                RotateDegree, -degree, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //Продолжительность анимации в миллисекундах:
        rotateAnimation.setDuration(210);

        //Настраиваем анимацию после завершения подсчетных действий датчика:
        rotateAnimation.setFillAfter(true);

        //Запускаем анимацию:
        HeaderImage.startAnimation(rotateAnimation);
        RotateDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}