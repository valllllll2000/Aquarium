package com.ied.projet.aqua;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.ied.projet.aqua.objets.Background;
import com.ied.projet.aqua.objets.Fish;
import com.ied.projet.aqua.objets.Plante1;
import com.ied.projet.aqua.objets.Plante2;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.FloatMath;
import net.rbgrn.android.glwallpaperservice.*;

public class FishRenderer implements GLWallpaperService.Renderer {

	private static int MAX_FISH = 30;
	private static int MAX_PLANT = 5;
	
	private float mTransX;
	private Context mContext;
	private Background backGroundSquare;

	private ArrayList<Fish> fishes = new ArrayList<Fish>();
	private ArrayList<Plante2> plantes = new ArrayList<Plante2>();

	private float mAngle;
	private Plante1 plante;

	public FishRenderer(Context ctx) {
		this.mContext = ctx;
		
		//creation de l'objet pour le fond
		this.backGroundSquare = new Background(2.5f);
		
		//creation des plantes de base
		this.plante = new Plante1();
		
		for (int j=0;j< MAX_PLANT;j++){
			plantes.add(new Plante2());
		}
		
		//creations des poissons
		for (int i = 0; i < MAX_FISH; i++) {
			Fish f = new Fish();
			if (i % 3 == 0) {
				f.z = 6f;
				f.add = 0.001f;
			}
			if (i % 2 == 0) {
				f.z = 7f;
				f.add = 0.01f;
			} else {
				f.z = 8f;
				f.add = 0.005f;
			}
			fishes.add(f);
		}
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		gl.glClearColor(0.0f, 0.3f, 0.5f, 1.0f);
		gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL10.GL_MODELVIEW);

		gl.glLoadIdentity();
		gl.glTranslatef(FloatMath.sin(mTransX), 0.0f, -2.0f);
		gl.glRotatef(mAngle, 0.0f, 1.0f, 0.0f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		
		backGroundSquare.draw(gl);

		for (Fish f : fishes) {
			gl.glLoadIdentity();
			gl.glTranslatef(f.x, f.y, f.z);
			f.draw(gl);
		}

		gl.glLoadIdentity();
		gl.glTranslatef(1f, 0.0f, -3.0f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		plante.draw(gl);

		gl.glTranslatef(-1f, 0.0f, 0.0f);
		plante.draw(gl);
		
		gl.glTranslatef(-1f, 0.0f, 0.0f);
		plante.draw(gl);

		gl.glTranslatef(-1f, 0.0f, 0.0f);
		plante.draw(gl);
		
		
		for (int j = 0; j < plantes.size(); j++) {
			gl.glLoadIdentity();
			gl.glTranslatef((float)j*(-0.5f)+2f, 3f, -5f);
			plantes.get(j).draw(gl);
		}
		mTransX += .0075f;

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		gl.glViewport(0, 0, width, height);

		float ratio = (float) width / height;

		gl.glMatrixMode(GL11.GL_PROJECTION);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

		if (width > height)
			backGroundSquare = new Background(5f); //format paysage
		else
			backGroundSquare = new Background(2.5f); //format portrait
		
		backGroundSquare.createTexture(gl, mContext, R.drawable.sea);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		MAX_FISH = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(mContext).getString("fish_number",
						"30"));

		MAX_PLANT = Integer.parseInt(PreferenceManager
				.getDefaultSharedPreferences(mContext).getString(
						"plant_number", "5"));

		gl.glDisable(GL11.GL_DITHER);

		gl.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);

		gl.glClearColor(0, 0, 1, 1);

		gl.glEnable(GL11.GL_CULL_FACE);

		gl.glShadeModel(GL11.GL_SMOOTH);
		
		
		//creation des textures
		backGroundSquare.createTexture(gl, this.mContext, R.drawable.sea);

		for (int i = 0; i < fishes.size(); i++) {
			if (i % 3 == 0) {
				fishes.get(i)
						.createTexture(gl, this.mContext, R.drawable.fish6);
			} else if (i % 2 == 0) {
				fishes.get(i)
						.createTexture(gl, this.mContext, R.drawable.fish7);
			} else {
				fishes.get(i)
						.createTexture(gl, this.mContext, R.drawable.fish8);
			}
		}

		plante.createTexture(gl, mContext, R.drawable.plant4);
		
		for(int i=0;i<plantes.size();i++){
			plantes.get(i).createTexture(gl, mContext, R.drawable.plant1);
		}

		gl.glDepthMask(false);
		initLighting(gl);
	}

	public void release() {

	}

	private void initLighting(GL10 gl) {
		
		//position de la lumière
		float[] positionLum = { 12.0f, -3.0f, 1.0f, 2.0f };

		//les différentes couleurs pour la lumière
		float[] blanc = { 1.0f, 1.0f, 1.0f, 0.8f };
		float[] rouge = { .5f, 0.0f, 0.0f, 1.0f };
		float[] cyan = { 0.0f, 1.0f, 3.0f, 0.0f };

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION,
				makeFloatBuffer(positionLum));
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, makeFloatBuffer(rouge));
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, makeFloatBuffer(cyan));

		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_QUADRATIC_ATTENUATION, .005f);

		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
				makeFloatBuffer(cyan));
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				makeFloatBuffer(blanc));

		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);

		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glLightModelf(GL10.GL_LIGHT_MODEL_AMBIENT, 1.0f);

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);

		gl.glLoadIdentity();
	}

	protected static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

}
