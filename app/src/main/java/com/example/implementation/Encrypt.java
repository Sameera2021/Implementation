package com.example.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encrypt {
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

    private byte[][] GetState(byte[] in)
    {
        int Nk = in.length/4;
        byte [][] out = new byte[4][Nk];
        int c,r,i=0;
        for(c =0 ; c< 4 ;c++)
            for(r = 0 ;r< Nk ; r++)
                out[r][c] = in[i++];
        return out;
    }
    private byte[] GetBlock(byte[][] in )
    {
        byte[] out = new byte[16];
        int r,c,i=0;
        for( r = 0 ; r < 4 ; r++)
            for(c =0 ;c < 4 ; c++ )
                out[i++] = in [r][c];
        return out;
    }

    private byte[] SubDNA (byte[] in)
    {
        String[][] dsbox = {{"CGAT", "CTTA", "CTCT", "CTGT", "TTAG", "CGGT", "CGTT", "TACC", "ATAA", "AAAC", "CGCT", "AGGT", "TTTG", "TCCT", "GGGT", "CTCG" },
                {"TAGG", "GAAG", "TAGC", "CTTC", "TTGG", "CCGC", "CACT", "TTAA", "GGTC", "TCCA", "GGAG", "GGTT", "GCTA", "GGCA", "CTAG", "TAAA"},
                {"GTCT", "TTTC", "GCAT", "AGCG", "ATCG", "ATTT", "TTCT", "TATA", "ATCA", "GGCC", "TGCC", "TTAC", "CTAC", "TCGA", "ATAC", "ACCC" },
                {"AACA", "TACT", "AGAT", "TAAT", "ACGA", "GCCG", "AACC", "GCGG", "AACT", "ACAG", "GAAA", "TGAG", "TGGT", "AGCT", "GTAG", "CTCC" },
                {"AAGC", "GAAT", "AGTA", "ACGG", "ACGT", "CGTG", "CCGG", "GGAA", "CCAG", "ATGT", "TCCG", "GTAT", "AGGC", "TGAT", "AGTT", "GACA" },
                {"CCAT", "TCAC", "AAAA", "TGTC", "AGAA", "TTTA", "GTAC", "CCGT", "CGGG", "TAGT", "GTTG", "ATGC", "CAGG", "CATA", "CCGA", "TATT" },
                {"TCAA", "TGTT", "GGGG", "TTGT", "CAAT", "CATC", "ATAT", "GACC", "CACC", "TTGC", "AAAG", "CTTT", "CCAA", "ATTA", "GCTT", "GGGA" },
                {"CCAC", "GGAT", "CAAA", "GATT", "GCAG", "GCTC", "ATGA", "TTCC", "GTTA", "GTCG", "TCGG", "AGAC", "ACAA", "TTTT", "TTAT", "TCAG" },
                {"TATC", "AATA", "ACAT", "TGTA", "CCTT", "GCCT", "CACA", "ACCT", "TACA", "GGCT", "CTTG", "ATTC", "CGCA", "CCTC", "ACGC", "CTAT" },
                {"CGAA", "GAAC", "CATT", "TCTA", "AGAG", "AGGG", "GCAA", "GAGA", "CACG", "TGTG", "GTGA", "ACCA", "TCTG", "CCTG", "AAGT", "TCGT" },
                {"TGAA", "ATAG", "ATGG", "AAGG", "CAGC", "AACG", "AGCA", "CCTA", "TAAG", "TCAT", "GGTA", "CGAG", "GCAC", "GCCC", "TGCA", "CTGC" },
                {"TGCT", "TAGA", "ATCT", "CGTC", "GATC", "TCCC", "CATG", "GGGC", "CGTA", "CCCG", "TTCA", "TGGG", "CGCC", "CTGG", "GGTG", "AAGA" },
                {"GTGG", "CTGA", "AGCC", "AGTG", "ACTA", "GGCG", "GTCA", "TACG", "TGGA", "TCTC", "CTCA", "ACTT", "CAGT", "GTTC", "GAGT", "GAGG" },
                {"CTAA", "ATTG", "GTCC", "CGCG", "CAGA", "AAAT", "TTCG", "AATG", "CGAC", "ATCC", "CCCT", "GTGC", "GACG", "TAAC", "ACTC", "GCTG" },
                {"TGAC", "TTGA", "GCGA", "ACAC", "CGGC", "TCGC", "GATG", "GCCA", "GCGT", "ACTG", "GACT", "TGGC", "TATG", "CCCC", "AGGA", "TCTT" },
                {"GATA", "GGAC", "GAGC", "AATC", "GTTT", "TGCG", "CAAG", "CGGA", "CAAC", "GCGC", "AGTC", "AATT", "GTAA", "CCCA", "GTGT", "ACCG" }};

        String[] temp = {"AA","AC","AG","AT","CA","CC","CG","CT","GA","GC","GG","GT","TA","TC","TG","TT",};

        String  str ,x1,x2;
        byte[] t = new byte[4];
        int i,j ,r=0,c=0, m=0;
        //System.out.println("\nInsid the function :");

        for(i = 0 ; i< 64 ; i+=4)
        {
            for(j=0 ;j<4 ; j++)
            {
                t[j] = in[i+j] ;
                //System.out.print(t[j]+"  ");
            }
            //System.out.println();
            str = new String(t);
          //  System.out.println(str);
            //convert DNA string to int to find raw and col
            x1 = str.substring(0,2);
            x2=str.substring(2,4) ;
            //System.out.println(x1+"   "+x2);
            //search on the x1 inside the dna 2byte array(temp)
            for(j=0;j<16;j++)
            {
                if(x1.equals(temp[j]))
                { r = j;
                    break ;
                }
            }
            //search on the x1 inside the dna 2byte array(temp)
            for(j=0;j<16;j++)
            {
                if(x2.equals(temp[j]))
                { c = j;
                    break ;
                }
            }//we have r and c to substute
            str = dsbox[r][c];
            //System.out.println("After subsutation "+ str);
            //put the substute of 4 dna in 4 byte
            t = str.getBytes();
            //System.out.print("The sub in byte = ");
            //for(j=0;j<4;j++)
               // System.out.print(t[j]+"  ");
            //put the 4 byte in the array in
            for(j=0;j<4;j++)
                in[i+j] = t[j];
        }//end sub
   //     System.out.println("The result : ");
     //   for( i=0 ; i<64 ;i++)
//            System.out.print("(byte)"+in[i]+ ",");
        return in;
    }

    private byte [] Sub(byte[] in )
    {
        byte [][] s = GetState(in);
        String str,x1,x2;
        int r,c,i,j;
        for( i =0 ; i <4 ; i++ )
            for( j = 0 ;j <4 ; j++)
            {
                str = String.format("%02x",s[i][j]);
                x1 = str.substring(0,1);
                x2 = str.substring(1,2);
                r = Integer.parseInt(x1,16);
                c = Integer.parseInt(x2 , 16);
                s[i][j] = sbox [r][c];
            }
        byte[] out = GetBlock(s);
        return out ;
    }

    public byte[] EncDNA(byte b)
    {
        String c1,c2,c3,c4,bin;
        String n = Integer.toBinaryString(b);
        if(n.length() == 8)
            bin = n;
        else if(n.length() < 8 ){
            bin = "0";
            for ( int j = 1 ; j < 8 - n.length() ; j++)
                bin = bin.concat("0");
            bin = bin.concat(n);
        }
        else
            bin = n.substring( n.length()-8 );
        c1 = bin.substring(0,2);
        c2 = bin.substring(2,4);
        c3 = bin.substring(4,6);
        c4 = bin.substring(6,8);
        if(c1.equals("00") )
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
        //complement c1
        if(c1.equals("A"))
            c1 = "T" ;
        else if(c1.equals("T"))
            c1 = "A";
        else if(c1.equals("C"))
            c1 = "G";
        else if(c1.equals("G"))
            c1 ="C" ;
        //complement c2
        if(c2.equals("A"))
            c2 = "T" ;
        else if(c2.equals("T"))
            c2 = "A";
        else if(c2.equals("C"))
            c2 = "G";
        else if(c2.equals("G"))
            c2 ="C" ;
        //complement c3
        if(c3.equals("A"))
            c3 = "T" ;
        else if(c3.equals("T"))
            c3 = "A";
        else if(c3.equals("C"))
            c3 = "G";
        else if(c3.equals("G"))
            c3 ="C" ;
        //complement c4
        if(c4.equals("A"))
            c4 = "T" ;
        else if(c4.equals("T"))
            c4 = "A";
        else if(c4.equals("C"))
            c4 = "G";
        else if(c4.equals("G"))
            c4 ="C" ;



        bin = c1.concat(c2).concat(c3).concat(c4);
        System.out.println(bin);
        byte[] value = bin.getBytes() ;
        return value;
    }
    /* private byte[] RoundKey(byte[] in , byte[][] word)
     {
         byte [][] s = GetState(in);
         byte[][] key = new byte[4][4];
         int i,j,r,c,round;
         for(round = 0 ; round<= 14 ;round ++)
         {
             for(i=0 ; i<4 ;i++)
                 for(j =0 ;j<4 ; j++)
                     key[i][j] =word[i][(round*4)+j];
                 //s = AddKey(s,key);
             for(r=0 ; r<4 ;r++)
                 for(c =0 ;c<4 ; c++)
                     s[r][c] = (byte)(s[r][c] ^ key[r][c]);
         }
         byte[] out = GetBlock(s);
         return out;
     }*/
   /*private byte[] RoundKey(byte[] in , byte[][] word)
   {

       byte[][] key = new byte[4][4];
       int i,j,r,c,round;
       for(round = 0 ; round<= 14 ;round ++)
       {
           for(i=0 ; i<4 ;i++)
               for(j =0 ;j<4 ; j++)
                   key[i][j] =word[i][(round*4)+j];
           byte [] k = GetBlock(key);
          in= AddKey(in,k);

       }

       return in;
   }
    private byte[] AddKey(byte[] s , byte[] key )
    {
        //byte[][] s = GetState(in);
        int i,j ;
        for(i = 0 ; i<16 ; i++)
            //for(j = 0 ;j<4 ;j++)
                s[i] =(byte) (s[i]^key[i]) ;
        //byte[] out =GetBlock(s);
        return s;
    }*/
    /*private byte[] AddRoundKey(byte[] in , byte[][] word)
    {

        byte[][] key = new byte[4][4];
        int i,j,r,c,round;
        int Nr = (word[0].length/4) -1 ;
        for(round = 0 ; round<= Nr ;round ++)
        {
            for(i=0 ; i<4 ;i++)
                for(j =0 ;j<4 ; j++)
                    key[i][j] =word[i][(round*4)+j];
            byte [] k = GetBlock(key);
            byte[] nk = new byte[64];
            int m =0;
            byte[] temp = new byte[4];
            for(r = 0 ; r< 64 ; r+=4)
            {
                temp = EncDNA(k[m++]);
                for(c=0 ; c<4;c++)
                    nk[r+c] = temp[c] ;
            }

            in= AddKey(in,nk);

        }

        return in;
    }
    private byte[] AddKey(byte[] s , byte[] key )
    {
        //byte[][] s = GetState(in);
        int i,j ;
//        for(i = 0 ; i<64 ; i++)
//            //for(j = 0 ;j<4 ;j++)
//        {//s[i] =(byte) (s[i]^key[i]) ;
//
//        System.out.println(s[i]+"      "+Integer.toBinaryString(s[i]));
//        }
        //byte[] out =GetBlock(s);
        for(i=0;i<64;i++)
            if(s[i]== key[i])
                s[i]=65;
            else if ( (s[i] == 65 && key[i]==84)|| (s[i] == 67 && key[i] == 71) ||
                    (s[i] == 71 && key[i]==67)|| (s[i] == 84 && key[i] == 65) )
                s[i] = 84 ;
            else if (s[i]== 65 || key[i] == 65)//One operand is A then the output is the other oprand
            {
                if(s[i]== 65)
                    s[i] = key [i];
                else
                    s[i] = s[i];
            }
            else if(s[i]== 84 || key[i] == 84)//One operand is T then the output is the inverse other oprand
            {
                if(s[i] == 84)
                {
                    if(key[i] == 65)
                        s[i] = 84 ;
                    else if (key[i] == 67)
                        s[i] = 71 ;
                    else if(key [i] == 71)
                        s[i] = 67;
                    else
                        s[i] = 65 ;
                }
                else
                {
                    if(s[i] == 65)
                        s[i] = 84 ;
                    else if (s[i] == 67)
                        s[i] = 71 ;
                    else if(s [i] == 71)
                        s[i] = 67;
                    else
                        s[i] = 65 ;
                }
            }

        return s;
    }*/

    private byte[] AddRoundKey(byte[] in , byte[] word)
    {

        byte[] key = new byte[64];
        int i,j,r,c,round;
        int Nr = (word.length/64) -1 ;
        for(round = 0 ; round<= Nr ;round ++)
        {
            in = SubDNA(in);
            for(i=0 ; i<64 ;i++)
                key[i] =word[(round*64)+i];

            for(i = 0 ; i<64 ; i++)
                if(in[i] == key[i])
                    in[i] = 65 ;
                else if ( (in[i] == 65 && key[i]==84)|| (in[i] == 67 && key[i] == 71) ||
                        (in[i] == 71 && key[i]==67)|| (in[i] == 84 && key[i] == 65) )
                    in[i] = 84 ;
                else if (in[i]== 65 || key[i] == 65)//One operand is A then the output is the other oprand
                {
                    if(in[i]== 65)
                        in[i] = key [i];
                    else
                        in[i] = in[i];
                }
                else if(in[i]== 84 || key[i] == 84)//One operand is T then the output is the inverse other oprand
                {
                    if(in[i] == 84)
                    {
                        if(key[i] == 65)
                            in[i] = 84 ;
                        else if (key[i] == 67)
                            in[i] = 71 ;
                        else if(key [i] == 71)
                            in[i] = 67;
                        else
                            in[i] = 65 ;
                    }
                    else
                    {
                        if(in[i] == 65)
                            in[i] = 84 ;
                        else if (in[i] == 67)
                            in[i] = 71 ;
                        else if(in [i] == 71)
                            in[i] = 67;
                        else
                            in[i] = 65 ;
                    }
                }

            // in= AddKey(in,key);

        }

        return in;
    }

    public boolean encrypt(String fileName,String passWord)
    {
        KeyExpansion KE = new KeyExpansion();
        byte [] word = KE.GetWordKey(passWord.getBytes());
        //byte[] key1 = passWord.substring(0,16).getBytes();
        //byte[] key2 = passWord.substring(16).getBytes();
        File fin = new File(fileName);
        String filePos = "/storage/emulated/0/Application/Encryption";//fin.getParent()+"//Encryption";
        /*if(filePos.substring(0,19).equals("/storage/emulated/0"))
            filePos ="/storage/66DA-E92C/Application/Encryption";
        else
            filePos ="/storage/66DA-E92C/Application/Encryption";*/
        String nameWithExt = fin.getName();
        String str = filePos+"/Enc"+nameWithExt ;
        File fout = new File(str);
        FileInputStream input = null ;
        FileOutputStream output = null ;
        // File fin = new File(fileName);
        //File fout = new File( fin.getParent()+"/Enc"+fin.getName());
        byte [] buffer = new byte[(int)fin.length()];
        byte [] block = new byte[16];
        byte [] test = new byte[64];
        byte[] dna = new byte[4];
        //String [] n = new String[(int)buffer.length];

        try {
            input = new FileInputStream(fin);
            output = new FileOutputStream(fout);
            input.read(buffer);
            int i,j,t,nb;
            if(buffer.length % 16 == 0)
                nb = buffer.length /16 ;
            else
                nb = (buffer.length /16)+1 ;
            for( i = 0 ; i< nb ;i ++)
            {
                for(j = 0 ; j < 16 ; j++)
                    if((16*i)+j >= buffer.length )
                        block[j] = 0 ;
                    else
                        block[j] = buffer[(16*i)+j];

                block = Sub(block) ;
                //block = RoundKey(block,word);
                // block = AddKey(block , key1);
                //block = AddKey(block , key2);
                t = 0;
                for(j = 0 ; j<16 ; j++)
                {
                    dna = EncDNA(block[j]);
                    for(int x=0;x<4;x++)
                        test[t++] = dna[x];
                    //output.write(dna);
                }
                test = AddRoundKey(test , word);
                block = change(test);
                output.write(block);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end finally

    }

    private byte[] change(byte[] t)
    {
        String arr[] = {"AAAA" , "AAAC" , "AAAG" , "AAAT" , "AACA" , "AACC","AACG" , "AACT" , "AAGA" , "AAGC" , "AAGG" ,"AAGT" , "AATA" , "AATC" , "AATG" , "AATT" , "ACAA" , "ACAC" , "ACAG" , "ACAT" , "ACCA" , "ACCC" , "ACCG" , "ACCT" , "ACGA" ,"ACGC" , "ACGG" , "ACGT" , "ACTA" , "ACTC" , "ACTG" , "ACTT" ,
                "CAAA" , "CAAC" , "CAAG" , "CAAT" , "CACA" , "CACC" , "CACG" , "CACT" , "CAGA" , "CAGC" , "CAGG" , "CAGT" , "CATA" , "CATC" , "CATG" , "CATT" , "CCAA" , "CCAC" , "CCAG" , "CCAT" , "CCCA" , "CCCC" , "CCCG" , "CCCT" , "CCGA" , "CCGC" ,"CCGG" , "CCGT" , "CCTA" , "CCTC" , "CCTG" , "CCTT" ,
                "GAAA" , "GAAC" , "GAAG" , "GAAT" , "GACA" , "GACC" , "GACG" , "GACT" , "GAGA" , "GAGC" , "GAGG" , "GAGT" , "GATA" , "GATC" , "GATG" , "GATT" , "GCAA" , "GCAC" , "GCAG" , "GCAT" , "GCCA" , "GCCC" , "GCCG" , "GCCT" , "GCGA" , "GCGC" , "GCGG" , "GCGT" , "GCTA" , "GCTC" , "GCTG" , "GCTT" ,
                "TAAA" ,"TAAC" , "TAAG" , "TAAT" , "TACA" , "TACC" , "TACG" , "TACT" , "TAGA" , "TAGC" , "TAGG" , "TAGT" , "TATA" , "TATC" , "TATG" , "TATT" , "TCAA" , "TCAC" , "TCAG" , "TCAT" , "TCCA" , "TCCC" , "TCCG" , "TCCT" , "TCGA" , "TCGC" , "TCGG" , "TCGT" , "TCTA" , "TCTC" , "TCTG" , "TCTT" ,
                "AGAA" , "AGAC" , "AGAG" , "AGAT" , "AGCA" , "AGCC" , "AGCG" , "AGCT" ,"AGGA" , "AGGC" , "AGGG" , "AGGT" , "AGTA" , "AGTC" , "AGTG" , "AGTT" , "ATAA" , "ATAC" , "ATAG" , "ATAT" , "ATCA" ,"ATCC" , "ATCG" , "ATCT" , "ATGA" , "ATGC" , "ATGG" , "ATGT" , "ATTA" , "ATTC" , "ATTG" , "ATTT" ,
                "CGAA" , "CGAC" , "CGAG" , "CGAT" , "CGCA" , "CGCC" , "CGCG" , "CGCT" , "CGGA" , "CGGC" , "CGGG" , "CGGT" , "CGTA" , "CGTC" , "CGTG" ,"CGTT" , "CTAA" , "CTAC" , "CTAG" , "CTAT" , "CTCA" , "CTCC" , "CTCG" , "CTCT" , "CTGA" , "CTGC" , "CTGG" , "CTGT" , "CTTA" , "CTTC" , "CTTG" , "CTTT" ,
                "GGAA" , "GGAC" , "GGAG" , "GGAT" , "GGCA" , "GGCC" , "GGCG" , "GGCT" , "GGGA" , "GGGC" , "GGGG" , "GGGT" , "GGTA" , "GGTC" , "GGTG" , "GGTT" , "GTAA" , "GTAC" , "GTAG" , "GTAT" , "GTCA" , "GTCC" ,"GTCG" , "GTCT" , "GTGA" , "GTGC" , "GTGG" , "GTGT" , "GTTA" , "GTTC" , "GTTG" , "GTTT" ,
                "TGAA" , "TGAC" , "TGAG" ,"TGAT" , "TGCA" , "TGCC" , "TGCG" , "TGCT" , "TGGA" , "TGGC" , "TGGG" , "TGGT" , "TGTA" , "TGTC" , "TGTG" , "TGTT" ,"TTAA" , "TTAC" , "TTAG" , "TTAT" , "TTCA" , "TTCC" , "TTCG" , "TTCT" , "TTGA" , "TTGC" , "TTGG" , "TTGT" , "TTTA" ,"TTTC" , "TTTG" , "TTTT"};
        byte[] result = new byte[16];
        int i ,j,k=0;
        String text = new String(t);
        String [] s = new String[16];
        for(i=0;i<64;i+=4)
        {
            s[k] = text.substring(i,i+4);
            for( j = 0; j <256 ; j++)
            {
                if(s[k].equals(arr[j]))
                    break;
            }
            result[k] = (byte) j ;
            //System.out.println(j);
            k++;
        }
        return result ;
    }
}
