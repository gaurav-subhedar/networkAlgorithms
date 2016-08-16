package com.uf.nads;

import java.util.BitSet;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class virtualBitmap {

	final static int BITMAP_SIZE = 16384 * 8;
	final static long S=500;
	static byte bitmap[] = null;
	static int R[] = new int[(int) S];
	
	static int NO_OF_FLOWS=1000;
	
	public static void main(String[] args) throws Exception {
		
		generatePseudoRandomArray();
		//for(int i=0;i<R.length;i++)
			//System.out.println(R[i]);
		bitmap = new byte[BITMAP_SIZE / 8];
		generateFlows dataGenerator=new generateFlows();
		flow[] flow=dataGenerator.generateData();
		
		int src, dst[],index1,temp1,temp2;
		
		long newHashVal,index2;
		
		double Un,Vn,Us,Vs;
		
		//NO_OF_FLOWS=flow.length;
		
		for(int i=1;i<NO_OF_FLOWS;i++)
		{
			//System.out.println("flow cardinality : " + flow[i].getCardinality());
			
			src=flow[i].getFlowId();
			dst=flow[i].getElements();
			//System.out.println("dst length : " + dst.length);
			//for(int j=0;j<dst.length;j++)
				//System.out.println(dst[j]);
			for(int j=0;j<dst.length;j++)
			{
				newHashVal = getHashCode(String.valueOf(dst[j]));
				//System.out.println("newHashVal : " + newHashVal);
				index1=(int) (newHashVal%S);
				//System.out.println("index1 : " + index1);
				temp1=R[index1];
				//System.out.println("temp1 : " + temp1);
				temp2=src ^ temp1;
				//System.out.println("temp2 : " + temp2);
				index2=getHashCode(String.valueOf(temp2));
				//System.out.println("index2 : " + index2);
				setBit((int) (index2%BITMAP_SIZE), true);	
			}
		}
		
		Un=(double) (BITMAP_SIZE-getBitCount());
		Vn=Un/BITMAP_SIZE;
		//System.out.println("Un : " + Un);
		//System.out.println("Vn : " + Vn);
		double noise = -S*Math.log(Vn);
		//System.out.println("Un : " + Un + " Vn : " + Vn + " noise : " + noise);
				
		
		for(int i=1;i<NO_OF_FLOWS;i++)
		{
			//System.out.println("flow cardinality : " + flow[i].getCardinality());

			byte[] virtualBitVector = new byte[(int) S];
			Random rand=new Random();
			int pos,ps,temp;
			long index,flowIdHash;
			src=flow[i].getFlowId();
			//dst=flow[i].getElements();
			//System.out.println("dst length : " + dst.length);
			for(int j=0;j<S;j++)
			{
				ps=R[j];
				//flowIdHash=getHashCode(String.valueOf(src));
				temp=ps^src;
				index=getHashCode(String.valueOf(temp));
				index=index%BITMAP_SIZE;
				virtualBitVector=setBitNew(virtualBitVector, j, getBit((int)index));
			}
			Us=(long) (S-getBitCountNew(virtualBitVector));
			//System.out.println("Us : " + Us);
			Vs=Us/S;
			//System.out.println("Vs : " + Vs);
			double info = -S*Math.log(Vs);
			//System.out.println("\nUs : " + Us + " Vs : " + Vs + " info : " + info);
			//if((info-noise)>0 && (info-noise)<100)
				System.out.println(flow[i].getCardinality() + ", " + (info-noise));
		}

	}

	private static long getHashCode(String str) {
		
		String hashVal = DigestUtils.sha1Hex(str);		
		long longHashVal = Long.parseLong(hashVal.substring(0, 8), 16);
		//System.out.println("in func : " + Math.abs(longHashVal));
		return Math.abs(longHashVal);
	}

	private static void generatePseudoRandomArray() {
		Random rand = new Random();
		for(int i=0;i<S;i++)
		{
			R[i]=Math.abs(rand.nextInt());
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
	
	public static byte[] setBitNew(byte[] byteArr, int pos, boolean b) 
	{
        byte b8 = byteArr[pos / 8];
        byte posBit = (byte) (1 << (pos % 8));
        if (b) {
            b8 |= posBit;
        } else {
            b8 &= (255 - posBit);
        }
        byteArr[pos / 8] = b8;
		return byteArr;
    }
	
	static double getBitCount()
	{
		BitSet bitset = BitSet.valueOf(bitmap);  
	    int setBits = bitset.cardinality();
		return setBits;
	}
	
	static double getBitCountNew(byte[] byteArr)
	{
		BitSet bitset = BitSet.valueOf(byteArr);  
	    int setBits = bitset.cardinality();
		return setBits;
	}
	
	public static boolean getBit(int pos) 
	{
        return (bitmap[pos / 8] & (1 << (pos % 8))) != 0;
    }
	
}
