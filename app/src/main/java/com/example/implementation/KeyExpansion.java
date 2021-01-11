package com.example.implementation;

public class KeyExpansion {
    private static final  byte[][] sbox = {{(byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2,(byte) 0x6b,(byte) 0x6f, (byte)0xc5, (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76},
            {(byte)0xca, (byte)0x82, (byte)0xc9, (byte)0x7d, (byte)0xfa, (byte)0x59, (byte)0x47, (byte)0xf0, (byte)0xad, (byte)0xd4, (byte)0xa2, (byte)0xaf, (byte)0x9c, (byte)0xa4, (byte)0x72, (byte)0xc0},
            {(byte)0xb7, (byte)0xfd, (byte)0x93, (byte)0x26, (byte)0x36, (byte)0x3f, (byte)0xf7, (byte)0xcc, (byte)0x34, (byte)0xa5, (byte)0xe5, (byte)0xf1, (byte)0x71, (byte)0xd8, (byte)0x31, (byte)0x15},
            {(byte)0x04, (byte)0xc7, (byte)0x23, (byte)0xc3, (byte)0x18, (byte)0x96, (byte)0x05, (byte)0x9a, (byte)0x07, (byte)0x12, (byte)0x80, (byte)0xe2, (byte)0xeb, (byte)0x27, (byte)0xb2, (byte)0x75},
            {(byte)0x09, (byte)0x83, (byte)0x2c, (byte)0x1a, (byte)0x1b, (byte)0x6e, (byte)0x5a, (byte)0xa0, (byte)0x52, (byte)0x3b, (byte)0xd6, (byte)0xb3, (byte)0x29, (byte)0xe3, (byte)0x2f, (byte)0x84},
            {(byte)0x53, (byte)0xd1, (byte)0x00, (byte)0xed, (byte)0x20, (byte)0xfc, (byte)0xb1, (byte)0x5b, (byte)0x6a, (byte)0xcb, (byte)0xbe, (byte)0x39, (byte)0x4a, (byte)0x4c, (byte)0x58, (byte)0xcf},
            {(byte)0xd0, (byte)0xef, (byte)0xaa, (byte)0xfb, (byte)0x43, (byte)0x4d, (byte)0x33, (byte)0x85, (byte)0x45, (byte)0xf9, (byte)0x02, (byte)0x7f, (byte)0x50, (byte)0x3c, (byte)0x9f, (byte)0xa8},
            {(byte)0x51, (byte)0xa3, (byte)0x40, (byte)0x8f, (byte)0x92, (byte)0x9d, (byte)0x38, (byte)0xf5, (byte)0xbc, (byte)0xb6, (byte)0xda, (byte)0x21, (byte)0x10, (byte)0xff, (byte)0xf3, (byte)0xd2},
            {(byte)0xcd, (byte)0x0c, (byte)0x13, (byte)0xec, (byte)0x5f, (byte)0x97, (byte)0x44, (byte)0x17, (byte)0xc4, (byte)0xa7, (byte)0x7e, (byte)0x3d, (byte)0x64, (byte)0x5d, (byte)0x19, (byte)0x73},
            {(byte)0x60, (byte)0x81, (byte)0x4f, (byte)0xdc, (byte)0x22, (byte)0x2a, (byte)0x90, (byte)0x88, (byte)0x46, (byte)0xee, (byte)0xb8, (byte)0x14, (byte)0xde, (byte)0x5e, (byte)0x0b, (byte)0xdb},
            {(byte)0xe0, (byte)0x32, (byte)0x3a, (byte)0x0a, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5c, (byte)0xc2, (byte)0xd3, (byte)0xac, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xe4, (byte)0x79},
            {(byte)0xe7, (byte)0xc8, (byte)0x37, (byte)0x6d, (byte)0x8d, (byte)0xd5, (byte)0x4e, (byte)0xa9, (byte)0x6c, (byte)0x56, (byte)0xf4, (byte)0xea, (byte)0x65, (byte)0x7a, (byte)0xae, (byte)0x08},
            {(byte)0xba, (byte)0x78, (byte)0x25, (byte)0x2e, (byte)0x1c, (byte)0xa6, (byte)0xb4, (byte)0xc6, (byte)0xe8, (byte)0xdd, (byte)0x74, (byte)0x1f, (byte)0x4b, (byte)0xbd, (byte)0x8b, (byte)0x8a},
            {(byte)0x70, (byte)0x3e, (byte)0xb5, (byte)0x66, (byte)0x48, (byte)0x03, (byte)0xf6, (byte)0x0e, (byte)0x61, (byte)0x35, (byte)0x57, (byte)0xb9, (byte)0x86, (byte)0xc1, (byte)0x1d, (byte)0x9e},
            {(byte)0xe1, (byte)0xf8, (byte)0x98, (byte)0x11, (byte)0x69, (byte)0xd9, (byte)0x8e, (byte)0x94, (byte)0x9b, (byte)0x1e, (byte)0x87, (byte)0xe9, (byte)0xce, (byte)0x55, (byte)0x28, (byte)0xdf},
            {(byte)0x8c, (byte)0xa1, (byte)0x89, (byte)0x0d, (byte)0xbf, (byte)0xe6, (byte)0x42, (byte)0x68, (byte)0x41, (byte)0x99, (byte)0x2d, (byte)0x0f, (byte)0xb0, (byte)0x54, (byte)0xbb, (byte)0x16}};
    private static final byte[][] RCon =
            {
                    new byte[] {(byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08,(byte) 0x10, (byte)0x20, (byte)0x40, (byte)0x80, (byte)0x1b, (byte)0x36},
                    new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte) 0x00},
                    new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00},
                    new byte[] {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,(byte) 0x00,(byte) 0x00, (byte)0x00,(byte) 0x00, (byte)0x00, (byte)0x00}
            };
    private final int Nb = 4;
    private int Nk,Nr;
    private byte[][] GetState(byte[] in )
    {
        int Nk = (in.length)/4, c,r,i=0;
        byte[][] out = new byte[Nb][Nk];
        for( c =0 ; c< Nk ; c++)
            for( r =0 ;r<Nb ; r++ )
                out[r][c] = in[i++];
        return out;
    }
    private byte[] GetBlock(byte[][] in)
    {
        byte[] out = new byte[4*in[0].length];
        int r,c,i=0;
        for(c =0 ; c < in[0].length; c++)
            for( r =0 ; r<4; r++ )
                out[i++] = in[r][c];
        return out;
    }


