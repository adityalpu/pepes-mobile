package org.bic.pepesmobile.custom;

import java.util.ArrayList;

/**
 * Created by adityalegowo on 12/6/15.
 */
public class PepesSingleton {

    public final static String PEPESMOBILE_KEY = "org.bic.pepesmobile.APP";

    private static PepesSingleton instance = null;
    public Pelayanan pelayanan;
    public int indexPelayanan = 0;

    private PepesSingleton() {
        pelayanan = new Pelayanan();
    }

    public static PepesSingleton getInstance() {
        if (instance == null) {
            instance = new PepesSingleton ();
        }
        return instance;
    }
}
