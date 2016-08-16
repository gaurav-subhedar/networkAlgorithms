package com.uf.nads;

import org.apache.commons.codec.digest.DigestUtils;

public class FMSketch_2 {
	
	private static final double PHI = 0.77351D; 
	private static int numBitMap=16; 
    private static int bitmapSize=32; 
    private static boolean[][] bitmaps; 

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		bitmaps = new boolean[numBitMap][bitmapSize];
		
		generateFlows dataGenerator=new generateFlows();
		flow[] flow=dataGenerator.generateData();
		
		int leadingZeros;
		int[] temp;
		double Zs,Vn;
		
		for(int i=1;i<flow.length;i++)
		{
			
			temp=flow[i].getElements();
			bitmaps = new boolean[numBitMap][bitmapSize];
			//System.out.println(temp.length + ", " + flow[i].getCardinality());
			for(int j=0;j<temp.length;j++)
			{
				long newHashVal = getHashCode(String.valueOf(temp[j]));
				String binaryHash=Long.toBinaryString(newHashVal);
				//System.out.println("newHashVal : " + newHashVal + " binaryHash : " + binaryHash);
				String index1=binaryHash.substring(binaryHash.length()-4,binaryHash.length());
				String index2=binaryHash.substring(0,binaryHash.length()-5);
				int intIndex1=Integer.parseInt(index1, 2);
				Long intIndex2=Long.parseLong(index2,2);
				//System.out.println("index1 : " + index1 + " index2 : " + index2 + " intIndex1 : " + intIndex1 + " intIndex2 : " + intIndex2);				
				int rhoIndex=rho(intIndex2%bitmapSize);
				/*leadingZeros=Integer.numberOfLeadingZeros(intIndex2);
				System.out.println(" leadingZeros : " + leadingZeros);
				if(leadingZeros>31)
					leadingZeros=31;
				if(leadingZeros<0)
						leadingZeros=0;
				setBit(intIndex1,intIndex2,true);*/
				if (!bitmaps[intIndex1][rhoIndex]) { 
                    bitmaps[intIndex1][rhoIndex] = true;
                     
                }
			}
			
			int sumR = 0; 
            for (int j=0; j<numBitMap; j++) { 
                sumR += (getFirstZeroBit(bitmaps[j])); 
            } 
            
            long r=sumR/numBitMap;
           double n = (numBitMap*(Math.pow(2, r) / PHI));
            
            System.out.println(n + ", " + flow[i].getCardinality());
		}
		
		
	}

	 private static int getFirstZeroBit(boolean[] b) { 
	        for (int i=0; i<b.length; i++) { 
	            if (b[i] == false) { 
	                return i; 
	            } 
	        } 
	        return b.length; 
	    }
	
	private static long getHashCode(String str) {
		
		String hashVal = DigestUtils.sha1Hex(str);		
		long longHashVal = Long.parseLong(hashVal.substring(0, 8), 16);
		//System.out.println("in func : " + Math.abs(longHashVal));
		return Math.abs(longHashVal);
	}
	
	private static int rho(long v) { 
        int rho = 0; 
        for (int i=0; i<bitmapSize; i++) { // size of long=64 bits. 
            if ((v & 0x01) == 0) { 
                v = v >> 1; 
                rho++; 
            } else { 
                break; 
            } 
        } 
        return rho == bitmapSize ? 0 : rho; 
    } 
}

