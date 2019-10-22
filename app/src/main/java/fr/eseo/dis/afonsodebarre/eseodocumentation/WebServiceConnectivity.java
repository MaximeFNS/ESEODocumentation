package fr.eseo.dis.afonsodebarre.eseodocumentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class WebServiceConnectivity extends AsyncTask<String, String, String> {

    private Context context;
    ProgressDialog processDialog;

    public WebServiceConnectivity(Context context){
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        processDialog = new ProgressDialog(this.context);
        processDialog.setMessage("Wait during process");
        processDialog.setCancelable(false);
        processDialog.show();
    }

    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            Log.d("newContent", "begin");

            InputStream in = null;

            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");

                // From https://www.washington.edu/itconnect/security/ca/load-der.crt
                Log.d("certificate", Integer.toString(R.raw.dis_inter_ca));

                InputStream caInput = this.context.getResources().openRawResource(R.raw.dis_inter_ca);
                Log.d("certificateIn", caInput.toString());

                Certificate certif;
                try {
                    certif = cf.generateCertificate(caInput);
                    System.out.println("certif=" + ((X509Certificate) certif).getSubjectDN());
                } finally {
                    caInput.close();
                }

                // Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", certif);

                // Create a TrustManager that trusts the CAs in our KeyStore
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                // Tell the URLConnection to use a SocketFactory from our SSLContext

                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection =
                        (HttpsURLConnection)url.openConnection();
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                in = urlConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }

            Log.d("InputStream", in.toString());
            reader = new BufferedReader(new InputStreamReader(in));

            return analyzeString(recupString(reader));

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    protected String recupString(BufferedReader rd) {
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = rd.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    protected String analyzeString(String response){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (processDialog.isShowing()) {
            processDialog.dismiss();
        }
        Log.d("postexecute","result"+result.toString());
    }

}


