/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 06/06/18 11.48
 */

package infocamere.it.icapp.sipert;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infocamere.it.icapp.model.presenze.Saldi;

class SipertResponseFactory {

    static ArrayList<Saldi> buildSaldi(JSONObject saldiResponse) throws JSONException {

        ArrayList<Saldi> saldi = new ArrayList<>();
        Saldi s;
        JSONObject tmp;
        JSONArray tmparray;

        JSONArray saldiArray = saldiResponse.getJSONArray("saldi");
        for (int i=0; i<saldiArray.length(); i++) {
            s = new Saldi();
            tmp = saldiArray.getJSONObject(i);
            s.setProgr(Integer.valueOf(tmp.getString("progr")));
            s.setDescr(tmp.getString("descr"));
            s.setUltagg(tmp.getString("ultagg"));
            tmparray = tmp.getJSONArray("celle");
            if (tmparray.length() == 4) {
                s.setResiduoAnnoPrec(tmparray.getString(0));
                s.setAnnoCorrente(tmparray.getString(1));
                s.setGoduteCorrente(tmparray.getString(2));
                s.setSaldo(tmparray.getString(3));
            }
            saldi.add(s);
        }

        return saldi;
    }
}
