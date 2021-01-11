package com.example.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decrypt {
    private static final byte[][] Invsbox = {{(byte)0x52, (byte)0x09, (byte)0x6a, (byte)0xd5, (byte)0x30,(byte) 0x36,(byte) 0xa5, (byte)0x38, (byte)0xbf, (byte)0x40, (byte)0xa3, (byte)0x9e, (byte)0x81, (byte)0xf3, (byte)0xd7, (byte)0xfb},
            {(byte)0x7c, (byte)0xe3, (byte)0x39, (byte)0x82, (byte)0x9b, (byte)0x2f, (byte)0xff, (byte)0x87, (byte)0x34, (byte)0x8e, (byte)0x43, (byte)0x44, (byte)0xc4, (byte)0xde, (byte)0xe9, (byte)0xcb},
            {(byte)0x54, (byte)0x7b, (byte)0x94, (byte)0x32, (byte)0xa6, (byte)0xc2, (byte)0x23, (byte)0x3d, (byte)0xee, (byte)0x4c, (byte)0x95, (byte)0x0b, (byte)0x42, (byte)0xfa, (byte)0xc3, (byte)0x4e},
            {(byte)0x08, (byte)0x2e, (byte)0xa1, (byte)0x66, (byte)0x28, (byte)0xd9, (byte)0x24, (byte)0xb2, (byte)0x76, (byte)0x5b, (byte)0xa2, (byte)0x49, (byte)0x6d, (byte)0x8b, (byte)0xd1, (byte)0x25},
            {(byte)0x72, (byte)0xf8, (byte)0xf6, (byte)0x64, (byte)0x86, (byte)0x68, (byte)0x98, (byte)0x16, (byte)0xd4, (byte)0xa4, (byte)0x5c, (byte)0xcc, (byte)0x5d, (byte)0x65, (byte)0xb6, (byte)0x92},
            {(byte)0x6c, (byte)0x70, (byte)0x48, (byte)0x50, (byte)0xfd, (byte)0xed, (byte)0xb9, (byte)0xda, (byte)0x5e, (byte)0x15, (byte)0x46, (byte)0x57, (byte)0xa7, (byte)0x8d, (byte)0x9d, (byte)0x84},
            {(byte)0x90, (byte)0xd8, (byte)0xab, (byte)0x00, (byte)0x8c, (byte)0xbc, (byte)0xd3, (byte)0x0a, (byte)0xf7, (byte)0xe4, (byte)0x58, (byte)0x05, (byte)0xb8, (byte)0xb3, (byte)0x45, (byte)0x06},
            {(byte)0xd0, (byte)0x2c, (byte)0x1e, (byte)0x8f, (byte)0xca, (byte)0x3f, (byte)0x0f, (byte)0x02, (byte)0xc1, (byte)0xaf, (byte)0xbd, (byte)0x03, (byte)0x01, (byte)0x13, (byte)0x8a, (byte)0x6b},
            {(byte)0x3a, (byte)0x91, (byte)0x11, (byte)0x41, (byte)0x4f, (byte)0x67, (byte)0xdc, (byte)0xea, (byte)0x97, (byte)0xf2, (byte)0xcf, (byte)0xce, (byte)0xf0, (byte)0xb4, (byte)0xe6, (byte)0x73},
            {(byte)0x96, (byte)0xac, (byte)0x74, (byte)0x22, (byte)0xe7, (byte)0xad, (byte)0x35, (byte)0x85, (byte)0xe2, (byte)0xf9, (byte)0x37, (byte)0xe8, (byte)0x1c, (byte)0x75, (byte)0xdf, (byte)0x6e},
            {(byte)0x47, (byte)0xf1, (byte)0x1a, (byte)0x71, (byte)0x1d, (byte)0x29, (byte)0xc5, (byte)0x89, (byte)0x6f, (byte)0xb7, (byte)0x62, (byte)0x0e, (byte)0xaa, (byte)0x18, (byte)0xbe, (byte)0x1b},
            {(byte)0xfc, (byte)0x56, (byte)0x3e, (byte)0x4b, (byte)0xc6, (byte)0xd2, (byte)0x79, (byte)0x20, (byte)0x9a, (byte)0xdb, (byte)0xc0, (byte)0xfe, (byte)0x78, (byte)0xcd, (byte)0x5a, (byte)0xf4},
            {(byte)0x1f, (byte)0xdd, (byte)0xa8, (byte)0x33, (byte)0x88, (byte)0x07, (byte)0xc7, (byte)0x31, (byte)0xb1, (byte)0x12, (byte)0x10, (byte)0x59, (byte)0x27, (byte)0x80, (byte)0xec, (byte)0x5f},
            {(byte)0x60, (byte)0x51, (byte)0x7f, (byte)0xa9, (byte)0x19, (byte)0xb5, (byte)0x4a, (byte)0x0d, (byte)0x2d, (byte)0xe5, (byte)0x7a, (byte)0x9f, (byte)0x93, (byte)0xc9, (byte)0x9c, (byte)0xef},
            {(byte)0xa0, (byte)0xe0, (byte)0x3b, (byte)0x4d, (byte)0xae, (byte)0x2a, (byte)0xf5, (byte)0xb0, (byte)0xc8, (byte)0xeb, (byte)0xbb, (byte)0x3c, (byte)0x83, (byte)0x53, (byte)0x99, (byte)0x61},
            {(byte)0x17, (byte)0x2b, (byte)0x04, (byte)0x7e, (byte)0xba, (byte)0x77, (byte)0xd6, (byte)0x26, (byte)0xe1, (byte)0x69, (byte)0x14, (byte)0x63, (byte)0x55, (byte)0x21, (byte)0x0c, (byte)0x7d}};

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
    }private byte[] InvSubDNA (byte[] in)
    {
        String [][] dInvsbox = { {"CCAG", "AAGC", "CGGG", "TCCC", "ATAA", "ATCG", "GGCC", "ATGA", "GTTT", "CAAA", "GGAT", "GCTG", "GAAC", "TTAT", "TCCT", "TTGT"},
                {"CTTA", "TGAT", "ATGC", "GAAG", "GCGT", "AGTT", "TTTT", "GACT", "ATCA", "GATG", "CAAT", "CACA", "TACA", "TCTG", "TGGC", "TAGT" },
                {"CCCA", "CTGT", "GCCA", "ATAG", "GGCG", "TAAG", "AGAT", "ATTC", "TGTG", "CATA", "GCCC", "AAGT", "CAAG", "TTGG", "TAAT", "CATG" },
                {"AAGA", "AGTG", "GGAC", "CGCG", "AGGA", "TCGC", "AGCA", "GTAG", "CTCG", "CCGT", "GGAG", "CAGC", "CGTC", "GAGT", "TCAC", "AGCC" },
                {"CTAG", "TTGA", "TTCG", "CGCA", "GACG", "CGGA", "GCGA", "ACCG", "TCCA", "GGCA", "CCTA", "TATA", "CCTC", "CGCC", "GTCG", "GCAG" },
                {"CGTA", "CTAA", "CAGA", "CCAA", "TTTC", "TGTC", "GTGC", "TCGG", "CCTG", "ACCC", "CACG", "CCCT", "GGCT", "GATC", "GCTC", "GACA" },
                {"GCAA", "TCGA", "GGGT", "AAAA", "GATA", "GTTA", "TCAT", "AAGG", "TTCT", "TGCA", "CCGA", "AACC", "GTGA", "GTAT", "CACC", "AACG" },
                {"TCAA", "AGTA", "ACTG", "GATT", "TAGG", "ATTT", "AATT", "AAAG", "TAAC", "GGTT", "GTTC", "AAAT", "AAAC", "ACAT", "GAGG", "CGGT" },
                {"ATGG", "GCAC", "ACAC", "CAAC", "CATT", "CGCT", "TCTA", "TGGG", "GCCT", "TTAG", "TATT", "TATG", "TTAA", "GTCA", "TGCG", "CTAT" },
                {"GCCG", "GGTA", "CTCA", "AGAG", "TGCT", "GGTC", "ATCC", "GACC", "TGAG", "TTGC", "ATCT", "TGGA", "ACTA", "CTCC", "TCTT", "CGTG" },
                {"CACT", "TTAC", "ACGG", "CTAC", "ACTC", "AGGC", "TACC", "GAGC", "CGTT", "GTCT", "CGAG", "AATG", "GGGG", "ACGA", "GTTG", "ACGT" },
                {"TTTA", "CCCG", "ATTG", "CAGT", "TACG", "TCAG", "CTGC", "AGAA", "GCGG", "TCGT", "TAAA", "TTTG", "CTGA", "TATC", "CCGG", "TTCA" },
                {"ACTT", "TCTC", "GGGA", "ATAT", "GAGA", "AACT", "TACT", "ATAC", "GTAC", "ACAG", "ACAA", "CCGC", "AGCT", "GAAA", "TGTA", "CCTT" },
                {"CGAA", "CCAC", "CTTT", "GGGC", "ACGC", "GTCC", "CAGG", "AATC", "AGTC", "TGCC", "CTGG", "GCTT", "GCAT", "TAGC", "GCTA", "TGTT" },
                {"GGAA", "TGAA", "ATGT", "CATC", "GGTG", "AGGG", "TTCC", "GTAA", "TAGA", "TGGT", "GTGT", "ATTA", "GAAT", "CCAT", "GCGC", "CGAC" },
                {"ACCT", "AGGT", "AACA", "CTTG", "GTGG", "CTCT", "TCCG", "AGCG", "TGAC", "CGGC", "ACCA", "CGAT", "CCCC", "AGAC", "AATA", "CTTC" }};


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
            str = dInvsbox[r][c];
            //System.out.println("After subsutation "+ str);
            //put the substute of 4 dna in 4 byte
            t = str.getBytes();
            //System.out.print("The sub in byte = ");
            //for(j=0;j<4;j++)
              //  System.out.print(t[j]+"  ");
            //put the 4 byte in the array in
            for(j=0;j<4;j++)
                in[i+j] = t[j];
        }//end sub
        //System.out.println("The  Inverse result : ");
        //for( i=0 ; i<64 ;i++)
           // System.out.print(in[i]+"   ");
    return in ;
    }
    private byte [] InvSub(byte[] in )
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
                s[i][j] = Invsbox [r][c];
            }
        byte[] out = GetBlock(s);
        return out ;
    }

   /* private byte[] InvRoundKey(byte[] in , byte[][] word)
    {
        byte [][] s = GetState(in);
        byte[][] key = new byte[4][4];
        int r,c,round;
        for(round = 0 ; round<= 14 ;round ++)
        {
            for(r=0 ; r<4 ;r++)
                for(c =0 ;c<4 ; c++)
                    key[r][c] =word[r][(round*4)+c];
            for(r=0 ; r<4 ;r++)
                for(c =0 ;c<4 ; c++)
                    s[r][c] = (byte)(s[r][c] ^ key[r][c]);
        }
        byte[] out = GetBlock(s);
        return out;
    }*/


    private byte DecDNA ( byte [] b )
    {

        String n = new String(b);
        System.out.println(n);
        String c1,c2,c3,c4,bin;
        int num ;
        c1 = n.substring(0 , 1);
        c2 = n.substring(1 , 2);
        c3 = n.substring(2 , 3);
        c4 = n.substring(3 , 4);
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

        if(c1.equals("A"))
            c1="00";
        else if(c1.equals("C"))
            c1="01";
        else if(c1.equals("G"))
            c1 = "10";
        else if (c1.equals("T"))
            c1 = "11";
        if(c2.equals("A"))
            c2="00";
        else if(c2.equals("C"))
            c2="01";
        else if(c2.equals("G"))
            c2 = "10";
        else if (c2.equals("T"))
            c2 = "11";
        if(c3.equals("A"))
            c3="00";
        else if(c3.equals("C"))
            c3="01";
        else if(c3.equals("G"))
            c3 = "10";
        else if (c3.equals("T"))
            c3 = "11";
        if(c4.equals("A"))
            c4="00";
        else if(c4.equals("C"))
            c4="01";
        else if(c4.equals("G"))
            c4 = "10";
        else if (c4.equals("T"))
            c4 = "11";
        n= c1.concat(c2).concat(c3).concat(c4);
        if(n.substring(0,1).equals("1"))
        {
            bin="0";
            for(int i = 1;i<n.length() ; i++ )
                if(n.substring(i, i+1).equals("1"))
                    bin = bin.concat("0");
                else
                    bin = bin.concat("1");
            num = -1*(Integer.parseInt(bin,2)+1);
        }
        else
            num = Integer.parseInt(n, 2);
        byte t = (byte) num ;
        return (t) ;
    }
    /*    private byte[] RoundKey(byte[]in , byte[][] word)
        {
            byte[][] s = GetState(in);
            byte[][] key = new byte[4][4];
            int i,j,r,c;
            for (int round = 0 ; round < 14 ; round++)
            {
                for( i=0 ; i<4 ; i++ )
                    for( j=0 ; j<4 ; j++ )
                        key[i][j] = word[i][((round)*4)+j];

                for(r = 0;r< 4 ; r++)
                    for(c=0;c<4;c++)
                        s[r][c]= (byte)(s[r][c]^key[r][c]);
                //s = AddKey(s,key);
            }
            byte [] out = GetBlock(s);
            return out ;
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
        Encrypt E = new Encrypt();
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
                temp = E.EncDNA(k[m++]);
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
//        for(i = 0 ; i<16 ; i++)
//            //for(j = 0 ;j<4 ;j++)
//            s[i] =(byte) (s[i]^key[i]) ;
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
        for(round = Nr ; round>= 0 ;round --)
        {
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
            in =InvSubDNA(in);
            // in= AddKey(in,key);


        }

        return in;
    }

    public boolean decrypt(String fileName,String passWord)
    {
        KeyExpansion KE = new KeyExpansion();
        byte[] word = KE.GetWordKey(passWord.getBytes());
        byte[] key1 = passWord.substring(0,16).getBytes();
        byte[] key2 = passWord.substring(16).getBytes();
        File fin = new File(fileName);
        String filePos =  "/storage/emulated/0/Application/Decryption";//fin.getParent()+"/Decryption";
        String nameWithExt = fin.getName().substring(3);
        String str = filePos+"/"+nameWithExt ;
        File fout = new File(str);

        FileInputStream input = null;
        FileOutputStream output = null ;
        byte[] buffer = new byte[(int) fin.length()];
        byte[] block  = new byte[16];
        byte[] d = new byte[4];
        int i,j,t,m=0;
        byte [] test = new byte[64];
        //String[] n = new String[buffer.length];
        try{
            input = new FileInputStream(fin);
            output = new FileOutputStream(fout);
            input.read(buffer);
            //Decrypt
            /*int nb ;
            if(buffer.length % 16 ==0)
                nb = buffer.length /16 ;
            else
                nb = (buffer.length / 16 )+ 1;*/
            for( i =0 ; i<buffer.length ; i+=16 )
            {
                for(j = 0 ; j<16 ; j++)
                    block[j] = buffer[i+j];

                test = change(block);

                test = AddRoundKey(test,word);
                for(t = 0 ; t<64 ; t+=4)
                { for(j = 0 ;j<4;j++)
                    d[j]=test[t+j];
//                for( j =0 ;j<4 ;j++)
//                    d[j] = buffer[i+j];
//                byte w = ED.DecByte(d);
//                block[m] = w ;
                    byte dna = DecDNA(d);
                    block[m]=dna;
                    m++;
                    if(m % 16 == 0)
                    {
                        m=0;
                        //block = InvAddRoundKey(block, key);
                        block = InvSub(block);
//                    byte[][] s = ED.InvRound(GetByte.GetState(block), key);
//                    s = ED.InvSub(s);
//                    block = GetByte.GetBlock(s);

                        output.write(block);
                    }
                }
                /*for(j=0 ; j<16 ; j++)
                {
                    if((16*i+j)>= buffer.length)
                        block[j]=0;
                    else
                        block[j]=buffer[(16*i)+j];
                }
                block = RoundKey(block,word);
                block = InvSub(block);
                output.write(block);*/

            }

            return true ;



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

        int i,j,k=0;
        String str;
        String [] s = new String[16];
        byte[] b = new byte[4];
        byte[] result = new byte[64];

        for(i=0;i<16;i++)
        {
            str = Integer.toBinaryString(t[i]);
            if(str.length()>8)//if( Integer.toBinaryString(text.charAt(i)).length() > 8 )
            {
                str = str.substring(str.length()-8, str.length());
            }
            s[i] = arr[Integer.parseInt(str,2)];
        }
        for(i = 0 ; i<64 ; i+=4)
        {
            b = s[k].getBytes();
            for(j=0;j<4;j++)
                result[i+j] = b[j];
            k++ ;
        }
        //for(i = 0 ;i<64 ;i++)
        //  System.out.println(result[i]);

        return result ;
    }
}
