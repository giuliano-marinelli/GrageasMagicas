package org.lab.grageasmagicas.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.lab.grageasmagicas.AdministradorPantalla;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Grageas Magicas";
		new LwjglApplication(new AdministradorPantalla(), config);
	}
}
