package com.qp.lib.tag;


public class TableItem
{

	public int tableid;
	public int chaircounts;
	public boolean bLooked;
	public boolean bPlayed;
	public int faceID[];
	public long userID[];
	public int status[];

	public TableItem(int count)
	{
		chaircounts = count;
		faceID = new int[count];
		userID = new long[count];
		status = new int[count];
		tableid = 0;
		bLooked = false;
		bPlayed = false;
		setEmpty();
	}

	public void setEmpty()
	{
		for (int i = 0; i < chaircounts; i++)
		{
			userID[i] = 65535L;
			status[i] = 0;
			faceID[i] = 0;
		}

	}
}
