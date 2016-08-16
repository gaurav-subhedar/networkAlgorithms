package com.uf.nads;

import java.util.Random;

public class flow
{	
	private int flowId;
	private  int cardinality;	
	private  int[] elements;
	private int flowSize;
	
	 void setFlowId(int fid)
	{
		flowId=fid;
	}
	 int getFlowId()
	{
		return flowId;
	}
	 int getFlowSize()
		{
			return flowSize;
		}
	
	 void setCardinality(int elementCnt)
	{
		cardinality=elementCnt;
	}
	 int getCardinality()
	{
		return cardinality;
	}
	
	 void setElements() throws Exception
	{
		Random rand=new Random(flowId);
		int temp;
		if(cardinality<1)
			throw new Exception();				
		elements=new int[cardinality];
		for(int i=0;i<cardinality;i++)
		{		
			temp=Math.abs(rand.nextInt());
			elements[i]= temp;
		}
	}
	 int[] getElements() throws Exception
	{
		if(elements.length<0||elements.length>cardinality)
			throw new Exception();
		return elements;
	}
}
