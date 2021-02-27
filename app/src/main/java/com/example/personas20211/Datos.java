package com.example.personas20211;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Datos {
	private static String bd = "Personas";
	private static ArrayList<Persona> personas = new ArrayList();
	private static DatabaseReference databaseReference = FirebaseDatabase.
			getInstance().getReference();
	private static StorageReference storageReference = FirebaseStorage.
			getInstance().getReference();

	public static String getId(){
		return databaseReference.push().getKey();
	}

	public static void guardar(Persona p){
		databaseReference.child(bd).child(p.getId()).setValue(p);
	}
	public static void eliminar(Persona p){
		databaseReference.child(bd).child(p.getId()).removeValue();
		storageReference.child(bd).child(p.getId()).delete();
	}

	public static void setPersonas(ArrayList<Persona> personas){
		personas = personas;
	}


}
