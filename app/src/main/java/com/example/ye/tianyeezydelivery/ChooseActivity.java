package com.example.ye.tianyeezydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ChooseActivity extends AppCompatActivity {
    EditText et;
    Spinner spinner;
    Button bt;
    String conpany="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        et=(EditText) findViewById(R.id.et);
        /*spinner=(Spinner) findViewById(R.id.spinner1);*/
        bt=(Button) findViewById(R.id.bt);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource ( this ,

                R.array.ctype,

                R.layout.spinner_choose );                                                                     // 改为 spinnerlayout

        adapter.setDropDownViewResource(R.layout.spinner_choose);           // 改为 spinnerlayout

        spinner = (Spinner) this .findViewById(R.id.spinner1);

        spinner .setAdapter(adapter);

        spinner .setPrompt( "  Choose quickly!!! " );

        spinner.getSelectedItem();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if ("申通".equals(text)) {
                    conpany = "zhongtong";
                } else if ("圆通".equals(text)) {
                    conpany = "yuantong";
                } else if ("天天".equals(text)) {
                    conpany = "tiantian";
                } else if ("顺风".equals(text)) {
                    conpany = "shunfeng";
                }else if ("中通".equals(text)) {
                    conpany = "zhongtong";
                }else if ("韵达".equals(text)) {
                    conpany = "yunda";
                }else if ("宅急送".equals(text)) {
                    conpany = "zhaijisong";
                }else if ("EMS".equals(text)) {
                    conpany = "ems";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String number = et.getText().toString().trim();
                System.out.print("公司：" + conpany + "单号" + number);
                /*Toast.makeText(ChooseActivity.this, "公司：" + conpany + "单号" + number, Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(ChooseActivity.this, InfomationActivity.class);
                intent.putExtra("company_data", conpany);
                intent.putExtra("number_data", number);
                startActivity(intent);
            }
        });

    }
}
