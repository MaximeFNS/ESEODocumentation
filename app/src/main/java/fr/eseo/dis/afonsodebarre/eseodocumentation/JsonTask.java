package fr.eseo.dis.afonsodebarre.eseodocumentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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


import fr.eseo.dis.afonsodebarre.eseodocumentation.R;

public class JsonTask extends AsyncTask<String, String, String> {

        ProgressDialog pd;
        private Context context;

        public JsonTask(Context context){
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(this.context);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                Log.d("newContent", "begin");
                /** NEW */
                InputStream in = null;

                try {
                    // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");

                    // From https://www.washington.edu/itconnect/security/ca/load-der.crt

                    Log.d("certificat", Integer.toString(R.raw.dis_inter_ca));

                    InputStream caInput = this.context.getResources().openRawResource(R.raw.dis_inter_ca);

                    //InputStream caInput = new BufferedInputStream(new FileInputStream(R.raw.dis_inter_ca));
                    Certificate ca;
                    try {
                        ca = cf.generateCertificate(caInput);
                        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                    } finally {
                        caInput.close();
                    }

                    // Create a KeyStore containing our trusted CAs
                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);

                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);

                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);

                    // Tell the URLConnection to use a SocketFactory from our SSLContext
                    /**URL url = new URL("https://certs.cac.washington.edu/CAtest/");*/

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

                /** USELESS ? */
                //copyInputStreamToOutputStream(stream, System.out);
                /** END */


            /*URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();*/
                Log.d("InputStream", in.toString());
                reader = new BufferedReader(new InputStreamReader(in));

                return recupToken(reader);




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
            //Log.d("saiCatch","null");
            //return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Log.d("postexecute","result"+result.toString());
            //Log.d("postexecute2","toto");
        }


        private String recupToken(BufferedReader reader){
            String line = "";

            Log.d("taskToken", "recup");
            try {
                while (((line = reader.readLine()) != null)) {
                    Log.d("taskToken", line);
                    if (line.contains("token"))
                        Log.d("taskToken", line.substring(11, line.length()-1));
                    return line.substring(11, line.length()-1);


                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("taskToken", e.toString());
            }
            return "not working dude";
        }
    }


