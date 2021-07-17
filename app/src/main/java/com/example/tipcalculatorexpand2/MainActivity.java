package com.example.tipcalculatorexpand2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {


    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private TextView textViewPercent;
    private TextView tipTextView;
    private TextView totalTextView;
    private SeekBar ss;
    private double billAmount = 0.0;
    private double percent = .15;
    private double total = 0.0;
    private double t = 0.0;
    private double totalA = 0.0;
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private TextView totalPerTextView;
    private Spinner spinnerP;
    private double numppl;
    private RadioButton noR;
    private RadioButton tipR;
    private RadioButton totalR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        editTextBillAmount = (EditText) findViewById(R.id.editText_BillAmount);
        textViewPercent = (TextView) findViewById(R.id.percent_textView);
        tipTextView = (TextView) findViewById(R.id.tipAmount_textView);
        totalTextView = (TextView) findViewById(R.id.textView9);

        editTextBillAmount.addTextChangedListener((TextWatcher) this);

        textViewBillAmount = (TextView) findViewById(R.id.textView_BillAmount);
        totalPerTextView = findViewById(R.id.textView3);
        noR = findViewById(R.id.no_round);
        tipR = findViewById(R.id.round_tip);
        totalR = findViewById(R.id.round_total);


        ss = (SeekBar) findViewById(R.id.percent_seekBar);
        ss.setOnSeekBarChangeListener(this);

        spinnerP = findViewById(R.id.spinner);
        if (spinnerP != null) {
            spinnerP.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinnerP != null) {
            spinnerP.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_share:
                String txt = "Bill Amount: " + textViewBillAmount.getText().toString() + " Tip: " + tipTextView.getText().toString() + " Total: " +
                        totalTextView.getText().toString() + " Per Person: " + totalPerTextView.getText().toString();
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(this)
                        .setType(mimeType)
                        .setChooserTitle("Send A Message")
                        .setText(txt)
                        .startChooser();
                return true;
            case R.id.action_dialog:
                AlertDialog.Builder myAlertBuilder = new
                        AlertDialog.Builder(MainActivity.this);

                myAlertBuilder.setTitle(R.string.spinner_info);
                myAlertBuilder.setMessage(R.string.spinner_message);

                myAlertBuilder.setPositiveButton((R.string.ok_button), new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked OK button.
                                Toast.makeText(getApplicationContext(), (R.string.toast_ok),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                myAlertBuilder.setNegativeButton((R.string.cancel_ok), new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User cancelled the dialog.
                                Toast.makeText(getApplicationContext(), (R.string.toast_cancel),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                myAlertBuilder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.no_round:
                if (checked)
                    calculate();
                break;
            case R.id.round_tip:
                if (checked) {

                    calculate3();
//                    textViewPercent.setText(percentFormat.format(percent));
//                    double tip = billAmount * percent;
//                    Math.ceil(tip);
//                    double total = billAmount + tip;
//                    tipTextView.setText(currencyFormat.format(tip));
//                    totalTextView.setText(currencyFormat.format(total));

                }

                break;
            case R.id.round_total:

                if (checked) {
                    calculate2();
//                    textViewPercent.setText(percentFormat.format(percent));
//                    double tip = billAmount * percent;
//                    double total = billAmount + tip;
//                    Math.ceil(total);
//                    tipTextView.setText(currencyFormat.format(tip));
//                    totalTextView.setText(currencyFormat.format(total));

                }


                break;
            default:
                // Do nothing.
                break;
        }
    }

    private void calculate3() {
        textViewPercent.setText(percentFormat.format(percent));
        double tip = billAmount * percent;
        double stip = Math.ceil(tip);
        double total = billAmount + stip;
        tipTextView.setText(currencyFormat.format(stip));
        totalTextView.setText(currencyFormat.format(total));
        double perTotal = total / numppl;
        totalPerTextView.setText(currencyFormat.format(perTotal));
    }

    private void calculate2() {
        textViewPercent.setText(percentFormat.format(percent));
        double tip = billAmount * percent;
        double total = billAmount + tip;
        tipTextView.setText(currencyFormat.format(tip));
        double stotal = Math.ceil(total);
        totalTextView.setText(currencyFormat.format(stotal));


        double perTotal = stotal / numppl;
        totalPerTextView.setText(currencyFormat.format(perTotal));

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= " + s);


        try { // get bill amount and display currency formatted value
            billAmount = Double.parseDouble(s.toString()) / 100.0;
            textViewBillAmount.setText(currencyFormat.format(billAmount));

//            tipTextView.setText(currencyFormat.format(t));
//           // total = t + billAmount;
//            totalTextView.setText(currencyFormat.format(total));

//            percent = Double.parseDouble(s.toString());
            t = billAmount * percent;
            tipTextView.setText(currencyFormat.format(t));
            //total = Double.parseDouble(s.toString());
            total = t + billAmount;
            totalTextView.setText(currencyFormat.format(total));
            totalA = total / numppl;
            totalPerTextView.setText(currencyFormat.format(totalA));
            //tip
            //total
        } catch (NumberFormatException e) { // if s is empty or non-numeric
            textViewBillAmount.setText("");
            billAmount = 0.0;
            tipTextView.setText("");
            t = 0.0;
            totalTextView.setText("");
            total = 0.0;
            totalPerTextView.setText("");
            totalA = 0.0;
        }

        switch (count) {

            case (R.id.no_round):
                calculate();
                break;

            case (R.id.round_tip):
                calculate3();
                break;

            case (R.id.round_total):
                calculate2();
                break;
        }

        // calculate();
    }

    private void calculate() {
        Log.d("MainActivity", "inside calculate method");

        textViewPercent.setText(percentFormat.format(percent));
        double tip = billAmount * percent;
        double total = billAmount + tip;
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));

        double perTotal = total / numppl;
        totalPerTextView.setText(currencyFormat.format(perTotal));


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();
        numppl = Double.valueOf(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        percent = progress / 100.0;
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
