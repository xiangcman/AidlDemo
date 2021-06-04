package com.xc.bproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.xc.aproject.CallBack;
import com.xc.aproject.IMyAidlInterface;
import com.xc.aproject.User;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setPackage("com.xc.aproject");
        intent.setAction("com.xc.aproject.AidlService");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                try {
                    int caculate = iMyAidlInterface.caculate(1, 2);
                    List<User> allUser = iMyAidlInterface.getAllUser();
                    String name1 = allUser.get(0).getName();
                    Log.d("binder", "name:" + name1);
                    Log.d("binder", "caculate:" + caculate);

                    //CallBack是a应用的binder对象，注册到binder通道中了
                    iMyAidlInterface.call(new CallBack.Stub() {
                        @Override
                        public void invoke(int position) throws RemoteException {
                            String name2 = Thread.currentThread().getName();
                            Log.d("binder", "binder thread:" + name2);
                            Log.d("binder", "position:" + position);
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void check(View view) {
        if (iMyAidlInterface != null) {
            try {
                iMyAidlInterface.waitSome();
                Log.d("binder", "client:" + android.os.Process.myPid() + "----" + Thread.currentThread().getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}