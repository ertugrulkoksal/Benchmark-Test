package com.atalay.myapplication;


import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView result;
    private Button btnPress;
    private String testString ,  hashVal, md5Val,st;
    private Long totalTime,totalTime2, totalTime3;
    private Integer finish;
    private final Integer code = 9999999;
    private final int[] sc={6,4,7,5};
    private  TextView otherDevice;



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Long tT3 = (Long) msg.obj;
            result.setText("Brute Force: " + tT3.toString());
            finish++;
            totalTime3 = tT3;
            if (finish==3) {
                printout();
            }

        }
    };

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Long tT2 = (Long) msg.obj;
            result.setText("MD5: " + tT2.toString());
            finish++;
            totalTime2 = tT2;
            if (finish==3) {
                printout();
            }
        }
    };

    Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Long tT = (Long) msg.obj;
            result.setText("SHA-1: " + tT.toString());
            finish++;
            totalTime = tT;
            if (finish==3) {
                printout();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPress = (Button) findViewById(R.id.btn1);
        result = (TextView) findViewById(R.id.textView2);
        otherDevice = findViewById(R.id.otherDevice);

    }



    public void printout() {
        btnPress.setText("CLICK HERE \n TO TEST AGAIN");
        long average = (totalTime2 + totalTime3 + totalTime) / 3;
        int score = Math.round(average / 100000000);
        result.setText("Taken Times \n\n SHA-1: " + totalTime.toString() + "\n MD5: " + totalTime2.toString() + "\n Brute Force: " + totalTime3.toString() + "\n \n Score: " + Integer.toString(score));
        st ="Pixel 2 "+"Score: " + sc[0]  +"\nPixel 3 "+"Score: " + sc[1]+"\nPixel 5 "+"Score: " + sc[2]+"\nNexus 6P "+"Score: " + sc[3];
        otherDevice.setText(st);
    }

    public void onBeginClick(View view) {
        finish = 0;
        computeSHAHash(testString);
        computeMD5Hash(testString);
        btnPress.setText("PROCESSING...");
        computebruteForce();
    }

    public void computebruteForce() {

        Runnable r = new Runnable() {
            public void run() {
                long tsLong3 = System.nanoTime();
                int successfulcode = 0;
                for (int i = 0; i < code; i++) {
                    successfulcode = i;
                }
                long ttLong3 = System.nanoTime() - tsLong3;
                Message msg = Message.obtain();
                msg.obj = ttLong3;
                msg.setTarget(handler);
                if (successfulcode != 0) {
                    msg.sendToTarget();
                }

            }
        };
        Thread newThread = new Thread(r);
        newThread.start();

    }

    public void computeMD5Hash(String password) {
        Runnable r = new Runnable() {
            public void run() {
                long tsLong = System.nanoTime();
                for (int s = 0; s < 20000; s++) {
                    try {
                        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                        digest.update("The big bad wolf".getBytes());
                        byte messageDigest[] = digest.digest();
                        StringBuffer MD5Hash = new StringBuffer();
                        for (int i = 0; i < messageDigest.length; i++) {
                            String h = Integer.toHexString(0xFF & messageDigest[i]);
                            while (h.length() < 2)
                                h = "0" + h;
                            MD5Hash.append(h);
                        }
                        md5Val = MD5Hash.toString();
                    } catch (NoSuchAlgorithmException e) {
                        Log.e("Benchmark", "Error initializing MD5");
                    }
                }
                long ttLong2 = System.nanoTime() - tsLong;
                Message msg = Message.obtain();
                msg.obj = ttLong2;
                msg.setTarget(handler2);
                msg.sendToTarget();
            }
        };
        Thread newThread = new Thread(r);
        newThread.start();
    }


    public void computeSHAHash(String password) {
        Runnable r = new Runnable() {
            public void run() {
                long tsLong = System.nanoTime();
                for (int i = 0; i < 20000; i++) {
                    MessageDigest mdSha1 = null;
                    try {
                        mdSha1 = MessageDigest.getInstance("SHA-1");
                    } catch (NoSuchAlgorithmException e1) {
                        Log.e("Benchmark", "Error initializing SHA1");
                    }
                    try {
                        mdSha1.update("The big bad wolf".getBytes("ASCII"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    byte[] data = mdSha1.digest();
                    StringBuffer sb = new StringBuffer();
                    String hex = null;
                    hex = Base64.encodeToString(data, 0, data.length, 0);
                    sb.append(hex);
                    hashVal = sb.toString();
                }
                long ttLong3 = System.nanoTime() - tsLong;
                Message msg = Message.obtain();
                msg.obj = ttLong3;
                msg.setTarget(handler3);
                msg.sendToTarget();
            }
        };
        Thread newThread = new Thread(r);
        newThread.start();
    }

}