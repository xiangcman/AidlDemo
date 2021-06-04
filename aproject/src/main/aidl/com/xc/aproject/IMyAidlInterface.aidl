// IMyAidlInterface.aidl
package com.xc.aproject;

// Declare any non-default types here with import statements
import com.xc.aproject.User;
import com.xc.aproject.CallBack;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    int caculate(int a1,int a2);

  List<User> getAllUser();

  void call(CallBack callBack);

  oneway void waitSome();
}