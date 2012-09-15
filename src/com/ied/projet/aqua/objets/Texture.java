package com.ied.projet.aqua.objets;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * @author valeria
 * @class Texture
 * 
 * Classe générale permettant la réutilisation 
 * de la méthode createTexture par les classes filles
 *
 */
public class Texture {
	
	public int[] textures = new int[1];
	
	public int createTexture(GL10 gl, Context contextRegf, int resource) {

		Bitmap image = BitmapFactory.decodeResource(contextRegf.getResources(),
				resource);

		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		image.recycle();
		return resource;
	}
}
