package com.example.personas20211;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CrearPersonas extends AppCompatActivity {
	private EditText cedula, nombre, apellido;
	private ImageView foto;
	private Uri uri;
	private StorageReference storageReference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crear_personas);

		cedula = findViewById(R.id.txtCedula);
		nombre = findViewById(R.id.txtNombre);
		apellido = findViewById(R.id.txtApellido);
		foto = findViewById(R.id.imgFotoSeleccionada);

		storageReference = FirebaseStorage.getInstance().getReference();
	}

	public void guardar(View v){
		String ced, nom, apell, id;
		Persona p;
		InputMethodManager imp;

		imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imp.hideSoftInputFromWindow(cedula.getWindowToken(),0);

		if(validar()) {
			ced = cedula.getText().toString();
			nom = nombre.getText().toString();
			apell = apellido.getText().toString();
			id = Datos.getId();

			p = new Persona(ced, nom, apell, id);
			p.guardar();
			Snackbar.make(v, R.string.mensaje_guardado_exitoso, Snackbar.LENGTH_LONG).show();
			limpiar();
			subir_foto(id);
			uri = null;
		}

	}

	public void subir_foto(String id){
		StorageReference child = storageReference.child(id);
		UploadTask uploadTask = child.putFile(uri);
	}
	public void limpiar(View v){
		limpiar();
	}

	public void limpiar(){
		cedula.setText("");
		nombre.setText("");
		apellido.setText("");
		cedula.requestFocus();
		foto.setImageResource(android.R.drawable.ic_menu_gallery);
	}

	public void seleccionarFoto(View v){
		Intent in = new Intent();
		in.setType("image/*");
		in.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(in,getString(R.string.mensaje_seleccion_foto)),1);
	}

	public boolean validar(){
		if(cedula.getText().toString().isEmpty()){
			cedula.setError(getString(R.string.error_ingreso_cedula));
			cedula.requestFocus();
			return false;
		}

		if (nombre.getText().toString().isEmpty()){
			nombre.setError(getString(R.string.error_ingreso_nombre));
			nombre.requestFocus();
			return false;
		}

		if (apellido.getText().toString().isEmpty()){
			apellido.setError(getString(R.string.error_ingreso_apellido));
			apellido.requestFocus();
			return false;
		}

		if(uri == null){
			Snackbar.make(cedula,"Seleccione una foto",Snackbar.LENGTH_LONG).show();
			return false;
		}
		return true;

	}

	public void onBackPressed(){
		finish();
		Intent i = new Intent(CrearPersonas.this, MainActivity.class);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			uri = data.getData();
			if(uri != null){
				foto.setImageURI(uri);
			}
		}
	}
}
