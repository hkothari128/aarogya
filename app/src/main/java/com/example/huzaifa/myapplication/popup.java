package com.example.huzaifa.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class popup extends Activity {

    String[] list = {"The components of food that provide calories include carbohydrates (4 calories per gram), protein (4 calories), fat (9 calories) and alcohol (7 calories).",
            "Calories themselves weigh nothing, but excess calories are stored as potential energy, mostly in the form of body fat. ",
            "Reduce the amount of processed and packaged foods you consume.",
            "Drink a glass after every bathroom break.",
            "Use seasonings on your food instead of sauces. The calories you save can translate into fat loss.",
            "Long-term weight loss isn't easy. But calories in, calories out is still key.",
            "Eat water-rich foods.",
            "Don’t waste your calories on drinks. Drink water instead, and save your calories for nutrient dense foods. Here is a list of 100 nutrient dense and healthy foods to eat.",
            "Same habits equal same results.",
            "Losing weight is just as much of (if not more of) a mental challenge than a physical one. If you can take control of your mind and body, the weight loss will follow.",
            "Choose fitness activities that you enjoy. Your heart knows no difference between basketball or running. It only knows perceived stress. Get your heart rate up any way you can.",
            "Eat at predetermined meal times. Your body adapts to these times, and when it knows it will be getting food soon, it’s less likely to send you hunger signals.",
            "Eat Before You Go Shopping",
            "Fit is not a destination, it is a way of life",
            "It takes a deficit of 3,500 calories to lose one pound.",
            "Make fitness a habit.\n" + "Motivation is what gets you started. Habit is what keeps you going. - Jim Ryin",
            "Eat your stress away. Prevent low blood sugar as it stresses you out. Eat regular and small healthy meals and keep fruit and veggies handy. Herbal teas will also soothe your frazzled nerves. ",
            "Eat Bananas\n" + "People whose diets are rich in potassium may be less prone to high blood pressure. Besides reducing sodium and taking other heart-healthy steps, eat potassium-packed picks such as bananas, cantaloupe, and oranges.",
            "Get enough sleep\n" + "A lack of shut-eye can make you snack, new research from the University of Chicago shows. People who got only 5 1/2 hours of sleep noshed more during the day.\n" + "\n" + "Snooze more and save about 1,087 calories."
    };
    Random r;
    Button close;
    TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        r = new Random();
        tip = (TextView)findViewById(R.id.tip);
        close = (Button)findViewById(R.id.close);

        String tip_taken = list[r.nextInt(list.length)];
        tip.setText(tip_taken);

        getWindow().setLayout((int)(width*0.8),(int)(height*0.3));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
