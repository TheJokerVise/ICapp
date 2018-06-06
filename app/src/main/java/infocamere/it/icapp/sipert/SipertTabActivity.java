/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 31/05/18 15.05
 */

package infocamere.it.icapp.sipert;

import android.app.SearchManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import infocamere.it.icapp.AppBaseActivity;
import infocamere.it.icapp.R;
import infocamere.it.icapp.model.presenze.Saldi;

public class SipertTabActivity extends AppBaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sipert_tab);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.setTitle("IC APP");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        callSipertSaldi();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sipert_tab, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = null;

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(
                        R.layout.fragment_sipert_saldi, container, false);
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(
                        R.layout.fragment_sipert_timbrature, container, false);
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                rootView = inflater.inflate(
                        R.layout.fragment_sipert_anomalie, container, false);
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void detailFerie_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailFerieCV = (CardView) findViewById(R.id.detail_ferie_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText("Dettagli");
            detailFerieCV.setVisibility(View.GONE);
        }
        else {
            detail.setText("Nascondi");
            detailFerieCV.setVisibility(View.VISIBLE);
        }
    }

    public void detailPar_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailParCV = (CardView) findViewById(R.id.detail_par_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText(R.string.detail_txt);
            detailParCV.setVisibility(View.GONE);
        }
        else {
            detail.setText(R.string.hide_txt);
            detailParCV.setVisibility(View.VISIBLE);
        }
    }

    public void detailBancaore_onClick(View view) {
        TextView detail = (TextView) view;
        CardView detailBancaoreCV = (CardView) findViewById(R.id.detail_bancaore_cv);
        if (detail.getText().toString().contains("Nascondi")) {
            detail.setText(R.string.detail_txt);
            detailBancaoreCV.setVisibility(View.GONE);
        }
        else {
            detail.setText(R.string.hide_txt);
            detailBancaoreCV.setVisibility(View.VISIBLE);
        }
    }

    public void callSipertSaldi() {
        Log.i("REQUEST", "START");

        String url = "";

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject request = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject sipertResponse) {
                ArrayList<Saldi> saldi = null;
                try {
                    saldi = SipertResponseFactory.buildSaldi(sipertResponse);
                    setValueForSaldi(saldi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // response
                Log.d("Response", sipertResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("ERROR","error => "+error.toString());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("wssipapikey", "1qaz2wsx");
                params.put("Content-type", "application/json");

                return params;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
        Log.i("Saldi", "Added to queue!");


    }

    public void setValueForSaldi(ArrayList<Saldi> saldi) {
        if (saldi != null) {
            Saldi s;
            for (int i=0; i<saldi.size(); i++) {
                s = saldi.get(i);
                if (s.getProgr() == 1) {
                   TextView ferie = (TextView) findViewById(R.id.saldo_ferie);
                   ferie.setText(s.getSaldo());
                   ferie = (TextView) findViewById(R.id.residuo_ferie);
                   ferie.setText(s.getResiduoAnnoPrec());
                   ferie = (TextView) findViewById(R.id.anno_corrente_ferie);
                   ferie.setText(s.getAnnoCorrente());
                   ferie = (TextView) findViewById(R.id.godute_corrente_ferie);
                   ferie.setText(s.getGoduteCorrente());
                }
                else if (saldi.get(i).getProgr() == 2) {
                    TextView par = (TextView) findViewById(R.id.saldo_par);
                    par.setText(s.getSaldo());
                    par = (TextView) findViewById(R.id.residuo_par);
                    par.setText(s.getResiduoAnnoPrec());
                    par = (TextView) findViewById(R.id.anno_corrente_par);
                    par.setText(s.getAnnoCorrente());
                    par = (TextView) findViewById(R.id.godute_corrente_par);
                    par.setText(s.getGoduteCorrente());

                }
                else if (saldi.get(i).getProgr() == 3) {
                    TextView banca = (TextView) findViewById(R.id.saldo_banca_ore);
                    banca.setText(s.getSaldo());
                    banca = (TextView) findViewById(R.id.residuo_banca_ore);
                    banca.setText(s.getResiduoAnnoPrec());
                    banca = (TextView) findViewById(R.id.anno_corrente_banca_ore);
                    banca.setText(s.getAnnoCorrente());
                    banca = (TextView) findViewById(R.id.godute_corrente_banca_ore);
                    banca.setText(s.getGoduteCorrente());

                }
            }
        }
    }
}