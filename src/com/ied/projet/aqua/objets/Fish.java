package com.ied.projet.aqua.objets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLES10;
import android.util.FloatMath;

public class Fish extends Texture

{
	
	private FloatBuffer mTextureBuffer;
	private float[] textureCoords = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
	private FloatBuffer mFVertexBuffer;
	private ByteBuffer mIndexBuffer;
	private float mSize=0;
	public float x, y;
	public float add=0.01f;
	public float z=-6f;
	public float mAngle;
	private float plusAngle=0.3f;
	private float mTransX;
	
	public Fish() {
			
		Random r=new Random();
		mSize = (float)r.nextInt(10) / 10f;
		x=r.nextInt(7)-3;
		y=r.nextInt(9)-4;

		float vertices[] = { mSize*(-1f), mSize*(-1f), mSize, mSize*(-1), 
				mSize*(-1), mSize, 
				mSize, mSize};

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

		z -= add;
		if (z < -8.0f || z > -6.f) {
			z = Math.min(-6, Math.max(z, -8));
			add *= (-1);
		}

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

		if (x % 2 == 0) {
			gl.glRotatef(mAngle, 0.0f, 0.8f, 0.0f);

			gl.glRotatef(mAngle, 0.0f, 0.2f, 0.0f);

		} else if (x % 3 == 0) {
			gl.glTranslatef(FloatMath.sin(mTransX) * 2, 0.0f, 0.0f);
			gl.glRotatef(mAngle, 0.0f, 0f, 1f);
			mTransX += .0075f;
		}

		mAngle += plusAngle;
		if (mAngle > 10 || mAngle < -10)
			plusAngle *= (-1);

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	

}
