package com.uf.nads;
import java.lang.*;
import java.util.BitSet;

public class probabilisticCounting {

	final static int BITMAP_SIZE = 512;
	static byte bitmap[] = null;
	
	public static void main(String[] args) throws Exception {
		
		generateFlows dataGenerator=new generateFlows();
		flow[] flow=dataGenerator.generateData();
		int hashVal;	
		
		int[] temp;
		double Un,Vn,n;
		for(int i=1;i<flow.length;i++)
		{
			temp=flow[i].getElements();
			bitmap = new byte[BITMAP_SIZE / 8 + (BITMAP_SIZE % 8 == 0 ? 0 : 1)];
			for(int j=0;j<temp.length;j++)
			{
				Integer intObj = new Integer(temp[j]);
				hashVal=intObj.hashCode()%BITMAP_SIZE;
				setBit(hashVal,true);
			}
			Un=BITMAP_SIZE-getBitCount();
			Vn=Un/BITMAP_SIZE;
			//System.out.println(Un + ", " + Vn);
			n=-1*BITMAP_SIZE*Math.log(Vn);
			System.out.println(n + ", " + flow[i].getCardinality());
			//System.out.println(n + ", " + flow[i].getCardinality() + ", " + Math.abs(n-flow[i].getCardinality()));
		}

	}
	public static void setBit(int pos, boolean b) 
	{
        byte b8 = bitmap[pos / 8];
        byte posBit = (byte) (1 << (pos % 8));
        if (b) {
            b8 |= posBit;
        } else {
            b8 &= (255 - posBit);
        }
        bitmap[pos / 8] = b8;
    }
	
	public boolean getBit(int pos) 
	{
        return (bitmap[pos / 8] & (1 << (pos % 8))) != 0;
    }
	
	static double getBitCount()
	{
		BitSet bitset = BitSet.valueOf(bitmap);  
	    int setBits = bitset.cardinality();
		return setBits;
	}


}
