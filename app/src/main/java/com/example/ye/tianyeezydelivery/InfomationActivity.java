package com.example.ye.tianyeezydelivery;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by ye on 2016/5/15.
 */
public class InfomationActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE_1=1;
    public static final int SHOW_RESPONSE_2=2;
    public static final int SHOW_RESPONSE_3=3;
    private TextView tv;
    public String infoStr = "";
    String conpany;
    String number;
    public Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case SHOW_RESPONSE_1:
                    String response=(String) msg.obj;
                    tv.setText(response);
                    break;
                case SHOW_RESPONSE_2:
                    Toast.makeText(InfomationActivity.this,"danhaocuowu",Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_RESPONSE_3:
                    Toast.makeText(InfomationActivity.this,"没网",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_infomation);
        tv=(TextView) findViewById(R.id.tv);
        Intent intent=getIntent();
        conpany=intent.getStringExtra("company_data");
        number=intent.getStringExtra("number_data");
        sendRequestWithHttpClient();
    }
    private void sendRequestWithHttpClient(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                try {
                    HttpClient httpClient=new DefaultHttpClient();
                    /*String url="http://api.ickd.cn/?id=102616&secret=16135ea51cb60246eff620f130a005bd&com=tiantian&nu=550404277359&type=xml&encode=utf8&ord=asc";*/
                    String url="http://api.ickd.cn/?id=102616&secret=16135ea51cb60246eff620f130a005bd&com=";
                    url+=conpany;
                    url+="&nu=";
                    url+=number;
                    url+="&type=xml&encode=utf8&ord=desc";

                    HttpGet httpGet=new HttpGet(url);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    HttpResponse httpResponse=httpClient.execute(httpGet);

                    if(httpResponse.getStatusLine().getStatusCode()==200){
                        HttpEntity entity=httpResponse.getEntity();
                        String response= EntityUtils.toString(entity, "utf-8");
                        parseXMLWithPull(response);
                    } else{
                        /*Message message=new Message();
                        message.what=SHOW_RESPONSE_3;
                        handler.sendMessage(message);*/
                    }
                } catch (Exception e) {
                   /* // TODO Auto-generated catch block
                    System.out.print("没有查询结果");*/
                    Message message=new Message();
                    message.what=SHOW_RESPONSE_3;
                    handler.sendMessage(message);

                }

            }
        }).start();
    }
    private void parseXMLWithPull(String xmlData){
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            String time="";
            String context="";
            String messagePlus="";
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:{
                        if("time".equals(nodeName)){
                            time=xmlPullParser.nextText();
                        }
                      /*  else if ("message".equals(nodeName)){
                            messagePlus=xmlPullParser.nextText();
                        }*/

                        else if("context".equals(nodeName)){
                            context=xmlPullParser.nextText();
                        }


                        break;

                    }
                    case XmlPullParser.END_TAG:{
                        if("item".equals(nodeName)){
                            /*System.out.println("time:"+time);
                            System.out.println("context:"+context);*/
                            infoStr+=time+context+"\n"+"\n"+"\n";
                        }
                        else{
                            infoStr+=messagePlus+time+context;
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
                Message message=new Message();
                message.what=SHOW_RESPONSE_1;
                message.obj=infoStr;
                handler.sendMessage(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}