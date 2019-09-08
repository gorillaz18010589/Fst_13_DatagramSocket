package tw.brad.apps.brad13;
//Socket
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        getMyIPv2();
    }

    private void getMyIP(){
        new Thread(){
            @Override
            public void run() {
                try {
                    String myip = InetAddress.getLocalHost().getHostAddress();
                    Log.v("brad", "myip = " + myip);
                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();
    }

    private void getMyIPv2(){
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()){
                NetworkInterface networkInterface = en.nextElement();
                Enumeration<InetAddress> ips = networkInterface.getInetAddresses();
                while (ips.hasMoreElements()){
                    InetAddress ip = ips.nextElement();
                    Log.v("brad", ip.getHostAddress());
                }
            }


        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }

    public void sendUDP(View view) {
        new Thread(){
            @Override
            public void run() {
                byte[] buf = input.getText().toString().getBytes();
                try {
                    DatagramSocket socket = new DatagramSocket();
//                    DatagramPacket packet = new DatagramPacket(buf, buf.length,
//                        InetAddress.getByName("10.0.2.2"), 8888);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length,
                            InetAddress.getByName("10.0.105.34"), 8888);
                    socket.send(packet);
                    socket.close();
                    Log.v("brad", "Send OK");
                }catch (Exception e){
                    Log.v("brad", e.toString());
                }

            }
        }.start();


    }
}