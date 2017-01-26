package org.lab.grageasmagicas.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.lab.grageasmagicas.parte_visual.AdministradorPantalla;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Grageas Magicas";
		config.width = 640;
		config.height = 310;
		config.addIcon("imagenes/launcher_windows.png", Files.FileType.Internal);
		new LwjglApplication(new AdministradorPantalla(), config);
	}
}
