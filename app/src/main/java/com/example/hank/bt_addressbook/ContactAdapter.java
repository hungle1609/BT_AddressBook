package com.example.hank.bt_addressbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Context context ;
    int resourse;
    List<Contact> arrayContact;
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourse=resource;
        this.arrayContact=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_contact_listview,parent,false);
            viewHolder.imgAvatar = convertView.findViewById(R.id.img_avatar);
            viewHolder.txtName = convertView.findViewById(R.id.txt_name);
            viewHolder.txtNumber = convertView.findViewById(R.id.txt_phone);
            convertView.setTag(viewHolder);


        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrayContact.get(position);
        viewHolder.txtName.setText(contact.getmName());
        viewHolder.txtNumber.setText(contact.getmNumber());
        if(contact.isMale())
        {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.male);
        }
        else
        {
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.female);
        }
       return convertView;
    }
    public class ViewHolder
    {
        ImageView imgAvatar;
        TextView txtName,txtNumber;

    }

}
