package com.daphne.safety;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity implements Parcelable {
        public String id,name,phone,label;

        ContactActivity(String id, String name,String phone,String label){
                this.id=id;
                this.name=name;
                this.phone=phone;
                this.label=label;
        }

        protected ContactActivity(Parcel in) {
                id = in.readString();
                name = in.readString();
                phone = in.readString();
                label = in.readString();
        }

        public static final Parcelable.Creator<ContactActivity> CREATOR = new Parcelable.Creator<ContactActivity>() {
                @Override
                public ContactActivity createFromParcel(Parcel in) {
                        return new ContactActivity(in);
                }

                @Override
                public ContactActivity[] newArray(int size) {
                        return new ContactActivity[size];
                }
        };

        @Override
        public String toString()
        {
                return name+" | "+label+" : "+phone;
        }

        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(name);
                dest.writeString(phone);
                dest.writeString(label);
        }
////        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data){
//                super.onActivityResult(requestCode, resultCode, data);
//
//                if(requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK){
//
//                        ArrayList<ContactsList> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
//
//                        String display="";
//                        for(int i=0;i<selectedContacts.size();i++){
//
//                                display += (i+1)+". "+selectedContacts.get(i).toString()+"\n";
//
//                        }
//                        contactsDisplay.setText("Selected Contacts : \n\n"+display);
//
//                }

        }






