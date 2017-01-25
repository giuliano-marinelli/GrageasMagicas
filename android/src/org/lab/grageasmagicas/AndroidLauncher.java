package org.lab.grageasmagicas;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new org.lab.grageasmagicas.parte_visual.AdministradorPantalla(), config);
	}
}
