package com.example.a5alumno.sensormanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private View view;
    private TextView txtview_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Para obtener y guardar el background de la main activity
        view = this.getWindow().getDecorView();
        this.txtview_hello = (TextView) findViewById(R.id.hello);
        txtview_hello.setText("SHAKE ME!!!!!");

        //Sensor Manager para mostrar el Toast
        this.mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> myDeviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        String sensorList = "";
        for (Sensor aSensor : myDeviceSensors) {
            sensorList = sensorList + aSensor.getName() + "\n";
        }
        Toast.makeText(this, sensorList, Toast.LENGTH_LONG).show();

        //Para el acelerómetro
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake() {
                //Aqui lanzo el metodo para cambiar el color del background
                handleShakeEvent();
            }
        });
    }

    private void handleShakeEvent() {
        //Toast.makeText(this, "BAAAAAAAAAAAAAAAAAAAAH", Toast.LENGTH_LONG).show();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        view.setBackgroundColor(color);
        Random rnd2 = new Random();
        int color2 = Color.argb(255, rnd2.nextInt(256), rnd2.nextInt(256), rnd2.nextInt(256));
        txtview_hello.setTextColor(color2);


    }

    //No olvidar de hacer la movida esta ni la del onPause() para no joder la bateria de los cojones
    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
