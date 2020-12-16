package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView scanButton;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initViews();


    }

    private void initViews() { // Функция инициализации компонентов взаимодействия
        scanButton = findViewById(R.id.textView);
        surfaceView = findViewById(R.id.surfaceView);



    }

    private void initialiseDetectorsAndSources() {


        barcodeDetector = new BarcodeDetector.Builder(this) // Инициализация детектора
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector) // камера
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // создание холдера для вывода камеры
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            // Чекаем привелегии
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        // Создаем процесс детектора
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                
            }
            // Функция обнаружения
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    scanButton.post(new Runnable() {
                        @Override
                        public void run() {
                                intentData = barcodes.valueAt(0).displayValue;
                            Intent infoPage = new Intent(MainActivity.this,AboutActivity.class);
                            infoPage.putExtra("info",intentData);
                            startActivity(infoPage);
                        }
                    });

                }else{
                    scanButton.setClickable(false);
                    scanButton.setText("Сканировать");
                }
            }
        });
    }


    public void ScanCode(View view){ // Используем QR

    }
    @Override
    protected void onPause() { //Приложение свернуто - Камера останавливается
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() { // Приложение работает - запускаем детектор
        super.onResume();
        initialiseDetectorsAndSources();


    }

}