package com.uf.nads;

import java.util.BitSet;

public class FMSketch {

	final static int FM_SKETCH_SIZE = 32;
	static byte bitmap[] = null;
	
	public static void main(String[] args) throws Exception {
		generateFlows dataGenerator=new generateFlows();
		flow[] flow=dataGenerator.generateData();
		int hashVal;	
		int leadingZeros;
		int[] temp;
		double Zs,Vn,n;
		byte tempB;
		
		for(int i=1;i<flow.length;i++)
		{
			temp=flow[i].getElements();
			bitmap = new byte[FM_SKETCH_SIZE / 8 + (FM_SKETCH_SIZE % 8 == 0 ? 0 : 1)];
			//System.out.println(temp.length + ", " + flow[i].getCardinality());
			for(int j=0;j<temp.length;j++)
			{
				Integer intObj = new Integer(temp[j]);
				hashVal=intObj.hashCode();
				hashVal=hashVal%FM_SKETCH_SIZE;
				leadingZeros=Integer.numberOfLeadingZeros(hashVal);
				//System.out.println(hashVal + " , " + leadingZeros);
				if(leadingZeros>31)
					leadingZeros=31;
				if(leadingZeros<0)
						leadingZeros=0;
				setBit(hashVal,true);
			}
			/*for(int k=0;k<bitmap.length;k++)
			{
				tempB=bitmap[k];
				String s1 = String.format("%8s", Integer.toBinaryString(tempB & 0xFF)).replace(' ', '0');
				System.out.println(s1);
			}*/
			Zs=getBitCount();
			System.out.println("Zs : " + Zs);
			n=(Math.pow(2, Zs))/0.77;
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
		int setBits = 0;
		boolean flag=true;
		String s1=toBinary(bitmap);
		//String s2=new StringBuilder(s1).reverse().toString();;
		char[] arr=s1.toCharArray();;
		
		if(arr[0]=='1')
		{
			for(int i=0;i<arr.length;i++)
			{
				if(arr[i]=='1')
					setBits++;
				else 
					break;
			}
		}
		
		/*for(int i=0;i<bitmap.length&&flag;i++)
		{
			s1 = String.format("%8s", Integer.toBinaryString(bitmap[i] & 0xFF)).replace(' ', '0');
			s2=new StringBuilder(s1).reverse().toString();
			//System.out.println(s1);
			//System.out.println(s2 + "\n");
			arr=s2.toCharArray();
			
			
			for(int j=0;j<arr.length&&flag;j++)
			{
				//System.out.println("inside for : " + arr[j]);
				if(arr[j]=='1')
					setBits++;
				else if(arr[j]=='0' && setBits>0)
					flag=false;
			}
		}*/
		return setBits;
	}
	
	static String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
	
	// tests if bit is set in value
	static boolean isSet(byte value, int bit){
	   System.out.println("val " + value + "print : " + (value&(1<<bit)));
		return (value&(1<<bit))!=0;
	} 

	// returns a byte with the required bit set
	byte set(byte value, int bit){
	   value= (byte) (value|(1<<bit));
	   return value;
	}

}
