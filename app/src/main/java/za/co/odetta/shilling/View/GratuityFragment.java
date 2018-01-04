package za.co.odetta.shilling.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

import za.co.odetta.shilling.R;

/**
 * Created by fredkobo on 2017/11/10.
 */

public class GratuityFragment extends Fragment{

    private EditText edBillAmount;
    private Spinner spPercentage;
    private TextView tvTotal;
    private TextView tvTip;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gratuity, container, false);
        initialiseViews();
        return rootView;
    }

    private void initialiseViews() {
        edBillAmount = rootView.findViewById(R.id.edBillTotal);
        spPercentage = rootView.findViewById(R.id.spPercentage);
        tvTotal = rootView.findViewById(R.id.tvTotal);
        tvTip = rootView.findViewById(R.id.tvTip);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.percentages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPercentage.setAdapter(adapter);

        edBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateTip();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("GRATUITY", "AFTER TEXT HAS CHANGED");
            }
        });

        spPercentage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculateTip();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private double getSelectedPercentage()
    {
        String perc = spPercentage.getSelectedItem().toString();
        perc = (perc.split("%"))[0];
        return Double.parseDouble(perc);
    }

    private void calculateTip()
    {
        //String s = edBillAmount.getText().toString();
        String s = getEnteredNumber();
        if(!s.equals(""))
        {
            double bill = Double.parseDouble(s);
            double tip =  bill * getSelectedPercentage() / 100;
            double total = bill + tip;
            Log.d("GRATUITY", "R" + total);

            DecimalFormat format = new DecimalFormat("##.00");
            tvTotal.setText(format.format(total));
            tvTip.setText(format.format(tip));
        }
    }

    private String getEnteredNumber()
    {
        String s = edBillAmount.getText().toString();
        if(s.contains("."))
        {
            return s + "0";
         }
        return s;
    }
}
