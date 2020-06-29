package com.zvhir.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView mActiveCase;
    private TextView mICU;
    private TextView mRespiratory;
    private TextView mRecovered;
    private TextView mCumulative;
    private TextView mDeath;
    private TextView mLastUpdate;

    private CardView xapi;
    private CardView xreadme;
    private CardView xgithub;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActiveCase = findViewById(R.id.case_active);
        mICU = findViewById(R.id.case_icu);
        mRespiratory = findViewById(R.id.case_respiratory);
        mRecovered = findViewById(R.id.case_recovered);
        mCumulative = findViewById(R.id.case_cumulative);
        mDeath = findViewById(R.id.case_death);
        mLastUpdate = findViewById(R.id.lastUpdate);

        xapi = findViewById(R.id.cardSumber);
        xreadme = findViewById(R.id.cardReadme);
        xgithub = findViewById(R.id.cardDeveloper);


        xapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAPI = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.apify.com/v2/key-value-stores/6t65lJVfs3d8s6aKc/records/LATEST?disableRedirect=true"));
                startActivity(openAPI);
            }
        });

        xreadme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openReadme = new Intent(Intent.ACTION_VIEW, Uri.parse("https://apify.com/zuzka/covid-my"));
                startActivity(openReadme);
            }
        });

        xgithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zahiruddinzainal/Covid.my_SimpleDashboard"));
                startActivity(openGithub);
            }
        });



        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = "https://api.apify.com/v2/key-value-stores/6t65lJVfs3d8s6aKc/records/LATEST?disableRedirect=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String aktif = response.getString("activeCases");
                            mActiveCase.append(aktif);

                            String icu = response.getString("inICU");
                            mICU.append(icu);

                            String res = response.getString("respiratoryAid");
                            mRespiratory.append(res);

                            String recover = response.getString("recovered");
                            mRecovered.append(recover);

                            String cumulative = response.getString("testedPositive");
                            mCumulative.append(cumulative);

                            String death = response.getString("deceased");
                            mDeath.append(death);

                            String tarikh = response.getString("lastUpdatedAtApify").substring(0, 10);
                            String masa = response.getString("lastUpdatedAtApify").substring(12, 16);
                            mLastUpdate.append("\n" + masa + "PM (" + tarikh + ")");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);


    }

}