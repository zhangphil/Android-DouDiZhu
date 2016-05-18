package com.smw.net;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public interface ICmd
{
	public abstract int WriteToByteArray(byte data[], int pos);

	public abstract int ReadFromByteArray(byte data[], int pos);

}
