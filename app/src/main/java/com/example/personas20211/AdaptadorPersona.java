package com.example.personas20211;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.PersonaViewHolder> {
	private ArrayList<Persona> personas;

	public AdaptadorPersona(ArrayList<Persona> personas){
		this.personas = personas;
	}

	@NonNull
	@Override
	public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).
				inflate(R.layout.item_persona, parent,false);
		return new PersonaViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull PersonaViewHolder holder, int position) {
		Persona p = personas.get(position);
		StorageReference storageReference;
		storageReference = FirebaseStorage.getInstance().getReference();

		storageReference.child(p.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
			@Override
			public void onSuccess(Uri uri) {
				Picasso.get().load(uri).into(holder.foto);
			}
		});
		holder.cedula.setText(p.getCedula());
		holder.nombre.setText(p.getNombre());
		holder.apellido.setText(p.getApellido());
	}

	@Override
	public int getItemCount() {
		return personas.size();
	}

	public static class PersonaViewHolder extends RecyclerView.ViewHolder{
		private CircleImageView foto;
		private TextView cedula;
		private TextView nombre;
		private TextView apellido;
		private View v;

		public PersonaViewHolder(@NonNull View itemView) {
			super(itemView);
			v = itemView;
			foto = v.findViewById(R.id.imagFotoItem);
			cedula = v.findViewById(R.id.lblCedula);
			nombre = v.findViewById(R.id.lblNombre);
			apellido = v.findViewById(R.id.lblApellido);
		}
	}
}
