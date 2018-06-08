package com.fireblend.uitest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.AddContact;
import com.fireblend.uitest.R;
import com.fireblend.uitest.entities.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    RecyclerView list;
    ArrayList<Contact> datos;
    Button addcontac;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (RecyclerView) findViewById(R.id.lista_contactos);
        list.setHasFixedSize(true);

        //traemos los datos nuevos del Activity AddContact
        Bundle nuevo = getIntent().getExtras();

        //Cargamos los datos
        setupList();


            final AdaptadorContactos adaptador = new AdaptadorContactos(datos);

            //Mostrar mensaje al tocar un dato
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = list.getChildAdapterPosition(view);
                    if(pos != RecyclerView.NO_POSITION){
                        Contact clickedDataItem = datos.get(pos);
                        Toast.makeText(view.getContext(), "Hola " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            list.setAdapter(adaptador);

            //Dar estructura al RecyclerView
            list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

            //Ir al nuevo Activity
        addcontac = (Button)findViewById(R.id.add_contact);
        addcontac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);

                startActivity(intent);
            }
            });



        //Si hay datos nuevos se cargan al Arreglo
        if(nuevo != null) {

            datos.add(0, new Contact(nuevo.getString("Nombre"), nuevo.getInt("Edad"), nuevo.getString("Email"), nuevo.getString("Telefono"), nuevo.getString("Provincia")));
            adaptador.notifyItemInserted(1);
        }

    }




    private void setupList() {

        datos = new ArrayList<Contact>();
            datos.add(new Contact("Sergio", 28, "sergiome@gmail.com", "88854764", "San José"));
            datos.add(new Contact("Andres", 1, "alex@gmail.com", "88883644", "Puntarenas"));
            datos.add(new Contact("Andrea", 2, "andrea@gmail.com", "98714764", "Puntarenas"));
            datos.add(new Contact("Fabian", 3, "fabian@gmail.com", "12345678", "Puntarenas"));
            datos.add(new Contact("Ivan", 4, "ivan@gmail.com", "87654321", "Puntarenas"));
            datos.add(new Contact("Gabriela", 5, "gabriela@gmail.com", "09871234", "Puntarenas"));
            datos.add(new Contact("Alex", 6, "sergiome@gmail.com", "43215678", "Puntarenas"));

    }



    /*private void setupList() {


        final List<Contact> contacts = new ArrayList();
        Bundle datos = getIntent().getExtras();

        if (datos == null) {
            //Lista ejemplo con datos estaticos. Normalmente, estos serían recuperados de una BD o un REST API.
            contacts.add(new Contact("Sergio", 28, "sergiome@gmail.com", "88854764", "San José"));
            contacts.add(new Contact("Andres", 1, "alex@gmail.com", "88883644", "Puntarenas"));
            contacts.add(new Contact("Andrea", 2, "andrea@gmail.com", "98714764", "Puntarenas"));
            contacts.add(new Contact("Fabian", 3, "fabian@gmail.com", "12345678", "Puntarenas"));
            contacts.add(new Contact("Ivan", 4, "ivan@gmail.com", "87654321", "Puntarenas"));
            contacts.add(new Contact("Gabriela", 5, "gabriela@gmail.com", "09871234", "Puntarenas"));
            contacts.add(new Contact("Alex", 6, "sergiome@gmail.com", "43215678", "Puntarenas"));

        }else {
            //Lista ejemplo con datos estaticos. Normalmente, estos serían recuperados de una BD o un REST API.
            contacts.add(new Contact("Sergio", 28, "sergiome@gmail.com", "88854764", "San José"));
            contacts.add(new Contact("Andres", 1, "alex@gmail.com", "88883644", "Puntarenas"));
            contacts.add(new Contact("Andrea", 2, "andrea@gmail.com", "98714764", "Puntarenas"));
            contacts.add(new Contact("Fabian", 3, "fabian@gmail.com", "12345678", "Puntarenas"));
            contacts.add(new Contact("Ivan", 4, "ivan@gmail.com", "87654321", "Puntarenas"));
            contacts.add(new Contact("Gabriela", 5, "gabriela@gmail.com", "09871234", "Puntarenas"));
            contacts.add(new Contact("Alex", 6, "sergiome@gmail.com", "43215678", "Puntarenas"));
            contacts.add(new Contact("Michael", 6, "sergiome@gmail.com", "43215678", "Puntarenas"));
            contacts.add(new Contact(datos.getString("Nombre"), datos.getInt("Edad"), datos.getString("Email"), datos.getString("Telefono"), datos.getString("Provincia")));
        }




        //Le asignamos a la lista un nuevo BaseAdapter, implementado a continuación
        list.setAdapter(new BaseAdapter() {
            @Override
            //Retorna el numero de elementos en la lista.
            public int getCount() {
                return contacts.size();
            }

            @Override
            //Retorna el elemento que pertenece a la posición especificada.
            public Object getItem(int position) {
                return contacts.get(position);
            }

            @Override
            //Devuelve un identificador único para cada elemento de la lista.
            //En nuestro caso, basta con devolver la posición del elemento en la lista.
            public long getItemId(int position) {
                return position;
            }

            @Override
            //Devuelve la vista que corresponde a cada elemento de la lista
            public View getView(int position, View convertView, ViewGroup parent) {
                //LayoutInflater inflater = getLayoutInflater();
                //View row = inflater.inflate(R.layout.contact_item, parent, false);
                View row = convertView;

                if (row == null){
                    LayoutInflater inflater = getLayoutInflater();
                    row = inflater.inflate(R.layout.contact_item, parent, false);
                }

                TextView name, age, phone, email, provincia;

                name = (TextView) row.findViewById(R.id.name);
                age = (TextView) row.findViewById(R.id.age);
                phone = (TextView) row.findViewById(R.id.phone);
                email = (TextView) row.findViewById(R.id.email);
                provincia = (TextView) row.findViewById(R.id.provincia);

                Button btn = (Button) row.findViewById(R.id.row_btn);


                //Basandonos en la posicion provista en este metodo, proveemos los datos
                //correctos para este elemento.
                final int pos = position;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Hola, "+contacts.get(pos).name, Toast.LENGTH_SHORT).show();
                    }
                });

                name.setText(contacts.get(position).name);
                age.setText(contacts.get(position).age+"");
                phone.setText(contacts.get(position).phone);
                email.setText(contacts.get(position).email);
                provincia.setText(contacts.get(position).provincia);

                return row;
            }
        });
    }*/
}
