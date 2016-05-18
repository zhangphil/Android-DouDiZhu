package com.qp.lib.cmd;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class TCP_Validate implements ICmd {

	public String	szValidateKey;

	public TCP_Validate() {
		szValidateKey = "";
	}

	public int ReadFromByteArray(byte arg0[], int arg1) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		if (szValidateKey != null && !szValidateKey.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szValidateKey, data, nIndex);
		return (nIndex += 128) - pos;
	}
}
