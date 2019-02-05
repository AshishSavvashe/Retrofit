package allnewspaper.parttimepayments.com.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    String versionName,versionCode,versionApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       intView();
    }

    private void intView() {

        Bundle bundle = getIntent().getExtras();
        versionName = bundle.getString("version").trim();
        versionCode = bundle.getString("name");
        versionApi = bundle.getString("api");

        ((TextView)findViewById(R.id.txtvername)).setText(versionName);
        ((TextView)findViewById(R.id.txtvercode)).setText(versionCode);
        ((TextView)findViewById(R.id.txtverapi)).setText(versionApi);

        ((Button)findViewById(R.id.btnView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailsActivity.this,WebViewActivity.class);
                startActivity(i);
            }
        });

    }
}
