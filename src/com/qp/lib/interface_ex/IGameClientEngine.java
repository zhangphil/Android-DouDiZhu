package com.qp.lib.interface_ex;

public interface IGameClientEngine extends IGameClientEngineAnalysis {

	public abstract boolean OnEventGameMessage(int i, Object obj);

	public abstract boolean OnEventSceneMessage(int i, Object obj);

	public abstract void UpdateGameView();
}
