package com.qp.lib.interface_ex;


public interface IGameClientEngineAnalysis
{

	public abstract Object OnEventGameMessageAnalysis(int sub, byte data[]);

	public abstract Object OnEventSceneMessageAnalysis(int sub, byte data[]);
}
