package com.uf.nads;

import org.apache.commons.codec.digest.DigestUtils;

public class PCSA {

	final static int FM_SKETCH_SIZE = 32;
	final static int NO_OF_FM_SKETCH = 16;
	static byte bitmap[] = null;
	static byte bitmap2D[][] = null;
	
	public static void main(String[] args) throws Exception {
		
		generateFlows dataGenerator=new generateFlows();
		flow[] flow=dataGenerator.generateData();
		int leadingZeros;
		int[] temp;
		double Zs,Vn,n;
				
		for(int i=1;i<2;i++)
		{
			temp=flow[i].getElements();
			bitmap = new byte[FM_SKETCH_SIZE/8];
			bitmap2D=new byte[NO_OF_FM_SKETCH][FM_SKETCH_SIZE/8];
			System.out.println(temp.length + ", " + flow[i].getCardinality());
			for(int j=0;j<temp.length;j++)
			{
				long newHashVal = getHashCode(String.valueOf(temp[j]));
				String binaryHash=Long.toBinaryString(newHashVal);
				System.out.println("newHashVal : " + newHashVal + " binaryHash : " + binaryHash);
				String index1=binaryHash.substring(binaryHash.length()-4,binaryHash.length());
				String index2=binaryHash.substring(0,binaryHash.length()-5);
				int intIndex1=Integer.parseInt(index1, 2);
				int intIndex2=Integer.parseInt(index2, 2)%FM_SKETCH_SIZE;
				System.out.println("index1 : " + index1 + " index2 : " + index2 + " intIndex1 : " + intIndex1 + " intIndex2 : " + intIndex2);				
				leadingZeros=Integer.numberOfLeadingZeros(intIndex2);
				System.out.println(" leadingZeros : " + leadingZeros);
				if(leadingZeros>31)
					leadingZeros=31;
				if(leadingZeros<0)
						leadingZeros=0;
				setBit(intIndex1,intIndex2,true);
			}
			String s1="";
			for(int k=0;k<NO_OF_FM_SKETCH;k++)
			{
				s1=s1.concat(toBinary(bitmap2D[k]));
			}
			System.out.println(s1);
			Zs=getBitCount();
			System.out.println("Zs : " + Zs);
			Zs=Zs/NO_OF_FM_SKETCH;
			n=NO_OF_FM_SKETCH*(Math.pow(2, Zs))/0.77351;
			System.out.println(n + ", " + flow[i].getCardinality());
			//System.out.println(n + ", " + flow[i].getCardinality() + ", " + Math.abs(n-flow[i].getCardinality()));
		}

	}
	
	private static long getHashCode(String str) {
		
		String hashVal = DigestUtils.sha1Hex(str);		
		long longHashVal = Long.parseLong(hashVal.substring(0, 8), 16);
		//System.out.println("in func : " + Math.abs(longHashVal));
		return Math.abs(longHashVal);
	}
	
	public static void setBit(int pos1,int pos2, boolean b) 
	{
        byte b8 = bitmap2D[pos1][pos2 / 8];
        byte posBit = (byte) (1 << (pos2 % 8));
        if (b) {
            b8 |= posBit;
        } else {
            b8 &= (255 - posBit);
        }
        bitmap2D[pos1][pos2 / 8] = b8;
    }
	
	public boolean getBit(int pos) 
	{
        return (bitmap[pos / 8] & (1 << (pos % 8))) != 0;
    }
	
	static double getBitCount()
	{
		int setBits = 0;
		String s1=null;
		String s2=null;
		char[] arr;
		for(int k=0;k<NO_OF_FM_SKETCH;k++)
		{
			s1 = toBinary(bitmap2D[k]);
			s2=new StringBuilder(s1).reverse().toString();
			arr=s2.toCharArray();
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
		}
		return setBits;
	}
	
	// tests if bit is set in value
	static boolean isSet(byte value, int bit){
	   System.out.println("val " + value + "print : " + (value&(1<<bit)));
		return (value&(1<<bit))!=0;
	} 
	
	static String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
	// returns a byte with the required bit set
	byte set(byte value, int bit){
	   value= (byte) (value|(1<<bit));
	   return value;
	}

}
