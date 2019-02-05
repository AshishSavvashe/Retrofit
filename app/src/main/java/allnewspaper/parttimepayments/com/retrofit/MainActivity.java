package allnewspaper.parttimepayments.com.retrofit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edtSearch;
    private AndroidVesrionAdapter androidVesrionAdapter;
    List<Android> androidlist= new ArrayList<>();
    DBHandler myDb;
    ArrayList<Android> tempArrayList;
    private Android android;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        myDb = new DBHandler(this);

        edtSearch = findViewById(R.id.edtSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (NetworkCheckUtility.isNetworkConnectionAvailable(MainActivity.this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<Example> call = apiService.getAndroidVersion("application/json");

            call.enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    androidlist = response.body().getAndroid();

                    if(androidlist.size()>0 && androidlist != null){

                        Android android = null;

                        for(int i =0; i<androidlist.size(); i++){
                            boolean isInserted = myDb.insertData(androidlist.get(i));
                            if(isInserted == true) {
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    androidVesrionAdapter = new AndroidVesrionAdapter(androidlist, MainActivity.this);
                    recyclerView.setAdapter(androidVesrionAdapter);
                    recyclerView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    // Log error here since request failed
                    //  Log.e(TAG, t.toString());
                }
            });
        } else {

            Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show();
            Cursor res = myDb.getAllData();
            if(res.getCount() > 0){
                if(res.moveToFirst()){
                    do{
                        android = new Android();
                        android.setVer(res.getString(1));
                        android.setName(res.getString(2));
                        android.setApi(res.getString(3));
                        tempArrayList = new ArrayList<>();
                        tempArrayList.addAll(androidlist);
                        UpdateList();
                        androidlist.add(android);

                    }while(res.moveToNext());
                }
            }
        }


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int textlength = s.length();
                tempArrayList = new ArrayList<Android>();
                for(Android c: androidlist){
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                androidVesrionAdapter = new AndroidVesrionAdapter(tempArrayList,MainActivity.this );
                recyclerView.setAdapter(androidVesrionAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        }

    private void UpdateList() {
        try {
            //Search details
            String strSearch = edtSearch.getText().toString().trim().toLowerCase();
            if (!strSearch.equalsIgnoreCase("")) {
                androidlist = new ArrayList<>();

                for (Android leadDetails : tempArrayList) {
                    if (leadDetails.getName().toLowerCase().contains(strSearch)) {
                        androidlist.add(leadDetails);
                    }
                }
            } else {
                androidlist = new ArrayList<>();
                androidlist.addAll(tempArrayList);
            }

            androidVesrionAdapter = new AndroidVesrionAdapter(androidlist, MainActivity.this);
            recyclerView.setAdapter(androidVesrionAdapter);
            recyclerView.setVisibility(View.VISIBLE);

            /*mListViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    POJO_DataList leadDetails = arrayListDetails.get(position);
                    Bundle data = new Bundle();
                    data.putSerializable("lead_details", leadDetails);

                    Intent intent = new Intent(mContext, DetailInfoActivity.class);
                    intent.putExtra("data", leadDetails);
                    startActivity(intent);
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
