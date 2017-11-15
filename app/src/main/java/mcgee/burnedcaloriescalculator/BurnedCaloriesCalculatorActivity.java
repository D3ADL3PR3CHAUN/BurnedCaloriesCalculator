package mcgee.burnedcaloriescalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class BurnedCaloriesCalculatorActivity extends AppCompatActivity implements OnEditorActionListener {

    //define widget variables
    private TextView weightTV;
    private TextView milesranTV;
    private TextView caloriesburnedTV;
    private TextView heightTV;
    private TextView bmiTV;
    private TextView milesTV;
    private TextView caloriesTV;
    private TextView bmiamountTV;
    private EditText weightET;
    private EditText nameET;
    private Spinner heightFT;
    private Spinner heightIN;
    private SeekBar milesranSB;

    //instance variables
    private String weightAmountString = "";
    private Double weightAmount = 000.0;
    private int milesRan = 00;
    private Double bmiAmount = 0.0;
    private int feet = 0;
    private int inches = 0;
    private int seekBarProgress;

    private SharedPreferences savedValues;

    //format for decimals



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories_calculator);

        //get reference to the widgets
        weightTV = (TextView) findViewById(R.id.weightTV);
        milesranTV = (TextView) findViewById(R.id.milesranTV);
        caloriesburnedTV = (TextView) findViewById(R.id.caloriesburnedTV);
        heightTV = (TextView) findViewById(R.id.heightTV);
        bmiTV = (TextView) findViewById(R.id.bmiTV);
        milesTV = (TextView) findViewById(R.id.milesTV);
        caloriesTV = (TextView) findViewById(R.id.caloriesTV);
        bmiamountTV = (TextView) findViewById(R.id.bmiamountTV);

        weightET = (EditText) findViewById(R.id.weightET);
        nameET = (EditText) findViewById(R.id.nameET);

        heightFT = (Spinner) findViewById(R.id.heightFT);
        heightIN = (Spinner) findViewById(R.id.heightIN);

        milesranSB = (SeekBar) findViewById(R.id.milesranSB);

        //set array adapter for spinners
        ArrayAdapter<CharSequence> adapterft = ArrayAdapter.createFromResource(this, R.array.htft_array, android.R.layout.simple_spinner_item);
        adapterft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterin = ArrayAdapter.createFromResource(this, R.array.htin_array, android.R.layout.simple_spinner_item);
        adapterin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heightFT.setAdapter(adapterft);
        heightIN.setAdapter(adapterin);

        //set listeners
        weightET.setOnEditorActionListener(this);
        milesranSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
                milesRan = seekBarProgress;
                milesTV.setText(String.valueOf(progress + " mi"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //anonymous class as its listener
        heightFT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feet = position + 1;
                calculateAndDisplay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        heightIN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inches = position +1;
                calculateAndDisplay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        //anonymous inner class as its listener

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        calculateAndDisplay();
        return false;
    }

    private void calculateAndDisplay() {
        weightAmountString = weightET.getEditableText().toString();
        if(weightAmountString.equals("")) {
            weightAmount = 000.0;
        } else {
            weightAmount = Double.parseDouble(weightAmountString);
        }

        //calculate calories and BMI
        Double caloriesBurned = 0.75 * weightAmount * milesRan;
        Double bmiAmount = (weightAmount * 703.0) / ((12.0 * feet + inches) * (12.0 * feet + inches));

        //display calculated results
        caloriesTV.setText(String.valueOf(caloriesBurned));
        bmiamountTV.setText(String.valueOf(bmiAmount));
    }

    @Override
    protected void onPause() {
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();

        editor.apply();
        editor.putString("weightAmountString", weightAmountString);
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}