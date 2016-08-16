package com.uf.nads;

import java.util.Random;

//import com.uf.nads.flow;



public class generateFlows 
{
	
	final static int NO_OF_FLOWS = 1000;
	final static int SAMPLE_COUNT=500;
	static double[] cardinalityArr = new double[SAMPLE_COUNT];
	static double[] freqDistribution = new double[SAMPLE_COUNT];
	
	public static void main(String[] args) throws Exception 
	{
	}
	
	flow[] generateData() throws Exception
	{
		
		Random rand=new Random();
		flow[] flowArray = new flow[NO_OF_FLOWS];
		
		double sum=0;
		
		for(int i=1;i<SAMPLE_COUNT;i++)
		{
			cardinalityArr[i]=30000*(Math.pow(i, -1.7));
			sum+=cardinalityArr[i];
		}
		
		//calculate frequency distribution
		for(int i=1;i<SAMPLE_COUNT;i++)
		{
			freqDistribution[i]=cardinalityArr[i]/sum;			
		}

				
		//set flowId, cardinality for all flows
		int cardinality;
		for(int i=1;i<NO_OF_FLOWS;i++)
		{
			flowArray[i]=new flow();
			flowArray[i].setFlowId(Math.abs(rand.nextInt()));						
			cardinality = getCardinalityIndex();
			//System.out.println("cardinality : " + cardinality);
			flowArray[i].setCardinality(cardinality);
			//System.out.println(flowArray[i].getFlowId());
			//System.out.println(flowArray[i].getCardinality());
			flowArray[i].setElements();	
		}
		
		//System.out.println("here");
		
		//print all elements of all flows
		/*int[] temp;
		for(int i=1;i<NO_OF_FLOWS;i++)
		{
			temp=flowArray[i].getElements();
			System.out.println("length : " + temp.length);
			
		*/		
		return flowArray;
	}
	
	int getCardinalityIndex()
	{
		double min=100,rand;
		int minIndex=1;
		for(int i=1;i<SAMPLE_COUNT;i++)
		{
			rand=Math.random();
			if(freqDistribution[i]==rand)
			{
				return i;
			}
			if(min>Math.abs(freqDistribution[i]-rand))
			{
				min=Math.abs(freqDistribution[i]-rand);
				minIndex=i;
			}
		}
		return minIndex;
		
	}

}
