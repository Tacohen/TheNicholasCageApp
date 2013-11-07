package com.tacohen.cage;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

/**IT'S A NICHOLAS CAGE APP!
 */
public class Cage extends SimpleBaseGameActivity {
	
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	public TextureRegion head, mBackgroundTextureRegion, head2;
	public Sprite headSprite;


	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
		    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		try {
			ITexture head = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/head.png");
		        }
		    });
			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/background-resized-blank.png");
		        }
		    });
			
		
		
		head.load();
		backgroundTexture.load();
		
		this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		this.head = TextureRegionFactory.extractFromTexture(head);
		//this.head2 = TextureRegionFactory.extractFromTexture(head);
		
		
		}catch (IOException e) {
		    Debug.e(e);
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		final Scene scene = new Scene();
		Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		headSprite = new Sprite(150,150, this.head, getVertexBufferObjectManager());
		GraphicsModel head = new GraphicsModel(1, 139, 174, this.head, getVertexBufferObjectManager()) {
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, 
		            pSceneTouchEvent.getY() - this.getHeight() / 2);
		        float x = pSceneTouchEvent.getX()- this.getWidth() / 2;
		        float y = pSceneTouchEvent.getY()- this.getWidth() / 2;
		        GraphicsModel head2 = AttachTrailHead(x,y);
		        scene.clearChildScene();
		        scene.attachChild(head2);
				return true;
		    }
		};
		
		scene.registerTouchArea(head);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.attachChild(head);
		return scene;
	}
	
	public GraphicsModel AttachTrailHead(float x,float y){
		headSprite = new Sprite(x,y, this.head, getVertexBufferObjectManager());
		GraphicsModel head = new GraphicsModel(1, x, y, this.head, getVertexBufferObjectManager());
		return head;
		
	}
}
