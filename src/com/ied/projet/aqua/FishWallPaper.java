package com.ied.projet.aqua;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;

public class FishWallPaper extends GLWallpaperService {

	public FishWallPaper() {
		super();
	}

	public Engine onCreateEngine() {
		return new FishEngine();
	}

	class FishEngine extends GLEngine {
		FishRenderer renderer;

		public FishEngine() {
			super();
			renderer = new FishRenderer(getApplicationContext());
			setRenderer(renderer);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
		}

		public void onDestroy() {
			super.onDestroy();
			if (renderer != null) {
				renderer.release();
			}
			renderer = null;
		}
	}
}