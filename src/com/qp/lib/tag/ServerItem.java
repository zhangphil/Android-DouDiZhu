package com.qp.lib.tag;


public class ServerItem
{

	public int nKindID;
	public int nNodeID;
	public int nSortID;
	public int nServerID;
	public int nServerPort;
	public long lOnLineCount;
	public long lOnFullCount;
	public long lCellScore;
	public long lMinEnterScore;
	public long lMaxEnterScore;
	public String szServerUrl;
	public String szServerName;
	public int nType;

	public ServerItem()
	{
		szServerUrl = "";
		szServerName = "";
		nType = 0;
	}

	public ServerItem(int kindid, int serverid, int serverport, long online, long fullcount, 
			String serverurl, String servername)
	{
		szServerUrl = "";
		szServerName = "";
		nType = 0;
		nKindID = kindid;
		nNodeID = 0;
		nSortID = 0;
		nServerID = serverid;
		nServerPort = serverport;
		lOnLineCount = online;
		lOnFullCount = fullcount;
		szServerUrl = serverurl;
		szServerName = servername;
	}

	public String toString()
	{
		return (new StringBuilder("[")).append(szServerName).append("]-[").append(szServerUrl).append("]-[").append(nServerPort).append("]-[").append(nServerID).append("]-[").append(nKindID).append("]-[").append(lCellScore).append("]-[").append(lMinEnterScore).append("]-[").append(lMaxEnterScore).append("]").toString();
	}

	public void setItem(ServerItem item)
	{
		if (item != null)
		{
			nKindID = item.nKindID;
			nNodeID = item.nNodeID;
			nSortID = item.nSortID;
			nServerID = item.nServerID;
			nServerPort = item.nServerPort;
			lOnLineCount = item.lOnLineCount;
			lOnFullCount = item.lOnFullCount;
			szServerUrl = item.szServerUrl;
			szServerName = item.szServerName;
		}
	}
}
