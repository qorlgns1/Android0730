package naver.rlgns1129.android0730;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText msgInput;
    private Button msgBtn;

    //자바 스크립트를 사용할 수 있도록 해주는 인터페이스 클래스 생성
    public class AndroidJavaScriptInterface{
        private Context context;
        private Handler handler = new Handler();

        //Context를 넘겨받기 위한 생성
        public AndroidJavaScriptInterface(Context context){
            this.context = context;
        }
        //웹에서 호출하기 위한 메소드
        //웹에서 호출한 메소드 이름과 일치해야 합니다.
        @JavascriptInterface
        public void showToastMessage(final String message){
            //매개변수로 넘어온 데이터를 Toast로 출력
            handler.post(new Runnable(){
                public void run(){
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webview);
        msgInput = (EditText)findViewById(R.id.msginput);
        msgBtn = (Button)findViewById(R.id.msgbtn);


        //redirect 되는 웹 사이트도 웹 뷰로 출력하기 위한 설정
        webView.setWebViewClient(new WebViewClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);

        //자바스크립트 메소드 등록
        webView.addJavascriptInterface(new AndroidJavaScriptInterface(this),"MyApp");

        //웹 페이지 로드
        //webView.loadUrl("https://www.google.com");

        //로컬의 파일 로드
        //로컬파일을 열때는 /// 3개가 들어가야합니다.
        webView.loadUrl("http://192.168.0.215:9000/mysqlserver");

        msgBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                //자바스크립트 함수 호출
                webView.loadUrl("javascript:showDisplayMessage('"+ msgInput.getText().toString() + "')");

            }
        });
    }
}