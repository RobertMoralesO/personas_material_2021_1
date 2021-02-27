package com.example.personas20211;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetallePersona extends AppCompatActivity {
	private ImageView foto;
	private TextView cedula, nombre, apellido;
	private Bundle bundle;
	private Intent intent;
	private StorageReference storageReference;
	private Persona p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_persona);

		String ced, nomb, apell, id;
		foto = findViewById(R.id.imgFotoDetalle);
		cedula = findViewById(R.id.lblCedulaDetalle);
		nombre = findViewById(R.id.lblNombreDetalle);
		apellido = findViewById(R.id.lblApellidoDetalle);

		intent = getIntent();
		bundle = intent.getBundleExtra("datos");

		id = bundle.getString("id");
		ced = bundle.getString("cedula");
		nomb = bundle.getString("nombre");
		apell = bundle.getString("apellido");

		p = new Persona(ced, nomb, apell, id);

		cedula.setText(ced);
		nombre.setText(nomb);
		apellido.setText(apell);
		storageReference = FirebaseStorage.getInstance().getReference();
		storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
			@Override
			public void onSuccess(Uri uri) {
				Picasso.get().load(uri).into(foto);
			}
		});
	}

	public void Eliminar(View v){
		String positivo, negativo;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.mensaje_eliminar);
		builder.setMessage(R.string.pregunta_eliminar_persona);
		positivo = getString(R.string.mensaje_si);
		negativo = getString(R.string.mensaje_no);

		builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				p.eliminar();
				onBackPressed();
			}
		});

		builder.setNegativeButton(negativo, null);

		AlertDialog dialog = builder.create();
		dialog.show();
	}



	public void onBackPressed(){
		finish();
		Intent intent = new Intent(DetallePersona.this, MainActivity.class);
	}
}
