package com.qp.lib.cmd;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class DataDescribe implements ICmd {

	public int	nDataSize;
	public int	nDataDescribe;

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nDataSize, nIndex);
		nIndex += 2;
		NetEncoding.write2byte(data, nDataDescribe, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
