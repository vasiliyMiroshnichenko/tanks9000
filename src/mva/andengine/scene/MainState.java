package mva.andengine.scene;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.input.touch.TouchEvent;

import java.util.ArrayList;

public class MainState extends Scene {

	public static MainMenuScene mainMenuScene;
	public static GameScene gameScene;
	public static LevelScene chooseLevelScene;
    public static HighScoreScene highScore;

    private ArrayList<ISwitchableScene> sceneList;

	public static int GAME_STATE;

	public static final int MAIN_MENU = 0;
	public static final int SELECT_LEVEL = 1;
	public static final int GAME_IS_RUNNING = 2;
    public static final int HIGH_SCORE_SCENE = 3;

	public MainState() {
		mainMenuScene = new MainMenuScene(this);
		gameScene = new GameScene(this);
		chooseLevelScene = new LevelScene(this);
        highScore = new HighScoreScene(this);

        sceneList = new ArrayList<ISwitchableScene>();
        sceneList.add(mainMenuScene);
        sceneList.add(gameScene);
        sceneList.add(chooseLevelScene);
        sceneList.add(highScore);

		attachChild(mainMenuScene);
		attachChild(gameScene);
        attachChild(chooseLevelScene);
        attachChild(highScore);

		ShowMainMenuScene();
	}

    public void ShowGameScene(int level) {
        GAME_STATE = GAME_IS_RUNNING;
        mainMenuScene.hide();
        chooseLevelScene.hide();
        highScore.hide();
        gameScene.show(level);
    }

    public void ShowMainMenuScene() {
        GAME_STATE = MAIN_MENU;
        switchScene();
    }

    public void ShowChooseLevelScene() {
        GAME_STATE = SELECT_LEVEL;
        switchScene();
    }

    public void ShowHighScoreScene() {
        GAME_STATE = HIGH_SCORE_SCENE;
        switchScene();
    }

/*
	public void ShowGameScene(int level) {
		GAME_STATE = GAME_IS_RUNNING;
		mainMenuScene.hide();
		chooseLevelScene.hide();
		gameScene.show(level);
	}

	public void ShowMainMenuScene() {
		GAME_STATE = MAIN_MENU;
		mainMenuScene.show();
		chooseLevelScene.hide();
		gameScene.hide();
	}

    public void ShowChooseLevelScene() {
        GAME_STATE = SELECT_LEVEL;
        mainMenuScene.hide();
        gameScene.hide();
        chooseLevelScene.show();
    }

    public void ShowHighScoreScene() {
        GAME_STATE = HIGH_SCORE_SCENE;
        highScore.show();
        mainMenuScene.hide();
        gameScene.hide();
        chooseLevelScene.hide();
    }            */

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		switch (GAME_STATE) {
		case GAME_IS_RUNNING:
			gameScene.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case MAIN_MENU:
			mainMenuScene.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case SELECT_LEVEL:
			chooseLevelScene.onSceneTouchEvent(pSceneTouchEvent);
			break;
        case HIGH_SCORE_SCENE :
            highScore.onSceneTouchEvent(pSceneTouchEvent);
            break;
		}
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

    private void switchScene(){
        for(ISwitchableScene isc : sceneList){
            if (GAME_STATE == isc.getCode()){
                isc.show();
            }
            else
                isc.hide();
        }
    }

    public void backButton(){
        switch(GAME_STATE){
            case SELECT_LEVEL :
                ShowMainMenuScene();
                break;
            case GAME_IS_RUNNING :
                gameScene.pause();
                break;
            case HIGH_SCORE_SCENE :
                ShowMainMenuScene();
                break;
        }
    }
}
