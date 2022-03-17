package com.passcrypt.passwordmanager;

import android.os.Parcel;
import android.os.Parcelable;
//This "parcable" is for passing objects between activities
public class Tileobj implements Parcelable {
    private String miconcol,mtext1,mweb,muser,mpass,mnote;
    public Tileobj(String iconcol,String text1,String web,String user,String pass,String note){
        miconcol = iconcol;
        mtext1 = text1;
        mweb = web;
        muser = user;
        mpass = pass;
        mnote = note;
    }

    protected Tileobj(Parcel in) {
        miconcol = in.readString();
        mtext1 = in.readString();
        mweb = in.readString();
        muser = in.readString();
        mpass = in.readString();
        mnote = in.readString();
    }

    public static final Creator<Tileobj> CREATOR = new Creator<Tileobj>() {
        @Override
        public Tileobj createFromParcel(Parcel in) {
            return new Tileobj(in);
        }

        @Override
        public Tileobj[] newArray(int size) {
            return new Tileobj[size];
        }
    };

    public String getIconcol(){
        return miconcol;
    }
    public String getText1(){
        return mtext1;
    }
    public String getweb(){
        return mweb;
    }
    public String getuser(){
        return muser;
    }
    public String getpass(){
        return mpass;
    }
    public String getnote(){
        return mnote;
    }

    //This "parcable" is for passing objects between activities
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(miconcol);
        dest.writeString(mtext1);
        dest.writeString(mweb);
        dest.writeString(muser);
        dest.writeString(mpass);
        dest.writeString(mnote);
    }
}