    private byte[] RotWord( byte[] w )
    {
        byte temp = w[0];
        for(int c = 0; c < Nb-1 ; c++ )
            w[c] = w[c+1];
        w[Nb-1] = temp;
        return w;
    }
    private byte[] SubWord(byte[] w)
    {
        int r,c,i;
        String str ,x1,x2;
        for( i =0; i< Nb ; i++)
        {
            str = String.format("%02x",w[i]);
            x1 =str.substring(0,1);
            x2 = str.substring(1,2);
            r = Integer.parseInt(x1,16);
            c = Integer.parseInt(x2,16);
            w[i] = sbox[r][c];
        }//end for i
        return w;
    }


    public byte[] GetWordKey (byte[] word)
    {
        Nk = word.length/4;
        Nr = Nk + 6;
        byte[][] w=GetState(word);
        byte[][] Key= new byte[Nb][Nb*(Nr+1)];
        byte[] temp = new byte[Nb];
        int r,c;
        //System.out.println("The password length = "+word.length+" the key length = "+w[0].length+"The new Key Genrated = "+Key[0].length);
        //the first Nk column with the actual key
        for( r=0 ; r<Nb; r++)
            for( c=0; c<Nk; c++)
                Key[r][c]=w[r][c];
        //fill the other Nb*(Nr+1) column of the key
        for( r = Nk ; r < Nb*(Nr+1) ; r++)//Nr Rounds
        {
            //create temp
            for(c = 0; c<Nb ;c++)
                temp[c]=Key[c][r];
            if( r%Nk == 0 )//the first of Nk column
            {
                temp =SubWord(RotWord(temp)) ;
                for( c=0 ; c<Nb ; c++ )
                    temp[c]=(byte)(temp[c] ^ RCon[c][(r/Nk)-1]);
            }
            else if ( Nk > 6 && (r%Nk) == 4)
            {
                temp = SubWord(temp);
            }
            //put the result in key column
            for(c =0 ; c< Nb ; c++)
                Key[c][r]=(byte)( Key[c][r-Nk] ^ temp[c]);

        }//end for R*/

        //To DNA
        byte[]k = GetBlock(Key);
        byte[]nk = new byte[4*k.length];
        int i,j,m=0;
        byte[]d = new byte[4];
        for(r = 0 ; r< 4*k.length ; r+=4)
        {
            d = DNA(k[m++]);
            for(c=0 ; c<4;c++)
                nk[r+c] = d[c] ;
        }
        return nk;
    }
    private byte[] DNA(byte b)
    {
        String c1,c2,c3,c4;
        String  n = Integer.toBinaryString(b);
        String bin;
        //System.out.println(n+" length = "+n.length()+"   ");
        if(n.length() == 8)
            bin = n ;
        else if(n.length()<8)
        {
            bin = "0" ;
            int leng = 8 - n.length() ;
            for(int j = 1 ; j < leng ; j++)
                bin = bin.concat("0");
            bin = bin.concat(n);
        }
        else
            bin = n.substring(n.length()-8);
        // System.out.println(String.format("%02x", b)+ " = " +bin);

        c1 = bin.substring(0,2);
        c2 = bin.substring(2,4);
        c3 = bin.substring(4,6);
        c4 = bin.substring(6,8);

        if (c1.equals("00") )
            c1 = "A";
        else if (c1.equals("01"))
            c1 = "C" ;
        else if (c1.equals("10"))
            c1 = "G" ;
        else if(c1.equals("11"))
            c1 = "T" ;
        if (c2.equals("00") )
            c2 = "A";
        else if (c2.equals("01"))
            c2 = "C" ;
        else if (c2.equals("10"))
            c2 = "G" ;
        else if(c2.equals("11"))
            c2 = "T" ;
        if (c3.equals("00") )
            c3 = "A";
        else if (c3.equals("01"))
            c3 = "C" ;
        else if (c3.equals("10"))
            c3 = "G" ;
        else if(c3.equals("11"))
            c3 = "T" ;
        if (c4.equals("00") )
            c4 = "A";
        else if (c4.equals("01"))
            c4 = "C" ;
        else if (c4.equals("10"))
            c4 = "G" ;
        else if(c4.equals("11"))
            c4 = "T" ;
        bin = c1.concat(c2).concat(c3).concat(c4);
        //System.out.println(bin);
        byte[] value = bin.getBytes() ;
        return value;


    }
}
