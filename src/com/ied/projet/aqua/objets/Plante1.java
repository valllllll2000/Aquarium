package com.ied.projet.aqua.objets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLES10;

public class Plante1 extends Texture

{

	private FloatBuffer mTextureBuffer;
	private float[] textureCoords = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
	private FloatBuffer mFVertexBuffer;
	private ByteBuffer mIndexBuffer;
	private float mAngle;
	private float plusAngle=0.01f;
	
	public Plante1() {
		
		float vertices[] = { 0f, -3.0f, 1.0f, -3.0f, 0f, 2.0f, 1.0f, 2.0f };

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

		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL11.GL_SRC1_RGB, GL11.GL_PREVIOUS);

		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//mouvement de la plante
		gl.glRotatef(mAngle, 0.0f, 0.9f, 0f);
		mAngle += plusAngle;
		if (mAngle > 8 || mAngle < -8)
			plusAngle *= (-1);

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

}
