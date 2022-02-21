package com.example.quiz_battle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class singlemode_questions extends AppCompatActivity {

    public interface VolleyCallback {
        void onSuccess(JSONArray result);
    }

    RadioButton Antwort_A, Antwort_B, Antwort_C, Antwort_D;
    RadioGroup radioGroup;
    Button Antworten, btn_Category_1, btn_Category_2, btn_Category_3, btn_Category_4;
    TextView Frage;
    String correct;
    Integer intQuestion = 0, choice, category;
    Boolean isCorrect;

    private static final String KEY_CATEGORY = "category";

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private String questio_url = "https://appprojekt2021.000webhostapp.com/question.php";

    public JSONArray loadedQuestions;

    private Context mContext;

    int blueColorValue = Color.parseColor("#0066ff");
    int redColorValue = Color.parseColor("#ff6600");
    int yellowColorValue = Color.parseColor("#ffff99");
    int grey = Color.parseColor("#636363");
    int defaultGreen = Color.parseColor("#00cc99");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlemode_questions);

        Antworten = (Button) findViewById(R.id.answer);


        Frage = (TextView) findViewById(R.id.txtQuestion);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        Antwort_A = (RadioButton) findViewById(R.id.reply_A);
        Antwort_B = (RadioButton) findViewById(R.id.reply_B);
        Antwort_C = (RadioButton) findViewById(R.id.reply_C);
        Antwort_D = (RadioButton) findViewById(R.id.reply_D);

        mContext = getApplicationContext();

        category(blueColorValue);

        Antworten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColors();


                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                    }
                }, 5000);*/



                intQuestion++;
                try {
                    processQuestion(loadedQuestions, intQuestion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.reply_A:

                        /*if (Antwort_A.getText() == correct){
                            Antwort_A.setBackgroundColor(blueColorValue);
                        }
                        else {
                            Antwort_A.setBackgroundColor(blueColorValue);
                        }*/

                        Antwort_A.setBackgroundColor(blueColorValue);
                        Antwort_B.setBackgroundColor(defaultGreen);
                        Antwort_C.setBackgroundColor(defaultGreen);
                        Antwort_D.setBackgroundColor(defaultGreen);
                        checkAnswer(Antwort_A.getText().toString());
                        choice = 1;


                        break;

                    case R.id.reply_B:
                        Antwort_B.setBackgroundColor(blueColorValue);
                        Antwort_A.setBackgroundColor(defaultGreen);
                        Antwort_C.setBackgroundColor(defaultGreen);
                        Antwort_D.setBackgroundColor(defaultGreen);
                        checkAnswer(Antwort_B.getText().toString());
                        choice = 2;
                        break;

                    case R.id.reply_C:
                        Antwort_C.setBackgroundColor(blueColorValue);
                        Antwort_A.setBackgroundColor(defaultGreen);
                        Antwort_B.setBackgroundColor(defaultGreen);
                        Antwort_D.setBackgroundColor(defaultGreen);
                        checkAnswer(Antwort_C.getText().toString());
                        choice = 3;
                        break;

                    case R.id.reply_D:
                        Antwort_D.setBackgroundColor(blueColorValue);
                        Antwort_A.setBackgroundColor(defaultGreen);
                        Antwort_B.setBackgroundColor(defaultGreen);
                        Antwort_C.setBackgroundColor(defaultGreen);
                        checkAnswer(Antwort_D.getText().toString());
                        choice = 4;
                        break;


                }
            }
        });


    }


    public void category(int gruen) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View categorypopupView = getLayoutInflater().inflate(R.layout.category, null);


        btn_Category_1 = (Button) categorypopupView.findViewById(R.id.category_A);
        btn_Category_2 = (Button) categorypopupView.findViewById(R.id.category_B);
        btn_Category_3 = (Button) categorypopupView.findViewById(R.id.category_C);
        btn_Category_4 = (Button) categorypopupView.findViewById(R.id.category_D);

        dialogBuilder.setView(categorypopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        btn_Category_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Category_1.setBackgroundColor(gruen);
                category = 1;
                onResume();
                try {
                    processQuestion(loadedQuestions, intQuestion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btn_Category_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Category_2.setBackgroundColor(gruen);
                category = 2;
                onResume();
                try {
                    processQuestion(loadedQuestions, intQuestion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btn_Category_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Category_3.setBackgroundColor(gruen);
                category = 3;
                onResume();
                try {
                    processQuestion(loadedQuestions, intQuestion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        btn_Category_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Category_4.setBackgroundColor(gruen);
                category = 4;
                onResume();
                try {
                    processQuestion(loadedQuestions, intQuestion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
    }


    private void processQuestion(JSONArray questions, Integer i) throws JSONException {

        JSONObject row = questions.getJSONObject(i);
        Frage.setText(row.getString("ask"));

        ArrayList<RadioButton> list = new ArrayList<RadioButton>();

        list.add(Antwort_A);
        list.add(Antwort_B);
        list.add(Antwort_C);
        list.add(Antwort_D);

        Collections.shuffle(list);

        list.get(0).setText(row.getString("reply_1"));
        list.get(1).setText(row.getString("reply_2"));
        list.get(2).setText(row.getString("reply_3"));
        list.get(3).setText(row.getString("correct_answer"));



        correct = row.getString("correct_answer");

    }

    private void checkAnswer(String answerGiven){
        if (answerGiven.equals(correct)) {
            isCorrect = true;
        }
        else {
            isCorrect = false;
        }

    }

    private void sleep(Integer sec){
        try
        {
            Thread.sleep(sec);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    private void setColors(){
        if (isCorrect) {
            if (choice == 1){
                Antwort_A.setBackgroundColor(yellowColorValue);
            }
            if (choice == 2){
                Antwort_B.setBackgroundColor(yellowColorValue);
            }
            if (choice == 3){
                Antwort_C.setBackgroundColor(yellowColorValue);
            }
            if (choice == 4){
                Antwort_D.setBackgroundColor(yellowColorValue);
            }
        }
        else {
            if (choice == 1){
                Antwort_A.setBackgroundColor(redColorValue);
            }
            if (choice == 2){
                Antwort_B.setBackgroundColor(redColorValue);
            }
            if (choice == 3){
                Antwort_C.setBackgroundColor(redColorValue);
            }
            if (choice == 4){
                Antwort_D.setBackgroundColor(redColorValue);
            }
        }

        //sleep(5000);


        /*Antwort_A.setBackgroundColor(defaultGreen);
        Antwort_B.setBackgroundColor(defaultGreen);
        Antwort_C.setBackgroundColor(defaultGreen);
        Antwort_D.setBackgroundColor(defaultGreen);*/

    }

    private void question(final VolleyCallback callback) throws JSONException {

        //try {
        //JSONArray request = new JSONArray();
        //request.put(Integer.parseInt(KEY_CATEGORY), category);


        /*} catch (JSONException e) {
            e.printStackTrace();
        }*/

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //JSONArray request = new JSONArray();


        System.out.println("Category: " + category);

        if (category != null) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, questio_url + "?category=" + category, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    loadedQuestions = response;
                    System.out.println("onResponse postiv");
                    callback.onSuccess(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Fehler: " + error);

                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_SHORT).show();
                            btn_Category_2.setText("Test2");
                        }
                    }
            );

            System.out.println("Array: " + jsonArrayRequest);
            System.out.println("Request finished");

            requestQueue.add(jsonArrayRequest);

        }
        else {
            System.out.println("Error (Category null): ");
        }


    }


    public void onResume(){
        super.onResume();

        try {
            question(new VolleyCallback(){
                @Override
                public void onSuccess(JSONArray result){
                    loadedQuestions = result;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}