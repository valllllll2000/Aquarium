package com.ied.projet.aqua.objets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLUtils;

public class Background extends Texture

{
	
	public FloatBuffer mTextureBuffer;
	private float[] textureCoords = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };

	private FloatBuffer mFVertexBuffer;
	private ByteBuffer mIndexBuffer;
	private float mSize=0;

	public Background(float sizeS) {
		
		mSize=sizeS;

		float vertices[] = { sizeS*(-1f), sizeS*(-1f), sizeS, sizeS*(-1), sizeS*(-1), sizeS, 
				sizeS, sizeS};

		byte indices[] = { 0, 3, 1, 0, 2, 3 };

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mFVertexBuffer = vbb.asFloatBuffer();
		mFVertexBuffer.put(vertices);
		mFVertexBuffer.position(0);

		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);

		ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer = tbb.asFloatBuffer();
		mTextureBuffer.put(textureCoords);
		mTextureBuffer.position(0);

	}

	public void draw(GL10 gl) {
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glEnable(GL10.GL_TEXTURE_2D);
	
	    gl.glActiveTexture(GL10.GL_TEXTURE0);	

		gl.glEnable(GL10.GL_BLEND);
	
		gl.glBlendFunc(GLES10.GL_ONE, GLES10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	

}
