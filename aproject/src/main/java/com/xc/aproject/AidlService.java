package com.xc.aproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AidlService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {

        @Override
        public int caculate(int a1, int a2) throws RemoteException {
            return a1 + a2;
        }

        @Override
        public List<User> getAllUser() throws RemoteException {
            List<User> users = new ArrayList<>();
            users.add(new User("zhangsan"));
            return users;
        }

        @Override
        public void call(CallBack callBack) throws RemoteException {
            callBack.invoke(50);
        }

        @Override
        public void waitSome() throws RemoteException {
            try {
                Thread.sleep(5000);
                Log.d("binder", "service:" + android.os.Process.myPid() + "----" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    };
}
