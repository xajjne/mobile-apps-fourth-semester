package com.mirea.zolotovskiyas.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {

    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String ageStr = bundle.getString("AGE");
                String job = bundle.getString("JOB");

                int age = Integer.parseInt(ageStr);

                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String result = "Возраст: " + age +
                        ", профессия: " + job +
                        ". Задержка составила " + age + " секунд.";

                Log.d("MyLooper", "Получено сообщение: " + result);

                Message message = new Message();
                Bundle resultBundle = new Bundle();
                resultBundle.putString("RESULT", result);
                message.setData(resultBundle);

                mainHandler.sendMessage(message);
            }
        };

        Looper.loop();
    }
}