package superhumancrew.com.kepoku;

import android.app.Activity;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private DatabaseReference database;
    private Button btnKepo, btnLogout;
    private EditText editKepo;
    private TextView txtUser;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Kepo> daftarKepo;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser firebaseUser;
    private GoogleSignInAccount googleSignInAccount;
    String waktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    String user = firebaseUser.getDisplayName();
                    txtUser.setText("Hai "+ user + ", beritahu keadaanmu pada orang sekitar");
                }


                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
            }
        };

        editKepo = (EditText)findViewById(R.id.editKepo);
        btnKepo = (Button)findViewById(R.id.btnKepo);
        txtUser = (TextView)findViewById(R.id.txtUser);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
        handler.postDelayed(runnable, 1000);
        database = FirebaseDatabase.getInstance().getReference();
        btnKepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitKepo(new Kepo(editKepo.getText().toString(), waktu.toString()));
            }
        });

        SmileRating smileRating = (SmileRating)findViewById(R.id.smileyKepo);

        recyclerView = (RecyclerView)findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        database.child("kepo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               ArrayList<Kepo> daftarKepo = new ArrayList<Kepo>();
                for(DataSnapshot noteDataSnapshot: dataSnapshot.getChildren()){
                   Kepo kepo = noteDataSnapshot.getValue(Kepo.class);

                    daftarKepo.add(0, kepo);


                }

                adapter = new AdapterKepo(daftarKepo, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy HH:mm:ss a");
            waktu = sdf1.format(calendar.getTime());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void submitKepo(Kepo kepo){
        database.child("kepo").push().setValue(kepo).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editKepo.setText("");
                Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, MainActivity.class);
    }
}
