package org.bic.pepesmobile.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.bic.pepesmobile.R;
import org.bic.pepesmobile.application.PepesApplication;
import org.bic.pepesmobile.custom.Pelayanan;
import org.bic.pepesmobile.custom.PepesSingleton;
import org.bic.pepesmobile.custom.RequestManager;
import org.bic.pepesmobile.custom.RequestNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Section1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Section1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Section1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ProgressDialog mProgressBar;
    public RequestManager requestFor;
    private RequestNetwork rNet;
    private PepesSingleton singleton;
    private UserRequestTask userRequestTask;
    public final static String URL_REQ = "http://pepes.saungit.org/pepes";
    public Pelayanan pelayanan;
    public Spinner spinner;
    public Spinner spinner2;
    public EditText mNIK;
    public EditText mLamaHari;
    public EditText mPungutan;
    public EditText mnamaLokasi;
    public Button mSubmitButton;

    public final static String TAG = "Section1Fragment";
    private String[] jenisDok = {"KTP", "KK", "Akta Lahir", "Surat KTM", "Surat Pindah Masuk",
    "Surat Pindah Keluar", "Surat Untuk SKCK", "Surat Meninggal"};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Section1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Section1Fragment newInstance(String param1, String param2) {
        Section1Fragment fragment = new Section1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Section1Fragment() {
        // Required empty public constructor
        singleton = PepesSingleton.getInstance();
        rNet = new RequestNetwork(URL_REQ, RequestManager.SUBMIT_RT);
        requestFor = RequestManager.SUBMIT_RT;
        pelayanan = new Pelayanan();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_section1, container, false);

        spinner = (Spinner) theView.findViewById(R.id.jenis_pelayanan_value);
        spinner2 = (Spinner) theView.findViewById(R.id.tingkat_value);
        mNIK = (EditText) theView.findViewById(R.id.nik_value);
        mLamaHari = (EditText) theView.findViewById(R.id.lama_value);
        mPungutan = (EditText) theView.findViewById(R.id.pungutan_value);
        mnamaLokasi = (EditText) theView.findViewById(R.id.lokasi_value);
        mSubmitButton = (Button) theView.findViewById(R.id.button_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.tingkat_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        jenisDok);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

        return theView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void submit(){
        //num
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        pelayanan.num = ts;
        pelayanan.dok = spinner.getSelectedItem().toString();
        pelayanan.tingkat = spinner2.getSelectedItem().toString();
        pelayanan.lama = mLamaHari.getText().toString();
        pelayanan.pungutan = mPungutan.getText().toString();
        pelayanan.namalokasi = mnamaLokasi.getText().toString();
        pelayanan.nik = mNIK.getText().toString();

        userRequestTask = new UserRequestTask();
        requestFor = RequestManager.SUBMIT_RT;
        userRequestTask.execute("PELAYANAN");
    }

    public class UserRequestTask extends AsyncTask<String, Void, Boolean> {

        UserRequestTask() {

        }

        @Override
        protected void onPreExecute(){
//            showProgress(true);
//            mProgressBar = ProgressDialog.show(getActivity(), null, "Loading", true);
            mProgressBar = new ProgressDialog(getActivity());
            mProgressBar.setMessage("Loading");
            mProgressBar.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            int count = 3;
            int countMakan = 2;
            try {
                // Simulate network access.
                switch (requestFor) {

                    case SUBMIT_RT:
                        rNet.setPelayananModelRequest(pelayanan.nik,pelayanan.num,pelayanan.tingkat,pelayanan.lama,pelayanan.pungutan,pelayanan.namalokasi,pelayanan.dok);
                        rNet.rManager = RequestManager.SUBMIT_RT;
                        rNet.setRequestJSONMethod(PepesApplication.getAppContext());
                        Thread.sleep(8000);
                        break;
                    case SUBMIT_RW:
                        rNet.setPelayananModelRequest("","","","","","","");
                        rNet.rManager = RequestManager.SUBMIT_RW;
                        rNet.setRequestJSONMethod(PepesApplication.getAppContext());
                        Thread.sleep(8000);
                        break;
                    case SUBMIT_KEL:
                        rNet.setPelayananModelRequest("","","","","","","");
                        rNet.rManager = RequestManager.SUBMIT_KEL;
                        rNet.setRequestJSONMethod(PepesApplication.getAppContext());
                        Thread.sleep(8000);
                        break;
                    case SUBMIT_KEC:
                        rNet.setPelayananModelRequest("","","","","","","");
                        rNet.rManager = RequestManager.SUBMIT_KEC;
                        rNet.setRequestJSONMethod(PepesApplication.getAppContext());
                        Thread.sleep(8000);
                        break;
                    case SUBMIT_KOTA:
                        rNet.setPelayananModelRequest("","","","","","","");
                        rNet.rManager = RequestManager.SUBMIT_KOTA;
                        rNet.setRequestJSONMethod(PepesApplication.getAppContext());
                        Thread.sleep(8000);
                        break;
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "got exception");
                return false;
            }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userRequestTask = null;
//            showProgress(false);
            if (success) {
                mProgressBar.dismiss();
                Toast.makeText(PepesApplication.getAppContext(), "Success Submit!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "list " + singleton.pelayanan + ", lain ");
                mLamaHari.setText("");
                mPungutan.setText("");
                mnamaLokasi.setText("");
                mNIK.setText("");
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(mNIK.getWindowToken(), 0);
                //finish();
                switch (requestFor){
                    case SUBMIT_RT:
                        //this fragment caller
                        Log.i(TAG,"lebet");
//                        navigate(mNavItemId);
//                        mFragmentManager = getSupportFragmentManager();
//                        mFragmentTransaction = mFragmentManager.beginTransaction();
//                        mFragmentTransaction.replace(R.id.content, new TabFragment()).commit();
                        break;

                }


            } else {
                Log.i(TAG,"not success");
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            userRequestTask = null;
        }
    }
}
